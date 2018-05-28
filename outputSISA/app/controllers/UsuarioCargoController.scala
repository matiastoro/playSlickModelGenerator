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
import play.api.data.Field
object UsuarioCargoController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[UsuarioCargo]

/*
GET         /usuarioCargo/                  controllers.UsuarioCargoController.index(page: Int = 1, pageLength: Int = 20)
GET         /usuarioCargo/show/:id          controllers.UsuarioCargoController.show(id: Long)
GET         /usuarioCargo/delete/:id          controllers.UsuarioCargoController.delete(id: Long)
POST        /usuarioCargo/save              controllers.UsuarioCargoController.save()
POST        /usuarioCargo/update/:id        controllers.UsuarioCargoController.update(id: Long)
GET         /usuarioCargo/options             controllers.UsuarioCargoController.options()
GET         /usuarioCargo/:page/:pageLength controllers.UsuarioCargoController.index(page: Int, pageLength: Int)
GET         /usuarioCargo/:page             controllers.UsuarioCargoController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = UsuarioCargoConsulta.paginate(UsuarioCargoConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = UsuarioCargoForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    UsuarioCargoConsulta.porId(id).map{ usuarioCargo =>
      Ok(Json.obj("obj" -> usuarioCargo.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val cargo = CargoConsulta.todos
    val usuario = UsuarioConsulta.todos
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    UsuarioCargoConsulta.porId(id).map{ usuarioCargo =>
      UsuarioCargoConsulta.eliminar(usuarioCargo)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    UsuarioCargoConsulta.porId(id).map{ usuarioCargo =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val cargo = CargoConsulta.todos
    val usuario = UsuarioConsulta.todos
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = usuarioCargo.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val cargos = CargoConsulta.todos.map(_.toJson)
    val usuarios = UsuarioConsulta.todos.map(_.toJson)
    Ok(Json.obj("cargos" -> cargos, "usuarios" -> usuarios))
  }
}