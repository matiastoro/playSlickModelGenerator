package models
import models.extensions.ItineraryAircraftManufacturerExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.libs.json._

case class ItineraryAircraftManufacturer(id: Option[Long] = None /*None*/,
                                         name: String = "" /*None*/) extends ItineraryAircraftManufacturerExtension{
  implicit val jsonFormat = Json.format[ItineraryAircraftManufacturer]
  def toJson(implicit session: Session) = Json.toJson(this).as[JsObject] + 
      ("itineraryAircrafts" -> Json.toJson(ItineraryAircraftQuery.byItineraryAircraftManufacturerId(id).map(_.toJson)))
         

  lazy val selectString = name

}



class ItineraryAircraftManufacturers(tag: Tag) extends Table[ItineraryAircraftManufacturer](tag, "itinerary_aircraft_manufacturer") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.Default(""))

  def * = (id.?, name).shaped <> (ItineraryAircraftManufacturer.tupled, ItineraryAircraftManufacturer.unapply)
}

class ItineraryAircraftManufacturerQueryBase extends BaseDAO[ItineraryAircraftManufacturer] {
  type DBTable = ItineraryAircraftManufacturerMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class ItineraryAircraftManufacturerFormData(obj: ItineraryAircraftManufacturer, itineraryAircrafts: List[ItineraryAircraftFormData]){
  def update(updatedObj: ItineraryAircraftManufacturer = obj)(implicit session: Session) = {
    //Delete elements that are not part of the form but they do exists in the database.
    ItineraryAircraftQuery.byItineraryAircraftManufacturerId(obj.id).filterNot{o => itineraryAircrafts.exists(_.obj.id == o.id)}.map{ItineraryAircraftQuery.eliminar(_)}
    itineraryAircrafts.map{o => o.update(o.obj.copy(itineraryAircraftManufacturerId = obj.id.get))}
    ItineraryAircraftManufacturerQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: ItineraryAircraftManufacturer)(implicit session: Session) = {
    val id = ItineraryAircraftManufacturerQuery.insert(insertedObj)
    itineraryAircrafts.map{o => o.insert(o.obj.copy(itineraryAircraftManufacturerId = id))}
    id
  }
}
object ItineraryAircraftManufacturerFormData{
  def apply(obj: ItineraryAircraftManufacturer)(implicit session: Session) = {
    val itineraryAircrafts = ItineraryAircraftQuery.byItineraryAircraftManufacturerId(obj.id).map(ItineraryAircraftFormData(_))
    new ItineraryAircraftManufacturerFormData(obj, itineraryAircrafts)
  }
}
object ItineraryAircraftManufacturerForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "name" -> text,
      "itineraryAircrafts" -> list(models.ItineraryAircraftForm.form.mapping)
    )/*(ItineraryAircraftManufacturer.apply)(ItineraryAircraftManufacturer.unapply)*/
    ((id,name,itineraryAircrafts) => {
      ItineraryAircraftManufacturerFormData(ItineraryAircraftManufacturer(id, name), itineraryAircrafts)
    })((formData: ItineraryAircraftManufacturerFormData) => {
      Some((formData.obj.id, formData.obj.name, formData.itineraryAircrafts))
    })
  )
}