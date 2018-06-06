package models
//import models.extensions.AircraftManufacturerModelExtension
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



case class AircraftManufacturerModel(id: Option[Long] = None /*None*/,
                                     manufacturerModel: String = "" /*None*/,
                                     aircraftManufacturerModelId: Option[Long] = None /*None*/) extends AircraftManufacturerModelExtension{

}

object AircraftManufacturerModel {
  implicit val format = Json.format[AircraftManufacturerModel]
  val tupled = (this.apply _).tupled
}


case class AircraftManufacturerModelFormData(obj: AircraftManufacturerModel){
  def update(updatedObj: AircraftManufacturerModel = obj)(implicit repo: AircraftManufacturerModelRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: AircraftManufacturerModel)(implicit repo: AircraftManufacturerModelRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object AircraftManufacturerModelFormData{
  def fapply(obj: AircraftManufacturerModel)(implicit ec: ExecutionContext) = {
    Future{
      new AircraftManufacturerModelFormData(obj)
    }
  }
}
object AircraftManufacturerModelForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "manufacturerModel" -> nonEmptyText,
      "aircraftManufacturerModelId" -> optional(longNumber)
    )/*(AircraftManufacturerModel.apply)(AircraftManufacturerModel.unapply)*/
    ((id,manufacturerModel,aircraftManufacturerModelId) => {
      AircraftManufacturerModelFormData(AircraftManufacturerModel(id, manufacturerModel, aircraftManufacturerModelId))
    })((formData: AircraftManufacturerModelFormData) => {
      Some((formData.obj.id, formData.obj.manufacturerModel, formData.obj.aircraftManufacturerModelId))
    })
  )
}



case class AircraftManufacturerModelFilter(id: Option[Long] = None,
                                           manufacturerModel: Option[String] = None,
                                           aircraftManufacturerModelId: Option[Long] = None)

object AircraftManufacturerModelFilter {
  val tupled = (this.apply _).tupled
}


object AircraftManufacturerModelFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "manufacturerModel" -> optional(nonEmptyText),
      "aircraftManufacturerModelId" -> optional(longNumber)
    )/*(AircraftManufacturerModelFilter.apply)(AircraftManufacturerModelFilter.unapply)*/
    ((id,manufacturerModel,aircraftManufacturerModelId) => {
      AircraftManufacturerModelFilter(id, manufacturerModel, aircraftManufacturerModelId)
    })((formData: AircraftManufacturerModelFilter) => {
      Some((formData.id, formData.manufacturerModel, formData.aircraftManufacturerModelId))
    })
  )
}

