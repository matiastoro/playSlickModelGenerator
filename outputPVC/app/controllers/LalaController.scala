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
object LalaController extends ApplicationController {

/*
GET         /lala/                  controllers.LalaController.index(page: Int = 1, pageLength: Int = 20)
GET         /lala/show/:id          controllers.LalaController.show(id: Long)
GET         /lala/edit/:id          controllers.LalaController.edit(id: Long)
GET         /lala/delete/:id          controllers.LalaController.delete(id: Long)
GET         /lala/create            controllers.LalaController.create()
POST        /lala/save              controllers.LalaController.save()
POST        /lala/update/:id        controllers.LalaController.update(id: Long)
GET         /lala/:page/:pageLength controllers.LalaController.index(page: Int, pageLength: Int)
GET         /lala/:page             controllers.LalaController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = LalaQuery.paginate(LalaQuery.allQuery,pageLength,page)
    Ok(views.html.lala.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    LalaQuery.byId(id).map{ lala =>
      Ok(views.html.lala.show(lala))
    }.getOrElse(NotFound)
  }
  val form = LalaForm.form

  def create() = Action{ implicit request =>

    Ok(views.html.lala.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        BadRequest(views.html.lala.create(formWithErrors))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.LalaController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    LalaQuery.byId(id).map{ lala =>
  
      Ok(views.html.lala.edit(form.fill(LalaFormData(lala)), lala))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    LalaQuery.byId(id).map{ lala =>
      LalaQuery.delete(lala)
      Redirect(controllers.routes.LalaController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    LalaQuery.byId(id).map{ lala =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          BadRequest(views.html.lala.edit(formWithErrors, lala))
        }, formData => {
          formData.update(formData.obj.copy(id = lala.id)).map{ id =>
            Redirect(controllers.routes.LalaController.show(lala.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}