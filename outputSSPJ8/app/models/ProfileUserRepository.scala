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
class ProfileUserRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, profileRepo: ProfileRepository, userRepo: UserRepository)(implicit ec: ExecutionContext) extends DatabaseClient[ProfileUser] with ProfileUserQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._


  class ProfileUserTable(tag: Tag) extends Table[ProfileUser](tag, "profile_user") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def profileId = column[Long]("profile_id", O.Default(0))
    def userId = column[Long]("user_id", O.Default(0))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, profileId, userId).shaped <> (ProfileUser.tupled, ProfileUser.unapply)
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = ProfileUserTable
  val all = TableQuery[ProfileUserTable]

  def byProfileId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.profileId===id).result
  }}.getOrElse{Future(List())}
                            

  def byUserId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.userId===id).result
  }}.getOrElse{Future(List())}
                            


  def getProfile(obj: ProfileUser) = profileRepo.byId(obj.profileId)
  def getUser(obj: ProfileUser) = userRepo.byId(obj.userId)
}