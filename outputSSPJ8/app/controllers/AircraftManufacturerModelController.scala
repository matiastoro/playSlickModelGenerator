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


class AircraftManufacturerModelController @Inject()(cc: MessagesControllerComponents)(implicit val repo: AircraftManufacturerModelRepository, ec: ExecutionContext) extends MessagesAbstractController(cc) with Autorization {

/*
GET         /aircraftManufacturerModel/                  controllers.AircraftManufacturerModelController.index(page: Int = 1, pageLength: Int = 20, sortColumn: String = "id", sortOrder: String = "desc")
GET         /aircraftManufacturerModel/show/:id          controllers.AircraftManufacturerModelController.show(id: Long)
GET         /aircraftManufacturerModel/delete/:id          controllers.AircraftManufacturerModelController.delete(id: Long)
POST        /aircraftManufacturerModel/save              controllers.AircraftManufacturerModelController.save()
POST        /aircraftManufacturerModel/update/:id        controllers.AircraftManufacturerModelController.update(id: Long)
GET         /aircraftManufacturerModel/options             controllers.AircraftManufacturerModelController.options()
GET         /aircraftManufacturerModel/:page/:pageLength/:sortColumn/:sortOrder controllers.AircraftManufacturerModelController.index(page: Int, pageLength: Int, sortColumn: String, sortOrder: String)
GET         /aircraftManufacturerModel/:page/:pageLength controllers.AircraftManufacturerModelController.index(page: Int, pageLength: Int, sortColumn: String = "id", sortOrder: String = "desc")
GET         /aircraftManufacturerModel/:page             controllers.AircraftManufacturerModelController.index(page: Int, pageLength: Int = 20, sortColumn: String = "id", sortOrder: String = "desc")
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
  val form = AircraftManufacturerModelForm.form
  val filterForm = AircraftManufacturerModelFilterForm.filterForm

  def getJson(obj: AircraftManufacturerModel): Future[JsObject] = {
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
      aircraftManufacturerModels <- repo.getAll
      } yield {
        Ok(Json.obj("aircraftManufacturerModels" -> Json.toJson(aircraftManufacturerModels)))
      }
       
  }
}