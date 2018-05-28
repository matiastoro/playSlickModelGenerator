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
object FplController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[Fpl]

/*
GET         /fpl/                  controllers.FplController.index(page: Int = 1, pageLength: Int = 20)
GET         /fpl/show/:id          controllers.FplController.show(id: Long)
GET         /fpl/delete/:id          controllers.FplController.delete(id: Long)
POST        /fpl/save              controllers.FplController.save()
POST        /fpl/update/:id        controllers.FplController.update(id: Long)
GET         /fpl/options             controllers.FplController.options()
GET         /fpl/:page/:pageLength controllers.FplController.index(page: Int, pageLength: Int)
GET         /fpl/:page             controllers.FplController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = FplQuery.paginate(FplQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = FplForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    FplQuery.byId(id).map{ fpl =>
      Ok(Json.obj("obj" -> fpl.toJson))
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
    FplQuery.byId(id).map{ fpl =>
      FplQuery.delete(fpl)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    FplQuery.byId(id).map{ fpl =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = fpl.id, createdAt = fpl.createdAt)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}