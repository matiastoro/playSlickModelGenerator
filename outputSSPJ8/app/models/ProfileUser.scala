package models
//import models.extensions.ProfileUserExtension
import extensions._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import play.api.data.Form
import play.api.data.Forms._
//support for joda is now a separate project
import play.api.data.JodaForms._
import play.api.data.format.Formats._
import org.joda.time.{DateTime, LocalDate}
//import com.github.tototoshi.slick.H2JodaSupport._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._

/*import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._


import play.api.libs.json._*/



case class ProfileUser(id: Option[Long] = None /*None*/,
                       profileId: Long = 0 /*None*/,
                       userId: Long = 0 /*None*/) extends ProfileUserExtension{

}

object ProfileUser {
  implicit val format = Json.format[ProfileUser]
  val tupled = (this.apply _).tupled
}




case class ProfileUserFormData(obj: ProfileUser){
  def update(updatedObj: ProfileUser = obj)(implicit repo: ProfileUserRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: ProfileUser)(implicit repo: ProfileUserRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}

object ProfileUserForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "profileId" -> longNumber,
      "userId" -> optional(longNumber)
    )/*(ProfileUser.apply)(ProfileUser.unapply)*/
    ((id,profileId,userId) => {
      ProfileUserFormData(ProfileUser(id, profileId, userId.getOrElse(0)))
    })((formData: ProfileUserFormData) => {
      Some((formData.obj.id, formData.obj.profileId, Some(formData.obj.userId)))
    })
  )
}