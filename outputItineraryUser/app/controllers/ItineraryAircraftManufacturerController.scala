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
object ItineraryAircraftManufacturerController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[ItineraryAircraftManufacturer]

/*
GET         /itineraryAircraftManufacturer/                  controllers.ItineraryAircraftManufacturerController.index(page: Int = 1, pageLength: Int = 20)
GET         /itineraryAircraftManufacturer/show/:id          controllers.ItineraryAircraftManufacturerController.show(id: Long)
GET         /itineraryAircraftManufacturer/delete/:id          controllers.ItineraryAircraftManufacturerController.delete(id: Long)
POST        /itineraryAircraftManufacturer/save              controllers.ItineraryAircraftManufacturerController.save()
POST        /itineraryAircraftManufacturer/update/:id        controllers.ItineraryAircraftManufacturerController.update(id: Long)
GET         /itineraryAircraftManufacturer/options             controllers.ItineraryAircraftManufacturerController.options()
GET         /itineraryAircraftManufacturer/:page/:pageLength controllers.ItineraryAircraftManufacturerController.index(page: Int, pageLength: Int)
GET         /itineraryAircraftManufacturer/:page             controllers.ItineraryAircraftManufacturerController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = ItineraryAircraftManufacturerQuery.paginate(ItineraryAircraftManufacturerQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = ItineraryAircraftManufacturerForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    ItineraryAircraftManufacturerQuery.byId(id).map{ itineraryAircraftManufacturer =>
      Ok(Json.obj("obj" -> itineraryAircraftManufacturer.toJson))
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
    ItineraryAircraftManufacturerQuery.byId(id).map{ itineraryAircraftManufacturer =>
      ItineraryAircraftManufacturerQuery.delete(itineraryAircraftManufacturer)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    ItineraryAircraftManufacturerQuery.byId(id).map{ itineraryAircraftManufacturer =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = itineraryAircraftManufacturer.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}