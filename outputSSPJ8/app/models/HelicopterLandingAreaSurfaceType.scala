package models
//import models.extensions.HelicopterLandingAreaSurfaceTypeExtension
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



case class HelicopterLandingAreaSurfaceType(id: Option[Long] = None /*None*/,
                                            surfaceType: String = "" /*None*/) extends HelicopterLandingAreaSurfaceTypeExtension{

}

object HelicopterLandingAreaSurfaceType {
  implicit val format = Json.format[HelicopterLandingAreaSurfaceType]
  val tupled = (this.apply _).tupled
}




case class HelicopterLandingAreaSurfaceTypeFormData(obj: HelicopterLandingAreaSurfaceType){
  def update(updatedObj: HelicopterLandingAreaSurfaceType = obj)(implicit repo: HelicopterLandingAreaSurfaceTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: HelicopterLandingAreaSurfaceType)(implicit repo: HelicopterLandingAreaSurfaceTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object HelicopterLandingAreaSurfaceTypeFormData{
  def fapply(obj: HelicopterLandingAreaSurfaceType)(implicit ec: ExecutionContext) = {
    Future{
      new HelicopterLandingAreaSurfaceTypeFormData(obj)
    }
  }
}
object HelicopterLandingAreaSurfaceTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "surfaceType" -> nonEmptyText
    )/*(HelicopterLandingAreaSurfaceType.apply)(HelicopterLandingAreaSurfaceType.unapply)*/
    ((id,surfaceType) => {
      HelicopterLandingAreaSurfaceTypeFormData(HelicopterLandingAreaSurfaceType(id, surfaceType))
    })((formData: HelicopterLandingAreaSurfaceTypeFormData) => {
      Some((formData.obj.id, formData.obj.surfaceType))
    })
  )
}