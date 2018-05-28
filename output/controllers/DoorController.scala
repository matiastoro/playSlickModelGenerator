package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Akka
import actors._

import play.api.Play.current
import models._
import models.extensions._


import org.joda.time.{DateTimeZone, DateTime}
import play.api.i18n.Messages
import play.api.data.Field
object DoorController extends ApplicationController {

/*
GET         /door/                  controllers.DoorController.index(page: Int = 1, pageLength: Int = 20)
GET         /door/show/:id          controllers.DoorController.show(id: Long)
GET         /door/edit/:id          controllers.DoorController.edit(id: Long)
GET         /door/delete/:id          controllers.DoorController.delete(id: Long)
GET         /door/create            controllers.DoorController.create()
GET         /door/nested            controllers.DoorController.createNested()
POST        /door/save              controllers.DoorController.save()
POST        /door/update/:id        controllers.DoorController.update(id: Long)
GET         /door/:page/:pageLength controllers.DoorController.index(page: Int, pageLength: Int)
GET         /door/:page             controllers.DoorController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = DoorQuery.paginate(DoorQuery.allQuery,pageLength,page)
    Ok(views.html.door.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    DoorQuery.byId(id).map{ door =>
      Ok(views.html.door.show(door))
    }.getOrElse(NotFound)
  }
  val form = DoorForm.form

  def create() = Action{ implicit request =>
    val branch = BranchQuery.getAll
    Ok(views.html.door.create(form, branch))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val branch = BranchQuery.getAll
        BadRequest(views.html.door.create(formWithErrors, branch))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(routes.DoorController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    DoorQuery.byId(id).map{ door =>
      val branch = BranchQuery.getAll
      Ok(views.html.door.edit(form.fill(DoorFormData(door)), branch, door))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    DoorQuery.byId(id).map{ door =>
      DoorQuery.delete(door)
      Redirect(routes.DoorController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    DoorQuery.byId(id).map{ door =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val branch = BranchQuery.getAll
          BadRequest(views.html.door.edit(formWithErrors, branch, door))
        }, formData => {
          formData.update(formData.obj.copy(id = door.id)).map{ id =>
            Redirect(routes.DoorController.show(door.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def nestedForm(form: Field)(implicit request: Request[AnyContent]) = {
    val branch = BranchQuery.getAll
    views.html.door._nestedForm(form, branch)
  }

  def createNested() = Action{ implicit request =>
    val i = request.getQueryString("i").getOrElse("0").toInt
    val name = request.getQueryString("name").getOrElse("doors")
    val branch = BranchQuery.getAll
    Ok(views.html.door._nestedForm(Field(form, name+"["+i+"]", Seq(), None, Seq(), None), branch))
  }

  def showByBranch(id: Option[Long]) = {
    views.html.door._nestedShow(DoorQuery.byBranchId(id))
  }
}