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
                                 aerodromeId: Long = 0L /*None*/,
                                 helicopterLandingAreaTypeId: Long = 0L /*None*/,
                                 helicopterLandingAreaAreaConfigurationId: Long = 0L /*None*/,
                                 helicopterLandingAreaSurfaceTypeId: Long = 0L /*None*/) extends HelicopterLandingAreaExtension{

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
      "aerodromeId" -> optional(longNumber),
      "helicopterLandingAreaTypeId" -> longNumber,
      "helicopterLandingAreaAreaConfigurationId" -> longNumber,
      "helicopterLandingAreaSurfaceTypeId" -> longNumber
    )/*(HelicopterLandingArea.apply)(HelicopterLandingArea.unapply)*/
    ((id,helicopterLandingArea,aerodromeId,helicopterLandingAreaTypeId,helicopterLandingAreaAreaConfigurationId,helicopterLandingAreaSurfaceTypeId) => {
      HelicopterLandingAreaFormData(HelicopterLandingArea(id, helicopterLandingArea, aerodromeId.getOrElse(0), helicopterLandingAreaTypeId, helicopterLandingAreaAreaConfigurationId, helicopterLandingAreaSurfaceTypeId))
    })((formData: HelicopterLandingAreaFormData) => {
      Some((formData.obj.id, formData.obj.helicopterLandingArea, Some(formData.obj.aerodromeId), formData.obj.helicopterLandingAreaTypeId, formData.obj.helicopterLandingAreaAreaConfigurationId, formData.obj.helicopterLandingAreaSurfaceTypeId))
    })
  )
}



case class HelicopterLandingAreaFilter(id: Option[Long] = None,
                                       helicopterLandingArea: Option[String] = None,
                                       aerodromeId: Option[Long] = None,
                                       helicopterLandingAreaTypeId: Option[Long] = None,
                                       helicopterLandingAreaAreaConfigurationId: Option[Long] = None,
                                       helicopterLandingAreaSurfaceTypeId: Option[Long] = None)

object HelicopterLandingAreaFilter {
  val tupled = (this.apply _).tupled
}


object HelicopterLandingAreaFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "helicopterLandingArea" -> optional(nonEmptyText),
      "aerodromeId" -> optional(longNumber),
      "helicopterLandingAreaTypeId" -> optional(longNumber),
      "helicopterLandingAreaAreaConfigurationId" -> optional(longNumber),
      "helicopterLandingAreaSurfaceTypeId" -> optional(longNumber)
    )/*(HelicopterLandingAreaFilter.apply)(HelicopterLandingAreaFilter.unapply)*/
    ((id,helicopterLandingArea,aerodromeId,helicopterLandingAreaTypeId,helicopterLandingAreaAreaConfigurationId,helicopterLandingAreaSurfaceTypeId) => {
      HelicopterLandingAreaFilter(id, helicopterLandingArea, aerodromeId, helicopterLandingAreaTypeId, helicopterLandingAreaAreaConfigurationId, helicopterLandingAreaSurfaceTypeId)
    })((formData: HelicopterLandingAreaFilter) => {
      Some((formData.id, formData.helicopterLandingArea, formData.aerodromeId, formData.helicopterLandingAreaTypeId, formData.helicopterLandingAreaAreaConfigurationId, formData.helicopterLandingAreaSurfaceTypeId))
    })
  )
}

