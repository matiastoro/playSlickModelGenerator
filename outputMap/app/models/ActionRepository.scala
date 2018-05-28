package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._

import scala.concurrent.{ Future, ExecutionContext }
import models.extensions._
import org.joda.time.{DateTime, LocalDate}
import com.github.tototoshi.slick.H2JodaSupport._

@Singleton
class ActionRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, userRepo: UserRepository, turnRepo: TurnRepository, characterRepo: CharacterRepository)(implicit ec: ExecutionContext) extends DatabaseClient[Action] with ActionQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._


  class ActionTable(tag: Tag) extends Table[Action](tag, "action") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[Long]("user_id", O.Default(0))
    def turnId = column[Long]("turn_id", O.Default(0))
    def characterId = column[Long]("character_id", O.Default(0))
    def fromQ = column[Int]("from_q", O.Default(0))
    def fromR = column[Int]("from_r", O.Default(0))
    def fromS = column[Int]("from_s", O.Default(0))
    def toQ = column[Int]("to_q", O.Default(0))
    def toR = column[Int]("to_r", O.Default(0))
    def toS = column[Int]("to_s", O.Default(0))
    def createdAt = column[Option[DateTime]]("created_at", O.Default(None))
    def updatedAt = column[Option[DateTime]]("updated_at", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, userId, turnId, characterId, fromQ, fromR, fromS, toQ, toR, toS, createdAt, updatedAt).shaped <> (Action.tupled, Action.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = ActionTable
  val all = TableQuery[ActionTable]

  def byUserId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.userId===id).result
  }}.getOrElse{Future(List())}
                            

  def byTurnId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.turnId===id).result
  }}.getOrElse{Future(List())}
                            

  def byCharacterId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.characterId===id).result
  }}.getOrElse{Future(List())}
                            


  def getUser(obj: Action) = userRepo.byId(obj.userId)
  def getTurn(obj: Action) = turnRepo.byId(obj.turnId)
  def getCharacter(obj: Action) = characterRepo.byId(obj.characterId)
}