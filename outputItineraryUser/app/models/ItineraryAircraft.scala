package models
import models.extensions.ItineraryAircraftExtension
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

case class ItineraryAircraft(id: Option[Long] = None /*None*/,
                             manufacturerId: Long = 0 /*None*/,
                             model: String = "" /*None*/,
                             tpe: String = "" /*None*/,
                             iataCode: Option[String] = None /*None*/,
                             nPassengers: Option[Int] = None /*None*/,
                             length: Double = 0 /*None*/,
                             width: Double = 0 /*None*/,
                             wingspan: Double = 0 /*None*/,
                             OMGWS: Double = 0 /*None*/,
                             series: Option[String] = None /*None*/,
                             ACN: Option[String] = None /*None*/) extends ItineraryAircraftExtension{
  implicit val jsonFormat = Json.format[ItineraryAircraft]
  def toJson = Json.toJson(this)
         

  lazy val selectString = model
  def getItineraryAircraftManufacturer(implicit session: Session) = ItineraryAircraftManufacturerQuery.byId(manufacturerId)
}



class ItineraryAircrafts(tag: Tag) extends Table[ItineraryAircraft](tag, "itinerary_aircraft") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def manufacturerId = column[Long]("manufacturerId", O.Default(0))
  def manufacturer = foreignKey("manufacturerId_fk", manufacturerId, ItineraryAircraftManufacturerQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def model = column[String]("model", O.Default(""))
  def tpe = column[String]("tpe", O.Default(""))
  def iataCode = column[Option[String]]("iataCode", O.Default(None))
  def nPassengers = column[Option[Int]]("nPassengers", O.Default(None))
  def length = column[Double]("length", O.Default(0))
  def width = column[Double]("width", O.Default(0))
  def wingspan = column[Double]("wingspan", O.Default(0))
  def OMGWS = column[Double]("OMGWS", O.Default(0))
  def series = column[Option[String]]("series", O.Default(None))
  def ACN = column[Option[String]]("ACN", O.Default(None))

  def * = (id.?, manufacturerId, model, tpe, iataCode, nPassengers, length, width, wingspan, OMGWS, series, ACN).shaped <> (ItineraryAircraft.tupled, ItineraryAircraft.unapply)
}

class ItineraryAircraftQueryBase extends BaseDAO[ItineraryAircraft] {
  type DBTable = ItineraryAircraftMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byItineraryAircraftManufacturerId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.manufacturerId===i).list
    }.getOrElse(List())
  }
                            
}
case class ItineraryAircraftFormData(obj: ItineraryAircraft){
  def update(updatedObj: ItineraryAircraft = obj)(implicit session: Session) = {

    ItineraryAircraftQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: ItineraryAircraft)(implicit session: Session) = {
    val id = ItineraryAircraftQuery.insert(insertedObj)

    id
  }
}

object ItineraryAircraftForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "manufacturerId" -> longNumber,
      "model" -> text,
      "tpe" -> text,
      "iataCode" -> optional(text),
      "nPassengers" -> optional(number),
      "length" -> of(doubleFormat),
      "width" -> of(doubleFormat),
      "wingspan" -> of(doubleFormat),
      "OMGWS" -> of(doubleFormat),
      "series" -> optional(text),
      "ACN" -> optional(text)
    )/*(ItineraryAircraft.apply)(ItineraryAircraft.unapply)*/
    ((id,manufacturerId,model,tpe,iataCode,nPassengers,length,width,wingspan,OMGWS,series,ACN) => {
      ItineraryAircraftFormData(ItineraryAircraft(id, manufacturerId, model, tpe, iataCode, nPassengers, length, width, wingspan, OMGWS, series, ACN))
    })((formData: ItineraryAircraftFormData) => {
      Some((formData.obj.id, formData.obj.manufacturerId, formData.obj.model, formData.obj.tpe, formData.obj.iataCode, formData.obj.nPassengers, formData.obj.length, formData.obj.width, formData.obj.wingspan, formData.obj.OMGWS, formData.obj.series, formData.obj.ACN))
    })
  )
}