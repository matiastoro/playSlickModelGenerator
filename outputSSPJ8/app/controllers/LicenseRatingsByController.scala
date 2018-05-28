package controllers
import javax.inject._
import models._
import models.extensions._
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.Controller
import play.api.Play.current
import play.api.mvc._
import play.api.mvc.BodyParsers._
import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.concurrent.Akka
import models._
import models.extensions._
import org.joda.time.{DateTimeZone, DateTime}
import scala.concurrent.{ExecutionContext, Future}
import play.api.i18n.Messages


class LicenseRatingsByController @Inject()(cc: MessagesControllerComponents)(implicit repo: LicenseRatingsByRepository, ec: ExecutionContext) extends MessagesAbstractController(cc) with Autorization {

/*
GET         /licenseRatingsBy/                  controllers.LicenseRatingsByController.index(page: Int = 1, pageLength: Int = 20)
GET         /licenseRatingsBy/show/:id          controllers.LicenseRatingsByController.show(id: Long)
GET         /licenseRatingsBy/delete/:id          controllers.LicenseRatingsByController.delete(id: Long)
POST        /licenseRatingsBy/save              controllers.LicenseRatingsByController.save()
POST        /licenseRatingsBy/update/:id        controllers.LicenseRatingsByController.update(id: Long)
GET         /licenseRatingsBy/options             controllers.LicenseRatingsByController.options()
GET         /licenseRatingsBy/:page/:pageLength controllers.LicenseRatingsByController.index(page: Int, pageLength: Int)
GET         /licenseRatingsBy/:page             controllers.LicenseRatingsByController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = withUserAsync{ user =>  implicit request =>
    repo.paginate(repo.all,pageLength,page).map{ pagination =>
      Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> page, "pageLength" -> pageLength)))
    }
  }

  def show(id: Long) = withUserAsync{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = LicenseRatingsByForm.form

  def objectResponse(id: Long)(implicit session: Session) = {
    repo.byId(id).flatMap{ oobj =>
      oobj.map{obj =>
        Future{Ok(Json.obj("obj" -> (Json.toJson(obj).as[JsObject] )))}
      }.getOrElse(Future{OrElse})
    }
  }


  def save() = withUserAsync{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        Future{Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))}
      }, formData => {
        formData.insert(formData.obj).flatMap{id =>
          objectResponse(id)
        }
      }
    )
  }
  

  def delete(id: Long) = withUserAsync{ user =>  implicit request =>
    repo.byId(id).map{ oobj =>
      oobj.map{ obj =>
        repo.delete(obj)
        Ok("ok")
      }.getOrElse(OrElse)
    }
  }

  def update(id: Long) = withUserAsync{ user =>  implicit request =>
    repo.byId(id).flatMap { oobj =>
      oobj.map { obj =>
        form.bindFromRequest.fold(
          formWithErrors => {
            Future{Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))}
          }, formData => {
            formData.update(formData.obj.copy(id = obj.id)).flatMap { _ =>
              objectResponse(id)
            }
          }
        )
      }.getOrElse(Future{OrElse})
    }
  }
}