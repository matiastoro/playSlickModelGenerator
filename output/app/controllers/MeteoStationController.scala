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
object MeteoStationController extends ApplicationController {

/*
GET         /meteoStation/                  controllers.MeteoStationController.index(page: Int = 1, pageLength: Int = 20)
GET         /meteoStation/show/:id          controllers.MeteoStationController.show(id: Long)
GET         /meteoStation/edit/:id          controllers.MeteoStationController.edit(id: Long)
GET         /meteoStation/delete/:id          controllers.MeteoStationController.delete(id: Long)
GET         /meteoStation/create            controllers.MeteoStationController.create()
POST        /meteoStation/save              controllers.MeteoStationController.save()
POST        /meteoStation/update/:id        controllers.MeteoStationController.update(id: Long)
GET         /meteoStation/:page/:pageLength controllers.MeteoStationController.index(page: Int, pageLength: Int)
GET         /meteoStation/:page             controllers.MeteoStationController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = MeteoStationConsulta.paginate(MeteoStationConsulta.allQuery,pageLength,page)
    Ok(views.html.meteoStation.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    MeteoStationConsulta.byId(id).map{ meteoStation =>
      Ok(views.html.meteoStation.show(meteoStation))
    }.getOrElse(NotFound)
  }
  val form = MeteoStationForm.form

  def create() = Action{ implicit request =>
    val location = LocationQuery.getAll
    Ok(views.html.meteoStation.create(form, location))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val location = LocationQuery.getAll
        BadRequest(views.html.meteoStation.create(formWithErrors, location))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.MeteoStationController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    MeteoStationConsulta.byId(id).map{ meteoStation =>
      val location = LocationQuery.getAll
      Ok(views.html.meteoStation.edit(form.fill(MeteoStationFormData(meteoStation)), location, meteoStation))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    MeteoStationConsulta.byId(id).map{ meteoStation =>
      MeteoStationConsulta.delete(meteoStation)
      Redirect(controllers.routes.MeteoStationController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    MeteoStationConsulta.byId(id).map{ meteoStation =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val location = LocationQuery.getAll
          BadRequest(views.html.meteoStation.edit(formWithErrors, location, meteoStation))
        }, formData => {
          formData.update(formData.obj.copy(id = meteoStation.id)).map{ id =>
            Redirect(controllers.routes.MeteoStationController.show(meteoStation.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}