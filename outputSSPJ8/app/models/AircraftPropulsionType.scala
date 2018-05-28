package models
//import models.extensions.AircraftPropulsionTypeExtension
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



case class AircraftPropulsionType(id: Option[Long] = None /*None*/,
                                  propulsionType: String = "" /*None*/) extends AircraftPropulsionTypeExtension{

}

object AircraftPropulsionType {
  implicit val format = Json.format[AircraftPropulsionType]
  val tupled = (this.apply _).tupled
}




case class AircraftPropulsionTypeFormData(obj: AircraftPropulsionType){
  def update(updatedObj: AircraftPropulsionType = obj)(implicit repo: AircraftPropulsionTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: AircraftPropulsionType)(implicit repo: AircraftPropulsionTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object AircraftPropulsionTypeFormData{
  def fapply(obj: AircraftPropulsionType)(implicit ec: ExecutionContext) = {
    Future{
      new AircraftPropulsionTypeFormData(obj)
    }
  }
}
object AircraftPropulsionTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "propulsionType" -> nonEmptyText
    )/*(AircraftPropulsionType.apply)(AircraftPropulsionType.unapply)*/
    ((id,propulsionType) => {
      AircraftPropulsionTypeFormData(AircraftPropulsionType(id, propulsionType))
    })((formData: AircraftPropulsionTypeFormData) => {
      Some((formData.obj.id, formData.obj.propulsionType))
    })
  )
}