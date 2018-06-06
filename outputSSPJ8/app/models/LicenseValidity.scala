package models
//import models.extensions.LicenseValidityExtension
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



case class LicenseValidity(id: Option[Long] = None /*None*/,
                           licenseValidity: String = "" /*None*/) extends LicenseValidityExtension{

}

object LicenseValidity {
  implicit val format = Json.format[LicenseValidity]
  val tupled = (this.apply _).tupled
}


case class LicenseValidityFormData(obj: LicenseValidity){
  def update(updatedObj: LicenseValidity = obj)(implicit repo: LicenseValidityRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: LicenseValidity)(implicit repo: LicenseValidityRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object LicenseValidityFormData{
  def fapply(obj: LicenseValidity)(implicit ec: ExecutionContext) = {
    Future{
      new LicenseValidityFormData(obj)
    }
  }
}
object LicenseValidityForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "licenseValidity" -> nonEmptyText
    )/*(LicenseValidity.apply)(LicenseValidity.unapply)*/
    ((id,licenseValidity) => {
      LicenseValidityFormData(LicenseValidity(id, licenseValidity))
    })((formData: LicenseValidityFormData) => {
      Some((formData.obj.id, formData.obj.licenseValidity))
    })
  )
}



case class LicenseValidityFilter(id: Option[Long] = None,
                                 licenseValidity: Option[String] = None)

object LicenseValidityFilter {
  val tupled = (this.apply _).tupled
}


object LicenseValidityFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "licenseValidity" -> optional(nonEmptyText)
    )/*(LicenseValidityFilter.apply)(LicenseValidityFilter.unapply)*/
    ((id,licenseValidity) => {
      LicenseValidityFilter(id, licenseValidity)
    })((formData: LicenseValidityFilter) => {
      Some((formData.id, formData.licenseValidity))
    })
  )
}

