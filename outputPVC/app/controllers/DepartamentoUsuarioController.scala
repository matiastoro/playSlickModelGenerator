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
object DepartamentoUsuarioController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[DepartamentoUsuario]

/*
GET         /departamentoUsuario/                  controllers.DepartamentoUsuarioController.index(page: Int = 1, pageLength: Int = 20)
GET         /departamentoUsuario/show/:id          controllers.DepartamentoUsuarioController.show(id: Long)
GET         /departamentoUsuario/delete/:id          controllers.DepartamentoUsuarioController.delete(id: Long)
POST        /departamentoUsuario/save              controllers.DepartamentoUsuarioController.save()
POST        /departamentoUsuario/update/:id        controllers.DepartamentoUsuarioController.update(id: Long)
GET         /departamentoUsuario/options             controllers.DepartamentoUsuarioController.options()
GET         /departamentoUsuario/:page/:pageLength controllers.DepartamentoUsuarioController.index(page: Int, pageLength: Int)
GET         /departamentoUsuario/:page             controllers.DepartamentoUsuarioController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = DepartamentoUsuarioQuery.paginate(DepartamentoUsuarioQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = DepartamentoUsuarioForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    DepartamentoUsuarioQuery.byId(id).map{ departamentoUsuario =>
      Ok(Json.obj("obj" -> departamentoUsuario.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val departamento = DepartamentoQuery.getAll
    val usuario = UsuarioQuery.getAll
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    DepartamentoUsuarioQuery.byId(id).map{ departamentoUsuario =>
      DepartamentoUsuarioQuery.delete(departamentoUsuario)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    DepartamentoUsuarioQuery.byId(id).map{ departamentoUsuario =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val departamento = DepartamentoQuery.getAll
    val usuario = UsuarioQuery.getAll
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = departamentoUsuario.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val departamentos = DepartamentoQuery.getAll.map(_.toJson)
    val usuarios = UsuarioQuery.getAll.map(_.toJson)
    Ok(Json.obj("departamentos" -> departamentos, "usuarios" -> usuarios))
  }
}