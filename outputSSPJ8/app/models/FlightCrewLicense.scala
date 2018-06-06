package models
//import models.extensions.FlightCrewLicenseExtension
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



case class FlightCrewLicense(id: Option[Long] = None /*None*/,
                             flightCrewId: Long = 0L /*None*/,
                             licenseTypeId: Long = 0L /*None*/,
                             licenseIssuedById: Option[Long] = None /*None*/,
                             dateOfLicense: Option[DateTime] = None /*None*/,
                             licenseValidityId: Option[Long] = None /*None*/,
                             licenseRatingsId: Option[Long] = None /*None*/) extends FlightCrewLicenseExtension{

}

object FlightCrewLicense {
  implicit val format = Json.format[FlightCrewLicense]
  val tupled = (this.apply _).tupled
}


case class FlightCrewLicenseFormData(obj: FlightCrewLicense){
  def update(updatedObj: FlightCrewLicense = obj)(implicit repo: FlightCrewLicenseRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: FlightCrewLicense)(implicit repo: FlightCrewLicenseRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object FlightCrewLicenseFormData{
  def fapply(obj: FlightCrewLicense)(implicit ec: ExecutionContext) = {
    Future{
      new FlightCrewLicenseFormData(obj)
    }
  }
}
object FlightCrewLicenseForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "flightCrewId" -> longNumber,
      "licenseTypeId" -> longNumber,
      "licenseIssuedById" -> optional(longNumber),
      "dateOfLicense" -> optional(jodaDate("YYYY-MM-dd", DateTimeZone.UTC)),
      "licenseValidityId" -> optional(longNumber),
      "licenseRatingsId" -> optional(longNumber)
    )/*(FlightCrewLicense.apply)(FlightCrewLicense.unapply)*/
    ((id,flightCrewId,licenseTypeId,licenseIssuedById,dateOfLicense,licenseValidityId,licenseRatingsId) => {
      FlightCrewLicenseFormData(FlightCrewLicense(id, flightCrewId, licenseTypeId, licenseIssuedById, dateOfLicense, licenseValidityId, licenseRatingsId))
    })((formData: FlightCrewLicenseFormData) => {
      Some((formData.obj.id, formData.obj.flightCrewId, formData.obj.licenseTypeId, formData.obj.licenseIssuedById, formData.obj.dateOfLicense, formData.obj.licenseValidityId, formData.obj.licenseRatingsId))
    })
  )
}



case class FlightCrewLicenseFilter(id: Option[Long] = None,
                                   flightCrewId: Option[Long] = None,
                                   licenseTypeId: Option[Long] = None,
                                   licenseIssuedById: Option[Long] = None,
                                   dateOfLicenseFrom: Option[DateTime] = None,
                                   dateOfLicenseTo: Option[DateTime] = None,
                                   licenseValidityId: Option[Long] = None,
                                   licenseRatingsId: Option[Long] = None)

object FlightCrewLicenseFilter {
  val tupled = (this.apply _).tupled
}


object FlightCrewLicenseFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "flightCrewId" -> optional(longNumber),
      "licenseTypeId" -> optional(longNumber),
      "licenseIssuedById" -> optional(longNumber),
      "dateOfLicenseFrom" -> optional(jodaDate("YYYY-MM-dd", DateTimeZone.UTC)),
      "dateOfLicenseTo" -> optional(jodaDate("YYYY-MM-dd", DateTimeZone.UTC)),
      "licenseValidityId" -> optional(longNumber),
      "licenseRatingsId" -> optional(longNumber)
    )/*(FlightCrewLicenseFilter.apply)(FlightCrewLicenseFilter.unapply)*/
    ((id,flightCrewId,licenseTypeId,licenseIssuedById,dateOfLicenseFrom,dateOfLicenseTo,licenseValidityId,licenseRatingsId) => {
      FlightCrewLicenseFilter(id, flightCrewId, licenseTypeId, licenseIssuedById, dateOfLicenseFrom, dateOfLicenseTo, licenseValidityId, licenseRatingsId)
    })((formData: FlightCrewLicenseFilter) => {
      Some((formData.id, formData.flightCrewId, formData.licenseTypeId, formData.licenseIssuedById, formData.dateOfLicenseFrom, formData.dateOfLicenseTo, formData.licenseValidityId, formData.licenseRatingsId))
    })
  )
}

