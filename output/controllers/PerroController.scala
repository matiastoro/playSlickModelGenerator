package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Akka
import actors._

import play.api.Play.current
import models._
import models.extensions._


import org.joda.time.{DateTimeZone, DateTime}
import play.api.i18n.Messages
object PerroController extends ApplicationController {

/*
GET         /perro/                  controllers.PerroController.index(page: Int = 1, pageLength: Int = 20)
GET         /perro/show/:id          controllers.PerroController.show(id: Long)
GET         /perro/edit/:id          controllers.PerroController.edit(id: Long)
GET         /perro/delete/:id          controllers.PerroController.delete(id: Long)
GET         /perro/create            controllers.PerroController.create()
POST        /perro/save              controllers.PerroController.save()
POST        /perro/update/:id        controllers.PerroController.update(id: Long)
GET         /perro/:page/:pageLength controllers.PerroController.index(page: Int, pageLength: Int)
GET         /perro/:page             controllers.PerroController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = PerroQuery.paginate(PerroQuery.allQuery,pageLength,page)
    Ok(views.html.perro.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    PerroQuery.byId(id).map{ perro =>
      Ok(views.html.perro.show(perro))
    }.getOrElse(NotFound)
  }
  val form = PerroForm.form

  def create() = Action{ implicit request =>

    Ok(views.html.perro.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        BadRequest(views.html.perro.create(formWithErrors))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(routes.PerroController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    PerroQuery.byId(id).map{ perro =>
  
      Ok(views.html.perro.edit(form.fill(PerroFormData(perro)), perro))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    PerroQuery.byId(id).map{ perro =>
      PerroQuery.delete(perro)
      Redirect(routes.PerroController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    PerroQuery.byId(id).map{ perro =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          BadRequest(views.html.perro.edit(formWithErrors, perro))
        }, formData => {
          formData.update(formData.obj.copy(id = perro.id)).map{ id =>
            Redirect(routes.PerroController.show(perro.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}