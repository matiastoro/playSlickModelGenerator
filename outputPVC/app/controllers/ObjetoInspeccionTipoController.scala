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
object ObjetoInspeccionTipoController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[ObjetoInspeccionTipo]

/*
GET         /objetoInspeccionTipo/                  controllers.ObjetoInspeccionTipoController.index(page: Int = 1, pageLength: Int = 20)
GET         /objetoInspeccionTipo/show/:id          controllers.ObjetoInspeccionTipoController.show(id: Long)
GET         /objetoInspeccionTipo/delete/:id          controllers.ObjetoInspeccionTipoController.delete(id: Long)
POST        /objetoInspeccionTipo/save              controllers.ObjetoInspeccionTipoController.save()
POST        /objetoInspeccionTipo/update/:id        controllers.ObjetoInspeccionTipoController.update(id: Long)
GET         /objetoInspeccionTipo/options             controllers.ObjetoInspeccionTipoController.options()
GET         /objetoInspeccionTipo/:page/:pageLength controllers.ObjetoInspeccionTipoController.index(page: Int, pageLength: Int)
GET         /objetoInspeccionTipo/:page             controllers.ObjetoInspeccionTipoController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = ObjetoInspeccionTipoQuery.paginate(ObjetoInspeccionTipoQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = ObjetoInspeccionTipoForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    ObjetoInspeccionTipoQuery.byId(id).map{ objetoInspeccionTipo =>
      Ok(Json.obj("obj" -> objetoInspeccionTipo.toJson))
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
    ObjetoInspeccionTipoQuery.byId(id).map{ objetoInspeccionTipo =>
      ObjetoInspeccionTipoQuery.delete(objetoInspeccionTipo)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    ObjetoInspeccionTipoQuery.byId(id).map{ objetoInspeccionTipo =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = objetoInspeccionTipo.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}