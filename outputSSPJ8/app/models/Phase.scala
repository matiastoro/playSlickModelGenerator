package models
//import models.extensions.PhaseExtension
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



case class Phase(id: Option[Long] = None /*None*/,
                 phase: String = "" /*None*/,
                 phaseId: Option[Long] = None /*None*/) extends PhaseExtension{

}

object Phase {
  implicit val format = Json.format[Phase]
  val tupled = (this.apply _).tupled
}


case class PhaseFormData(obj: Phase){
  def update(updatedObj: Phase = obj)(implicit repo: PhaseRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Phase)(implicit repo: PhaseRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object PhaseFormData{
  def fapply(obj: Phase)(implicit ec: ExecutionContext) = {
    Future{
      new PhaseFormData(obj)
    }
  }
}
object PhaseForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "phase" -> nonEmptyText,
      "phaseId" -> optional(longNumber)
    )/*(Phase.apply)(Phase.unapply)*/
    ((id,phase,phaseId) => {
      PhaseFormData(Phase(id, phase, phaseId))
    })((formData: PhaseFormData) => {
      Some((formData.obj.id, formData.obj.phase, formData.obj.phaseId))
    })
  )
}



case class PhaseFilter(id: Option[Long] = None,
                       phase: Option[String] = None,
                       phaseId: Option[Long] = None)

object PhaseFilter {
  val tupled = (this.apply _).tupled
}


object PhaseFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "phase" -> optional(nonEmptyText),
      "phaseId" -> optional(longNumber)
    )/*(PhaseFilter.apply)(PhaseFilter.unapply)*/
    ((id,phase,phaseId) => {
      PhaseFilter(id, phase, phaseId)
    })((formData: PhaseFilter) => {
      Some((formData.id, formData.phase, formData.phaseId))
    })
  )
}

