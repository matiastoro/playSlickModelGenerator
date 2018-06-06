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
object PreguntaHabilidadCognitivaController extends ApplicationController {

/*
GET         /preguntaHabilidadCognitiva/                  controllers.PreguntaHabilidadCognitivaController.index(page: Int = 1, pageLength: Int = 20)
GET         /preguntaHabilidadCognitiva/show/:id          controllers.PreguntaHabilidadCognitivaController.show(id: Long)
GET         /preguntaHabilidadCognitiva/edit/:id          controllers.PreguntaHabilidadCognitivaController.edit(id: Long)
GET         /preguntaHabilidadCognitiva/create            controllers.PreguntaHabilidadCognitivaController.create()
POST        /preguntaHabilidadCognitiva/save              controllers.PreguntaHabilidadCognitivaController.save()
POST        /preguntaHabilidadCognitiva/update/:id        controllers.PreguntaHabilidadCognitivaController.update(id: Long)
GET         /preguntaHabilidadCognitiva/:page/:pageLength controllers.PreguntaHabilidadCognitivaController.index(page: Int, pageLength: Int)
GET         /preguntaHabilidadCognitiva/:page             controllers.PreguntaHabilidadCognitivaController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = PreguntaHabilidadCognitivaQuery.paginate(PreguntaHabilidadCognitivaQuery.allQuery,pageLength,page)
    Ok(views.html.preguntaHabilidadCognitiva.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    PreguntaHabilidadCognitivaQuery.byId(id).map{ preguntaHabilidadCognitiva =>
      Ok(views.html.preguntaHabilidadCognitiva.show(preguntaHabilidadCognitiva))
    }.getOrElse(NotFound)
  }

  val form = Form(
    mapping(
      "id" -> optional(longNumber),
      "preguntaId" -> longNumber,
      "habilidadCognitivaId" -> longNumber
    )(PreguntaHabilidadCognitiva.apply)(PreguntaHabilidadCognitiva.unapply)
    /*((id, preguntaId, habilidadCognitivaId) => {
      PreguntaHabilidadCognitiva(id, preguntaId, habilidadCognitivaId)
    })((obj: PreguntaHabilidadCognitiva) => {
      Some(obj.id, obj.preguntaId, obj.habilidadCognitivaId)
    }))*/
  )

  def create() = Action{ implicit request =>
    Ok(views.html.preguntaHabilidadCognitiva.create(form))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.preguntaHabilidadCognitiva.create(formWithErrors)),
      obj => {
        val id = PreguntaHabilidadCognitivaQuery.insert(obj)
        Redirect(routes.PreguntaHabilidadCognitivaController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    PreguntaHabilidadCognitivaQuery.byId(id).map{ preguntaHabilidadCognitiva =>

      Ok(views.html.preguntaHabilidadCognitiva.edit(form.fill(preguntaHabilidadCognitiva), preguntaHabilidadCognitiva))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    PreguntaHabilidadCognitivaQuery.byId(id).map{ preguntaHabilidadCognitiva =>
      form.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.preguntaHabilidadCognitiva.edit(formWithErrors, preguntaHabilidadCognitiva)),
        obj => {
          PreguntaHabilidadCognitivaQuery.update(obj.copy(id = preguntaHabilidadCognitiva.id)).map{ id =>
            Redirect(routes.PreguntaHabilidadCognitivaController.show(preguntaHabilidadCognitiva.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
}