package models
//import models.extensions.FlightRuleExtension
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



case class FlightRule(id: Option[Long] = None /*None*/,
                      flightRule: String = "" /*None*/) extends FlightRuleExtension{

}

object FlightRule {
  implicit val format = Json.format[FlightRule]
  val tupled = (this.apply _).tupled
}




case class FlightRuleFormData(obj: FlightRule){
  def update(updatedObj: FlightRule = obj)(implicit repo: FlightRuleRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: FlightRule)(implicit repo: FlightRuleRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object FlightRuleFormData{
  def fapply(obj: FlightRule)(implicit ec: ExecutionContext) = {
    Future{
      new FlightRuleFormData(obj)
    }
  }
}
object FlightRuleForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "flightRule" -> nonEmptyText
    )/*(FlightRule.apply)(FlightRule.unapply)*/
    ((id,flightRule) => {
      FlightRuleFormData(FlightRule(id, flightRule))
    })((formData: FlightRuleFormData) => {
      Some((formData.obj.id, formData.obj.flightRule))
    })
  )
}