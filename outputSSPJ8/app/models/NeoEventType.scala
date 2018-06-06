package models
//import models.extensions.NeoEventTypeExtension
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



case class NeoEventType(id: Option[Long] = None /*None*/,
                        eventType: String = "" /*None*/) extends NeoEventTypeExtension{

}

object NeoEventType {
  implicit val format = Json.format[NeoEventType]
  val tupled = (this.apply _).tupled
}


case class NeoEventTypeFormData(obj: NeoEventType){
  def update(updatedObj: NeoEventType = obj)(implicit repo: NeoEventTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: NeoEventType)(implicit repo: NeoEventTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object NeoEventTypeFormData{
  def fapply(obj: NeoEventType)(implicit ec: ExecutionContext) = {
    Future{
      new NeoEventTypeFormData(obj)
    }
  }
}
object NeoEventTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "eventType" -> nonEmptyText
    )/*(NeoEventType.apply)(NeoEventType.unapply)*/
    ((id,eventType) => {
      NeoEventTypeFormData(NeoEventType(id, eventType))
    })((formData: NeoEventTypeFormData) => {
      Some((formData.obj.id, formData.obj.eventType))
    })
  )
}



case class NeoEventTypeFilter(id: Option[Long] = None,
                              eventType: Option[String] = None)

object NeoEventTypeFilter {
  val tupled = (this.apply _).tupled
}


object NeoEventTypeFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "eventType" -> optional(nonEmptyText)
    )/*(NeoEventTypeFilter.apply)(NeoEventTypeFilter.unapply)*/
    ((id,eventType) => {
      NeoEventTypeFilter(id, eventType)
    })((formData: NeoEventTypeFilter) => {
      Some((formData.id, formData.eventType))
    })
  )
}

