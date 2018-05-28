package models
//import models.extensions.HelicopterLandingAreaAreaConfigurationExtension
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



case class HelicopterLandingAreaAreaConfiguration(id: Option[Long] = None /*None*/,
                                                  areaConfiguration: String = "" /*None*/) extends HelicopterLandingAreaAreaConfigurationExtension{

}

object HelicopterLandingAreaAreaConfiguration {
  implicit val format = Json.format[HelicopterLandingAreaAreaConfiguration]
  val tupled = (this.apply _).tupled
}




case class HelicopterLandingAreaAreaConfigurationFormData(obj: HelicopterLandingAreaAreaConfiguration){
  def update(updatedObj: HelicopterLandingAreaAreaConfiguration = obj)(implicit repo: HelicopterLandingAreaAreaConfigurationRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: HelicopterLandingAreaAreaConfiguration)(implicit repo: HelicopterLandingAreaAreaConfigurationRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object HelicopterLandingAreaAreaConfigurationFormData{
  def fapply(obj: HelicopterLandingAreaAreaConfiguration)(implicit ec: ExecutionContext) = {
    Future{
      new HelicopterLandingAreaAreaConfigurationFormData(obj)
    }
  }
}
object HelicopterLandingAreaAreaConfigurationForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "areaConfiguration" -> nonEmptyText
    )/*(HelicopterLandingAreaAreaConfiguration.apply)(HelicopterLandingAreaAreaConfiguration.unapply)*/
    ((id,areaConfiguration) => {
      HelicopterLandingAreaAreaConfigurationFormData(HelicopterLandingAreaAreaConfiguration(id, areaConfiguration))
    })((formData: HelicopterLandingAreaAreaConfigurationFormData) => {
      Some((formData.obj.id, formData.obj.areaConfiguration))
    })
  )
}