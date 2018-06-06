package models
//import models.extensions.StateAreaExtension
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



case class StateArea(id: Option[Long] = None /*None*/,
                     stateArea: String = "" /*None*/,
                     stateAreaId: Option[Long] = None /*None*/) extends StateAreaExtension{

}

object StateArea {
  implicit val format = Json.format[StateArea]
  val tupled = (this.apply _).tupled
}


case class StateAreaFormData(obj: StateArea){
  def update(updatedObj: StateArea = obj)(implicit repo: StateAreaRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: StateArea)(implicit repo: StateAreaRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object StateAreaFormData{
  def fapply(obj: StateArea)(implicit ec: ExecutionContext) = {
    Future{
      new StateAreaFormData(obj)
    }
  }
}
object StateAreaForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "stateArea" -> nonEmptyText,
      "stateAreaId" -> optional(longNumber)
    )/*(StateArea.apply)(StateArea.unapply)*/
    ((id,stateArea,stateAreaId) => {
      StateAreaFormData(StateArea(id, stateArea, stateAreaId))
    })((formData: StateAreaFormData) => {
      Some((formData.obj.id, formData.obj.stateArea, formData.obj.stateAreaId))
    })
  )
}



case class StateAreaFilter(id: Option[Long] = None,
                           stateArea: Option[String] = None,
                           stateAreaId: Option[Long] = None)

object StateAreaFilter {
  val tupled = (this.apply _).tupled
}


object StateAreaFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "stateArea" -> optional(nonEmptyText),
      "stateAreaId" -> optional(longNumber)
    )/*(StateAreaFilter.apply)(StateAreaFilter.unapply)*/
    ((id,stateArea,stateAreaId) => {
      StateAreaFilter(id, stateArea, stateAreaId)
    })((formData: StateAreaFilter) => {
      Some((formData.id, formData.stateArea, formData.stateAreaId))
    })
  )
}

