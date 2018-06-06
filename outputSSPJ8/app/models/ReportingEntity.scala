package models
//import models.extensions.ReportingEntityExtension
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



case class ReportingEntity(id: Option[Long] = None /*None*/,
                           reportingEntity: String = "" /*None*/,
                           reportingEntityId: Option[Long] = None /*None*/) extends ReportingEntityExtension{

}

object ReportingEntity {
  implicit val format = Json.format[ReportingEntity]
  val tupled = (this.apply _).tupled
}


case class ReportingEntityFormData(obj: ReportingEntity){
  def update(updatedObj: ReportingEntity = obj)(implicit repo: ReportingEntityRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: ReportingEntity)(implicit repo: ReportingEntityRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object ReportingEntityFormData{
  def fapply(obj: ReportingEntity)(implicit ec: ExecutionContext) = {
    Future{
      new ReportingEntityFormData(obj)
    }
  }
}
object ReportingEntityForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "reportingEntity" -> nonEmptyText,
      "reportingEntityId" -> optional(longNumber)
    )/*(ReportingEntity.apply)(ReportingEntity.unapply)*/
    ((id,reportingEntity,reportingEntityId) => {
      ReportingEntityFormData(ReportingEntity(id, reportingEntity, reportingEntityId))
    })((formData: ReportingEntityFormData) => {
      Some((formData.obj.id, formData.obj.reportingEntity, formData.obj.reportingEntityId))
    })
  )
}



case class ReportingEntityFilter(id: Option[Long] = None,
                                 reportingEntity: Option[String] = None,
                                 reportingEntityId: Option[Long] = None)

object ReportingEntityFilter {
  val tupled = (this.apply _).tupled
}


object ReportingEntityFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "reportingEntity" -> optional(nonEmptyText),
      "reportingEntityId" -> optional(longNumber)
    )/*(ReportingEntityFilter.apply)(ReportingEntityFilter.unapply)*/
    ((id,reportingEntity,reportingEntityId) => {
      ReportingEntityFilter(id, reportingEntity, reportingEntityId)
    })((formData: ReportingEntityFilter) => {
      Some((formData.id, formData.reportingEntity, formData.reportingEntityId))
    })
  )
}

