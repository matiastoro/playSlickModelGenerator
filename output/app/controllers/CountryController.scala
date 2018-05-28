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
object CountryController extends ApplicationController {

/*
GET         /country/                  controllers.CountryController.index(page: Int = 1, pageLength: Int = 20)
GET         /country/show/:id          controllers.CountryController.show(id: Long)
GET         /country/edit/:id          controllers.CountryController.edit(id: Long)
GET         /country/delete/:id          controllers.CountryController.delete(id: Long)
GET         /country/create            controllers.CountryController.create()
POST        /country/save              controllers.CountryController.save()
POST        /country/update/:id        controllers.CountryController.update(id: Long)
GET         /country/:page/:pageLength controllers.CountryController.index(page: Int, pageLength: Int)
GET         /country/:page             controllers.CountryController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = CountryConsulta.paginate(CountryConsulta.allQuery,pageLength,page)
    Ok(views.html.country.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    CountryConsulta.byId(id).map{ country =>
      Ok(views.html.country.show(country))
    }.getOrElse(NotFound)
  }
  val form = CountryForm.form

  def create() = Action{ implicit request =>

    Ok(views.html.country.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        BadRequest(views.html.country.create(formWithErrors))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.CountryController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    CountryConsulta.byId(id).map{ country =>
  
      Ok(views.html.country.edit(form.fill(CountryFormData(country)), country))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    CountryConsulta.byId(id).map{ country =>
      CountryConsulta.delete(country)
      Redirect(controllers.routes.CountryController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    CountryConsulta.byId(id).map{ country =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          BadRequest(views.html.country.edit(formWithErrors, country))
        }, formData => {
          formData.update(formData.obj.copy(id = country.id)).map{ id =>
            Redirect(controllers.routes.CountryController.show(country.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}