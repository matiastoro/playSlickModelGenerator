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
object SeasonController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[Season]

/*
GET         /season/                  controllers.SeasonController.index(page: Int = 1, pageLength: Int = 20)
GET         /season/show/:id          controllers.SeasonController.show(id: Long)
GET         /season/delete/:id          controllers.SeasonController.delete(id: Long)
POST        /season/save              controllers.SeasonController.save()
POST        /season/update/:id        controllers.SeasonController.update(id: Long)
GET         /season/options             controllers.SeasonController.options()
GET         /season/:page/:pageLength controllers.SeasonController.index(page: Int, pageLength: Int)
GET         /season/:page             controllers.SeasonController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = SeasonQuery.paginate(SeasonQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = SeasonForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    SeasonQuery.byId(id).map{ season =>
      Ok(Json.obj("obj" -> season.toJson))
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
    SeasonQuery.byId(id).map{ season =>
      SeasonQuery.delete(season)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    SeasonQuery.byId(id).map{ season =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = season.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}