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
object UserProfileController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[UserProfile]

/*
GET         /userProfile/                  controllers.UserProfileController.index(page: Int = 1, pageLength: Int = 20)
GET         /userProfile/show/:id          controllers.UserProfileController.show(id: Long)
GET         /userProfile/delete/:id          controllers.UserProfileController.delete(id: Long)
POST        /userProfile/save              controllers.UserProfileController.save()
POST        /userProfile/update/:id        controllers.UserProfileController.update(id: Long)
GET         /userProfile/options             controllers.UserProfileController.options()
GET         /userProfile/:page/:pageLength controllers.UserProfileController.index(page: Int, pageLength: Int)
GET         /userProfile/:page             controllers.UserProfileController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = UserProfileQuery.paginate(UserProfileQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = UserProfileForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    UserProfileQuery.byId(id).map{ userProfile =>
      Ok(Json.obj("obj" -> userProfile.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val profile = ProfileQuery.getAll
    val user = UserQuery.getAll
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    UserProfileQuery.byId(id).map{ userProfile =>
      UserProfileQuery.delete(userProfile)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    UserProfileQuery.byId(id).map{ userProfile =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val profile = ProfileQuery.getAll
    val user = UserQuery.getAll
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = userProfile.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val profiles = ProfileQuery.getAll.map(_.toJson)
    val users = UserQuery.getAll.map(_.toJson)
    Ok(Json.obj("profiles" -> profiles, "users" -> users))
  }
}