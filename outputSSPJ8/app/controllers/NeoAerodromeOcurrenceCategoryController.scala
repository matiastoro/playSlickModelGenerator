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
import play.api.data.Field


class NeoAerodromeOcurrenceCategoryController @Inject()(cc: MessagesControllerComponents)(implicit val repo: NeoAerodromeOcurrenceCategoryRepository, neo_aerodromeRepo: NeoAerodromeRepository, ocurrence_categoryRepo: OcurrenceCategoryRepository, ec: ExecutionContext) extends MessagesAbstractController(cc) with Autorization {

/*
GET         /neoAerodromeOcurrenceCategory/                  controllers.NeoAerodromeOcurrenceCategoryController.index(page: Int = 1, pageLength: Int = 20)
GET         /neoAerodromeOcurrenceCategory/show/:id          controllers.NeoAerodromeOcurrenceCategoryController.show(id: Long)
GET         /neoAerodromeOcurrenceCategory/delete/:id          controllers.NeoAerodromeOcurrenceCategoryController.delete(id: Long)
POST        /neoAerodromeOcurrenceCategory/save              controllers.NeoAerodromeOcurrenceCategoryController.save()
POST        /neoAerodromeOcurrenceCategory/update/:id        controllers.NeoAerodromeOcurrenceCategoryController.update(id: Long)
GET         /neoAerodromeOcurrenceCategory/options             controllers.NeoAerodromeOcurrenceCategoryController.options()
GET         /neoAerodromeOcurrenceCategory/:page/:pageLength controllers.NeoAerodromeOcurrenceCategoryController.index(page: Int, pageLength: Int)
GET         /neoAerodromeOcurrenceCategory/:page             controllers.NeoAerodromeOcurrenceCategoryController.index(page: Int, pageLength: Int = 20)
*/

  def getQuery[A](implicit request: MessagesRequest[A]) = {
    if(!request.queryString.isEmpty){
      filterForm.bindFromRequest.fold(
        formWithErrors => {
          repo.all
        }, formData => {
          repo.filter(formData)
        })
    } else{
      repo.all
    }
  }

  def index(page: Int = 1, pageLength: Int = 20) = withUserAsync{ user =>  implicit request =>
    val q = getQuery
    repo.paginate(q,pageLength,page).flatMap{ pagination =>
      Future.sequence{
        pagination.results.map{ obj =>
          getJson(obj)
        }
      }.map{ r =>
        Ok(Json.toJson(Json.obj("results" -> r, "count" -> pagination.count, "page" -> page, "pageLength" -> pageLength)))
      }
    }
  }

  def show(id: Long) = withUserAsync{ user =>  implicit request =>
    objectResponse(id)
  }
  val form = NeoAerodromeOcurrenceCategoryForm.form
  val filterForm = NeoAerodromeOcurrenceCategoryForm.filterForm

  def getJson(obj: NeoAerodromeOcurrenceCategory): Future[JsObject] = {
    Future{}
  }
  def objectResponse(id: Long)(implicit session: Session) = {
    repo.byId(id).flatMap{ oobj =>
      oobj.map{obj =>
       getJson(obj).map{ x =>  Ok(Json.obj("obj" -> x)) }
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
  def options() = withUserAsync{ user =>  implicit request =>
    for{
      neoAerodromes <- neo_aerodromeRepo.getAll
      ocurrenceCategorys <- ocurrence_categoryRepo.getAll
      } yield {
        Ok(Json.obj("neoAerodromes" -> Json.toJson(neoAerodromes), "ocurrenceCategorys" -> Json.toJson(ocurrenceCategorys)))
      }
       
  }
}