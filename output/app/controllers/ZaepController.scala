package controllers.ifiscore

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
object ZaepController extends ApplicationController {

/*
GET         /zaep/                  controllers.ifiscore.ZaepController.index(page: Int = 1, pageLength: Int = 20)
GET         /zaep/show/:id          controllers.ifiscore.ZaepController.show(id: Long)
GET         /zaep/edit/:id          controllers.ifiscore.ZaepController.edit(id: Long)
GET         /zaep/delete/:id          controllers.ifiscore.ZaepController.delete(id: Long)
GET         /zaep/create            controllers.ifiscore.ZaepController.create()
POST        /zaep/save              controllers.ifiscore.ZaepController.save()
POST        /zaep/update/:id        controllers.ifiscore.ZaepController.update(id: Long)
GET         /zaep/:page/:pageLength controllers.ifiscore.ZaepController.index(page: Int, pageLength: Int)
GET         /zaep/:page             controllers.ifiscore.ZaepController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = ZaepQuery.paginate(ZaepQuery.allQuery,pageLength,page)
    Ok(views.html.ifiscore.zaep.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    ZaepQuery.byId(id).map{ zaep =>
      Ok(views.html.ifiscore.zaep.show(zaep))
    }.getOrElse(NotFound)
  }
  val form = ZaepForm.form

  def create() = Action{ implicit request =>

    Ok(views.html.ifiscore.zaep.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    
        BadRequest(views.html.ifiscore.zaep.create(formWithErrors))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.ifiscore.routes.ZaepController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    ZaepQuery.byId(id).map{ zaep =>
  
      Ok(views.html.ifiscore.zaep.edit(form.fill(ZaepFormData(zaep)), zaep))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    ZaepQuery.byId(id).map{ zaep =>
      ZaepQuery.delete(zaep)
      Redirect(controllers.ifiscore.routes.ZaepController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    ZaepQuery.byId(id).map{ zaep =>
      form.bindFromRequest.fold(
        formWithErrors => {
     
          BadRequest(views.html.ifiscore.zaep.edit(formWithErrors, zaep))
        }, formData => {
          formData.update(formData.obj.copy(id = zaep.id, createdAt = zaep.createdAt)).map{ id =>
            Redirect(controllers.ifiscore.routes.ZaepController.show(zaep.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}