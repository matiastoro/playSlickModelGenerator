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


class HelicopterLandingAreaSurfaceTypeController @Inject()(cc: MessagesControllerComponents)(implicit val repo: HelicopterLandingAreaSurfaceTypeRepository, ec: ExecutionContext) extends MessagesAbstractController(cc) with Autorization {

/*
GET         /helicopterLandingAreaSurfaceType/                  controllers.HelicopterLandingAreaSurfaceTypeController.index(page: Int = 1, pageLength: Int = 20)
GET         /helicopterLandingAreaSurfaceType/show/:id          controllers.HelicopterLandingAreaSurfaceTypeController.show(id: Long)
GET         /helicopterLandingAreaSurfaceType/delete/:id          controllers.HelicopterLandingAreaSurfaceTypeController.delete(id: Long)
POST        /helicopterLandingAreaSurfaceType/save              controllers.HelicopterLandingAreaSurfaceTypeController.save()
POST        /helicopterLandingAreaSurfaceType/update/:id        controllers.HelicopterLandingAreaSurfaceTypeController.update(id: Long)
GET         /helicopterLandingAreaSurfaceType/options             controllers.HelicopterLandingAreaSurfaceTypeController.options()
GET         /helicopterLandingAreaSurfaceType/:page/:pageLength controllers.HelicopterLandingAreaSurfaceTypeController.index(page: Int, pageLength: Int)
GET         /helicopterLandingAreaSurfaceType/:page             controllers.HelicopterLandingAreaSurfaceTypeController.index(page: Int, pageLength: Int = 20)
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
  val form = HelicopterLandingAreaSurfaceTypeForm.form
  val filterForm = HelicopterLandingAreaSurfaceTypeForm.filterForm

  def getJson(obj: HelicopterLandingAreaSurfaceType): Future[JsObject] = {
    Future{}
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