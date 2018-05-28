package models
//import models.extensions.OcurrenceClassExtension
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



case class OcurrenceClass(id: Option[Long] = None /*None*/,
                          ocurrenceClass: String = "" /*None*/) extends OcurrenceClassExtension{

}

object OcurrenceClass {
  implicit val format = Json.format[OcurrenceClass]
  val tupled = (this.apply _).tupled
}




case class OcurrenceClassFormData(obj: OcurrenceClass){
  def update(updatedObj: OcurrenceClass = obj)(implicit repo: OcurrenceClassRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: OcurrenceClass)(implicit repo: OcurrenceClassRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object OcurrenceClassFormData{
  def fapply(obj: OcurrenceClass)(implicit ec: ExecutionContext) = {
    Future{
      new OcurrenceClassFormData(obj)
    }
  }
}
object OcurrenceClassForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "ocurrenceClass" -> nonEmptyText
    )/*(OcurrenceClass.apply)(OcurrenceClass.unapply)*/
    ((id,ocurrenceClass) => {
      OcurrenceClassFormData(OcurrenceClass(id, ocurrenceClass))
    })((formData: OcurrenceClassFormData) => {
      Some((formData.obj.id, formData.obj.ocurrenceClass))
    })
  )
}