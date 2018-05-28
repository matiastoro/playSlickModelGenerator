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
object AttachmentController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[Attachment]

/*
GET         /attachment/                  controllers.AttachmentController.index(page: Int = 1, pageLength: Int = 20)
GET         /attachment/show/:id          controllers.AttachmentController.show(id: Long)
GET         /attachment/delete/:id          controllers.AttachmentController.delete(id: Long)
POST        /attachment/save              controllers.AttachmentController.save()
POST        /attachment/update/:id        controllers.AttachmentController.update(id: Long)
GET         /attachment/options             controllers.AttachmentController.options()
GET         /attachment/:page/:pageLength controllers.AttachmentController.index(page: Int, pageLength: Int)
GET         /attachment/:page             controllers.AttachmentController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = AttachmentConsulta.paginate(AttachmentConsulta.todosConsulta,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = AttachmentForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    AttachmentConsulta.porId(id).map{ attachment =>
      Ok(Json.obj("obj" -> attachment.toJson))
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
    AttachmentConsulta.porId(id).map{ attachment =>
      AttachmentConsulta.eliminar(attachment)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    AttachmentConsulta.porId(id).map{ attachment =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = attachment.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}