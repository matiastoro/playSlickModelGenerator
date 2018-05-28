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
object CompanyController extends Controller with Autorizacion {
implicit val jsonFormat = Json.format[Company]

/*
GET         /company/                  controllers.CompanyController.index(page: Int = 1, pageLength: Int = 20)
GET         /company/show/:id          controllers.CompanyController.show(id: Long)
GET         /company/delete/:id          controllers.CompanyController.delete(id: Long)
POST        /company/save              controllers.CompanyController.save()
POST        /company/update/:id        controllers.CompanyController.update(id: Long)
GET         /company/options             controllers.CompanyController.options()
GET         /company/:page/:pageLength controllers.CompanyController.index(page: Int, pageLength: Int)
GET         /company/:page             controllers.CompanyController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = CompanyQuery.paginate(CompanyQuery.allQuery,pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }

  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = CompanyForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    CompanyQuery.byId(id).map{ company =>
      Ok(Json.obj("obj" -> company.toJson))
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
    CompanyQuery.byId(id).map{ company =>
      CompanyQuery.delete(company)
      Ok("ok")
    }.getOrElse(NotFound)
  }

  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    CompanyQuery.byId(id).map{ company =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = company.id)).map{ id =>
            objectResponse(id)
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}