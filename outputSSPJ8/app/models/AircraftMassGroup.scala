package models
//import models.extensions.AircraftMassGroupExtension
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



case class AircraftMassGroup(id: Option[Long] = None /*None*/,
                             massGroup: String = "" /*None*/) extends AircraftMassGroupExtension{

}

object AircraftMassGroup {
  implicit val format = Json.format[AircraftMassGroup]
  val tupled = (this.apply _).tupled
}




case class AircraftMassGroupFormData(obj: AircraftMassGroup){
  def update(updatedObj: AircraftMassGroup = obj)(implicit repo: AircraftMassGroupRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: AircraftMassGroup)(implicit repo: AircraftMassGroupRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object AircraftMassGroupFormData{
  def fapply(obj: AircraftMassGroup)(implicit ec: ExecutionContext) = {
    Future{
      new AircraftMassGroupFormData(obj)
    }
  }
}
object AircraftMassGroupForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "massGroup" -> nonEmptyText
    )/*(AircraftMassGroup.apply)(AircraftMassGroup.unapply)*/
    ((id,massGroup) => {
      AircraftMassGroupFormData(AircraftMassGroup(id, massGroup))
    })((formData: AircraftMassGroupFormData) => {
      Some((formData.obj.id, formData.obj.massGroup))
    })
  )
}