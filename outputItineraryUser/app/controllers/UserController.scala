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
object UserController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[User]

/*
GET         /user/                  controllers.UserController.index(page: Int = 1, pageLength: Int = 20)
GET         /user/show/:id          controllers.UserController.show(id: Long)
GET         /user/delete/:id          controllers.UserController.delete(id: Long)
POST        /user/save              controllers.UserController.save()
POST        /user/update/:id        controllers.UserController.update(id: Long)
GET         /user/options             controllers.UserController.options()
GET         /user/:page/:pageLength controllers.UserController.index(page: Int, pageLength: Int)
GET         /user/:page             controllers.UserController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = UserQuery.paginate(UserQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = UserForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    UserQuery.byId(id).map{ user =>
      Ok(Json.obj("obj" -> user.toJson))
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
    UserQuery.byId(id).map{ user =>
      UserQuery.delete(user)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    UserQuery.byId(id).map{ user =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = user.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}