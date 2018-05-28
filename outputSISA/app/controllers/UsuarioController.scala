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
object UsuarioController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[Usuario]

/*
GET         /usuario/                  controllers.UsuarioController.index(page: Int = 1, pageLength: Int = 20)
GET         /usuario/show/:id          controllers.UsuarioController.show(id: Long)
GET         /usuario/delete/:id          controllers.UsuarioController.delete(id: Long)
POST        /usuario/save              controllers.UsuarioController.save()
POST        /usuario/update/:id        controllers.UsuarioController.update(id: Long)
GET         /usuario/options             controllers.UsuarioController.options()
GET         /usuario/:page/:pageLength controllers.UsuarioController.index(page: Int, pageLength: Int)
GET         /usuario/:page             controllers.UsuarioController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = UsuarioConsulta.paginate(UsuarioConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = UsuarioForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    UsuarioConsulta.porId(id).map{ usuario =>
      Ok(Json.obj("obj" -> usuario.toJson))
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
    UsuarioConsulta.porId(id).map{ usuario =>
      UsuarioConsulta.eliminar(usuario)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    UsuarioConsulta.porId(id).map{ usuario =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = usuario.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}