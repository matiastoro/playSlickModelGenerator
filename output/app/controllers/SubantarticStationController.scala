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
object SubantarticStationController extends ApplicationController {

/*
GET         /subantarticStation/                  controllers.SubantarticStationController.index(page: Int = 1, pageLength: Int = 20)
GET         /subantarticStation/show/:id          controllers.SubantarticStationController.show(id: Long)
GET         /subantarticStation/edit/:id          controllers.SubantarticStationController.edit(id: Long)
GET         /subantarticStation/delete/:id          controllers.SubantarticStationController.delete(id: Long)
GET         /subantarticStation/create            controllers.SubantarticStationController.create()
POST        /subantarticStation/save              controllers.SubantarticStationController.save()
POST        /subantarticStation/update/:id        controllers.SubantarticStationController.update(id: Long)
GET         /subantarticStation/:page/:pageLength controllers.SubantarticStationController.index(page: Int, pageLength: Int)
GET         /subantarticStation/:page             controllers.SubantarticStationController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = SubantarticStationConsulta.paginate(SubantarticStationConsulta.allQuery,pageLength,page)
    Ok(views.html.subantarticStation.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    SubantarticStationConsulta.byId(id).map{ subantarticStation =>
      Ok(views.html.subantarticStation.show(subantarticStation))
    }.getOrElse(NotFound)
  }
  val form = SubantarticStationForm.form

  def create() = Action{ implicit request =>
    val location = LocationQuery.getAll
    Ok(views.html.subantarticStation.create(form, location))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val location = LocationQuery.getAll
        BadRequest(views.html.subantarticStation.create(formWithErrors, location))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.SubantarticStationController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    SubantarticStationConsulta.byId(id).map{ subantarticStation =>
      val location = LocationQuery.getAll
      Ok(views.html.subantarticStation.edit(form.fill(SubantarticStationFormData(subantarticStation)), location, subantarticStation))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    SubantarticStationConsulta.byId(id).map{ subantarticStation =>
      SubantarticStationConsulta.delete(subantarticStation)
      Redirect(controllers.routes.SubantarticStationController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    SubantarticStationConsulta.byId(id).map{ subantarticStation =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val location = LocationQuery.getAll
          BadRequest(views.html.subantarticStation.edit(formWithErrors, location, subantarticStation))
        }, formData => {
          formData.update(formData.obj.copy(id = subantarticStation.id)).map{ id =>
            Redirect(controllers.routes.SubantarticStationController.show(subantarticStation.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}