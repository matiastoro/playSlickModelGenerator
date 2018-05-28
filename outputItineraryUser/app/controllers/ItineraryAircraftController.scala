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
object ItineraryAircraftController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[ItineraryAircraft]

/*
GET         /itineraryAircraft/                  controllers.ItineraryAircraftController.index(page: Int = 1, pageLength: Int = 20)
GET         /itineraryAircraft/show/:id          controllers.ItineraryAircraftController.show(id: Long)
GET         /itineraryAircraft/delete/:id          controllers.ItineraryAircraftController.delete(id: Long)
POST        /itineraryAircraft/save              controllers.ItineraryAircraftController.save()
POST        /itineraryAircraft/update/:id        controllers.ItineraryAircraftController.update(id: Long)
GET         /itineraryAircraft/options             controllers.ItineraryAircraftController.options()
GET         /itineraryAircraft/:page/:pageLength controllers.ItineraryAircraftController.index(page: Int, pageLength: Int)
GET         /itineraryAircraft/:page             controllers.ItineraryAircraftController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = ItineraryAircraftQuery.paginate(ItineraryAircraftQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = ItineraryAircraftForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    ItineraryAircraftQuery.byId(id).map{ itineraryAircraft =>
      Ok(Json.obj("obj" -> itineraryAircraft.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val itinerary_aircraft_manufacturer = ItineraryAircraftManufacturerQuery.getAll
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    ItineraryAircraftQuery.byId(id).map{ itineraryAircraft =>
      ItineraryAircraftQuery.delete(itineraryAircraft)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    ItineraryAircraftQuery.byId(id).map{ itineraryAircraft =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val itinerary_aircraft_manufacturer = ItineraryAircraftManufacturerQuery.getAll
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = itineraryAircraft.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val itinerary_aircraft_manufacturers = ItineraryAircraftManufacturerQuery.getAll.map(_.toJson)
    Ok(Json.obj("itinerary_aircraft_manufacturers" -> itinerary_aircraft_manufacturers))
  }
}