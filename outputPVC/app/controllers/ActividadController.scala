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
object ActividadController extends ApplicationController {

/*
GET         /actividad/                  controllers.ActividadController.index(page: Int = 1, pageLength: Int = 20)
GET         /actividad/show/:id          controllers.ActividadController.show(id: Long)
GET         /actividad/edit/:id          controllers.ActividadController.edit(id: Long)
GET         /actividad/delete/:id          controllers.ActividadController.delete(id: Long)
GET         /actividad/create            controllers.ActividadController.create()
POST        /actividad/save              controllers.ActividadController.save()
POST        /actividad/update/:id        controllers.ActividadController.update(id: Long)
GET         /actividad/:page/:pageLength controllers.ActividadController.index(page: Int, pageLength: Int)
GET         /actividad/:page             controllers.ActividadController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = ActividadConsulta.paginate(ActividadConsulta.allQuery,pageLength,page)
    Ok(views.html.actividad.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    ActividadConsulta.byId(id).map{ actividad =>
      Ok(views.html.actividad.show(actividad))
    }.getOrElse(NotFound)
  }
  val form = ActividadForm.form

  def create() = Action{ implicit request =>

    Ok(views.html.actividad.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        BadRequest(views.html.actividad.create(formWithErrors))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.ActividadController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    ActividadConsulta.byId(id).map{ actividad =>
  
      Ok(views.html.actividad.edit(form.fill(ActividadFormData(actividad)), actividad))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    ActividadConsulta.byId(id).map{ actividad =>
      ActividadConsulta.delete(actividad)
      Redirect(controllers.routes.ActividadController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    ActividadConsulta.byId(id).map{ actividad =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          BadRequest(views.html.actividad.edit(formWithErrors, actividad))
        }, formData => {
          formData.update(formData.obj.copy(id = actividad.id)).map{ id =>
            Redirect(controllers.routes.ActividadController.show(actividad.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}