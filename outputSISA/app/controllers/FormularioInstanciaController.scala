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
object FormularioInstanciaController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[FormularioInstancia]

/*
GET         /formularioInstancia/                  controllers.FormularioInstanciaController.index(page: Int = 1, pageLength: Int = 20)
GET         /formularioInstancia/show/:id          controllers.FormularioInstanciaController.show(id: Long)
GET         /formularioInstancia/delete/:id          controllers.FormularioInstanciaController.delete(id: Long)
POST        /formularioInstancia/save              controllers.FormularioInstanciaController.save()
POST        /formularioInstancia/update/:id        controllers.FormularioInstanciaController.update(id: Long)
GET         /formularioInstancia/options             controllers.FormularioInstanciaController.options()
GET         /formularioInstancia/:page/:pageLength controllers.FormularioInstanciaController.index(page: Int, pageLength: Int)
GET         /formularioInstancia/:page             controllers.FormularioInstanciaController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = FormularioInstanciaConsulta.paginate(FormularioInstanciaConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = FormularioInstanciaForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    FormularioInstanciaConsulta.porId(id).map{ formularioInstancia =>
      Ok(Json.obj("obj" -> formularioInstancia.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val formulario = FormularioConsulta.todos
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    FormularioInstanciaConsulta.porId(id).map{ formularioInstancia =>
      FormularioInstanciaConsulta.eliminar(formularioInstancia)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    FormularioInstanciaConsulta.porId(id).map{ formularioInstancia =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val formulario = FormularioConsulta.todos
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = formularioInstancia.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val formularios = FormularioConsulta.todos.map(_.toJson)
    Ok(Json.obj("formularios" -> formularios))
  }
}