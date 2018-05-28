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
object UserAerodromeController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[UserAerodrome]

/*
GET         /userAerodrome/                  controllers.UserAerodromeController.index(page: Int = 1, pageLength: Int = 20)
GET         /userAerodrome/show/:id          controllers.UserAerodromeController.show(id: Long)
GET         /userAerodrome/delete/:id          controllers.UserAerodromeController.delete(id: Long)
POST        /userAerodrome/save              controllers.UserAerodromeController.save()
POST        /userAerodrome/update/:id        controllers.UserAerodromeController.update(id: Long)
GET         /userAerodrome/options             controllers.UserAerodromeController.options()
GET         /userAerodrome/:page/:pageLength controllers.UserAerodromeController.index(page: Int, pageLength: Int)
GET         /userAerodrome/:page             controllers.UserAerodromeController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = UserAerodromeQuery.paginate(UserAerodromeQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = UserAerodromeForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    UserAerodromeQuery.byId(id).map{ userAerodrome =>
      Ok(Json.obj("obj" -> userAerodrome.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val user = UserQuery.getAll
    val aerodrome = AerodromeQuery.getAll
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    UserAerodromeQuery.byId(id).map{ userAerodrome =>
      UserAerodromeQuery.delete(userAerodrome)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    UserAerodromeQuery.byId(id).map{ userAerodrome =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val user = UserQuery.getAll
    val aerodrome = AerodromeQuery.getAll
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = userAerodrome.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val users = UserQuery.getAll.map(_.toJson)
    val aerodromes = AerodromeQuery.getAll.map(_.toJson)
    Ok(Json.obj("users" -> users, "aerodromes" -> aerodromes))
  }
}