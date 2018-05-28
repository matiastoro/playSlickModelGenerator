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
object AirfieldController extends ApplicationController {

/*
GET         /airfield/                  controllers.AirfieldController.index(page: Int = 1, pageLength: Int = 20)
GET         /airfield/show/:id          controllers.AirfieldController.show(id: Long)
GET         /airfield/edit/:id          controllers.AirfieldController.edit(id: Long)
GET         /airfield/delete/:id          controllers.AirfieldController.delete(id: Long)
GET         /airfield/create            controllers.AirfieldController.create()
POST        /airfield/save              controllers.AirfieldController.save()
POST        /airfield/update/:id        controllers.AirfieldController.update(id: Long)
GET         /airfield/:page/:pageLength controllers.AirfieldController.index(page: Int, pageLength: Int)
GET         /airfield/:page             controllers.AirfieldController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = AirfieldConsulta.paginate(AirfieldConsulta.allQuery,pageLength,page)
    Ok(views.html.airfield.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    AirfieldConsulta.byId(id).map{ airfield =>
      Ok(views.html.airfield.show(airfield))
    }.getOrElse(NotFound)
  }
  val form = AirfieldForm.form

  def create() = Action{ implicit request =>
    val location = LocationQuery.getAll
    Ok(views.html.airfield.create(form, location))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val location = LocationQuery.getAll
        BadRequest(views.html.airfield.create(formWithErrors, location))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.AirfieldController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    AirfieldConsulta.byId(id).map{ airfield =>
      val location = LocationQuery.getAll
      Ok(views.html.airfield.edit(form.fill(AirfieldFormData(airfield)), location, airfield))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    AirfieldConsulta.byId(id).map{ airfield =>
      AirfieldConsulta.delete(airfield)
      Redirect(controllers.routes.AirfieldController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    AirfieldConsulta.byId(id).map{ airfield =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val location = LocationQuery.getAll
          BadRequest(views.html.airfield.edit(formWithErrors, location, airfield))
        }, formData => {
          formData.update(formData.obj.copy(id = airfield.id)).map{ id =>
            Redirect(controllers.routes.AirfieldController.show(airfield.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}