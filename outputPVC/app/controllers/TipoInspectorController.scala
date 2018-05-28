package controllers
import models._
import models.extensions._
import play.api._
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.Controller
import play.api.Play.current
import play.api.mvc.BodyParsers._
import play.api.libs.json.Json
import play.api.libs.json.Json._
import com.roundeights.hasher.Implicits._
import com.github.nscala_time.time.Imports._
import play.api.libs.concurrent.Akka
import models._
import models.extensions._
import org.joda.time.{DateTimeZone, DateTime}
import play.api.i18n.Messages
object TipoInspectorController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[TipoInspector]

/*
GET         /tipoInspector/                  controllers.TipoInspectorController.index(page: Int = 1, pageLength: Int = 20)
GET         /tipoInspector/show/:id          controllers.TipoInspectorController.show(id: Long)
GET         /tipoInspector/delete/:id          controllers.TipoInspectorController.delete(id: Long)
POST        /tipoInspector/save              controllers.TipoInspectorController.save()
POST        /tipoInspector/update/:id        controllers.TipoInspectorController.update(id: Long)
GET         /tipoInspector/options             controllers.TipoInspectorController.options()
GET         /tipoInspector/:page/:pageLength controllers.TipoInspectorController.index(page: Int, pageLength: Int)
GET         /tipoInspector/:page             controllers.TipoInspectorController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = TipoInspectorQuery.paginate(TipoInspectorQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = TipoInspectorForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    TipoInspectorQuery.byId(id).map{ tipoInspector =>
      Ok(Json.obj("obj" -> tipoInspector.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    TipoInspectorQuery.byId(id).map{ tipoInspector =>
      TipoInspectorQuery.delete(tipoInspector)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    TipoInspectorQuery.byId(id).map{ tipoInspector =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = tipoInspector.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}