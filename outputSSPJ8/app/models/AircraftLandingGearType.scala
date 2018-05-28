package models
//import models.extensions.AircraftLandingGearTypeExtension
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



case class AircraftLandingGearType(id: Option[Long] = None /*None*/,
                                   landingGearType: String = "" /*None*/,
                                   aircraftLandingGearTypeId: Option[Long] = None /*None*/) extends AircraftLandingGearTypeExtension{

}

object AircraftLandingGearType {
  implicit val format = Json.format[AircraftLandingGearType]
  val tupled = (this.apply _).tupled
}




case class AircraftLandingGearTypeFormData(obj: AircraftLandingGearType){
  def update(updatedObj: AircraftLandingGearType = obj)(implicit repo: AircraftLandingGearTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: AircraftLandingGearType)(implicit repo: AircraftLandingGearTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object AircraftLandingGearTypeFormData{
  def fapply(obj: AircraftLandingGearType)(implicit ec: ExecutionContext) = {
    Future{
      new AircraftLandingGearTypeFormData(obj)
    }
  }
}
object AircraftLandingGearTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "landingGearType" -> nonEmptyText,
      "aircraftLandingGearTypeId" -> optional(longNumber)
    )/*(AircraftLandingGearType.apply)(AircraftLandingGearType.unapply)*/
    ((id,landingGearType,aircraftLandingGearTypeId) => {
      AircraftLandingGearTypeFormData(AircraftLandingGearType(id, landingGearType, aircraftLandingGearTypeId))
    })((formData: AircraftLandingGearTypeFormData) => {
      Some((formData.obj.id, formData.obj.landingGearType, formData.obj.aircraftLandingGearTypeId))
    })
  )
}