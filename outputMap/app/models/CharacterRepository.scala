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
class CharacterRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, userRepo: UserRepository)(implicit ec: ExecutionContext) extends DatabaseClient[Character] with CharacterQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._


  class CharacterTable(tag: Tag) extends Table[Character](tag, "character") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def character = column[String]("character", O.Default(""))
    def background = column[String]("background", O.Default(""))
    def observations = column[String]("observations", O.Default(""))
    def leader = column[Boolean]("leader", O.Default(false))
    def userId = column[Long]("user_id", O.Default(0))
    def number = column[Int]("number", O.Default(0))
    def q = column[Int]("q", O.Default(0))
    def r = column[Int]("r", O.Default(0))
    def s = column[Int]("s", O.Default(0))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, character, background, observations, leader, userId, number, q, r, s).shaped <> (Character.tupled, Character.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = CharacterTable
  val all = TableQuery[CharacterTable]

  def byUserId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.userId===id).result
  }}.getOrElse{Future(List())}
                            


  def getUser(obj: Character) = userRepo.byId(obj.userId)
}