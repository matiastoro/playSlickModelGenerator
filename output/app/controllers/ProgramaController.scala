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
object ProgramaController extends ApplicationController {

/*
GET         /programa/                  controllers.ProgramaController.index(page: Int = 1, pageLength: Int = 20)
GET         /programa/show/:id          controllers.ProgramaController.show(id: Long)
GET         /programa/edit/:id          controllers.ProgramaController.edit(id: Long)
GET         /programa/delete/:id          controllers.ProgramaController.delete(id: Long)
GET         /programa/create            controllers.ProgramaController.create()
POST        /programa/save              controllers.ProgramaController.save()
POST        /programa/update/:id        controllers.ProgramaController.update(id: Long)
GET         /programa/:page/:pageLength controllers.ProgramaController.index(page: Int, pageLength: Int)
GET         /programa/:page             controllers.ProgramaController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = ProgramaQuery.paginate(ProgramaQuery.allQuery,pageLength,page)
    Ok(views.html.programa.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    ProgramaQuery.byId(id).map{ programa =>
      Ok(views.html.programa.show(programa))
    }.getOrElse(NotFound)
  }
  val form = ProgramaForm.form

  def create() = Action{ implicit request =>

    Ok(views.html.programa.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        BadRequest(views.html.programa.create(formWithErrors))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.ProgramaController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    ProgramaQuery.byId(id).map{ programa =>
  
      Ok(views.html.programa.edit(form.fill(ProgramaFormData(programa)), programa))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    ProgramaQuery.byId(id).map{ programa =>
      ProgramaQuery.delete(programa)
      Redirect(controllers.routes.ProgramaController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    ProgramaQuery.byId(id).map{ programa =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          BadRequest(views.html.programa.edit(formWithErrors, programa))
        }, formData => {
          formData.update(formData.obj.copy(id = programa.id)).map{ id =>
            Redirect(controllers.routes.ProgramaController.show(programa.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}