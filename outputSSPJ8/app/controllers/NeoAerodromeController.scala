package controllers
import javax.inject._
import models._
import models.extensions._
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.Controller
import play.api.Play.current
import play.api.mvc._
import play.api.mvc.BodyParsers._
import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.concurrent.Akka
import models._
import models.extensions._
import org.joda.time.{DateTimeZone, DateTime}
import scala.concurrent.{ExecutionContext, Future}
import play.api.i18n.Messages


class NeoAerodromeController @Inject()(cc: MessagesControllerComponents)(implicit val repo: NeoAerodromeRepository, aerodrome_locationRepo: AerodromeLocationRepository, helicopter_landing_area_area_configurationRepo: HelicopterLandingAreaAreaConfigurationRepository, neoEventTypePhaseRepo: NeoEventTypePhaseRepository, runwayRepo: RunwayRepository, detection_phaseRepo: DetectionPhaseRepository, helicopter_landing_area_typeRepo: HelicopterLandingAreaTypeRepository, helicopter_landing_area_surface_typeRepo: HelicopterLandingAreaSurfaceTypeRepository, aerodrome_statusRepo: AerodromeStatusRepository, aerodrome_typeRepo: AerodromeTypeRepository, ocurrence_classRepo: OcurrenceClassRepository, neo_report_statusRepo: NeoReportStatusRepository, reporting_entityRepo: ReportingEntityRepository, aerodromeRepo: AerodromeRepository, runway_surface_typeRepo: RunwaySurfaceTypeRepository, neoAerodromeOcurrenceCategoryRepo: NeoAerodromeOcurrenceCategoryRepository, state_areaRepo: StateAreaRepository, ec: ExecutionContext) extends MessagesAbstractController(cc) with Autorization {

/*
GET         /neoAerodrome/                  controllers.NeoAerodromeController.index(page: Int = 1, pageLength: Int = 20)
GET         /neoAerodrome/show/:id          controllers.NeoAerodromeController.show(id: Long)
GET         /neoAerodrome/delete/:id          controllers.NeoAerodromeController.delete(id: Long)
POST        /neoAerodrome/save              controllers.NeoAerodromeController.save()
POST        /neoAerodrome/update/:id        controllers.NeoAerodromeController.update(id: Long)
GET         /neoAerodrome/options             controllers.NeoAerodromeController.options()
GET         /neoAerodrome/:page/:pageLength controllers.NeoAerodromeController.index(page: Int, pageLength: Int)
GET         /neoAerodrome/:page             controllers.NeoAerodromeController.index(page: Int, pageLength: Int = 20)
*/

  def getQuery[A](implicit request: MessagesRequest[A]) = {
    if(!request.queryString.isEmpty){
      filterForm.bindFromRequest.fold(
        formWithErrors => {
          repo.all
        }, formData => {
          repo.filter(formData)
        })
    } else{
      repo.all
    }
  }

  def index(page: Int = 1, pageLength: Int = 20) = withUserAsync{ user =>  implicit request =>
    val q = getQuery
    repo.paginate(q,pageLength,page).flatMap{ pagination =>
      Future.sequence{
        pagination.results.map{ obj =>
          getJson(obj)
        }
      }.map{ r =>
        Ok(Json.toJson(Json.obj("results" -> r, "count" -> pagination.count, "page" -> page, "pageLength" -> pageLength)))
      }
    }
  }

  def show(id: Long) = withUserAsync{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = NeoAerodromeForm.form
  val filterForm = NeoAerodromeForm.filterForm

  def getJson(obj: NeoAerodrome): Future[JsObject] = {
    for{
          neoAerodromeOcurrenceCategorys <- neoAerodromeOcurrenceCategoryRepo.byNeoAerodromeId(obj.id)
          neoEventTypePhases <- neoEventTypePhaseRepo.byNeoAerodromeId(obj.id)
       } yield {
          (Json.toJson(obj).as[JsObject] + 
          ("neoAerodromeOcurrenceCategorys" -> Json.toJson(neoAerodromeOcurrenceCategorys))+
         ("neoEventTypePhases" -> Json.toJson(neoEventTypePhases)))
       }
       
  }
  def objectResponse(id: Long)(implicit session: Session) = {
    repo.byId(id).flatMap{ oobj =>
      oobj.map{obj =>
       getJson(obj).map{ x =>  Ok(Json.obj("obj" -> x)) }
      }.getOrElse(Future{OrElse})
    }
  }


  def save() = withUserAsync{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        Future{Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))}
      }, formData => {
        formData.insert(formData.obj).flatMap{id =>
          objectResponse(id)
        }
      }
    )
  }
  

  def delete(id: Long) = withUserAsync{ user =>  implicit request =>
    repo.byId(id).map{ oobj =>
      oobj.map{ obj =>
        repo.delete(obj)
        Ok("ok")
      }.getOrElse(OrElse)
    }
  }

  def update(id: Long) = withUserAsync{ user =>  implicit request =>
    repo.byId(id).flatMap { oobj =>
      oobj.map { obj =>
        form.bindFromRequest.fold(
          formWithErrors => {
            Future{Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))}
          }, formData => {
            formData.update(formData.obj.copy(id = obj.id)).flatMap { _ =>
              objectResponse(id)
            }
          }
        )
      }.getOrElse(Future{OrElse})
    }
  }
  def options() = withUserAsync{ user =>  implicit request =>
    for{
      reportingEntitys <- reporting_entityRepo.getAll
      stateAreas <- state_areaRepo.getAll
      aerodromes <- aerodromeRepo.getAll
      aerodromeLocations <- aerodrome_locationRepo.getAll
      aerodromeStatuss <- aerodrome_statusRepo.getAll
      aerodromeTypes <- aerodrome_typeRepo.getAll
      runways <- runwayRepo.getAll
      runwaySurfaceTypes <- runway_surface_typeRepo.getAll
      helicopterLandingAreaTypes <- helicopter_landing_area_typeRepo.getAll
      helicopterLandingAreaAreaConfigurations <- helicopter_landing_area_area_configurationRepo.getAll
      helicopterLandingAreaSurfaceTypes <- helicopter_landing_area_surface_typeRepo.getAll
      ocurrenceClasss <- ocurrence_classRepo.getAll
      detectionPhases <- detection_phaseRepo.getAll
      neoReportStatuss <- neo_report_statusRepo.getAll
      } yield {
        Ok(Json.obj("reportingEntitys" -> Json.toJson(reportingEntitys), "stateAreas" -> Json.toJson(stateAreas), "aerodromes" -> Json.toJson(aerodromes), "aerodromeLocations" -> Json.toJson(aerodromeLocations), "aerodromeStatuss" -> Json.toJson(aerodromeStatuss), "aerodromeTypes" -> Json.toJson(aerodromeTypes), "runways" -> Json.toJson(runways), "runwaySurfaceTypes" -> Json.toJson(runwaySurfaceTypes), "helicopterLandingAreaTypes" -> Json.toJson(helicopterLandingAreaTypes), "helicopterLandingAreaAreaConfigurations" -> Json.toJson(helicopterLandingAreaAreaConfigurations), "helicopterLandingAreaSurfaceTypes" -> Json.toJson(helicopterLandingAreaSurfaceTypes), "ocurrenceClasss" -> Json.toJson(ocurrenceClasss), "detectionPhases" -> Json.toJson(detectionPhases), "neoReportStatuss" -> Json.toJson(neoReportStatuss)))
      }
       
  }
}