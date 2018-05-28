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
object ItineraryUserAerodromeController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[ItineraryUserAerodrome]

/*
GET         /itineraryUserAerodrome/                  controllers.ItineraryUserAerodromeController.index(page: Int = 1, pageLength: Int = 20)
GET         /itineraryUserAerodrome/show/:id          controllers.ItineraryUserAerodromeController.show(id: Long)
GET         /itineraryUserAerodrome/delete/:id          controllers.ItineraryUserAerodromeController.delete(id: Long)
POST        /itineraryUserAerodrome/save              controllers.ItineraryUserAerodromeController.save()
POST        /itineraryUserAerodrome/update/:id        controllers.ItineraryUserAerodromeController.update(id: Long)
GET         /itineraryUserAerodrome/options             controllers.ItineraryUserAerodromeController.options()
GET         /itineraryUserAerodrome/:page/:pageLength controllers.ItineraryUserAerodromeController.index(page: Int, pageLength: Int)
GET         /itineraryUserAerodrome/:page             controllers.ItineraryUserAerodromeController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = ItineraryUserAerodromeQuery.paginate(ItineraryUserAerodromeQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = ItineraryUserAerodromeForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    ItineraryUserAerodromeQuery.byId(id).map{ itineraryUserAerodrome =>
      Ok(Json.obj("obj" -> itineraryUserAerodrome.toJson))
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
    ItineraryUserAerodromeQuery.byId(id).map{ itineraryUserAerodrome =>
      ItineraryUserAerodromeQuery.delete(itineraryUserAerodrome)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    ItineraryUserAerodromeQuery.byId(id).map{ itineraryUserAerodrome =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val user = UserQuery.getAll
    val aerodrome = AerodromeQuery.getAll
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = itineraryUserAerodrome.id)).map{ id =>
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