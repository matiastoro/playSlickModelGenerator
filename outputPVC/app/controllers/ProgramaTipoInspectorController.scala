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
import play.api.data.Field
object ProgramaTipoInspectorController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[ProgramaTipoInspector]

/*
GET         /programaTipoInspector/                  controllers.ProgramaTipoInspectorController.index(page: Int = 1, pageLength: Int = 20)
GET         /programaTipoInspector/show/:id          controllers.ProgramaTipoInspectorController.show(id: Long)
GET         /programaTipoInspector/delete/:id          controllers.ProgramaTipoInspectorController.delete(id: Long)
POST        /programaTipoInspector/save              controllers.ProgramaTipoInspectorController.save()
POST        /programaTipoInspector/update/:id        controllers.ProgramaTipoInspectorController.update(id: Long)
GET         /programaTipoInspector/options             controllers.ProgramaTipoInspectorController.options()
GET         /programaTipoInspector/:page/:pageLength controllers.ProgramaTipoInspectorController.index(page: Int, pageLength: Int)
GET         /programaTipoInspector/:page             controllers.ProgramaTipoInspectorController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = ProgramaTipoInspectorQuery.paginate(ProgramaTipoInspectorQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = ProgramaTipoInspectorForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    ProgramaTipoInspectorQuery.byId(id).map{ programaTipoInspector =>
      Ok(Json.obj("obj" -> programaTipoInspector.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val programa = ProgramaQuery.getAll
    val tipo_inspector = TipoInspectorQuery.getAll
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    ProgramaTipoInspectorQuery.byId(id).map{ programaTipoInspector =>
      ProgramaTipoInspectorQuery.delete(programaTipoInspector)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    ProgramaTipoInspectorQuery.byId(id).map{ programaTipoInspector =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val programa = ProgramaQuery.getAll
    val tipo_inspector = TipoInspectorQuery.getAll
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = programaTipoInspector.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val programas = ProgramaQuery.getAll.map(_.toJson)
    val tipo_inspectors = TipoInspectorQuery.getAll.map(_.toJson)
    Ok(Json.obj("programas" -> programas, "tipo_inspectors" -> tipo_inspectors))
  }
}