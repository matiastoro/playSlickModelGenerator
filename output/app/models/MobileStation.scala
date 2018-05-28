package models
import models.extensions.MobileStationExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class MobileStation(id: Option[Long] = None /*None*/,
                         locationId: Long = 0 /*None*/,
                         mainBulletin: String = "" /*None*/,
                         network: String = "" /*None*/,
                         observations: String = "" /*None*/) extends MobileStationExtension{
  lazy val selectString = mainBulletin
  def getLocation = LocationConsulta.byId(locationId)
}



class MobileStationMapeo(tag: Tag) extends Table[MobileStation](tag, "mobile_station") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def locationId = column[Long]("location_id", O.Default(0))
  def location = foreignKey("location_id_fk", locationId, LocationQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)
  def mainBulletin = column[String]("main_bulletin", O.Default(""))
  def network = column[String]("network", O.Default(""))
  def observations = column[String]("observations", O.Default(""))

  def * = (id.?, locationId, mainBulletin, network, observations).shaped <> (MobileStation.tupled, MobileStation.unapply)
}

class MobileStationConsulta extends BaseDAO[MobileStation] {
  type DBTable = MobileStationMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byLocationId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.locationId===i).list
    }.getOrElse(List())
  }
                            
}
case class MobileStationFormData(obj: MobileStation){
  def update(updatedObj: MobileStation = obj)(implicit session: Session) = {

    MobileStationConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: MobileStation)(implicit session: Session) = {
    val id = MobileStationConsulta.insertar(insertedObj)

    id
  }
}

object MobileStationForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "locationId" -> longNumber,
      "mainBulletin" -> text,
      "network" -> text,
      "observations" -> text
    )/*(MobileStation.apply)(MobileStation.unapply)*/
    ((id,locationId,mainBulletin,network,observations) => {
      MobileStationFormData(MobileStation(id, locationId, mainBulletin, network, observations))
    })((formData: MobileStationFormData) => {
      Some((formData.obj.id, formData.obj.locationId, formData.obj.mainBulletin, formData.obj.network, formData.obj.observations))
    })
  )
}