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
import play.api.data.Field


class HelicopterLandingAreaController @Inject()(cc: MessagesControllerComponents)(implicit val repo: HelicopterLandingAreaRepository, aerodromeRepo: AerodromeRepository, helicopter_landing_area_typeRepo: HelicopterLandingAreaTypeRepository, helicopter_landing_area_area_configurationRepo: HelicopterLandingAreaAreaConfigurationRepository, helicopter_landing_area_surface_typeRepo: HelicopterLandingAreaSurfaceTypeRepository, ec: ExecutionContext) extends MessagesAbstractController(cc) with Autorization {

/*
GET         /helicopterLandingArea/                  controllers.HelicopterLandingAreaController.index(page: Int = 1, pageLength: Int = 20, sortColumn: String = "id", sortOrder: String = "desc")
GET         /helicopterLandingArea/show/:id          controllers.HelicopterLandingAreaController.show(id: Long)
GET         /helicopterLandingArea/delete/:id          controllers.HelicopterLandingAreaController.delete(id: Long)
POST        /helicopterLandingArea/save              controllers.HelicopterLandingAreaController.save()
POST        /helicopterLandingArea/update/:id        controllers.HelicopterLandingAreaController.update(id: Long)
GET         /helicopterLandingArea/options             controllers.HelicopterLandingAreaController.options()
GET         /helicopterLandingArea/:page/:pageLength/:sortColumn/:sortOrder controllers.HelicopterLandingAreaController.index(page: Int, pageLength: Int, sortColumn: String, sortOrder: String)
GET         /helicopterLandingArea/:page/:pageLength controllers.HelicopterLandingAreaController.index(page: Int, pageLength: Int, sortColumn: String = "id", sortOrder: String = "desc")
GET         /helicopterLandingArea/:page             controllers.HelicopterLandingAreaController.index(page: Int, pageLength: Int = 20, sortColumn: String = "id", sortOrder: String = "desc")
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
  val form = HelicopterLandingAreaForm.form
  val filterForm = HelicopterLandingAreaFilterForm.filterForm

  def getJson(obj: HelicopterLandingArea): Future[JsObject] = {
    Future{Json.toJson(obj).as[JsObject]}
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
      aerodromes <- aerodromeRepo.getAll
      helicopterLandingAreaTypes <- helicopter_landing_area_typeRepo.getAll
      helicopterLandingAreaAreaConfigurations <- helicopter_landing_area_area_configurationRepo.getAll
      helicopterLandingAreaSurfaceTypes <- helicopter_landing_area_surface_typeRepo.getAll
      } yield {
        Ok(Json.obj("aerodromes" -> Json.toJson(aerodromes), "helicopterLandingAreaTypes" -> Json.toJson(helicopterLandingAreaTypes), "helicopterLandingAreaAreaConfigurations" -> Json.toJson(helicopterLandingAreaAreaConfigurations), "helicopterLandingAreaSurfaceTypes" -> Json.toJson(helicopterLandingAreaSurfaceTypes)))
      }
       
  }
}