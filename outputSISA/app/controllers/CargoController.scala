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
object CargoController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[Cargo]

/*
GET         /cargo/                  controllers.CargoController.index(page: Int = 1, pageLength: Int = 20)
GET         /cargo/show/:id          controllers.CargoController.show(id: Long)
GET         /cargo/delete/:id          controllers.CargoController.delete(id: Long)
POST        /cargo/save              controllers.CargoController.save()
POST        /cargo/update/:id        controllers.CargoController.update(id: Long)
GET         /cargo/options             controllers.CargoController.options()
GET         /cargo/:page/:pageLength controllers.CargoController.index(page: Int, pageLength: Int)
GET         /cargo/:page             controllers.CargoController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = CargoConsulta.paginate(CargoConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = CargoForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    CargoConsulta.porId(id).map{ cargo =>
      Ok(Json.obj("obj" -> cargo.toJson))
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
    CargoConsulta.porId(id).map{ cargo =>
      CargoConsulta.eliminar(cargo)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    CargoConsulta.porId(id).map{ cargo =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val organizacion = OrganizacionConsulta.todos
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = cargo.id)).map{ id =>
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