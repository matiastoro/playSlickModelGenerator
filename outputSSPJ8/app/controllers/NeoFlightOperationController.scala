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


class NeoFlightOperationController @Inject()(cc: MessagesControllerComponents)(implicit val repo: NeoFlightOperationRepository, type_of_airspeedRepo: TypeOfAirspeedRepository, flight_ruleRepo: FlightRuleRepository, neoFlightOperationEventTypePhaseRepo: NeoFlightOperationEventTypePhaseRepository, aircraft_manufacturer_modelRepo: AircraftManufacturerModelRepository, neoFlightOperationOcurrenceCategoryRepo: NeoFlightOperationOcurrenceCategoryRepository, detection_phaseRepo: DetectionPhaseRepository, aircraft_categoryRepo: AircraftCategoryRepository, operation_typeRepo: OperationTypeRepository, aircraft_mass_groupRepo: AircraftMassGroupRepository, flightCrewRepo: FlightCrewRepository, ocurrence_classRepo: OcurrenceClassRepository, aircraft_wake_turb_categoryRepo: AircraftWakeTurbCategoryRepository, flightCrewLicenseRepo: FlightCrewLicenseRepository, neo_report_statusRepo: NeoReportStatusRepository, aircraft_propulsion_typeRepo: AircraftPropulsionTypeRepository, instrument_appr_typeRepo: InstrumentApprTypeRepository, reporting_entityRepo: ReportingEntityRepository, aerodromeRepo: AerodromeRepository, phaseRepo: PhaseRepository, traffic_typeRepo: TrafficTypeRepository, aircraft_landing_gear_typeRepo: AircraftLandingGearTypeRepository, state_areaRepo: StateAreaRepository, operatorRepo: OperatorRepository, ec: ExecutionContext) extends MessagesAbstractController(cc) with Autorization {

/*
GET         /neoFlightOperation/                  controllers.NeoFlightOperationController.index(page: Int = 1, pageLength: Int = 20)
GET         /neoFlightOperation/show/:id          controllers.NeoFlightOperationController.show(id: Long)
GET         /neoFlightOperation/delete/:id          controllers.NeoFlightOperationController.delete(id: Long)
POST        /neoFlightOperation/save              controllers.NeoFlightOperationController.save()
POST        /neoFlightOperation/update/:id        controllers.NeoFlightOperationController.update(id: Long)
GET         /neoFlightOperation/options             controllers.NeoFlightOperationController.options()
GET         /neoFlightOperation/:page/:pageLength controllers.NeoFlightOperationController.index(page: Int, pageLength: Int)
GET         /neoFlightOperation/:page             controllers.NeoFlightOperationController.index(page: Int, pageLength: Int = 20)
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
  val form = NeoFlightOperationForm.form
  val filterForm = NeoFlightOperationForm.filterForm

  def getJson(obj: NeoFlightOperation): Future[JsObject] = {
    for{
          flightCrews <- flightCrewRepo.byNeoFlightOperationId(obj.id)
          neoFlightOperationOcurrenceCategorys <- neoFlightOperationOcurrenceCategoryRepo.byNeoFlightOperationId(obj.id)
          neoFlightOperationEventTypePhases <- neoFlightOperationEventTypePhaseRepo.byNeoFlightOperationId(obj.id)
       } yield {
          (Json.toJson(obj).as[JsObject] + 
          ("flightCrews" -> Json.toJson(flightCrews))+
         ("neoFlightOperationOcurrenceCategorys" -> Json.toJson(neoFlightOperationOcurrenceCategorys))+
         ("neoFlightOperationEventTypePhases" -> Json.toJson(neoFlightOperationEventTypePhases)))
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
      stateAreas <- state_areaRepo.getAll
      aircraftManufacturerModels <- aircraft_manufacturer_modelRepo.getAll
      aircraftCategorys <- aircraft_categoryRepo.getAll
      aircraftMassGroups <- aircraft_mass_groupRepo.getAll
      aircraftPropulsionTypes <- aircraft_propulsion_typeRepo.getAll
      aircraftWakeTurbCategorys <- aircraft_wake_turb_categoryRepo.getAll
      aircraftLandingGearTypes <- aircraft_landing_gear_typeRepo.getAll
      aerodromes <- aerodromeRepo.getAll
      aerodromes <- aerodromeRepo.getAll
      phases <- phaseRepo.getAll
      operators <- operatorRepo.getAll
      operationTypes <- operation_typeRepo.getAll
      typeOfAirspeeds <- type_of_airspeedRepo.getAll
      flightRules <- flight_ruleRepo.getAll
      trafficTypes <- traffic_typeRepo.getAll
      instrumentApprTypes <- instrument_appr_typeRepo.getAll
      ocurrenceClasss <- ocurrence_classRepo.getAll
      detectionPhases <- detection_phaseRepo.getAll
      neoReportStatuss <- neo_report_statusRepo.getAll
      } yield {
        Ok(Json.obj("reportingEntitys" -> Json.toJson(reportingEntitys), "stateAreas" -> Json.toJson(stateAreas), "stateAreas" -> Json.toJson(stateAreas), "aircraftManufacturerModels" -> Json.toJson(aircraftManufacturerModels), "aircraftCategorys" -> Json.toJson(aircraftCategorys), "aircraftMassGroups" -> Json.toJson(aircraftMassGroups), "aircraftPropulsionTypes" -> Json.toJson(aircraftPropulsionTypes), "aircraftWakeTurbCategorys" -> Json.toJson(aircraftWakeTurbCategorys), "aircraftLandingGearTypes" -> Json.toJson(aircraftLandingGearTypes), "aerodromes" -> Json.toJson(aerodromes), "aerodromes" -> Json.toJson(aerodromes), "phases" -> Json.toJson(phases), "operators" -> Json.toJson(operators), "operationTypes" -> Json.toJson(operationTypes), "typeOfAirspeeds" -> Json.toJson(typeOfAirspeeds), "flightRules" -> Json.toJson(flightRules), "trafficTypes" -> Json.toJson(trafficTypes), "instrumentApprTypes" -> Json.toJson(instrumentApprTypes), "ocurrenceClasss" -> Json.toJson(ocurrenceClasss), "detectionPhases" -> Json.toJson(detectionPhases), "neoReportStatuss" -> Json.toJson(neoReportStatuss)))
      }
       
  }
}