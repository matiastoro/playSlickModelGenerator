package models
//import models.extensions.TrafficTypeExtension
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



case class TrafficType(id: Option[Long] = None /*None*/,
                       trafficType: String = "" /*None*/) extends TrafficTypeExtension{

}

object TrafficType {
  implicit val format = Json.format[TrafficType]
  val tupled = (this.apply _).tupled
}


case class TrafficTypeFormData(obj: TrafficType){
  def update(updatedObj: TrafficType = obj)(implicit repo: TrafficTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: TrafficType)(implicit repo: TrafficTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object TrafficTypeFormData{
  def fapply(obj: TrafficType)(implicit ec: ExecutionContext) = {
    Future{
      new TrafficTypeFormData(obj)
    }
  }
}
object TrafficTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "trafficType" -> nonEmptyText
    )/*(TrafficType.apply)(TrafficType.unapply)*/
    ((id,trafficType) => {
      TrafficTypeFormData(TrafficType(id, trafficType))
    })((formData: TrafficTypeFormData) => {
      Some((formData.obj.id, formData.obj.trafficType))
    })
  )
}



case class TrafficTypeFilter(id: Option[Long] = None,
                             trafficType: Option[String] = None)

object TrafficTypeFilter {
  val tupled = (this.apply _).tupled
}


object TrafficTypeFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "trafficType" -> optional(nonEmptyText)
    )/*(TrafficTypeFilter.apply)(TrafficTypeFilter.unapply)*/
    ((id,trafficType) => {
      TrafficTypeFilter(id, trafficType)
    })((formData: TrafficTypeFilter) => {
      Some((formData.id, formData.trafficType))
    })
  )
}

