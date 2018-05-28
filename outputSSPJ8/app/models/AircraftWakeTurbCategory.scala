package models
//import models.extensions.AircraftWakeTurbCategoryExtension
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



case class AircraftWakeTurbCategory(id: Option[Long] = None /*None*/,
                                    wakeTurbCategory: String = "" /*None*/) extends AircraftWakeTurbCategoryExtension{

}

object AircraftWakeTurbCategory {
  implicit val format = Json.format[AircraftWakeTurbCategory]
  val tupled = (this.apply _).tupled
}




case class AircraftWakeTurbCategoryFormData(obj: AircraftWakeTurbCategory){
  def update(updatedObj: AircraftWakeTurbCategory = obj)(implicit repo: AircraftWakeTurbCategoryRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: AircraftWakeTurbCategory)(implicit repo: AircraftWakeTurbCategoryRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object AircraftWakeTurbCategoryFormData{
  def fapply(obj: AircraftWakeTurbCategory)(implicit ec: ExecutionContext) = {
    Future{
      new AircraftWakeTurbCategoryFormData(obj)
    }
  }
}
object AircraftWakeTurbCategoryForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "wakeTurbCategory" -> nonEmptyText
    )/*(AircraftWakeTurbCategory.apply)(AircraftWakeTurbCategory.unapply)*/
    ((id,wakeTurbCategory) => {
      AircraftWakeTurbCategoryFormData(AircraftWakeTurbCategory(id, wakeTurbCategory))
    })((formData: AircraftWakeTurbCategoryFormData) => {
      Some((formData.obj.id, formData.obj.wakeTurbCategory))
    })
  )
}