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
object ProgramaTipoInspectorController extends ApplicationController {

/*
GET         /programaTipoInspector/                  controllers.ProgramaTipoInspectorController.index(page: Int = 1, pageLength: Int = 20)
GET         /programaTipoInspector/show/:id          controllers.ProgramaTipoInspectorController.show(id: Long)
GET         /programaTipoInspector/edit/:id          controllers.ProgramaTipoInspectorController.edit(id: Long)
GET         /programaTipoInspector/delete/:id          controllers.ProgramaTipoInspectorController.delete(id: Long)
GET         /programaTipoInspector/create            controllers.ProgramaTipoInspectorController.create()
POST        /programaTipoInspector/save              controllers.ProgramaTipoInspectorController.save()
POST        /programaTipoInspector/update/:id        controllers.ProgramaTipoInspectorController.update(id: Long)
GET         /programaTipoInspector/:page/:pageLength controllers.ProgramaTipoInspectorController.index(page: Int, pageLength: Int)
GET         /programaTipoInspector/:page             controllers.ProgramaTipoInspectorController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = ProgramaTipoInspectorQuery.paginate(ProgramaTipoInspectorQuery.allQuery,pageLength,page)
    Ok(views.html.programaTipoInspector.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    ProgramaTipoInspectorQuery.byId(id).map{ programaTipoInspector =>
      Ok(views.html.programaTipoInspector.show(programaTipoInspector))
    }.getOrElse(NotFound)
  }
  val form = ProgramaTipoInspectorForm.form

  def create() = Action{ implicit request =>

    Ok(views.html.programaTipoInspector.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        BadRequest(views.html.programaTipoInspector.create(formWithErrors))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.ProgramaTipoInspectorController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    ProgramaTipoInspectorQuery.byId(id).map{ programaTipoInspector =>
  
      Ok(views.html.programaTipoInspector.edit(form.fill(ProgramaTipoInspectorFormData(programaTipoInspector)), programaTipoInspector))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    ProgramaTipoInspectorQuery.byId(id).map{ programaTipoInspector =>
      ProgramaTipoInspectorQuery.delete(programaTipoInspector)
      Redirect(controllers.routes.ProgramaTipoInspectorController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    ProgramaTipoInspectorQuery.byId(id).map{ programaTipoInspector =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          BadRequest(views.html.programaTipoInspector.edit(formWithErrors, programaTipoInspector))
        }, formData => {
          formData.update(formData.obj.copy(id = programaTipoInspector.id)).map{ id =>
            Redirect(controllers.routes.ProgramaTipoInspectorController.show(programaTipoInspector.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}