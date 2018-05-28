package models
//import models.extensions.HelicopterLandingAreaTypeExtension
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



case class HelicopterLandingAreaType(id: Option[Long] = None /*None*/,
                                     landingAreaType: String = "" /*None*/) extends HelicopterLandingAreaTypeExtension{

}

object HelicopterLandingAreaType {
  implicit val format = Json.format[HelicopterLandingAreaType]
  val tupled = (this.apply _).tupled
}




case class HelicopterLandingAreaTypeFormData(obj: HelicopterLandingAreaType){
  def update(updatedObj: HelicopterLandingAreaType = obj)(implicit repo: HelicopterLandingAreaTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: HelicopterLandingAreaType)(implicit repo: HelicopterLandingAreaTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object HelicopterLandingAreaTypeFormData{
  def fapply(obj: HelicopterLandingAreaType)(implicit ec: ExecutionContext) = {
    Future{
      new HelicopterLandingAreaTypeFormData(obj)
    }
  }
}
object HelicopterLandingAreaTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "landingAreaType" -> nonEmptyText
    )/*(HelicopterLandingAreaType.apply)(HelicopterLandingAreaType.unapply)*/
    ((id,landingAreaType) => {
      HelicopterLandingAreaTypeFormData(HelicopterLandingAreaType(id, landingAreaType))
    })((formData: HelicopterLandingAreaTypeFormData) => {
      Some((formData.obj.id, formData.obj.landingAreaType))
    })
  )
}