package models
//import models.extensions.LicenseRatingsExtension
import extensions._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import play.api.data.Form
import play.api.data.Forms._
//support for joda is now a separate project
import play.api.data.JodaForms._
import play.api.data.format.Formats._
import org.joda.time.{DateTime, LocalDate, DateTimeZone}
//import com.github.tototoshi.slick.H2JodaSupport._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._
//import scala.concurrent.ExecutionContext.Implicits.global

/*import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._


import play.api.libs.json._*/



case class LicenseRatings(id: Option[Long] = None /*None*/,
                          licenseRatings: String = "" /*None*/) extends LicenseRatingsExtension{

}

object LicenseRatings {
  implicit val format = Json.format[LicenseRatings]
  val tupled = (this.apply _).tupled
}




case class LicenseRatingsFormData(obj: LicenseRatings){
  def update(updatedObj: LicenseRatings = obj)(implicit repo: LicenseRatingsRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: LicenseRatings)(implicit repo: LicenseRatingsRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object LicenseRatingsFormData{
  def fapply(obj: LicenseRatings)(implicit ec: ExecutionContext) = {
    Future{
      new LicenseRatingsFormData(obj)
    }
  }
}
object LicenseRatingsForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "licenseRatings" -> nonEmptyText
    )/*(LicenseRatings.apply)(LicenseRatings.unapply)*/
    ((id,licenseRatings) => {
      LicenseRatingsFormData(LicenseRatings(id, licenseRatings))
    })((formData: LicenseRatingsFormData) => {
      Some((formData.obj.id, formData.obj.licenseRatings))
    })
  )
}