package models
import models.extensions.UserProfileExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.libs.json._

case class UserProfile(id: Option[Long] = None /*None*/,
                       profileId: Long = 0 /*None*/,
                       userId: Long = 0 /*None*/) extends UserProfileExtension{
  implicit val jsonFormat = Json.format[UserProfile]
  def toJson = Json.toJson(this)
         

  lazy val selectString = id
  def getProfile(implicit session: Session) = ProfileQuery.byId(profileId)
  def getUser(implicit session: Session) = UserQuery.byId(userId)
}



class UserProfiles(tag: Tag) extends Table[UserProfile](tag, "user_profile") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def profileId = column[Long]("profile_id", O.Default(0))
  def profile = foreignKey("profile_id_fk", profileId, ProfileQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def userId = column[Long]("user_id", O.Default(0))
  def user = foreignKey("user_id_fk", userId, UserQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)

  def * = (id.?, profileId, userId).shaped <> (UserProfile.tupled, UserProfile.unapply)
}

class UserProfileQueryBase extends BaseDAO[UserProfile] {
  type DBTable = UserProfileMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byProfileId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.profileId===i).list
    }.getOrElse(List())
  }
                            

  def byUserId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.userId===i).list
    }.getOrElse(List())
  }
                            
}
case class UserProfileFormData(obj: UserProfile){
  def update(updatedObj: UserProfile = obj)(implicit session: Session) = {

    UserProfileQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: UserProfile)(implicit session: Session) = {
    val id = UserProfileQuery.insert(insertedObj)

    id
  }
}

object UserProfileForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "profileId" -> longNumber,
      "userId" -> optional(longNumber)
    )/*(UserProfile.apply)(UserProfile.unapply)*/
    ((id,profileId,userId) => {
      UserProfileFormData(UserProfile(id, profileId, userId.getOrElse(0)))
    })((formData: UserProfileFormData) => {
      Some((formData.obj.id, formData.obj.profileId, Some(formData.obj.userId)))
    })
  )
}