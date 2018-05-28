package models
import models.extensions.CountryExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Country(id: Option[Long] = None /*None*/,
                   country: String = "" /*None*/) extends CountryExtension{
  lazy val selectString = country

}



class CountryMapeo(tag: Tag) extends Table[Country](tag, "country") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def country = column[String]("country", O.Default(""))

  def * = (id.?, country).shaped <> (Country.tupled, Country.unapply)
}

class CountryConsulta extends BaseDAO[Country] {
  type DBTable = CountryMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class CountryFormData(obj: Country){
  def update(updatedObj: Country = obj)(implicit session: Session) = {

    CountryConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: Country)(implicit session: Session) = {
    val id = CountryConsulta.insertar(insertedObj)

    id
  }
}

object CountryForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "country" -> text
    )/*(Country.apply)(Country.unapply)*/
    ((id,country) => {
      CountryFormData(Country(id, country))
    })((formData: CountryFormData) => {
      Some((formData.obj.id, formData.obj.country))
    })
  )
}