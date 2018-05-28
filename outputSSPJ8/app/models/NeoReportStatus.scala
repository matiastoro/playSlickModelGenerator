package models
//import models.extensions.NeoReportStatusExtension
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



case class NeoReportStatus(id: Option[Long] = None /*None*/,
                           reportStatus: String = "" /*None*/) extends NeoReportStatusExtension{

}

object NeoReportStatus {
  implicit val format = Json.format[NeoReportStatus]
  val tupled = (this.apply _).tupled
}




case class NeoReportStatusFormData(obj: NeoReportStatus){
  def update(updatedObj: NeoReportStatus = obj)(implicit repo: NeoReportStatusRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: NeoReportStatus)(implicit repo: NeoReportStatusRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object NeoReportStatusFormData{
  def fapply(obj: NeoReportStatus)(implicit ec: ExecutionContext) = {
    Future{
      new NeoReportStatusFormData(obj)
    }
  }
}
object NeoReportStatusForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "reportStatus" -> nonEmptyText
    )/*(NeoReportStatus.apply)(NeoReportStatus.unapply)*/
    ((id,reportStatus) => {
      NeoReportStatusFormData(NeoReportStatus(id, reportStatus))
    })((formData: NeoReportStatusFormData) => {
      Some((formData.obj.id, formData.obj.reportStatus))
    })
  )
}