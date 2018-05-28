package models
//import models.extensions.LicenseRatingsByExtension
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



case class LicenseRatingsBy(id: Option[Long] = None /*None*/,
                            licenseRatings: String = "" /*None*/) extends LicenseRatingsByExtension{

}

object LicenseRatingsBy {
  implicit val format = Json.format[LicenseRatingsBy]
  val tupled = (this.apply _).tupled
}




case class LicenseRatingsByFormData(obj: LicenseRatingsBy){
  def update(updatedObj: LicenseRatingsBy = obj)(implicit repo: LicenseRatingsByRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: LicenseRatingsBy)(implicit repo: LicenseRatingsByRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}

object LicenseRatingsByForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "licenseRatings" -> nonEmptyText
    )/*(LicenseRatingsBy.apply)(LicenseRatingsBy.unapply)*/
    ((id,licenseRatings) => {
      LicenseRatingsByFormData(LicenseRatingsBy(id, licenseRatings))
    })((formData: LicenseRatingsByFormData) => {
      Some((formData.obj.id, formData.obj.licenseRatings))
    })
  )
}