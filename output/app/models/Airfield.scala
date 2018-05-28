package models
import models.extensions.AirfieldExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Airfield(id: Option[Long] = None /*None*/,
                    locationId: Long = 0 /*None*/,
                    stationFirstOpened: Option[Int] = None /*None*/,
                    icaoCode: Option[String] = None /*None*/,
                    iataCode: Option[String] = None /*None*/) extends AirfieldExtension{
  lazy val selectString = icaoCode
  def getLocation = LocationConsulta.byId(locationId)
}



class AirfieldMapeo(tag: Tag) extends Table[Airfield](tag, "airfield") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def locationId = column[Long]("location_id", O.Default(0))
  def location = foreignKey("location_id_fk", locationId, LocationQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)
  def stationFirstOpened = column[Option[Int]]("station_first_opened", O.Default(None))
  def icaoCode = column[Option[String]]("icao_code", O.Default(None))
  def iataCode = column[Option[String]]("iata_code", O.Default(None))

  def * = (id.?, locationId, stationFirstOpened, icaoCode, iataCode).shaped <> (Airfield.tupled, Airfield.unapply)
}

class AirfieldConsulta extends BaseDAO[Airfield] {
  type DBTable = AirfieldMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byLocationId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.locationId===i).list
    }.getOrElse(List())
  }
                            
}
case class AirfieldFormData(obj: Airfield){
  def update(updatedObj: Airfield = obj)(implicit session: Session) = {

    AirfieldConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: Airfield)(implicit session: Session) = {
    val id = AirfieldConsulta.insertar(insertedObj)

    id
  }
}

object AirfieldForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "locationId" -> longNumber,
      "stationFirstOpened" -> optional(number),
      "icaoCode" -> optional(text),
      "iataCode" -> optional(text)
    )/*(Airfield.apply)(Airfield.unapply)*/
    ((id,locationId,stationFirstOpened,icaoCode,iataCode) => {
      AirfieldFormData(Airfield(id, locationId, stationFirstOpened, icaoCode, iataCode))
    })((formData: AirfieldFormData) => {
      Some((formData.obj.id, formData.obj.locationId, formData.obj.stationFirstOpened, formData.obj.icaoCode, formData.obj.iataCode))
    })
  )
}