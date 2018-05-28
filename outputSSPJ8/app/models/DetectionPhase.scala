package models
//import models.extensions.DetectionPhaseExtension
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



case class DetectionPhase(id: Option[Long] = None /*None*/,
                          detectionPhase: String = "" /*None*/) extends DetectionPhaseExtension{

}

object DetectionPhase {
  implicit val format = Json.format[DetectionPhase]
  val tupled = (this.apply _).tupled
}




case class DetectionPhaseFormData(obj: DetectionPhase){
  def update(updatedObj: DetectionPhase = obj)(implicit repo: DetectionPhaseRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: DetectionPhase)(implicit repo: DetectionPhaseRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object DetectionPhaseFormData{
  def fapply(obj: DetectionPhase)(implicit ec: ExecutionContext) = {
    Future{
      new DetectionPhaseFormData(obj)
    }
  }
}
object DetectionPhaseForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "detectionPhase" -> nonEmptyText
    )/*(DetectionPhase.apply)(DetectionPhase.unapply)*/
    ((id,detectionPhase) => {
      DetectionPhaseFormData(DetectionPhase(id, detectionPhase))
    })((formData: DetectionPhaseFormData) => {
      Some((formData.obj.id, formData.obj.detectionPhase))
    })
  )
}