package models
//import models.extensions.HelicopterLandingAreaAreaTypeExtension
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



case class HelicopterLandingAreaAreaType(id: Option[Long] = None /*None*/,
                                         helicopterLandingArea: String = "" /*None*/,
                                         helicopterLandingAreaTypeId: Long = 0L /*None*/,
                                         helicopterLandingAreaAreaConfigurationId: Long = 0L /*None*/,
                                         helicopterLandingAreaSurfaceTypeId: Long = 0L /*None*/) extends HelicopterLandingAreaAreaTypeExtension{

}

object HelicopterLandingAreaAreaType {
  implicit val format = Json.format[HelicopterLandingAreaAreaType]
  val tupled = (this.apply _).tupled
}




case class HelicopterLandingAreaAreaTypeFormData(obj: HelicopterLandingAreaAreaType){
  def update(updatedObj: HelicopterLandingAreaAreaType = obj)(implicit repo: HelicopterLandingAreaAreaTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: HelicopterLandingAreaAreaType)(implicit repo: HelicopterLandingAreaAreaTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object HelicopterLandingAreaAreaTypeFormData{
  def fapply(obj: HelicopterLandingAreaAreaType)(implicit ec: ExecutionContext) = {
    Future{
      new HelicopterLandingAreaAreaTypeFormData(obj)
    }
  }
}
object HelicopterLandingAreaAreaTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "helicopterLandingArea" -> nonEmptyText,
      "helicopterLandingAreaTypeId" -> longNumber,
      "helicopterLandingAreaAreaConfigurationId" -> longNumber,
      "helicopterLandingAreaSurfaceTypeId" -> longNumber
    )/*(HelicopterLandingAreaAreaType.apply)(HelicopterLandingAreaAreaType.unapply)*/
    ((id,helicopterLandingArea,helicopterLandingAreaTypeId,helicopterLandingAreaAreaConfigurationId,helicopterLandingAreaSurfaceTypeId) => {
      HelicopterLandingAreaAreaTypeFormData(HelicopterLandingAreaAreaType(id, helicopterLandingArea, helicopterLandingAreaTypeId, helicopterLandingAreaAreaConfigurationId, helicopterLandingAreaSurfaceTypeId))
    })((formData: HelicopterLandingAreaAreaTypeFormData) => {
      Some((formData.obj.id, formData.obj.helicopterLandingArea, formData.obj.helicopterLandingAreaTypeId, formData.obj.helicopterLandingAreaAreaConfigurationId, formData.obj.helicopterLandingAreaSurfaceTypeId))
    })
  )
}