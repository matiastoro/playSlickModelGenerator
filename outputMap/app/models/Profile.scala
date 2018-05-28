package models
import models.extensions.ProfileExtension
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import play.api.data.Form
import play.api.data.Forms._
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

case class Profile(id: Option[Long] = None /*None*/,
                   profile: String = "" /*None*/) extends ProfileExtension{
  lazy val selectString = profile
}

object Profile {
  implicit val format = Json.format[Profile]
  val tupled = (this.apply _).tupled
}




case class ProfileFormData(obj: Profile){
  def update(updatedObj: Profile = obj)(implicit repo: ProfileRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Profile)(implicit repo: ProfileRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}

object ProfileForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "profile" -> text
    )/*(Profile.apply)(Profile.unapply)*/
    ((id,profile) => {
      ProfileFormData(Profile(id, profile))
    })((formData: ProfileFormData) => {
      Some((formData.obj.id, formData.obj.profile))
    })
  )
}