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


class AircraftPropulsionTypeController @Inject()(cc: MessagesControllerComponents)(implicit val repo: AircraftPropulsionTypeRepository, ec: ExecutionContext) extends MessagesAbstractController(cc) with Autorization {

/*
GET         /aircraftPropulsionType/                  controllers.AircraftPropulsionTypeController.index(page: Int = 1, pageLength: Int = 20, sortColumn: String = "id", sortOrder: String = "desc")
GET         /aircraftPropulsionType/show/:id          controllers.AircraftPropulsionTypeController.show(id: Long)
GET         /aircraftPropulsionType/delete/:id          controllers.AircraftPropulsionTypeController.delete(id: Long)
POST        /aircraftPropulsionType/save              controllers.AircraftPropulsionTypeController.save()
POST        /aircraftPropulsionType/update/:id        controllers.AircraftPropulsionTypeController.update(id: Long)
GET         /aircraftPropulsionType/options             controllers.AircraftPropulsionTypeController.options()
GET         /aircraftPropulsionType/:page/:pageLength/:sortColumn/:sortOrder controllers.AircraftPropulsionTypeController.index(page: Int, pageLength: Int, sortColumn: String, sortOrder: String)
GET         /aircraftPropulsionType/:page/:pageLength controllers.AircraftPropulsionTypeController.index(page: Int, pageLength: Int, sortColumn: String = "id", sortOrder: String = "desc")
GET         /aircraftPropulsionType/:page             controllers.AircraftPropulsionTypeController.index(page: Int, pageLength: Int = 20, sortColumn: String = "id", sortOrder: String = "desc")
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
  val form = AircraftPropulsionTypeForm.form
  val filterForm = AircraftPropulsionTypeFilterForm.filterForm

  def getJson(obj: AircraftPropulsionType): Future[JsObject] = {
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
}