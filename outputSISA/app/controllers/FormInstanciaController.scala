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
object FormInstanciaController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[FormInstancia]

/*
GET         /formInstancia/                  controllers.FormInstanciaController.index(page: Int = 1, pageLength: Int = 20)
GET         /formInstancia/show/:id          controllers.FormInstanciaController.show(id: Long)
GET         /formInstancia/delete/:id          controllers.FormInstanciaController.delete(id: Long)
POST        /formInstancia/save              controllers.FormInstanciaController.save()
POST        /formInstancia/update/:id        controllers.FormInstanciaController.update(id: Long)
GET         /formInstancia/options             controllers.FormInstanciaController.options()
GET         /formInstancia/:page/:pageLength controllers.FormInstanciaController.index(page: Int, pageLength: Int)
GET         /formInstancia/:page             controllers.FormInstanciaController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = FormInstanciaConsulta.paginate(FormInstanciaConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = FormInstanciaForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    FormInstanciaConsulta.porId(id).map{ formInstancia =>
      Ok(Json.obj("obj" -> Json.toJson(formInstancia)))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val form = FormConsulta.todos
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    FormInstanciaConsulta.porId(id).map{ formInstancia =>
      FormInstanciaConsulta.eliminar(formInstancia)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    FormInstanciaConsulta.porId(id).map{ formInstancia =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val form = FormConsulta.todos
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = formInstancia.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val forms = FormConsulta.todos.map(_.toJson)
    Ok(Json.obj("forms" -> forms))
  }
}