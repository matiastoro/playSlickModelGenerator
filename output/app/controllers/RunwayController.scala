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
object RunwayController extends ApplicationController {

/*
GET         /runway/                  controllers.RunwayController.index(page: Int = 1, pageLength: Int = 20)
GET         /runway/show/:id          controllers.RunwayController.show(id: Long)
GET         /runway/edit/:id          controllers.RunwayController.edit(id: Long)
GET         /runway/delete/:id          controllers.RunwayController.delete(id: Long)
GET         /runway/create            controllers.RunwayController.create()
POST        /runway/save              controllers.RunwayController.save()
POST        /runway/update/:id        controllers.RunwayController.update(id: Long)
GET         /runway/:page/:pageLength controllers.RunwayController.index(page: Int, pageLength: Int)
GET         /runway/:page             controllers.RunwayController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = RunwayConsulta.paginate(RunwayConsulta.allQuery,pageLength,page)
    Ok(views.html.runway.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    RunwayConsulta.byId(id).map{ runway =>
      Ok(views.html.runway.show(runway))
    }.getOrElse(NotFound)
  }
  val form = RunwayForm.form

  def create() = Action{ implicit request =>

    Ok(views.html.runway.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        BadRequest(views.html.runway.create(formWithErrors))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.RunwayController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    RunwayConsulta.byId(id).map{ runway =>
  
      Ok(views.html.runway.edit(form.fill(RunwayFormData(runway)), runway))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    RunwayConsulta.byId(id).map{ runway =>
      RunwayConsulta.delete(runway)
      Redirect(controllers.routes.RunwayController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    RunwayConsulta.byId(id).map{ runway =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          BadRequest(views.html.runway.edit(formWithErrors, runway))
        }, formData => {
          formData.update(formData.obj.copy(id = runway.id)).map{ id =>
            Redirect(controllers.routes.RunwayController.show(runway.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}