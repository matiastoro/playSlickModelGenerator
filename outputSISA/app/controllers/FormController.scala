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
object FormController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[Form]

/*
GET         /form/                  controllers.FormController.index(page: Int = 1, pageLength: Int = 20)
GET         /form/show/:id          controllers.FormController.show(id: Long)
GET         /form/delete/:id          controllers.FormController.delete(id: Long)
POST        /form/save              controllers.FormController.save()
POST        /form/update/:id        controllers.FormController.update(id: Long)
GET         /form/options             controllers.FormController.options()
GET         /form/:page/:pageLength controllers.FormController.index(page: Int, pageLength: Int)
GET         /form/:page             controllers.FormController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = FormConsulta.paginate(FormConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = FormForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    FormConsulta.porId(id).map{ form =>
      Ok(Json.obj("obj" -> Json.toJson(form)))
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
    FormConsulta.porId(id).map{ form =>
      FormConsulta.eliminar(form)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    FormConsulta.porId(id).map{ form =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val organizacion = OrganizacionConsulta.todos
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = form.id)).map{ id =>
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