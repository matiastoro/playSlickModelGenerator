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
import play.api.data.Field
object LocationCountryController extends ApplicationController {

/*
GET         /locationCountry/                  controllers.LocationCountryController.index(page: Int = 1, pageLength: Int = 20)
GET         /locationCountry/show/:id          controllers.LocationCountryController.show(id: Long)
GET         /locationCountry/edit/:id          controllers.LocationCountryController.edit(id: Long)
GET         /locationCountry/delete/:id          controllers.LocationCountryController.delete(id: Long)
GET         /locationCountry/create            controllers.LocationCountryController.create()
GET         /locationCountry/nested            controllers.LocationCountryController.createNested()
POST        /locationCountry/save              controllers.LocationCountryController.save()
POST        /locationCountry/update/:id        controllers.LocationCountryController.update(id: Long)
GET         /locationCountry/:page/:pageLength controllers.LocationCountryController.index(page: Int, pageLength: Int)
GET         /locationCountry/:page             controllers.LocationCountryController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = LocationCountryConsulta.paginate(LocationCountryConsulta.allQuery,pageLength,page)
    Ok(views.html.locationCountry.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    LocationCountryConsulta.byId(id).map{ locationCountry =>
      Ok(views.html.locationCountry.show(locationCountry))
    }.getOrElse(NotFound)
  }
  val form = LocationCountryForm.form

  def create() = Action{ implicit request =>
    val location = LocationQuery.getAll
    val country = CountryQuery.getAll
    Ok(views.html.locationCountry.create(form, location, country))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val location = LocationQuery.getAll
    val country = CountryQuery.getAll
        BadRequest(views.html.locationCountry.create(formWithErrors, location, country))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.LocationCountryController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    LocationCountryConsulta.byId(id).map{ locationCountry =>
      val location = LocationQuery.getAll
    val country = CountryQuery.getAll
      Ok(views.html.locationCountry.edit(form.fill(LocationCountryFormData(locationCountry)), location, country, locationCountry))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    LocationCountryConsulta.byId(id).map{ locationCountry =>
      LocationCountryConsulta.delete(locationCountry)
      Redirect(controllers.routes.LocationCountryController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    LocationCountryConsulta.byId(id).map{ locationCountry =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val location = LocationQuery.getAll
    val country = CountryQuery.getAll
          BadRequest(views.html.locationCountry.edit(formWithErrors, location, country, locationCountry))
        }, formData => {
          formData.update(formData.obj.copy(id = locationCountry.id)).map{ id =>
            Redirect(controllers.routes.LocationCountryController.show(locationCountry.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def nestedForm(form: Field)(implicit request: Request[AnyContent]) = {
    val location = LocationQuery.getAll
    val country = CountryQuery.getAll
    views.html.locationCountry._nestedForm(form, location, country)
  }

  def createNested() = Action{ implicit request =>
    val i = request.getQueryString("i").getOrElse("0").toInt
    val name = request.getQueryString("name").getOrElse("locationCountrys")
    val location = LocationQuery.getAll
    val country = CountryQuery.getAll
    Ok(views.html.locationCountry._nestedForm(Field(form, name+"["+i+"]", Seq(), None, Seq(), None), location, country))
  }

  def showByLocation(id: Option[Long]) = {
    views.html.locationCountry._nestedShow(LocationCountryConsulta.byLocationId(id))
  }
}