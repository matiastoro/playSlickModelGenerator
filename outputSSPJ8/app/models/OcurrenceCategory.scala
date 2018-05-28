package models
//import models.extensions.OcurrenceCategoryExtension
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



case class OcurrenceCategory(id: Option[Long] = None /*None*/,
                             code: String = "" /*None*/,
                             ocurrenceCategory: String = "" /*None*/) extends OcurrenceCategoryExtension{

}

object OcurrenceCategory {
  implicit val format = Json.format[OcurrenceCategory]
  val tupled = (this.apply _).tupled
}




case class OcurrenceCategoryFormData(obj: OcurrenceCategory){
  def update(updatedObj: OcurrenceCategory = obj)(implicit repo: OcurrenceCategoryRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: OcurrenceCategory)(implicit repo: OcurrenceCategoryRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object OcurrenceCategoryFormData{
  def fapply(obj: OcurrenceCategory)(implicit ec: ExecutionContext) = {
    Future{
      new OcurrenceCategoryFormData(obj)
    }
  }
}
object OcurrenceCategoryForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "code" -> nonEmptyText,
      "ocurrenceCategory" -> nonEmptyText
    )/*(OcurrenceCategory.apply)(OcurrenceCategory.unapply)*/
    ((id,code,ocurrenceCategory) => {
      OcurrenceCategoryFormData(OcurrenceCategory(id, code, ocurrenceCategory))
    })((formData: OcurrenceCategoryFormData) => {
      Some((formData.obj.id, formData.obj.code, formData.obj.ocurrenceCategory))
    })
  )
}