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
object BranchController extends ApplicationController {

/*
GET         /branch/                  controllers.BranchController.index(page: Int = 1, pageLength: Int = 20)
GET         /branch/show/:id          controllers.BranchController.show(id: Long)
GET         /branch/edit/:id          controllers.BranchController.edit(id: Long)
GET         /branch/delete/:id          controllers.BranchController.delete(id: Long)
GET         /branch/create            controllers.BranchController.create()
GET         /branch/nested            controllers.BranchController.createNested()
POST        /branch/save              controllers.BranchController.save()
POST        /branch/update/:id        controllers.BranchController.update(id: Long)
GET         /branch/:page/:pageLength controllers.BranchController.index(page: Int, pageLength: Int)
GET         /branch/:page             controllers.BranchController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = BranchQuery.paginate(BranchQuery.allQuery,pageLength,page)
    Ok(views.html.branch.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    BranchQuery.byId(id).map{ branch =>
      Ok(views.html.branch.show(branch))
    }.getOrElse(NotFound)
  }
  val form = BranchForm.form

  def create() = Action{ implicit request =>
    val company = CompanyQuery.getAll
    Ok(views.html.branch.create(form, company))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val company = CompanyQuery.getAll
        BadRequest(views.html.branch.create(formWithErrors, company))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(routes.BranchController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    BranchQuery.byId(id).map{ branch =>
      val company = CompanyQuery.getAll
      Ok(views.html.branch.edit(form.fill(BranchFormData(branch)), company, branch))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    BranchQuery.byId(id).map{ branch =>
      BranchQuery.delete(branch)
      Redirect(routes.BranchController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    BranchQuery.byId(id).map{ branch =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val company = CompanyQuery.getAll
          BadRequest(views.html.branch.edit(formWithErrors, company, branch))
        }, formData => {
          formData.update(formData.obj.copy(id = branch.id, createdAt = branch.createdAt)).map{ id =>
            Redirect(routes.BranchController.show(branch.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def nestedForm(form: Field)(implicit request: Request[AnyContent]) = {
    val company = CompanyQuery.getAll
    views.html.branch._nestedForm(form, company)
  }

  def createNested() = Action{ implicit request =>
    val i = request.getQueryString("i").getOrElse("0").toInt
    val name = request.getQueryString("name").getOrElse("branchs")
    val company = CompanyQuery.getAll
    Ok(views.html.branch._nestedForm(Field(form, name+"["+i+"]", Seq(), None, Seq(), None), company))
  }

  def showByCompany(id: Option[Long]) = {
    views.html.branch._nestedShow(BranchQuery.byCompanyId(id))
  }
}