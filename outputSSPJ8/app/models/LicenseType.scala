package models
//import models.extensions.LicenseTypeExtension
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



case class LicenseType(id: Option[Long] = None /*None*/,
                       licenseType: String = "" /*None*/,
                       licenseTypeId: Option[Long] = None /*None*/) extends LicenseTypeExtension{

}

object LicenseType {
  implicit val format = Json.format[LicenseType]
  val tupled = (this.apply _).tupled
}


case class LicenseTypeFormData(obj: LicenseType){
  def update(updatedObj: LicenseType = obj)(implicit repo: LicenseTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: LicenseType)(implicit repo: LicenseTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object LicenseTypeFormData{
  def fapply(obj: LicenseType)(implicit ec: ExecutionContext) = {
    Future{
      new LicenseTypeFormData(obj)
    }
  }
}
object LicenseTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "licenseType" -> nonEmptyText,
      "licenseTypeId" -> optional(longNumber)
    )/*(LicenseType.apply)(LicenseType.unapply)*/
    ((id,licenseType,licenseTypeId) => {
      LicenseTypeFormData(LicenseType(id, licenseType, licenseTypeId))
    })((formData: LicenseTypeFormData) => {
      Some((formData.obj.id, formData.obj.licenseType, formData.obj.licenseTypeId))
    })
  )
}



case class LicenseTypeFilter(id: Option[Long] = None,
                             licenseType: Option[String] = None,
                             licenseTypeId: Option[Long] = None)

object LicenseTypeFilter {
  val tupled = (this.apply _).tupled
}


object LicenseTypeFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "licenseType" -> optional(nonEmptyText),
      "licenseTypeId" -> optional(longNumber)
    )/*(LicenseTypeFilter.apply)(LicenseTypeFilter.unapply)*/
    ((id,licenseType,licenseTypeId) => {
      LicenseTypeFilter(id, licenseType, licenseTypeId)
    })((formData: LicenseTypeFilter) => {
      Some((formData.id, formData.licenseType, formData.licenseTypeId))
    })
  )
}

