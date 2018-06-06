package models
//import models.extensions.AircraftCategoryExtension
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



case class AircraftCategory(id: Option[Long] = None /*None*/,
                            category: String = "" /*None*/,
                            aircraftCategoryId: Option[Long] = None /*None*/) extends AircraftCategoryExtension{

}

object AircraftCategory {
  implicit val format = Json.format[AircraftCategory]
  val tupled = (this.apply _).tupled
}


case class AircraftCategoryFormData(obj: AircraftCategory){
  def update(updatedObj: AircraftCategory = obj)(implicit repo: AircraftCategoryRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: AircraftCategory)(implicit repo: AircraftCategoryRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object AircraftCategoryFormData{
  def fapply(obj: AircraftCategory)(implicit ec: ExecutionContext) = {
    Future{
      new AircraftCategoryFormData(obj)
    }
  }
}
object AircraftCategoryForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "category" -> nonEmptyText,
      "aircraftCategoryId" -> optional(longNumber)
    )/*(AircraftCategory.apply)(AircraftCategory.unapply)*/
    ((id,category,aircraftCategoryId) => {
      AircraftCategoryFormData(AircraftCategory(id, category, aircraftCategoryId))
    })((formData: AircraftCategoryFormData) => {
      Some((formData.obj.id, formData.obj.category, formData.obj.aircraftCategoryId))
    })
  )
}



case class AircraftCategoryFilter(id: Option[Long] = None,
                                  category: Option[String] = None,
                                  aircraftCategoryId: Option[Long] = None)

object AircraftCategoryFilter {
  val tupled = (this.apply _).tupled
}


object AircraftCategoryFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "category" -> optional(nonEmptyText),
      "aircraftCategoryId" -> optional(longNumber)
    )/*(AircraftCategoryFilter.apply)(AircraftCategoryFilter.unapply)*/
    ((id,category,aircraftCategoryId) => {
      AircraftCategoryFilter(id, category, aircraftCategoryId)
    })((formData: AircraftCategoryFilter) => {
      Some((formData.id, formData.category, formData.aircraftCategoryId))
    })
  )
}

