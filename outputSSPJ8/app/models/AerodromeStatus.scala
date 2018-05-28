package models
//import models.extensions.AerodromeStatusExtension
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



case class AerodromeStatus(id: Option[Long] = None /*None*/,
                           status: String = "" /*None*/) extends AerodromeStatusExtension{

}

object AerodromeStatus {
  implicit val format = Json.format[AerodromeStatus]
  val tupled = (this.apply _).tupled
}




case class AerodromeStatusFormData(obj: AerodromeStatus){
  def update(updatedObj: AerodromeStatus = obj)(implicit repo: AerodromeStatusRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: AerodromeStatus)(implicit repo: AerodromeStatusRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object AerodromeStatusFormData{
  def fapply(obj: AerodromeStatus)(implicit ec: ExecutionContext) = {
    Future{
      new AerodromeStatusFormData(obj)
    }
  }
}
object AerodromeStatusForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "status" -> nonEmptyText
    )/*(AerodromeStatus.apply)(AerodromeStatus.unapply)*/
    ((id,status) => {
      AerodromeStatusFormData(AerodromeStatus(id, status))
    })((formData: AerodromeStatusFormData) => {
      Some((formData.obj.id, formData.obj.status))
    })
  )
}