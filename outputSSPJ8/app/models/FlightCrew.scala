package models
//import models.extensions.FlightCrewExtension
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



case class FlightCrew(id: Option[Long] = None /*None*/,
                      neoFlightOperationId: Long = 0L /*None*/,
                      categoryId: Long = 0L /*None*/,
                      experienceThis: Option[Double] = None /*None*/,
                      experienceAll: Option[Double] = None /*None*/,
                      dutyLast24Hours: Option[Double] = None /*None*/,
                      restBeforeDuty: Option[Double] = None /*None*/) extends FlightCrewExtension{

}

object FlightCrew {
  implicit val format = Json.format[FlightCrew]
  val tupled = (this.apply _).tupled
}




case class FlightCrewFormData(obj: FlightCrew, flightCrewLicenses: List[FlightCrewLicenseFormData]){
  def update(updatedObj: FlightCrew = obj)(implicit repo: FlightCrewRepository, flightCrewLicenseRepo: FlightCrewLicenseRepository, ec: ExecutionContext) = {
    //Delete elements that are not part of the form but they do exists in the database.
    flightCrewLicenseRepo.byFlightCrewId(obj.id).map{ l =>  l.filterNot{o => flightCrewLicenses.exists(_.obj.id == o.id)}.map{flightCrewLicenseRepo.delete(_)}}
    flightCrewLicenses.map{o => o.update(o.obj.copy(flightCrewId = obj.id.get))}
    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: FlightCrew)(implicit repo: FlightCrewRepository, flightCrewLicenseRepo: FlightCrewLicenseRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future.sequence(    flightCrewLicenses.map{o => o.insert(o.obj.copy(flightCrewId = id))}).map{ x =>
        id
      }
    
    }
  }
}
object FlightCrewFormData{
  def fapply(obj: FlightCrew)(implicit repo: FlightCrewRepository, flightCrewLicenseRepo: FlightCrewLicenseRepository, ec: ExecutionContext) = {
    for{
      flightCrewLicenses <- flightCrewLicenseRepo.byFlightCrewId(obj.id).flatMap(l => Future.sequence(l.map(FlightCrewLicenseFormData.fapply(_))))
    } yield{
      new FlightCrewFormData(obj, flightCrewLicenses.toList)
    }
  }
}
object FlightCrewForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "neoFlightOperationId" -> longNumber,
      "categoryId" -> longNumber,
      "experienceThis" -> optional(of(doubleFormat)),
      "experienceAll" -> optional(of(doubleFormat)),
      "dutyLast24Hours" -> optional(of(doubleFormat)),
      "restBeforeDuty" -> optional(of(doubleFormat)),
      "flightCrewLicenses" -> list(models.FlightCrewLicenseForm.form.mapping)
    )/*(FlightCrew.apply)(FlightCrew.unapply)*/
    ((id,neoFlightOperationId,categoryId,experienceThis,experienceAll,dutyLast24Hours,restBeforeDuty,flightCrewLicenses) => {
      FlightCrewFormData(FlightCrew(id, neoFlightOperationId, categoryId, experienceThis, experienceAll, dutyLast24Hours, restBeforeDuty), flightCrewLicenses)
    })((formData: FlightCrewFormData) => {
      Some((formData.obj.id, formData.obj.neoFlightOperationId, formData.obj.categoryId, formData.obj.experienceThis, formData.obj.experienceAll, formData.obj.dutyLast24Hours, formData.obj.restBeforeDuty, formData.flightCrewLicenses))
    })
  )
}