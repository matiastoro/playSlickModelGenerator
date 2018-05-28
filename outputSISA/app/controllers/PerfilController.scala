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
object PerfilController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[Perfil]

/*
GET         /perfil/                  controllers.PerfilController.index(page: Int = 1, pageLength: Int = 20)
GET         /perfil/show/:id          controllers.PerfilController.show(id: Long)
GET         /perfil/delete/:id          controllers.PerfilController.delete(id: Long)
POST        /perfil/save              controllers.PerfilController.save()
POST        /perfil/update/:id        controllers.PerfilController.update(id: Long)
GET         /perfil/options             controllers.PerfilController.options()
GET         /perfil/:page/:pageLength controllers.PerfilController.index(page: Int, pageLength: Int)
GET         /perfil/:page             controllers.PerfilController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = PerfilConsulta.paginate(PerfilConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = PerfilForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    PerfilConsulta.porId(id).map{ perfil =>
      Ok(Json.obj("obj" -> perfil.toJson))
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
    PerfilConsulta.porId(id).map{ perfil =>
      PerfilConsulta.eliminar(perfil)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    PerfilConsulta.porId(id).map{ perfil =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = perfil.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}