package models
//import models.extensions.LicenseIssuedByExtension
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



case class LicenseIssuedBy(id: Option[Long] = None /*None*/,
                           licenseIssuedBy: String = "" /*None*/,
                           licenseIssuedById: Option[Long] = None /*None*/) extends LicenseIssuedByExtension{

}

object LicenseIssuedBy {
  implicit val format = Json.format[LicenseIssuedBy]
  val tupled = (this.apply _).tupled
}


case class LicenseIssuedByFormData(obj: LicenseIssuedBy){
  def update(updatedObj: LicenseIssuedBy = obj)(implicit repo: LicenseIssuedByRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: LicenseIssuedBy)(implicit repo: LicenseIssuedByRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object LicenseIssuedByFormData{
  def fapply(obj: LicenseIssuedBy)(implicit ec: ExecutionContext) = {
    Future{
      new LicenseIssuedByFormData(obj)
    }
  }
}
object LicenseIssuedByForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "licenseIssuedBy" -> nonEmptyText,
      "licenseIssuedById" -> optional(longNumber)
    )/*(LicenseIssuedBy.apply)(LicenseIssuedBy.unapply)*/
    ((id,licenseIssuedBy,licenseIssuedById) => {
      LicenseIssuedByFormData(LicenseIssuedBy(id, licenseIssuedBy, licenseIssuedById))
    })((formData: LicenseIssuedByFormData) => {
      Some((formData.obj.id, formData.obj.licenseIssuedBy, formData.obj.licenseIssuedById))
    })
  )
}



case class LicenseIssuedByFilter(id: Option[Long] = None,
                                 licenseIssuedBy: Option[String] = None,
                                 licenseIssuedById: Option[Long] = None)

object LicenseIssuedByFilter {
  val tupled = (this.apply _).tupled
}


object LicenseIssuedByFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "licenseIssuedBy" -> optional(nonEmptyText),
      "licenseIssuedById" -> optional(longNumber)
    )/*(LicenseIssuedByFilter.apply)(LicenseIssuedByFilter.unapply)*/
    ((id,licenseIssuedBy,licenseIssuedById) => {
      LicenseIssuedByFilter(id, licenseIssuedBy, licenseIssuedById)
    })((formData: LicenseIssuedByFilter) => {
      Some((formData.id, formData.licenseIssuedBy, formData.licenseIssuedById))
    })
  )
}

