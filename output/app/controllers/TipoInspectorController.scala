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
object TipoInspectorController extends ApplicationController {

/*
GET         /tipoInspector/                  controllers.TipoInspectorController.index(page: Int = 1, pageLength: Int = 20)
GET         /tipoInspector/show/:id          controllers.TipoInspectorController.show(id: Long)
GET         /tipoInspector/edit/:id          controllers.TipoInspectorController.edit(id: Long)
GET         /tipoInspector/delete/:id          controllers.TipoInspectorController.delete(id: Long)
GET         /tipoInspector/create            controllers.TipoInspectorController.create()
POST        /tipoInspector/save              controllers.TipoInspectorController.save()
POST        /tipoInspector/update/:id        controllers.TipoInspectorController.update(id: Long)
GET         /tipoInspector/:page/:pageLength controllers.TipoInspectorController.index(page: Int, pageLength: Int)
GET         /tipoInspector/:page             controllers.TipoInspectorController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = TipoInspectorQuery.paginate(TipoInspectorQuery.allQuery,pageLength,page)
    Ok(views.html.tipoInspector.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    TipoInspectorQuery.byId(id).map{ tipoInspector =>
      Ok(views.html.tipoInspector.show(tipoInspector))
    }.getOrElse(NotFound)
  }
  val form = TipoInspectorForm.form

  def create() = Action{ implicit request =>

    Ok(views.html.tipoInspector.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        BadRequest(views.html.tipoInspector.create(formWithErrors))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.TipoInspectorController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    TipoInspectorQuery.byId(id).map{ tipoInspector =>
  
      Ok(views.html.tipoInspector.edit(form.fill(TipoInspectorFormData(tipoInspector)), tipoInspector))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    TipoInspectorQuery.byId(id).map{ tipoInspector =>
      TipoInspectorQuery.delete(tipoInspector)
      Redirect(controllers.routes.TipoInspectorController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    TipoInspectorQuery.byId(id).map{ tipoInspector =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          BadRequest(views.html.tipoInspector.edit(formWithErrors, tipoInspector))
        }, formData => {
          formData.update(formData.obj.copy(id = tipoInspector.id)).map{ id =>
            Redirect(controllers.routes.TipoInspectorController.show(tipoInspector.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}