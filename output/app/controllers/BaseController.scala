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
object BaseController extends ApplicationController {

/*
GET         /base/                  controllers.BaseController.index(page: Int = 1, pageLength: Int = 20)
GET         /base/show/:id          controllers.BaseController.show(id: Long)
GET         /base/edit/:id          controllers.BaseController.edit(id: Long)
GET         /base/delete/:id          controllers.BaseController.delete(id: Long)
GET         /base/create            controllers.BaseController.create()
POST        /base/save              controllers.BaseController.save()
POST        /base/update/:id        controllers.BaseController.update(id: Long)
GET         /base/:page/:pageLength controllers.BaseController.index(page: Int, pageLength: Int)
GET         /base/:page             controllers.BaseController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = BaseConsulta.paginate(BaseConsulta.allQuery,pageLength,page)
    Ok(views.html.base.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    BaseConsulta.byId(id).map{ base =>
      Ok(views.html.base.show(base))
    }.getOrElse(NotFound)
  }
  val form = BaseForm.form

  def create() = Action{ implicit request =>
    val location = LocationQuery.getAll
    Ok(views.html.base.create(form, location))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val location = LocationQuery.getAll
        BadRequest(views.html.base.create(formWithErrors, location))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.BaseController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    BaseConsulta.byId(id).map{ base =>
      val location = LocationQuery.getAll
      Ok(views.html.base.edit(form.fill(BaseFormData(base)), location, base))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    BaseConsulta.byId(id).map{ base =>
      BaseConsulta.delete(base)
      Redirect(controllers.routes.BaseController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    BaseConsulta.byId(id).map{ base =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val location = LocationQuery.getAll
          BadRequest(views.html.base.edit(formWithErrors, location, base))
        }, formData => {
          formData.update(formData.obj.copy(id = base.id)).map{ id =>
            Redirect(controllers.routes.BaseController.show(base.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}