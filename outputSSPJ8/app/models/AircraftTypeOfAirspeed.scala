package models
//import models.extensions.AircraftTypeOfAirspeedExtension
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



case class AircraftTypeOfAirspeed(id: Option[Long] = None /*None*/,
                                  typeOfAirspeed: String = "" /*None*/) extends AircraftTypeOfAirspeedExtension{

}

object AircraftTypeOfAirspeed {
  implicit val format = Json.format[AircraftTypeOfAirspeed]
  val tupled = (this.apply _).tupled
}




case class AircraftTypeOfAirspeedFormData(obj: AircraftTypeOfAirspeed){
  def update(updatedObj: AircraftTypeOfAirspeed = obj)(implicit repo: AircraftTypeOfAirspeedRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: AircraftTypeOfAirspeed)(implicit repo: AircraftTypeOfAirspeedRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object AircraftTypeOfAirspeedFormData{
  def fapply(obj: AircraftTypeOfAirspeed)(implicit ec: ExecutionContext) = {
    Future{
      new AircraftTypeOfAirspeedFormData(obj)
    }
  }
}
object AircraftTypeOfAirspeedForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "typeOfAirspeed" -> nonEmptyText
    )/*(AircraftTypeOfAirspeed.apply)(AircraftTypeOfAirspeed.unapply)*/
    ((id,typeOfAirspeed) => {
      AircraftTypeOfAirspeedFormData(AircraftTypeOfAirspeed(id, typeOfAirspeed))
    })((formData: AircraftTypeOfAirspeedFormData) => {
      Some((formData.obj.id, formData.obj.typeOfAirspeed))
    })
  )
}