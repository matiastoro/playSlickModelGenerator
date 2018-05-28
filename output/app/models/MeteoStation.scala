package models
import models.extensions.MeteoStationExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class MeteoStation(id: Option[Long] = None /*None*/,
                        locationId: Option[Long] = None /*None*/,
                        wmoArgos: Int = 0 /*None*/,
                        mainBulletin: String = "" /*None*/,
                        network: String = "" /*None*/,
                        observations: String = "" /*None*/) extends MeteoStationExtension{
  lazy val selectString = mainBulletin
  def getLocation = if(locationId.isDefined) LocationConsulta.byId(locationId.get) else None
}



class MeteoStationMapeo(tag: Tag) extends Table[MeteoStation](tag, "meteo_station") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def locationId = column[Option[Long]]("location_id", O.Default(None))
  def location = foreignKey("location_id_fk", locationId, LocationQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)
  def wmoArgos = column[Int]("wmo_argos", O.Default(0))
  def mainBulletin = column[String]("main_bulletin", O.Default(""))
  def network = column[String]("network", O.Default(""))
  def observations = column[String]("observations", O.Default(""))

  def * = (id.?, locationId, wmoArgos, mainBulletin, network, observations).shaped <> (MeteoStation.tupled, MeteoStation.unapply)
}

class MeteoStationConsulta extends BaseDAO[MeteoStation] {
  type DBTable = MeteoStationMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byLocationId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.locationId===i).list
    }.getOrElse(List())
  }
                            
}
case class MeteoStationFormData(obj: MeteoStation){
  def update(updatedObj: MeteoStation = obj)(implicit session: Session) = {

    MeteoStationConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: MeteoStation)(implicit session: Session) = {
    val id = MeteoStationConsulta.insertar(insertedObj)

    id
  }
}

object MeteoStationForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "locationId" -> optional(longNumber),
      "wmoArgos" -> number,
      "mainBulletin" -> text,
      "network" -> text,
      "observations" -> text
    )/*(MeteoStation.apply)(MeteoStation.unapply)*/
    ((id,locationId,wmoArgos,mainBulletin,network,observations) => {
      MeteoStationFormData(MeteoStation(id, locationId, wmoArgos, mainBulletin, network, observations))
    })((formData: MeteoStationFormData) => {
      Some((formData.obj.id, formData.obj.locationId, formData.obj.wmoArgos, formData.obj.mainBulletin, formData.obj.network, formData.obj.observations))
    })
  )
}