package models
//import models.extensions.RunwayExtension
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



case class Runway(id: Option[Long] = None /*None*/,
                  runway: String = "" /*None*/,
                  runwaySurfaceTypeId: Long = 0L /*None*/,
                  aerodromeId: Long = 0L /*None*/) extends RunwayExtension{

}

object Runway {
  implicit val format = Json.format[Runway]
  val tupled = (this.apply _).tupled
}


case class RunwayFormData(obj: Runway){
  def update(updatedObj: Runway = obj)(implicit repo: RunwayRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Runway)(implicit repo: RunwayRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object RunwayFormData{
  def fapply(obj: Runway)(implicit ec: ExecutionContext) = {
    Future{
      new RunwayFormData(obj)
    }
  }
}
object RunwayForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "runway" -> nonEmptyText,
      "runwaySurfaceTypeId" -> longNumber,
      "aerodromeId" -> optional(longNumber)
    )/*(Runway.apply)(Runway.unapply)*/
    ((id,runway,runwaySurfaceTypeId,aerodromeId) => {
      RunwayFormData(Runway(id, runway, runwaySurfaceTypeId, aerodromeId.getOrElse(0)))
    })((formData: RunwayFormData) => {
      Some((formData.obj.id, formData.obj.runway, formData.obj.runwaySurfaceTypeId, Some(formData.obj.aerodromeId)))
    })
  )
}



case class RunwayFilter(id: Option[Long] = None,
                        runway: Option[String] = None,
                        runwaySurfaceTypeId: Option[Long] = None,
                        aerodromeId: Option[Long] = None)

object RunwayFilter {
  val tupled = (this.apply _).tupled
}


object RunwayFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "runway" -> optional(nonEmptyText),
      "runwaySurfaceTypeId" -> optional(longNumber),
      "aerodromeId" -> optional(longNumber)
    )/*(RunwayFilter.apply)(RunwayFilter.unapply)*/
    ((id,runway,runwaySurfaceTypeId,aerodromeId) => {
      RunwayFilter(id, runway, runwaySurfaceTypeId, aerodromeId)
    })((formData: RunwayFilter) => {
      Some((formData.id, formData.runway, formData.runwaySurfaceTypeId, formData.aerodromeId))
    })
  )
}

