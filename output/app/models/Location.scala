package models
import models.extensions.LocationExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Location(id: Option[Long] = None /*None*/,
                    location: String = "" /*None*/,
                    locationType: Int = 0 /*None*/,
                    latitude: Double = 0 /*None*/,
                    longitude: Double = 0 /*None*/,
                    altitude: Double = 0 /*None*/,
                    slug: String = "" /*None*/) extends LocationExtension{
  lazy val selectString = location

}



class LocationMapeo(tag: Tag) extends Table[Location](tag, "location") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def location = column[String]("location", O.Default(""))
  def locationType = column[Int]("location_type", O.Default(0))
  def latitude = column[Double]("latitude", O.Default(0))
  def longitude = column[Double]("longitude", O.Default(0))
  def altitude = column[Double]("altitude", O.Default(0))
  def slug = column[String]("slug", O.Default(""))

  def * = (id.?, location, locationType, latitude, longitude, altitude, slug).shaped <> (Location.tupled, Location.unapply)
}

class LocationConsulta extends BaseDAO[Location] {
  type DBTable = LocationMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class LocationFormData(obj: Location, LocationCountrys: List[LocationCountryFormData]){
  def update(updatedObj: Location = obj)(implicit session: Session) = {
    //Delete elements that are not part of the form but they do exists in the database.
    LocationCountryConsulta.byLocationId(obj.id).filterNot{o => LocationCountrys.exists(_.obj.id == o.id)}.map{LocationCountryConsulta.elimina(_)}
    LocationCountrys.map{o => o.update(o.obj.copy(locationId = obj.id.get))}
    LocationConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: Location)(implicit session: Session) = {
    val id = LocationConsulta.insertar(insertedObj)
    LocationCountrys.map{o => o.insert(o.obj.copy(locationId = id))}
    id
  }
}
object LocationFormData{
  def apply(obj: Location) = {
    val LocationCountrys = LocationCountryConsulta.byLocationId(obj.id).map(LocationCountryFormData(_))
    new LocationFormData(obj, LocationCountrys)
  }
}
object LocationForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "location" -> text,
      "locationType" -> number,
      "latitude" -> of(doubleFormat),
      "longitude" -> of(doubleFormat),
      "altitude" -> of(doubleFormat),
      "slug" -> text,
      "LocationCountrys" -> list(models.LocationCountryForm.form.mapping)
    )/*(Location.apply)(Location.unapply)*/
    ((id,location,locationType,latitude,longitude,altitude,slug,LocationCountrys) => {
      LocationFormData(Location(id, location, locationType, latitude, longitude, altitude, slug), LocationCountrys)
    })((formData: LocationFormData) => {
      Some((formData.obj.id, formData.obj.location, formData.obj.locationType, formData.obj.latitude, formData.obj.longitude, formData.obj.altitude, formData.obj.slug, formData.LocationCountrys))
    })
  )
}