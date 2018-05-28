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
object FormInstanciaHistoriaController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[FormInstanciaHistoria]

/*
GET         /formInstanciaHistoria/                  controllers.FormInstanciaHistoriaController.index(page: Int = 1, pageLength: Int = 20)
GET         /formInstanciaHistoria/show/:id          controllers.FormInstanciaHistoriaController.show(id: Long)
GET         /formInstanciaHistoria/delete/:id          controllers.FormInstanciaHistoriaController.delete(id: Long)
POST        /formInstanciaHistoria/save              controllers.FormInstanciaHistoriaController.save()
POST        /formInstanciaHistoria/update/:id        controllers.FormInstanciaHistoriaController.update(id: Long)
GET         /formInstanciaHistoria/options             controllers.FormInstanciaHistoriaController.options()
GET         /formInstanciaHistoria/:page/:pageLength controllers.FormInstanciaHistoriaController.index(page: Int, pageLength: Int)
GET         /formInstanciaHistoria/:page             controllers.FormInstanciaHistoriaController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = FormInstanciaHistoriaConsulta.paginate(FormInstanciaHistoriaConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = FormInstanciaHistoriaForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    FormInstanciaHistoriaConsulta.porId(id).map{ formInstanciaHistoria =>
      Ok(Json.obj("obj" -> Json.toJson(formInstanciaHistoria)))
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
    FormInstanciaHistoriaConsulta.porId(id).map{ formInstanciaHistoria =>
      FormInstanciaHistoriaConsulta.eliminar(formInstanciaHistoria)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    FormInstanciaHistoriaConsulta.porId(id).map{ formInstanciaHistoria =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val form = FormConsulta.todos
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = formInstanciaHistoria.id)).map{ id =>
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