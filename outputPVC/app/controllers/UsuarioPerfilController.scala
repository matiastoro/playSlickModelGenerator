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
object UsuarioPerfilController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[UsuarioPerfil]

/*
GET         /usuarioPerfil/                  controllers.UsuarioPerfilController.index(page: Int = 1, pageLength: Int = 20)
GET         /usuarioPerfil/show/:id          controllers.UsuarioPerfilController.show(id: Long)
GET         /usuarioPerfil/delete/:id          controllers.UsuarioPerfilController.delete(id: Long)
POST        /usuarioPerfil/save              controllers.UsuarioPerfilController.save()
POST        /usuarioPerfil/update/:id        controllers.UsuarioPerfilController.update(id: Long)
GET         /usuarioPerfil/options             controllers.UsuarioPerfilController.options()
GET         /usuarioPerfil/:page/:pageLength controllers.UsuarioPerfilController.index(page: Int, pageLength: Int)
GET         /usuarioPerfil/:page             controllers.UsuarioPerfilController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = UsuarioPerfilConsulta.paginate(UsuarioPerfilConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = UsuarioPerfilForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    UsuarioPerfilConsulta.porId(id).map{ usuarioPerfil =>
      Ok(Json.obj("obj" -> Json.toJson(usuarioPerfil)))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val perfil = PerfilConsulta.todos
    val usuario = UsuarioConsulta.todos
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    UsuarioPerfilConsulta.porId(id).map{ usuarioPerfil =>
      UsuarioPerfilConsulta.eliminar(usuarioPerfil)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    UsuarioPerfilConsulta.porId(id).map{ usuarioPerfil =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val perfil = PerfilConsulta.todos
    val usuario = UsuarioConsulta.todos
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = usuarioPerfil.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val perfils = PerfilConsulta.todos.map(_.toJson)
    val usuarios = UsuarioConsulta.todos.map(_.toJson)
    Ok(Json.obj("perfils" -> perfils, "usuarios" -> usuarios))
  }
}