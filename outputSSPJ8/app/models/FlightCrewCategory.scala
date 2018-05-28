package models
//import models.extensions.FlightCrewCategoryExtension
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



case class FlightCrewCategory(id: Option[Long] = None /*None*/,
                              category: String = "" /*None*/) extends FlightCrewCategoryExtension{

}

object FlightCrewCategory {
  implicit val format = Json.format[FlightCrewCategory]
  val tupled = (this.apply _).tupled
}




case class FlightCrewCategoryFormData(obj: FlightCrewCategory){
  def update(updatedObj: FlightCrewCategory = obj)(implicit repo: FlightCrewCategoryRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: FlightCrewCategory)(implicit repo: FlightCrewCategoryRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object FlightCrewCategoryFormData{
  def fapply(obj: FlightCrewCategory)(implicit ec: ExecutionContext) = {
    Future{
      new FlightCrewCategoryFormData(obj)
    }
  }
}
object FlightCrewCategoryForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "category" -> nonEmptyText
    )/*(FlightCrewCategory.apply)(FlightCrewCategory.unapply)*/
    ((id,category) => {
      FlightCrewCategoryFormData(FlightCrewCategory(id, category))
    })((formData: FlightCrewCategoryFormData) => {
      Some((formData.obj.id, formData.obj.category))
    })
  )
}