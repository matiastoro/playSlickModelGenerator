package models
//import models.extensions.WeatherConditionExtension
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



case class WeatherCondition(id: Option[Long] = None /*None*/,
                            weatherCondition: String = "" /*None*/) extends WeatherConditionExtension{

}

object WeatherCondition {
  implicit val format = Json.format[WeatherCondition]
  val tupled = (this.apply _).tupled
}


case class WeatherConditionFormData(obj: WeatherCondition){
  def update(updatedObj: WeatherCondition = obj)(implicit repo: WeatherConditionRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: WeatherCondition)(implicit repo: WeatherConditionRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object WeatherConditionFormData{
  def fapply(obj: WeatherCondition)(implicit ec: ExecutionContext) = {
    Future{
      new WeatherConditionFormData(obj)
    }
  }
}
object WeatherConditionForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "weatherCondition" -> nonEmptyText
    )/*(WeatherCondition.apply)(WeatherCondition.unapply)*/
    ((id,weatherCondition) => {
      WeatherConditionFormData(WeatherCondition(id, weatherCondition))
    })((formData: WeatherConditionFormData) => {
      Some((formData.obj.id, formData.obj.weatherCondition))
    })
  )
}



case class WeatherConditionFilter(id: Option[Long] = None,
                                  weatherCondition: Option[String] = None)

object WeatherConditionFilter {
  val tupled = (this.apply _).tupled
}


object WeatherConditionFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "weatherCondition" -> optional(nonEmptyText)
    )/*(WeatherConditionFilter.apply)(WeatherConditionFilter.unapply)*/
    ((id,weatherCondition) => {
      WeatherConditionFilter(id, weatherCondition)
    })((formData: WeatherConditionFilter) => {
      Some((formData.id, formData.weatherCondition))
    })
  )
}

