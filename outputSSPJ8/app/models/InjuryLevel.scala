package models
//import models.extensions.InjuryLevelExtension
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



case class InjuryLevel(id: Option[Long] = None /*None*/,
                       injuryLevel: String = "" /*None*/) extends InjuryLevelExtension{

}

object InjuryLevel {
  implicit val format = Json.format[InjuryLevel]
  val tupled = (this.apply _).tupled
}


case class InjuryLevelFormData(obj: InjuryLevel){
  def update(updatedObj: InjuryLevel = obj)(implicit repo: InjuryLevelRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: InjuryLevel)(implicit repo: InjuryLevelRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object InjuryLevelFormData{
  def fapply(obj: InjuryLevel)(implicit ec: ExecutionContext) = {
    Future{
      new InjuryLevelFormData(obj)
    }
  }
}
object InjuryLevelForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "injuryLevel" -> nonEmptyText
    )/*(InjuryLevel.apply)(InjuryLevel.unapply)*/
    ((id,injuryLevel) => {
      InjuryLevelFormData(InjuryLevel(id, injuryLevel))
    })((formData: InjuryLevelFormData) => {
      Some((formData.obj.id, formData.obj.injuryLevel))
    })
  )
}



case class InjuryLevelFilter(id: Option[Long] = None,
                             injuryLevel: Option[String] = None)

object InjuryLevelFilter {
  val tupled = (this.apply _).tupled
}


object InjuryLevelFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "injuryLevel" -> optional(nonEmptyText)
    )/*(InjuryLevelFilter.apply)(InjuryLevelFilter.unapply)*/
    ((id,injuryLevel) => {
      InjuryLevelFilter(id, injuryLevel)
    })((formData: InjuryLevelFilter) => {
      Some((formData.id, formData.injuryLevel))
    })
  )
}

