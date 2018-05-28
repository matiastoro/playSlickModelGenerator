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
object ProgramaController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[Programa]

/*
GET         /programa/                  controllers.ProgramaController.index(page: Int = 1, pageLength: Int = 20)
GET         /programa/show/:id          controllers.ProgramaController.show(id: Long)
GET         /programa/delete/:id          controllers.ProgramaController.delete(id: Long)
POST        /programa/save              controllers.ProgramaController.save()
POST        /programa/update/:id        controllers.ProgramaController.update(id: Long)
GET         /programa/options             controllers.ProgramaController.options()
GET         /programa/:page/:pageLength controllers.ProgramaController.index(page: Int, pageLength: Int)
GET         /programa/:page             controllers.ProgramaController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = ProgramaQuery.paginate(ProgramaQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = ProgramaForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    ProgramaQuery.byId(id).map{ programa =>
      Ok(Json.obj("obj" -> programa.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val departamento = DepartamentoQuery.getAll
    val categoria_inspeccion = CategoriaInspeccionQuery.getAll
    val objeto_inspeccion_tipo = ObjetoInspeccionTipoQuery.getAll
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    ProgramaQuery.byId(id).map{ programa =>
      ProgramaQuery.delete(programa)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    ProgramaQuery.byId(id).map{ programa =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val departamento = DepartamentoQuery.getAll
    val categoria_inspeccion = CategoriaInspeccionQuery.getAll
    val objeto_inspeccion_tipo = ObjetoInspeccionTipoQuery.getAll
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = programa.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val departamentos = DepartamentoQuery.getAll.map(_.toJson)
    val categoria_inspeccions = CategoriaInspeccionQuery.getAll.map(_.toJson)
    val objeto_inspeccion_tipos = ObjetoInspeccionTipoQuery.getAll.map(_.toJson)
    Ok(Json.obj("departamentos" -> departamentos, "categoria_inspeccions" -> categoria_inspeccions, "objeto_inspeccion_tipos" -> objeto_inspeccion_tipos))
  }
}