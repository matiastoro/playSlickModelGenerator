package models
//import models.extensions.NeoFlightOperationOcurrenceCategoryExtension
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



case class NeoFlightOperationOcurrenceCategory(id: Option[Long] = None /*None*/,
                                               neoFlightOperationId: Long = 0L /*None*/,
                                               ocurrenceCategoryId: Long = 0L /*None*/) extends NeoFlightOperationOcurrenceCategoryExtension{

}

object NeoFlightOperationOcurrenceCategory {
  implicit val format = Json.format[NeoFlightOperationOcurrenceCategory]
  val tupled = (this.apply _).tupled
}


case class NeoFlightOperationOcurrenceCategoryFormData(obj: NeoFlightOperationOcurrenceCategory){
  def update(updatedObj: NeoFlightOperationOcurrenceCategory = obj)(implicit repo: NeoFlightOperationOcurrenceCategoryRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: NeoFlightOperationOcurrenceCategory)(implicit repo: NeoFlightOperationOcurrenceCategoryRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object NeoFlightOperationOcurrenceCategoryFormData{
  def fapply(obj: NeoFlightOperationOcurrenceCategory)(implicit ec: ExecutionContext) = {
    Future{
      new NeoFlightOperationOcurrenceCategoryFormData(obj)
    }
  }
}
object NeoFlightOperationOcurrenceCategoryForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "neoFlightOperationId" -> longNumber,
      "ocurrenceCategoryId" -> longNumber
    )/*(NeoFlightOperationOcurrenceCategory.apply)(NeoFlightOperationOcurrenceCategory.unapply)*/
    ((id,neoFlightOperationId,ocurrenceCategoryId) => {
      NeoFlightOperationOcurrenceCategoryFormData(NeoFlightOperationOcurrenceCategory(id, neoFlightOperationId, ocurrenceCategoryId))
    })((formData: NeoFlightOperationOcurrenceCategoryFormData) => {
      Some((formData.obj.id, formData.obj.neoFlightOperationId, formData.obj.ocurrenceCategoryId))
    })
  )
}



case class NeoFlightOperationOcurrenceCategoryFilter(id: Option[Long] = None,
                                                     neoFlightOperationId: Option[Long] = None,
                                                     ocurrenceCategoryId: Option[Long] = None)

object NeoFlightOperationOcurrenceCategoryFilter {
  val tupled = (this.apply _).tupled
}


object NeoFlightOperationOcurrenceCategoryFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "neoFlightOperationId" -> optional(longNumber),
      "ocurrenceCategoryId" -> optional(longNumber)
    )/*(NeoFlightOperationOcurrenceCategoryFilter.apply)(NeoFlightOperationOcurrenceCategoryFilter.unapply)*/
    ((id,neoFlightOperationId,ocurrenceCategoryId) => {
      NeoFlightOperationOcurrenceCategoryFilter(id, neoFlightOperationId, ocurrenceCategoryId)
    })((formData: NeoFlightOperationOcurrenceCategoryFilter) => {
      Some((formData.id, formData.neoFlightOperationId, formData.ocurrenceCategoryId))
    })
  )
}

