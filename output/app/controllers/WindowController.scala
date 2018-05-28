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
object WindowController extends ApplicationController {

/*
GET         /window/                  controllers.WindowController.index(page: Int = 1, pageLength: Int = 20)
GET         /window/show/:id          controllers.WindowController.show(id: Long)
GET         /window/edit/:id          controllers.WindowController.edit(id: Long)
GET         /window/delete/:id          controllers.WindowController.delete(id: Long)
GET         /window/create            controllers.WindowController.create()
GET         /window/nested            controllers.WindowController.createNested()
POST        /window/save              controllers.WindowController.save()
POST        /window/update/:id        controllers.WindowController.update(id: Long)
GET         /window/:page/:pageLength controllers.WindowController.index(page: Int, pageLength: Int)
GET         /window/:page             controllers.WindowController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = WindowQuery.paginate(WindowQuery.allQuery,pageLength,page)
    Ok(views.html.window.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    WindowQuery.byId(id).map{ window =>
      Ok(views.html.window.show(window))
    }.getOrElse(NotFound)
  }
  val form = WindowForm.form

  def create() = Action{ implicit request =>
    val branch = BranchQuery.getAll
    Ok(views.html.window.create(form, branch))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val branch = BranchQuery.getAll
        BadRequest(views.html.window.create(formWithErrors, branch))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.routes.WindowController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    WindowQuery.byId(id).map{ window =>
      val branch = BranchQuery.getAll
      Ok(views.html.window.edit(form.fill(WindowFormData(window)), branch, window))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    WindowQuery.byId(id).map{ window =>
      WindowQuery.delete(window)
      Redirect(controllers.routes.WindowController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    WindowQuery.byId(id).map{ window =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val branch = BranchQuery.getAll
          BadRequest(views.html.window.edit(formWithErrors, branch, window))
        }, formData => {
          formData.update(formData.obj.copy(id = window.id)).map{ id =>
            Redirect(controllers.routes.WindowController.show(window.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def nestedForm(form: Field)(implicit request: Request[AnyContent]) = {
    val branch = BranchQuery.getAll
    views.html.window._nestedForm(form, branch)
  }

  def createNested() = Action{ implicit request =>
    val i = request.getQueryString("i").getOrElse("0").toInt
    val name = request.getQueryString("name").getOrElse("windows")
    val branch = BranchQuery.getAll
    Ok(views.html.window._nestedForm(Field(form, name+"["+i+"]", Seq(), None, Seq(), None), branch))
  }

  def showByBranch(id: Option[Long]) = {
    views.html.window._nestedShow(WindowQuery.byBranchId(id))
  }
}