package models
//import models.extensions.AerodromeExtension
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



case class Aerodrome(id: Option[Long] = None /*None*/,
                     icaoCode: String = "" /*None*/,
                     aerodromeStatusId: Long = 0L /*None*/,
                     aerodromeTypeId: Long = 0L /*None*/,
                     latitude: Option[String] = None /*None*/,
                     longitude: Option[String] = None /*None*/,
                     elevationAboveMsl: Option[Double] = None /*None*/) extends AerodromeExtension{

}

object Aerodrome {
  implicit val format = Json.format[Aerodrome]
  val tupled = (this.apply _).tupled
}




case class AerodromeFormData(obj: Aerodrome, runways: List[RunwayFormData], helicopterLandingAreas: List[HelicopterLandingAreaFormData]){
  def update(updatedObj: Aerodrome = obj)(implicit repo: AerodromeRepository, runwayRepo: RunwayRepository, helicopterLandingAreaRepo: HelicopterLandingAreaRepository, ec: ExecutionContext) = {
    //Delete elements that are not part of the form but they do exists in the database.
    runwayRepo.byAerodromeId(obj.id).map{ l =>  l.filterNot{o => runways.exists(_.obj.id == o.id)}.map{runwayRepo.delete(_)}}
    runways.map{o => o.update(o.obj.copy(aerodromeId = obj.id.get))}
    //Delete elements that are not part of the form but they do exists in the database.
    helicopterLandingAreaRepo.byAerodromeId(obj.id).map{ l =>  l.filterNot{o => helicopterLandingAreas.exists(_.obj.id == o.id)}.map{helicopterLandingAreaRepo.delete(_)}}
    helicopterLandingAreas.map{o => o.update(o.obj.copy(aerodromeId = obj.id.get))}
    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Aerodrome)(implicit repo: AerodromeRepository, runwayRepo: RunwayRepository, helicopterLandingAreaRepo: HelicopterLandingAreaRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future.sequence(    runways.map{o => o.insert(o.obj.copy(aerodromeId = id))}++    helicopterLandingAreas.map{o => o.insert(o.obj.copy(aerodromeId = id))}).map{ x =>
        id
      }
    
    }
  }
}
object AerodromeFormData{
  def fapply(obj: Aerodrome)(implicit repo: AerodromeRepository, runwayRepo: RunwayRepository, helicopterLandingAreaRepo: HelicopterLandingAreaRepository, ec: ExecutionContext) = {
    for{
      runways <- runwayRepo.byAerodromeId(obj.id).flatMap(l => Future.sequence(l.map(RunwayFormData.fapply(_))))
      helicopterLandingAreas <- helicopterLandingAreaRepo.byAerodromeId(obj.id).flatMap(l => Future.sequence(l.map(HelicopterLandingAreaFormData.fapply(_))))
    } yield{
      new AerodromeFormData(obj, runways.toList, helicopterLandingAreas.toList)
    }
  }
}
object AerodromeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "icaoCode" -> nonEmptyText,
      "aerodromeStatusId" -> longNumber,
      "aerodromeTypeId" -> longNumber,
      "latitude" -> optional(nonEmptyText),
      "longitude" -> optional(nonEmptyText),
      "elevationAboveMsl" -> optional(of(doubleFormat)),
      "runways" -> list(models.RunwayForm.form.mapping),
      "helicopterLandingAreas" -> list(models.HelicopterLandingAreaForm.form.mapping)
    )/*(Aerodrome.apply)(Aerodrome.unapply)*/
    ((id,icaoCode,aerodromeStatusId,aerodromeTypeId,latitude,longitude,elevationAboveMsl,runways,helicopterLandingAreas) => {
      AerodromeFormData(Aerodrome(id, icaoCode, aerodromeStatusId, aerodromeTypeId, latitude, longitude, elevationAboveMsl), runways, helicopterLandingAreas)
    })((formData: AerodromeFormData) => {
      Some((formData.obj.id, formData.obj.icaoCode, formData.obj.aerodromeStatusId, formData.obj.aerodromeTypeId, formData.obj.latitude, formData.obj.longitude, formData.obj.elevationAboveMsl, formData.runways, formData.helicopterLandingAreas))
    })
  )
}