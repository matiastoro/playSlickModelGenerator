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
class UserRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, armyRepo: ArmyRepository)(implicit ec: ExecutionContext) extends DatabaseClient[User] with UserQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._


  class UserTable(tag: Tag) extends Table[User](tag, "user") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username", O.Default(""))
    def password = column[String]("password", O.Default(""))
    def name = column[String]("name", O.Default(""))
    def email = column[String]("email", O.Default(""))
    def armyId = column[Option[Long]]("army_id", O.Default(None))
    def army = column[Option[String]]("army", O.Default(None))
    def background = column[Option[String]]("background", O.Default(None))
    def observations = column[Option[String]]("observations", O.Default(None))
    def color = column[Option[String]]("color", O.Default(None))
    def icon = column[Option[String]]("icon", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, username, password, name, email, armyId, army, background, observations, color, icon).shaped <> (User.tupled, User.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = UserTable
  val all = TableQuery[UserTable]

  def byArmyId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.armyId===id).result
  }}.getOrElse{Future(List())}
                            


  def getArmy(obj: User) = if(obj.armyId.isDefined) armyRepo.byId(obj.armyId.get) else None
}