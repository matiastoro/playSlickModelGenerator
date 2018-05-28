package models
//import models.extensions.HelicopterLandingAreaExtension
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



case class HelicopterLandingArea(id: Option[Long] = None /*None*/,
                                 helicopterLandingArea: String = "" /*None*/,
                                 aerodromeId: Long = 0L /*None*/) extends HelicopterLandingAreaExtension{

}

object HelicopterLandingArea {
  implicit val format = Json.format[HelicopterLandingArea]
  val tupled = (this.apply _).tupled
}




case class HelicopterLandingAreaFormData(obj: HelicopterLandingArea){
  def update(updatedObj: HelicopterLandingArea = obj)(implicit repo: HelicopterLandingAreaRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: HelicopterLandingArea)(implicit repo: HelicopterLandingAreaRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object HelicopterLandingAreaFormData{
  def fapply(obj: HelicopterLandingArea)(implicit ec: ExecutionContext) = {
    Future{
      new HelicopterLandingAreaFormData(obj)
    }
  }
}
object HelicopterLandingAreaForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "helicopterLandingArea" -> nonEmptyText,
      "aerodromeId" -> optional(longNumber)
    )/*(HelicopterLandingArea.apply)(HelicopterLandingArea.unapply)*/
    ((id,helicopterLandingArea,aerodromeId) => {
      HelicopterLandingAreaFormData(HelicopterLandingArea(id, helicopterLandingArea, aerodromeId.getOrElse(0)))
    })((formData: HelicopterLandingAreaFormData) => {
      Some((formData.obj.id, formData.obj.helicopterLandingArea, Some(formData.obj.aerodromeId)))
    })
  )
}