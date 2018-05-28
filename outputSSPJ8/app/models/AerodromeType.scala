package models
//import models.extensions.AerodromeTypeExtension
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



case class AerodromeType(id: Option[Long] = None /*None*/,
                         tpe: String = "" /*None*/) extends AerodromeTypeExtension{

}

object AerodromeType {
  implicit val format = Json.format[AerodromeType]
  val tupled = (this.apply _).tupled
}




case class AerodromeTypeFormData(obj: AerodromeType){
  def update(updatedObj: AerodromeType = obj)(implicit repo: AerodromeTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: AerodromeType)(implicit repo: AerodromeTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object AerodromeTypeFormData{
  def fapply(obj: AerodromeType)(implicit ec: ExecutionContext) = {
    Future{
      new AerodromeTypeFormData(obj)
    }
  }
}
object AerodromeTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "tpe" -> nonEmptyText
    )/*(AerodromeType.apply)(AerodromeType.unapply)*/
    ((id,tpe) => {
      AerodromeTypeFormData(AerodromeType(id, tpe))
    })((formData: AerodromeTypeFormData) => {
      Some((formData.obj.id, formData.obj.tpe))
    })
  )
}