package models
//import models.extensions.TypeOfAirspeedExtension
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



case class TypeOfAirspeed(id: Option[Long] = None /*None*/,
                          typeOfAirspeed: String = "" /*None*/) extends TypeOfAirspeedExtension{

}

object TypeOfAirspeed {
  implicit val format = Json.format[TypeOfAirspeed]
  val tupled = (this.apply _).tupled
}




case class TypeOfAirspeedFormData(obj: TypeOfAirspeed){
  def update(updatedObj: TypeOfAirspeed = obj)(implicit repo: TypeOfAirspeedRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: TypeOfAirspeed)(implicit repo: TypeOfAirspeedRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object TypeOfAirspeedFormData{
  def fapply(obj: TypeOfAirspeed)(implicit ec: ExecutionContext) = {
    Future{
      new TypeOfAirspeedFormData(obj)
    }
  }
}
object TypeOfAirspeedForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "typeOfAirspeed" -> nonEmptyText
    )/*(TypeOfAirspeed.apply)(TypeOfAirspeed.unapply)*/
    ((id,typeOfAirspeed) => {
      TypeOfAirspeedFormData(TypeOfAirspeed(id, typeOfAirspeed))
    })((formData: TypeOfAirspeedFormData) => {
      Some((formData.obj.id, formData.obj.typeOfAirspeed))
    })
  )
}