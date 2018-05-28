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
object DepartamentoController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[Departamento]

/*
GET         /departamento/                  controllers.DepartamentoController.index(page: Int = 1, pageLength: Int = 20)
GET         /departamento/show/:id          controllers.DepartamentoController.show(id: Long)
GET         /departamento/delete/:id          controllers.DepartamentoController.delete(id: Long)
POST        /departamento/save              controllers.DepartamentoController.save()
POST        /departamento/update/:id        controllers.DepartamentoController.update(id: Long)
GET         /departamento/options             controllers.DepartamentoController.options()
GET         /departamento/:page/:pageLength controllers.DepartamentoController.index(page: Int, pageLength: Int)
GET         /departamento/:page             controllers.DepartamentoController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = DepartamentoQuery.paginate(DepartamentoQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = DepartamentoForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    DepartamentoQuery.byId(id).map{ departamento =>
      Ok(Json.obj("obj" -> departamento.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val departamento = DepartamentoQuery.getAll
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    DepartamentoQuery.byId(id).map{ departamento =>
      DepartamentoQuery.delete(departamento)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    DepartamentoQuery.byId(id).map{ departamento =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val departamento = DepartamentoQuery.getAll
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = departamento.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val departamentos = DepartamentoQuery.getAll.map(_.toJson)
    Ok(Json.obj("departamentos" -> departamentos))
  }
}