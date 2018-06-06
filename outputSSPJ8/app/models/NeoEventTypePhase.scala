package models
//import models.extensions.NeoEventTypePhaseExtension
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



case class NeoEventTypePhase(id: Option[Long] = None /*None*/,
                             neoAerodromeId: Long = 0L /*None*/,
                             neoEventTypeId: Long = 0L /*None*/,
                             phaseId: Long = 0L /*None*/) extends NeoEventTypePhaseExtension{

}

object NeoEventTypePhase {
  implicit val format = Json.format[NeoEventTypePhase]
  val tupled = (this.apply _).tupled
}


case class NeoEventTypePhaseFormData(obj: NeoEventTypePhase){
  def update(updatedObj: NeoEventTypePhase = obj)(implicit repo: NeoEventTypePhaseRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: NeoEventTypePhase)(implicit repo: NeoEventTypePhaseRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object NeoEventTypePhaseFormData{
  def fapply(obj: NeoEventTypePhase)(implicit ec: ExecutionContext) = {
    Future{
      new NeoEventTypePhaseFormData(obj)
    }
  }
}
object NeoEventTypePhaseForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "neoAerodromeId" -> longNumber,
      "neoEventTypeId" -> longNumber,
      "phaseId" -> longNumber
    )/*(NeoEventTypePhase.apply)(NeoEventTypePhase.unapply)*/
    ((id,neoAerodromeId,neoEventTypeId,phaseId) => {
      NeoEventTypePhaseFormData(NeoEventTypePhase(id, neoAerodromeId, neoEventTypeId, phaseId))
    })((formData: NeoEventTypePhaseFormData) => {
      Some((formData.obj.id, formData.obj.neoAerodromeId, formData.obj.neoEventTypeId, formData.obj.phaseId))
    })
  )
}



case class NeoEventTypePhaseFilter(id: Option[Long] = None,
                                   neoAerodromeId: Option[Long] = None,
                                   neoEventTypeId: Option[Long] = None,
                                   phaseId: Option[Long] = None)

object NeoEventTypePhaseFilter {
  val tupled = (this.apply _).tupled
}


object NeoEventTypePhaseFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "neoAerodromeId" -> optional(longNumber),
      "neoEventTypeId" -> optional(longNumber),
      "phaseId" -> optional(longNumber)
    )/*(NeoEventTypePhaseFilter.apply)(NeoEventTypePhaseFilter.unapply)*/
    ((id,neoAerodromeId,neoEventTypeId,phaseId) => {
      NeoEventTypePhaseFilter(id, neoAerodromeId, neoEventTypeId, phaseId)
    })((formData: NeoEventTypePhaseFilter) => {
      Some((formData.id, formData.neoAerodromeId, formData.neoEventTypeId, formData.phaseId))
    })
  )
}

