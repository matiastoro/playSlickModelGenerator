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
object PerfilUsuarioController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[PerfilUsuario]

/*
GET         /perfilUsuario/                  controllers.PerfilUsuarioController.index(page: Int = 1, pageLength: Int = 20)
GET         /perfilUsuario/show/:id          controllers.PerfilUsuarioController.show(id: Long)
GET         /perfilUsuario/delete/:id          controllers.PerfilUsuarioController.delete(id: Long)
POST        /perfilUsuario/save              controllers.PerfilUsuarioController.save()
POST        /perfilUsuario/update/:id        controllers.PerfilUsuarioController.update(id: Long)
GET         /perfilUsuario/options             controllers.PerfilUsuarioController.options()
GET         /perfilUsuario/:page/:pageLength controllers.PerfilUsuarioController.index(page: Int, pageLength: Int)
GET         /perfilUsuario/:page             controllers.PerfilUsuarioController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = PerfilUsuarioConsulta.paginate(PerfilUsuarioConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = PerfilUsuarioForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    PerfilUsuarioConsulta.porId(id).map{ perfilUsuario =>
      Ok(Json.obj("obj" -> perfilUsuario.toJson))
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
    PerfilUsuarioConsulta.porId(id).map{ perfilUsuario =>
      PerfilUsuarioConsulta.eliminar(perfilUsuario)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    PerfilUsuarioConsulta.porId(id).map{ perfilUsuario =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val perfil = PerfilConsulta.todos
    val usuario = UsuarioConsulta.todos
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = perfilUsuario.id)).map{ id =>
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