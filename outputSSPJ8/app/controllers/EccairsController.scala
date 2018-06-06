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


class EccairsController @Inject()(cc: MessagesControllerComponents)(implicit val repo: EccairsRepository, injury_levelRepo: InjuryLevelRepository, weather_conditionRepo: WeatherConditionRepository, aircraft_categoryRepo: AircraftCategoryRepository, operation_typeRepo: OperationTypeRepository, eccairsOcurrenceCategoryRepo: EccairsOcurrenceCategoryRepository, operator_typeRepo: OperatorTypeRepository, aircraft_mass_groupRepo: AircraftMassGroupRepository, ocurrence_classRepo: OcurrenceClassRepository, state_areaRepo: StateAreaRepository, operatorRepo: OperatorRepository, ec: ExecutionContext) extends MessagesAbstractController(cc) with Autorization {

/*
GET         /eccairs/                  controllers.EccairsController.index(page: Int = 1, pageLength: Int = 20, sortColumn: String = "id", sortOrder: String = "desc")
GET         /eccairs/show/:id          controllers.EccairsController.show(id: Long)
GET         /eccairs/delete/:id          controllers.EccairsController.delete(id: Long)
POST        /eccairs/save              controllers.EccairsController.save()
POST        /eccairs/update/:id        controllers.EccairsController.update(id: Long)
GET         /eccairs/options             controllers.EccairsController.options()
GET         /eccairs/:page/:pageLength/:sortColumn/:sortOrder controllers.EccairsController.index(page: Int, pageLength: Int, sortColumn: String, sortOrder: String)
GET         /eccairs/:page/:pageLength controllers.EccairsController.index(page: Int, pageLength: Int, sortColumn: String = "id", sortOrder: String = "desc")
GET         /eccairs/:page             controllers.EccairsController.index(page: Int, pageLength: Int = 20, sortColumn: String = "id", sortOrder: String = "desc")
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

  def index(page: Int = 1, pageLength: Int = 20, sortColumn: String = "", sortOrder: String = "") = withUserAsync{ user =>  implicit request =>
    val q = getQuery
    repo.paginate(q,pageLength,page, sortColumn, sortOrder).flatMap{ pagination =>
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
  val form = EccairsForm.form
  val filterForm = EccairsFilterForm.filterForm

  def getJson(obj: Eccairs): Future[JsObject] = {
    for{
          eccairsOcurrenceCategorys <- eccairsOcurrenceCategoryRepo.byEccairsId(obj.id)
       } yield {
          (Json.toJson(obj).as[JsObject] + 
          ("eccairsOcurrenceCategorys" -> Json.toJson(eccairsOcurrenceCategorys)))
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
      stateAreas <- state_areaRepo.getAll
      ocurrenceClasss <- ocurrence_classRepo.getAll
      injuryLevels <- injury_levelRepo.getAll
      aircraftMassGroups <- aircraft_mass_groupRepo.getAll
      aircraftCategorys <- aircraft_categoryRepo.getAll
      operationTypes <- operation_typeRepo.getAll
      operatorTypes <- operator_typeRepo.getAll
      operators <- operatorRepo.getAll
      weatherConditions <- weather_conditionRepo.getAll
      } yield {
        Ok(Json.obj("stateAreas" -> Json.toJson(stateAreas), "ocurrenceClasss" -> Json.toJson(ocurrenceClasss), "injuryLevels" -> Json.toJson(injuryLevels), "aircraftMassGroups" -> Json.toJson(aircraftMassGroups), "aircraftCategorys" -> Json.toJson(aircraftCategorys), "operationTypes" -> Json.toJson(operationTypes), "operatorTypes" -> Json.toJson(operatorTypes), "operators" -> Json.toJson(operators), "weatherConditions" -> Json.toJson(weatherConditions)))
      }
       
  }
}