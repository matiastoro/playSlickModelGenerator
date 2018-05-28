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
object OrganizacionController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[Organizacion]

/*
GET         /organizacion/                  controllers.OrganizacionController.index(page: Int = 1, pageLength: Int = 20)
GET         /organizacion/show/:id          controllers.OrganizacionController.show(id: Long)
GET         /organizacion/delete/:id          controllers.OrganizacionController.delete(id: Long)
POST        /organizacion/save              controllers.OrganizacionController.save()
POST        /organizacion/update/:id        controllers.OrganizacionController.update(id: Long)
GET         /organizacion/options             controllers.OrganizacionController.options()
GET         /organizacion/:page/:pageLength controllers.OrganizacionController.index(page: Int, pageLength: Int)
GET         /organizacion/:page             controllers.OrganizacionController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = OrganizacionConsulta.paginate(OrganizacionConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = OrganizacionForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    OrganizacionConsulta.porId(id).map{ organizacion =>
      Ok(Json.obj("obj" -> organizacion.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    OrganizacionConsulta.porId(id).map{ organizacion =>
      OrganizacionConsulta.eliminar(organizacion)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    OrganizacionConsulta.porId(id).map{ organizacion =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = organizacion.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}