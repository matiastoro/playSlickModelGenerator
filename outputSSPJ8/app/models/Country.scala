package models
import models.extensions.CountryExtension
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import org.joda.time.{DateTime, LocalDate}
//import com.github.tototoshi.slick.H2JodaSupport._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._


/*import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._


import play.api.libs.json._*/

case class Country(id: Option[Long] = None /*None*/,
                   country: String = "" /*None*/) extends CountryExtension{
  lazy val selectString = country
}

object Country {
  implicit val format = Json.format[Country]
  val tupled = (this.apply _).tupled
}




case class CountryFormData(obj: Country){
  def update(updatedObj: Country = obj)(implicit repo: CountryRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Country)(implicit repo: CountryRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
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