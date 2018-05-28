package models
//import models.extensions.AerodromeLocationExtension
import extensions._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import play.api.data.Form
import play.api.data.Forms._
//support for joda is now a separate project
import play.api.data.JodaForms._
import play.api.data.format.Formats._
import org.joda.time.{DateTime, LocalDate, DateTimeZone}
//import com.github.tototoshi.slick.H2JodaSupport._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._
//import scala.concurrent.ExecutionContext.Implicits.global

/*import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._


import play.api.libs.json._*/



case class AerodromeLocation(id: Option[Long] = None /*None*/,
                             location: String = "" /*None*/) extends AerodromeLocationExtension{

}

object AerodromeLocation {
  implicit val format = Json.format[AerodromeLocation]
  val tupled = (this.apply _).tupled
}




case class AerodromeLocationFormData(obj: AerodromeLocation){
  def update(updatedObj: AerodromeLocation = obj)(implicit repo: AerodromeLocationRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: AerodromeLocation)(implicit repo: AerodromeLocationRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object AerodromeLocationFormData{
  def fapply(obj: AerodromeLocation)(implicit ec: ExecutionContext) = {
    Future{
      new AerodromeLocationFormData(obj)
    }
  }
}
object AerodromeLocationForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "location" -> nonEmptyText
    )/*(AerodromeLocation.apply)(AerodromeLocation.unapply)*/
    ((id,location) => {
      AerodromeLocationFormData(AerodromeLocation(id, location))
    })((formData: AerodromeLocationFormData) => {
      Some((formData.obj.id, formData.obj.location))
    })
  )
}