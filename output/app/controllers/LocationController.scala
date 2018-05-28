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
object LocationController extends ApplicationController {

/*
GET         /location/                  controllers.LocationController.index(page: Int = 1, pageLength: Int = 20)
GET         /location/show/:id          controllers.LocationController.show(id: Long)
GET         /location/edit/:id          controllers.LocationController.edit(id: Long)
GET         /location/delete/:id          controllers.LocationController.delete(id: Long)
GET         /location/create            controllers.LocationController.create()
POST        /location/save              controllers.LocationController.save()
POST        /location/update/:id        controllers.LocationController.update(id: Long)
GET         /location/:page/:pageLength controllers.LocationController.index(page: Int, pageLength: Int)
GET         /location/:page             controllers.LocationController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = LocationConsulta.paginate(LocationConsulta.allQuery,pageLength,page)
    Ok(views.html.location.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    LocationConsulta.byId(id).map{ location =>
      Ok(views.html.location.show(location))
    }.getOrElse(NotFound)
  }
  val form = LocationForm.form

  def create() = Action{ implicit request =>

    Ok(views.html.location.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        BadRequest(views.html.location.create(formWithErrors))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.LocationController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    LocationConsulta.byId(id).map{ location =>
  
      Ok(views.html.location.edit(form.fill(LocationFormData(location)), location))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    LocationConsulta.byId(id).map{ location =>
      LocationConsulta.delete(location)
      Redirect(controllers.routes.LocationController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    LocationConsulta.byId(id).map{ location =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          BadRequest(views.html.location.edit(formWithErrors, location))
        }, formData => {
          formData.update(formData.obj.copy(id = location.id)).map{ id =>
            Redirect(controllers.routes.LocationController.show(location.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}