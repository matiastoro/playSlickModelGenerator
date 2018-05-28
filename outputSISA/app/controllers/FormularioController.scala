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
object FormularioController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[Formulario]

/*
GET         /formulario/                  controllers.FormularioController.index(page: Int = 1, pageLength: Int = 20)
GET         /formulario/show/:id          controllers.FormularioController.show(id: Long)
GET         /formulario/delete/:id          controllers.FormularioController.delete(id: Long)
POST        /formulario/save              controllers.FormularioController.save()
POST        /formulario/update/:id        controllers.FormularioController.update(id: Long)
GET         /formulario/options             controllers.FormularioController.options()
GET         /formulario/:page/:pageLength controllers.FormularioController.index(page: Int, pageLength: Int)
GET         /formulario/:page             controllers.FormularioController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = FormularioConsulta.paginate(FormularioConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = FormularioForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    FormularioConsulta.porId(id).map{ formulario =>
      Ok(Json.obj("obj" -> formulario.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val organizacion = OrganizacionConsulta.todos
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    FormularioConsulta.porId(id).map{ formulario =>
      FormularioConsulta.eliminar(formulario)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    FormularioConsulta.porId(id).map{ formulario =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val organizacion = OrganizacionConsulta.todos
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = formulario.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val organizacions = OrganizacionConsulta.todos.map(_.toJson)
    Ok(Json.obj("organizacions" -> organizacions))
  }
}