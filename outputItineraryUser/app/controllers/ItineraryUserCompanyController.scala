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
object ItineraryUserCompanyController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[ItineraryUserCompany]

/*
GET         /itineraryUserCompany/                  controllers.ItineraryUserCompanyController.index(page: Int = 1, pageLength: Int = 20)
GET         /itineraryUserCompany/show/:id          controllers.ItineraryUserCompanyController.show(id: Long)
GET         /itineraryUserCompany/delete/:id          controllers.ItineraryUserCompanyController.delete(id: Long)
POST        /itineraryUserCompany/save              controllers.ItineraryUserCompanyController.save()
POST        /itineraryUserCompany/update/:id        controllers.ItineraryUserCompanyController.update(id: Long)
GET         /itineraryUserCompany/options             controllers.ItineraryUserCompanyController.options()
GET         /itineraryUserCompany/:page/:pageLength controllers.ItineraryUserCompanyController.index(page: Int, pageLength: Int)
GET         /itineraryUserCompany/:page             controllers.ItineraryUserCompanyController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = ItineraryUserCompanyQuery.paginate(ItineraryUserCompanyQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = ItineraryUserCompanyForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    ItineraryUserCompanyQuery.byId(id).map{ itineraryUserCompany =>
      Ok(Json.obj("obj" -> itineraryUserCompany.toJson))
    }.getOrElse(NotFound)
  }


  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val user = UserQuery.getAll
    val company = CompanyQuery.getAll
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
      }
    )
  }
  

  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    ItineraryUserCompanyQuery.byId(id).map{ itineraryUserCompany =>
      ItineraryUserCompanyQuery.delete(itineraryUserCompany)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    ItineraryUserCompanyQuery.byId(id).map{ itineraryUserCompany =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val user = UserQuery.getAll
    val company = CompanyQuery.getAll
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = itineraryUserCompany.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def options() = conUsuarioDB{ user =>  implicit request =>
    val users = UserQuery.getAll.map(_.toJson)
    val companys = CompanyQuery.getAll.map(_.toJson)
    Ok(Json.obj("users" -> users, "companys" -> companys))
  }
}