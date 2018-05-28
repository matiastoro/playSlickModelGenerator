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
object CategoriaInspeccionController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[CategoriaInspeccion]

/*
GET         /categoriaInspeccion/                  controllers.CategoriaInspeccionController.index(page: Int = 1, pageLength: Int = 20)
GET         /categoriaInspeccion/show/:id          controllers.CategoriaInspeccionController.show(id: Long)
GET         /categoriaInspeccion/delete/:id          controllers.CategoriaInspeccionController.delete(id: Long)
POST        /categoriaInspeccion/save              controllers.CategoriaInspeccionController.save()
POST        /categoriaInspeccion/update/:id        controllers.CategoriaInspeccionController.update(id: Long)
GET         /categoriaInspeccion/options             controllers.CategoriaInspeccionController.options()
GET         /categoriaInspeccion/:page/:pageLength controllers.CategoriaInspeccionController.index(page: Int, pageLength: Int)
GET         /categoriaInspeccion/:page             controllers.CategoriaInspeccionController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = CategoriaInspeccionQuery.paginate(CategoriaInspeccionQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = CategoriaInspeccionForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    CategoriaInspeccionQuery.byId(id).map{ categoriaInspeccion =>
      Ok(Json.obj("obj" -> categoriaInspeccion.toJson))
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
    CategoriaInspeccionQuery.byId(id).map{ categoriaInspeccion =>
      CategoriaInspeccionQuery.delete(categoriaInspeccion)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    CategoriaInspeccionQuery.byId(id).map{ categoriaInspeccion =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = categoriaInspeccion.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}