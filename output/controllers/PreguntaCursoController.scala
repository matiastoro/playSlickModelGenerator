package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Akka
import actors._

import play.api.Play.current
import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

import org.joda.time.{DateTimeZone, DateTime}
import play.api.i18n.Messages
object PreguntaCursoController extends ApplicationController {

/*
GET         /preguntaCurso/                  controllers.PreguntaCursoController.index(page: Int = 1, pageLength: Int = 20)
GET         /preguntaCurso/show/:id          controllers.PreguntaCursoController.show(id: Long)
GET         /preguntaCurso/edit/:id          controllers.PreguntaCursoController.edit(id: Long)
GET         /preguntaCurso/create            controllers.PreguntaCursoController.create()
POST        /preguntaCurso/save              controllers.PreguntaCursoController.save()
POST        /preguntaCurso/update/:id        controllers.PreguntaCursoController.update(id: Long)
GET         /preguntaCurso/:page/:pageLength controllers.PreguntaCursoController.index(page: Int, pageLength: Int)
GET         /preguntaCurso/:page             controllers.PreguntaCursoController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = PreguntaCursoQuery.paginate(PreguntaCursoQuery.allQuery,pageLength,page)
    Ok(views.html.preguntaCurso.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    PreguntaCursoQuery.byId(id).map{ preguntaCurso =>
      Ok(views.html.preguntaCurso.show(preguntaCurso))
    }.getOrElse(NotFound)
  }

  val form = Form(
    mapping(
      "id" -> optional(longNumber),
      "preguntaId" -> longNumber,
      "cursoId" -> longNumber
    )(PreguntaCurso.apply)(PreguntaCurso.unapply)
    /*((id, preguntaId, cursoId) => {
      PreguntaCurso(id, preguntaId, cursoId)
    })((obj: PreguntaCurso) => {
      Some(obj.id, obj.preguntaId, obj.cursoId)
    }))*/
  )

  def create() = Action{ implicit request =>
    Ok(views.html.preguntaCurso.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.preguntaCurso.create(formWithErrors)),
      obj => {
        val id = PreguntaCursoQuery.insert(obj)
        Redirect(routes.PreguntaCursoController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    PreguntaCursoQuery.byId(id).map{ preguntaCurso =>

      Ok(views.html.preguntaCurso.edit(form.fill(preguntaCurso), preguntaCurso))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    PreguntaCursoQuery.byId(id).map{ preguntaCurso =>
      form.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.preguntaCurso.edit(formWithErrors, preguntaCurso)),
        obj => {
          PreguntaCursoQuery.update(obj.copy(id = preguntaCurso.id)).map{ id =>
            Redirect(routes.PreguntaCursoController.show(preguntaCurso.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}