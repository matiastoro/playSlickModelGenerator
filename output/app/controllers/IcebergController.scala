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
object IcebergController extends ApplicationController {

/*
GET         /iceberg/                  controllers.IcebergController.index(page: Int = 1, pageLength: Int = 20)
GET         /iceberg/show/:id          controllers.IcebergController.show(id: Long)
GET         /iceberg/edit/:id          controllers.IcebergController.edit(id: Long)
GET         /iceberg/delete/:id          controllers.IcebergController.delete(id: Long)
GET         /iceberg/create            controllers.IcebergController.create()
POST        /iceberg/save              controllers.IcebergController.save()
POST        /iceberg/update/:id        controllers.IcebergController.update(id: Long)
GET         /iceberg/:page/:pageLength controllers.IcebergController.index(page: Int, pageLength: Int)
GET         /iceberg/:page             controllers.IcebergController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = IcebergConsulta.paginate(IcebergConsulta.allQuery,pageLength,page)
    Ok(views.html.iceberg.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    IcebergConsulta.byId(id).map{ iceberg =>
      Ok(views.html.iceberg.show(iceberg))
    }.getOrElse(NotFound)
  }
  val form = IcebergForm.form

  def create() = Action{ implicit request =>
    val location = LocationQuery.getAll
    Ok(views.html.iceberg.create(form, location))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val location = LocationQuery.getAll
        BadRequest(views.html.iceberg.create(formWithErrors, location))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.IcebergController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    IcebergConsulta.byId(id).map{ iceberg =>
      val location = LocationQuery.getAll
      Ok(views.html.iceberg.edit(form.fill(IcebergFormData(iceberg)), location, iceberg))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    IcebergConsulta.byId(id).map{ iceberg =>
      IcebergConsulta.delete(iceberg)
      Redirect(controllers.routes.IcebergController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    IcebergConsulta.byId(id).map{ iceberg =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val location = LocationQuery.getAll
          BadRequest(views.html.iceberg.edit(formWithErrors, location, iceberg))
        }, formData => {
          formData.update(formData.obj.copy(id = iceberg.id)).map{ id =>
            Redirect(controllers.routes.IcebergController.show(iceberg.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}