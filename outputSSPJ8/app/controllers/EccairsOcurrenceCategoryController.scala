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


class EccairsOcurrenceCategoryController @Inject()(cc: MessagesControllerComponents)(implicit val repo: EccairsOcurrenceCategoryRepository, eccairsRepo: EccairsRepository, ocurrence_categoryRepo: OcurrenceCategoryRepository, ec: ExecutionContext) extends MessagesAbstractController(cc) with Autorization {

/*
GET         /eccairsOcurrenceCategory/                  controllers.EccairsOcurrenceCategoryController.index(page: Int = 1, pageLength: Int = 20, sortColumn: String = "id", sortOrder: String = "desc")
GET         /eccairsOcurrenceCategory/show/:id          controllers.EccairsOcurrenceCategoryController.show(id: Long)
GET         /eccairsOcurrenceCategory/delete/:id          controllers.EccairsOcurrenceCategoryController.delete(id: Long)
POST        /eccairsOcurrenceCategory/save              controllers.EccairsOcurrenceCategoryController.save()
POST        /eccairsOcurrenceCategory/update/:id        controllers.EccairsOcurrenceCategoryController.update(id: Long)
GET         /eccairsOcurrenceCategory/options             controllers.EccairsOcurrenceCategoryController.options()
GET         /eccairsOcurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder controllers.EccairsOcurrenceCategoryController.index(page: Int, pageLength: Int, sortColumn: String, sortOrder: String)
GET         /eccairsOcurrenceCategory/:page/:pageLength controllers.EccairsOcurrenceCategoryController.index(page: Int, pageLength: Int, sortColumn: String = "id", sortOrder: String = "desc")
GET         /eccairsOcurrenceCategory/:page             controllers.EccairsOcurrenceCategoryController.index(page: Int, pageLength: Int = 20, sortColumn: String = "id", sortOrder: String = "desc")
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

  def index(page: Int = 1, pageLength: Int = 20, sortColumn: String = "", sortOrder: String = "") = withUserAsync{ user =>  implicit request =>
    val q = getQuery
    repo.paginate(q,pageLength,page, sortColumn, sortOrder).flatMap{ pagination =>
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
  val form = EccairsOcurrenceCategoryForm.form
  val filterForm = EccairsOcurrenceCategoryFilterForm.filterForm

  def getJson(obj: EccairsOcurrenceCategory): Future[JsObject] = {
    Future{Json.toJson(obj).as[JsObject]}
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
      eccairss <- eccairsRepo.getAll
      ocurrenceCategorys <- ocurrence_categoryRepo.getAll
      } yield {
        Ok(Json.obj("eccairss" -> Json.toJson(eccairss), "ocurrenceCategorys" -> Json.toJson(ocurrenceCategorys)))
      }
       
  }
}