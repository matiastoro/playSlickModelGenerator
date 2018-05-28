package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Akka

import play.api.Play.current
import models._
import models.extensions._
import controllers.ApplicationController
import scala.slick.driver.H2Driver.simple._
import org.joda.time.{DateTimeZone, DateTime}
import play.api.i18n.Messages
object CacaController extends ApplicationController {

/*
GET         /caca/                  controllers.CacaController.index(page: Int = 1, pageLength: Int = 20)
GET         /caca/show/:id          controllers.CacaController.show(id: Long)
GET         /caca/edit/:id          controllers.CacaController.edit(id: Long)
GET         /caca/delete/:id          controllers.CacaController.delete(id: Long)
GET         /caca/create            controllers.CacaController.create()
POST        /caca/save              controllers.CacaController.save()
POST        /caca/update/:id        controllers.CacaController.update(id: Long)
GET         /caca/:page/:pageLength controllers.CacaController.index(page: Int, pageLength: Int)
GET         /caca/:page             controllers.CacaController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = CacaQuery.paginate(CacaQuery.allQuery,pageLength,page)
    Ok(views.html.caca.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    CacaQuery.byId(id).map{ caca =>
      Ok(views.html.caca.show(caca))
    }.getOrElse(NotFound)
  }
  val form = CacaForm.form

  def create() = Action{ implicit request =>

    Ok(views.html.caca.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        BadRequest(views.html.caca.create(formWithErrors))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.CacaController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    CacaQuery.byId(id).map{ caca =>
  
      Ok(views.html.caca.edit(form.fill(CacaFormData(caca)), caca))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    CacaQuery.byId(id).map{ caca =>
      CacaQuery.delete(caca)
      Redirect(controllers.routes.CacaController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    CacaQuery.byId(id).map{ caca =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          BadRequest(views.html.caca.edit(formWithErrors, caca))
        }, formData => {
          formData.update(formData.obj.copy(id = caca.id)).map{ id =>
            Redirect(controllers.routes.CacaController.show(caca.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}