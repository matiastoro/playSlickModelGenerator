package models
//import models.extensions.EccairsOcurrenceCategoryExtension
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



case class EccairsOcurrenceCategory(id: Option[Long] = None /*None*/,
                                    eccairsId: Long = 0L /*None*/,
                                    ocurrenceCategoryId: Long = 0L /*None*/) extends EccairsOcurrenceCategoryExtension{

}

object EccairsOcurrenceCategory {
  implicit val format = Json.format[EccairsOcurrenceCategory]
  val tupled = (this.apply _).tupled
}


case class EccairsOcurrenceCategoryFormData(obj: EccairsOcurrenceCategory){
  def update(updatedObj: EccairsOcurrenceCategory = obj)(implicit repo: EccairsOcurrenceCategoryRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: EccairsOcurrenceCategory)(implicit repo: EccairsOcurrenceCategoryRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object EccairsOcurrenceCategoryFormData{
  def fapply(obj: EccairsOcurrenceCategory)(implicit ec: ExecutionContext) = {
    Future{
      new EccairsOcurrenceCategoryFormData(obj)
    }
  }
}
object EccairsOcurrenceCategoryForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "eccairsId" -> optional(longNumber),
      "ocurrenceCategoryId" -> longNumber
    )/*(EccairsOcurrenceCategory.apply)(EccairsOcurrenceCategory.unapply)*/
    ((id,eccairsId,ocurrenceCategoryId) => {
      EccairsOcurrenceCategoryFormData(EccairsOcurrenceCategory(id, eccairsId.getOrElse(0), ocurrenceCategoryId))
    })((formData: EccairsOcurrenceCategoryFormData) => {
      Some((formData.obj.id, Some(formData.obj.eccairsId), formData.obj.ocurrenceCategoryId))
    })
  )
}



case class EccairsOcurrenceCategoryFilter(id: Option[Long] = None,
                                          eccairsId: Option[Long] = None,
                                          ocurrenceCategoryId: Option[Long] = None)

object EccairsOcurrenceCategoryFilter {
  val tupled = (this.apply _).tupled
}


object EccairsOcurrenceCategoryFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "eccairsId" -> optional(longNumber),
      "ocurrenceCategoryId" -> optional(longNumber)
    )/*(EccairsOcurrenceCategoryFilter.apply)(EccairsOcurrenceCategoryFilter.unapply)*/
    ((id,eccairsId,ocurrenceCategoryId) => {
      EccairsOcurrenceCategoryFilter(id, eccairsId, ocurrenceCategoryId)
    })((formData: EccairsOcurrenceCategoryFilter) => {
      Some((formData.id, formData.eccairsId, formData.ocurrenceCategoryId))
    })
  )
}

