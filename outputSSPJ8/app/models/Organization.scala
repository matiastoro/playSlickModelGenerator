package models
//import models.extensions.OrganizationExtension
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



case class Organization(id: Option[Long] = None /*None*/,
                        organization: String = "" /*None*/) extends OrganizationExtension{

}

object Organization {
  implicit val format = Json.format[Organization]
  val tupled = (this.apply _).tupled
}


case class OrganizationFormData(obj: Organization){
  def update(updatedObj: Organization = obj)(implicit repo: OrganizationRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Organization)(implicit repo: OrganizationRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object OrganizationFormData{
  def fapply(obj: Organization)(implicit ec: ExecutionContext) = {
    Future{
      new OrganizationFormData(obj)
    }
  }
}
object OrganizationForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "organization" -> nonEmptyText
    )/*(Organization.apply)(Organization.unapply)*/
    ((id,organization) => {
      OrganizationFormData(Organization(id, organization))
    })((formData: OrganizationFormData) => {
      Some((formData.obj.id, formData.obj.organization))
    })
  )
}



case class OrganizationFilter(id: Option[Long] = None,
                              organization: Option[String] = None)

object OrganizationFilter {
  val tupled = (this.apply _).tupled
}


object OrganizationFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "organization" -> optional(nonEmptyText)
    )/*(OrganizationFilter.apply)(OrganizationFilter.unapply)*/
    ((id,organization) => {
      OrganizationFilter(id, organization)
    })((formData: OrganizationFilter) => {
      Some((formData.id, formData.organization))
    })
  )
}

