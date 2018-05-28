package models
import models.extensions.SubantarticStationExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class SubantarticStation(id: Option[Long] = None /*None*/,
                              locationId: Long = 0 /*None*/,
                              wmoArgos: Int = 0 /*None*/,
                              mainBulletin: String = "" /*None*/,
                              network: String = "" /*None*/,
                              observations: String = "" /*None*/) extends SubantarticStationExtension{
  lazy val selectString = mainBulletin
  def getLocation = LocationConsulta.byId(locationId)
}



class SubantarticStationMapeo(tag: Tag) extends Table[SubantarticStation](tag, "subantartic_station") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def locationId = column[Long]("location_id", O.Default(0))
  def location = foreignKey("location_id_fk", locationId, LocationQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)
  def wmoArgos = column[Int]("wmo_argos", O.Default(0))
  def mainBulletin = column[String]("main_bulletin", O.Default(""))
  def network = column[String]("network", O.Default(""))
  def observations = column[String]("observations", O.Default(""))

  def * = (id.?, locationId, wmoArgos, mainBulletin, network, observations).shaped <> (SubantarticStation.tupled, SubantarticStation.unapply)
}

class SubantarticStationConsulta extends BaseDAO[SubantarticStation] {
  type DBTable = SubantarticStationMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byLocationId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.locationId===i).list
    }.getOrElse(List())
  }
                            
}
case class SubantarticStationFormData(obj: SubantarticStation){
  def update(updatedObj: SubantarticStation = obj)(implicit session: Session) = {

    SubantarticStationConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: SubantarticStation)(implicit session: Session) = {
    val id = SubantarticStationConsulta.insertar(insertedObj)

    id
  }
}

object SubantarticStationForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "locationId" -> longNumber,
      "wmoArgos" -> number,
      "mainBulletin" -> text,
      "network" -> text,
      "observations" -> text
    )/*(SubantarticStation.apply)(SubantarticStation.unapply)*/
    ((id,locationId,wmoArgos,mainBulletin,network,observations) => {
      SubantarticStationFormData(SubantarticStation(id, locationId, wmoArgos, mainBulletin, network, observations))
    })((formData: SubantarticStationFormData) => {
      Some((formData.obj.id, formData.obj.locationId, formData.obj.wmoArgos, formData.obj.mainBulletin, formData.obj.network, formData.obj.observations))
    })
  )
}