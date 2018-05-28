package models
//import models.extensions.NeoAerodromeOcurrenceCategoryExtension
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



case class NeoAerodromeOcurrenceCategory(id: Option[Long] = None /*None*/,
                                         neoAerodromeId: Long = 0L /*None*/,
                                         ocurrenceCategoryId: Long = 0L /*None*/) extends NeoAerodromeOcurrenceCategoryExtension{

}

object NeoAerodromeOcurrenceCategory {
  implicit val format = Json.format[NeoAerodromeOcurrenceCategory]
  val tupled = (this.apply _).tupled
}




case class NeoAerodromeOcurrenceCategoryFormData(obj: NeoAerodromeOcurrenceCategory){
  def update(updatedObj: NeoAerodromeOcurrenceCategory = obj)(implicit repo: NeoAerodromeOcurrenceCategoryRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: NeoAerodromeOcurrenceCategory)(implicit repo: NeoAerodromeOcurrenceCategoryRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object NeoAerodromeOcurrenceCategoryFormData{
  def fapply(obj: NeoAerodromeOcurrenceCategory)(implicit ec: ExecutionContext) = {
    Future{
      new NeoAerodromeOcurrenceCategoryFormData(obj)
    }
  }
}
object NeoAerodromeOcurrenceCategoryForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "neoAerodromeId" -> longNumber,
      "ocurrenceCategoryId" -> longNumber
    )/*(NeoAerodromeOcurrenceCategory.apply)(NeoAerodromeOcurrenceCategory.unapply)*/
    ((id,neoAerodromeId,ocurrenceCategoryId) => {
      NeoAerodromeOcurrenceCategoryFormData(NeoAerodromeOcurrenceCategory(id, neoAerodromeId, ocurrenceCategoryId))
    })((formData: NeoAerodromeOcurrenceCategoryFormData) => {
      Some((formData.obj.id, formData.obj.neoAerodromeId, formData.obj.ocurrenceCategoryId))
    })
  )
}