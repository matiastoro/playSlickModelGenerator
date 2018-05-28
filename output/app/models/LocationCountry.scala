package models
import models.extensions.LocationCountryExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class LocationCountry(id: Option[Long] = None /*None*/,
                           locationId: Long = 0 /*None*/,
                           countryId: Long = 0 /*None*/) extends LocationCountryExtension{
  lazy val selectString = id
  def getLocation = LocationConsulta.byId(locationId)
  def getCountry = CountryConsulta.byId(countryId)
}



class LocationCountryMapeo(tag: Tag) extends Table[LocationCountry](tag, "location_country") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def locationId = column[Long]("location_id", O.Default(0))
  def location = foreignKey("location_id_fk", locationId, LocationQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)
  def countryId = column[Long]("country_id", O.Default(0))
  def country = foreignKey("country_id_fk", countryId, CountryQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)

  def * = (id.?, locationId, countryId).shaped <> (LocationCountry.tupled, LocationCountry.unapply)
}

class LocationCountryConsulta extends BaseDAO[LocationCountry] {
  type DBTable = LocationCountryMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byLocationId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.locationId===i).list
    }.getOrElse(List())
  }
                            

  def byCountryId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.countryId===i).list
    }.getOrElse(List())
  }
                            
}
case class LocationCountryFormData(obj: LocationCountry){
  def update(updatedObj: LocationCountry = obj)(implicit session: Session) = {

    LocationCountryConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: LocationCountry)(implicit session: Session) = {
    val id = LocationCountryConsulta.insertar(insertedObj)

    id
  }
}

object LocationCountryForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "locationId" -> optional(longNumber),
      "countryId" -> longNumber
    )/*(LocationCountry.apply)(LocationCountry.unapply)*/
    ((id,locationId,countryId) => {
      LocationCountryFormData(LocationCountry(id, locationId.getOrElse(0), countryId))
    })((formData: LocationCountryFormData) => {
      Some((formData.obj.id, Some(formData.obj.locationId), formData.obj.countryId))
    })
  )
}