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
object CompanyController extends ApplicationController {

/*
GET         /company/                  controllers.CompanyController.index(page: Int = 1, pageLength: Int = 20)
GET         /company/show/:id          controllers.CompanyController.show(id: Long)
GET         /company/edit/:id          controllers.CompanyController.edit(id: Long)
GET         /company/delete/:id          controllers.CompanyController.delete(id: Long)
GET         /company/create            controllers.CompanyController.create()
POST        /company/save              controllers.CompanyController.save()
POST        /company/update/:id        controllers.CompanyController.update(id: Long)
GET         /company/:page/:pageLength controllers.CompanyController.index(page: Int, pageLength: Int)
GET         /company/:page             controllers.CompanyController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = CompanyQuery.paginate(CompanyQuery.allQuery,pageLength,page)
    Ok(views.html.company.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    CompanyQuery.byId(id).map{ company =>
      Ok(views.html.company.show(company))
    }.getOrElse(NotFound)
  }
  val form = CompanyForm.form

  def create() = Action{ implicit request =>

    Ok(views.html.company.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        BadRequest(views.html.company.create(formWithErrors))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.CompanyController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    CompanyQuery.byId(id).map{ company =>
  
      Ok(views.html.company.edit(form.fill(CompanyFormData(company)), company))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    CompanyQuery.byId(id).map{ company =>
      CompanyQuery.delete(company)
      Redirect(controllers.routes.CompanyController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    CompanyQuery.byId(id).map{ company =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          BadRequest(views.html.company.edit(formWithErrors, company))
        }, formData => {
          formData.update(formData.obj.copy(id = company.id, createdAt = company.createdAt)).map{ id =>
            Redirect(controllers.routes.CompanyController.show(company.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}