package models
//import models.extensions.NeoFlightOperationEventTypePhaseExtension
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



case class NeoFlightOperationEventTypePhase(id: Option[Long] = None /*None*/,
                                            neoFlightOperationId: Long = 0L /*None*/,
                                            neoEventTypeId: Long = 0L /*None*/,
                                            phaseId: Long = 0L /*None*/) extends NeoFlightOperationEventTypePhaseExtension{

}

object NeoFlightOperationEventTypePhase {
  implicit val format = Json.format[NeoFlightOperationEventTypePhase]
  val tupled = (this.apply _).tupled
}


case class NeoFlightOperationEventTypePhaseFormData(obj: NeoFlightOperationEventTypePhase){
  def update(updatedObj: NeoFlightOperationEventTypePhase = obj)(implicit repo: NeoFlightOperationEventTypePhaseRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: NeoFlightOperationEventTypePhase)(implicit repo: NeoFlightOperationEventTypePhaseRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object NeoFlightOperationEventTypePhaseFormData{
  def fapply(obj: NeoFlightOperationEventTypePhase)(implicit ec: ExecutionContext) = {
    Future{
      new NeoFlightOperationEventTypePhaseFormData(obj)
    }
  }
}
object NeoFlightOperationEventTypePhaseForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "neoFlightOperationId" -> longNumber,
      "neoEventTypeId" -> longNumber,
      "phaseId" -> longNumber
    )/*(NeoFlightOperationEventTypePhase.apply)(NeoFlightOperationEventTypePhase.unapply)*/
    ((id,neoFlightOperationId,neoEventTypeId,phaseId) => {
      NeoFlightOperationEventTypePhaseFormData(NeoFlightOperationEventTypePhase(id, neoFlightOperationId, neoEventTypeId, phaseId))
    })((formData: NeoFlightOperationEventTypePhaseFormData) => {
      Some((formData.obj.id, formData.obj.neoFlightOperationId, formData.obj.neoEventTypeId, formData.obj.phaseId))
    })
  )
}



case class NeoFlightOperationEventTypePhaseFilter(id: Option[Long] = None,
                                                  neoFlightOperationId: Option[Long] = None,
                                                  neoEventTypeId: Option[Long] = None,
                                                  phaseId: Option[Long] = None)

object NeoFlightOperationEventTypePhaseFilter {
  val tupled = (this.apply _).tupled
}


object NeoFlightOperationEventTypePhaseFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "neoFlightOperationId" -> optional(longNumber),
      "neoEventTypeId" -> optional(longNumber),
      "phaseId" -> optional(longNumber)
    )/*(NeoFlightOperationEventTypePhaseFilter.apply)(NeoFlightOperationEventTypePhaseFilter.unapply)*/
    ((id,neoFlightOperationId,neoEventTypeId,phaseId) => {
      NeoFlightOperationEventTypePhaseFilter(id, neoFlightOperationId, neoEventTypeId, phaseId)
    })((formData: NeoFlightOperationEventTypePhaseFilter) => {
      Some((formData.id, formData.neoFlightOperationId, formData.neoEventTypeId, formData.phaseId))
    })
  )
}

