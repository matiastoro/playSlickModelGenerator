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
object InspeccionController extends ApplicationController {

/*
GET         /inspeccion/                  controllers.InspeccionController.index(page: Int = 1, pageLength: Int = 20)
GET         /inspeccion/show/:id          controllers.InspeccionController.show(id: Long)
GET         /inspeccion/edit/:id          controllers.InspeccionController.edit(id: Long)
GET         /inspeccion/delete/:id          controllers.InspeccionController.delete(id: Long)
GET         /inspeccion/create            controllers.InspeccionController.create()
GET         /inspeccion/nested            controllers.InspeccionController.createNested()
POST        /inspeccion/save              controllers.InspeccionController.save()
POST        /inspeccion/update/:id        controllers.InspeccionController.update(id: Long)
GET         /inspeccion/:page/:pageLength controllers.InspeccionController.index(page: Int, pageLength: Int)
GET         /inspeccion/:page             controllers.InspeccionController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = InspeccionConsulta.paginate(InspeccionConsulta.allQuery,pageLength,page)
    Ok(views.html.inspeccion.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    InspeccionConsulta.byId(id).map{ inspeccion =>
      Ok(views.html.inspeccion.show(inspeccion))
    }.getOrElse(NotFound)
  }
  val form = InspeccionForm.form

  def create() = Action{ implicit request =>
    val actividad = ActividadQuery.getAll
    Ok(views.html.inspeccion.create(form, actividad))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val actividad = ActividadQuery.getAll
        BadRequest(views.html.inspeccion.create(formWithErrors, actividad))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.InspeccionController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    InspeccionConsulta.byId(id).map{ inspeccion =>
      val actividad = ActividadQuery.getAll
      Ok(views.html.inspeccion.edit(form.fill(InspeccionFormData(inspeccion)), actividad, inspeccion))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    InspeccionConsulta.byId(id).map{ inspeccion =>
      InspeccionConsulta.delete(inspeccion)
      Redirect(controllers.routes.InspeccionController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    InspeccionConsulta.byId(id).map{ inspeccion =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val actividad = ActividadQuery.getAll
          BadRequest(views.html.inspeccion.edit(formWithErrors, actividad, inspeccion))
        }, formData => {
          formData.update(formData.obj.copy(id = inspeccion.id)).map{ id =>
            Redirect(controllers.routes.InspeccionController.show(inspeccion.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def nestedForm(form: Field)(implicit request: Request[AnyContent]) = {
    val actividad = ActividadQuery.getAll
    views.html.inspeccion._nestedForm(form, actividad)
  }

  def createNested() = Action{ implicit request =>
    val i = request.getQueryString("i").getOrElse("0").toInt
    val name = request.getQueryString("name").getOrElse("inspeccions")
    val actividad = ActividadQuery.getAll
    Ok(views.html.inspeccion._nestedForm(Field(form, name+"["+i+"]", Seq(), None, Seq(), None), actividad))
  }

  def showByActividad(id: Option[Long]) = {
    views.html.inspeccion._nestedShow(InspeccionConsulta.byActividadId(id))
  }
}