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
object MobileStationController extends ApplicationController {

/*
GET         /mobileStation/                  controllers.MobileStationController.index(page: Int = 1, pageLength: Int = 20)
GET         /mobileStation/show/:id          controllers.MobileStationController.show(id: Long)
GET         /mobileStation/edit/:id          controllers.MobileStationController.edit(id: Long)
GET         /mobileStation/delete/:id          controllers.MobileStationController.delete(id: Long)
GET         /mobileStation/create            controllers.MobileStationController.create()
POST        /mobileStation/save              controllers.MobileStationController.save()
POST        /mobileStation/update/:id        controllers.MobileStationController.update(id: Long)
GET         /mobileStation/:page/:pageLength controllers.MobileStationController.index(page: Int, pageLength: Int)
GET         /mobileStation/:page             controllers.MobileStationController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = MobileStationConsulta.paginate(MobileStationConsulta.allQuery,pageLength,page)
    Ok(views.html.mobileStation.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    MobileStationConsulta.byId(id).map{ mobileStation =>
      Ok(views.html.mobileStation.show(mobileStation))
    }.getOrElse(NotFound)
  }
  val form = MobileStationForm.form

  def create() = Action{ implicit request =>
    val location = LocationQuery.getAll
    Ok(views.html.mobileStation.create(form, location))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val location = LocationQuery.getAll
        BadRequest(views.html.mobileStation.create(formWithErrors, location))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.MobileStationController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    MobileStationConsulta.byId(id).map{ mobileStation =>
      val location = LocationQuery.getAll
      Ok(views.html.mobileStation.edit(form.fill(MobileStationFormData(mobileStation)), location, mobileStation))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    MobileStationConsulta.byId(id).map{ mobileStation =>
      MobileStationConsulta.delete(mobileStation)
      Redirect(controllers.routes.MobileStationController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    MobileStationConsulta.byId(id).map{ mobileStation =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val location = LocationQuery.getAll
          BadRequest(views.html.mobileStation.edit(formWithErrors, location, mobileStation))
        }, formData => {
          formData.update(formData.obj.copy(id = mobileStation.id)).map{ id =>
            Redirect(controllers.routes.MobileStationController.show(mobileStation.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}