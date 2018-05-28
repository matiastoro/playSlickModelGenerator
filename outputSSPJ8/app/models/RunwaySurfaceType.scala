package models
//import models.extensions.RunwaySurfaceTypeExtension
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



case class RunwaySurfaceType(id: Option[Long] = None /*None*/,
                             surfaceType: String = "" /*None*/) extends RunwaySurfaceTypeExtension{

}

object RunwaySurfaceType {
  implicit val format = Json.format[RunwaySurfaceType]
  val tupled = (this.apply _).tupled
}




case class RunwaySurfaceTypeFormData(obj: RunwaySurfaceType){
  def update(updatedObj: RunwaySurfaceType = obj)(implicit repo: RunwaySurfaceTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: RunwaySurfaceType)(implicit repo: RunwaySurfaceTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object RunwaySurfaceTypeFormData{
  def fapply(obj: RunwaySurfaceType)(implicit ec: ExecutionContext) = {
    Future{
      new RunwaySurfaceTypeFormData(obj)
    }
  }
}
object RunwaySurfaceTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "surfaceType" -> nonEmptyText
    )/*(RunwaySurfaceType.apply)(RunwaySurfaceType.unapply)*/
    ((id,surfaceType) => {
      RunwaySurfaceTypeFormData(RunwaySurfaceType(id, surfaceType))
    })((formData: RunwaySurfaceTypeFormData) => {
      Some((formData.obj.id, formData.obj.surfaceType))
    })
  )
}