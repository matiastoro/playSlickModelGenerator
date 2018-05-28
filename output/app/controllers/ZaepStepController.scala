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
import play.api.data.Field
object ZaepStepController extends ApplicationController {

/*
GET         /zaepStep/                  controllers.ifiscore.ZaepStepController.index(page: Int = 1, pageLength: Int = 20)
GET         /zaepStep/show/:id          controllers.ifiscore.ZaepStepController.show(id: Long)
GET         /zaepStep/edit/:id          controllers.ifiscore.ZaepStepController.edit(id: Long)
GET         /zaepStep/delete/:id          controllers.ifiscore.ZaepStepController.delete(id: Long)
GET         /zaepStep/create            controllers.ifiscore.ZaepStepController.create()
GET         /zaepStep/nested            controllers.ifiscore.ZaepStepController.createNested()
POST        /zaepStep/save              controllers.ifiscore.ZaepStepController.save()
POST        /zaepStep/update/:id        controllers.ifiscore.ZaepStepController.update(id: Long)
GET         /zaepStep/:page/:pageLength controllers.ifiscore.ZaepStepController.index(page: Int, pageLength: Int)
GET         /zaepStep/:page             controllers.ifiscore.ZaepStepController.index(page: Int, pageLength: Int = 20)
*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = ZaepStepQuery.paginate(ZaepStepQuery.allQuery,pageLength,page)
    Ok(views.html.ifiscore.zaepStep.index(pagination.results, pagination.count, page, pageLength))
  }

  def show(id: Long) = Action{ implicit request =>
    ZaepStepQuery.byId(id).map{ zaepStep =>
      Ok(views.html.ifiscore.zaepStep.show(zaepStep))
    }.getOrElse(NotFound)
  }
  val form = ZaepStepForm.form

  def create() = Action{ implicit request =>
    val zaep = ZaepQuery.getAll
    Ok(views.html.ifiscore.zaepStep.create(form, zaep))

  }

  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        val zaep = ZaepQuery.getAll
        BadRequest(views.html.ifiscore.zaepStep.create(formWithErrors, zaep))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers.ifiscore.routes.ZaepStepController.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  

  def edit(id: Long) = Action{ implicit request =>
    ZaepStepQuery.byId(id).map{ zaepStep =>
      val zaep = ZaepQuery.getAll
      Ok(views.html.ifiscore.zaepStep.edit(form.fill(ZaepStepFormData(zaepStep)), zaep, zaepStep))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action{ implicit request =>
    ZaepStepQuery.byId(id).map{ zaepStep =>
      ZaepStepQuery.delete(zaepStep)
      Redirect(controllers.ifiscore.routes.ZaepStepController.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action{ implicit request =>
    ZaepStepQuery.byId(id).map{ zaepStep =>
      form.bindFromRequest.fold(
        formWithErrors => {
         val zaep = ZaepQuery.getAll
          BadRequest(views.html.ifiscore.zaepStep.edit(formWithErrors, zaep, zaepStep))
        }, formData => {
          formData.update(formData.obj.copy(id = zaepStep.id)).map{ id =>
            Redirect(controllers.ifiscore.routes.ZaepStepController.show(zaepStep.id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }
  def nestedForm(form: Field)(implicit request: Request[AnyContent]) = {
    val zaep = ZaepQuery.getAll
    views.html.ifiscore.zaepStep._nestedForm(form, zaep)
  }

  def createNested() = Action{ implicit request =>
    val i = request.getQueryString("i").getOrElse("0").toInt
    val name = request.getQueryString("name").getOrElse("zaepSteps")
    val zaep = ZaepQuery.getAll
    Ok(views.html.ifiscore.zaepStep._nestedForm(Field(form, name+"["+i+"]", Seq(), None, Seq(), None), zaep))
  }

  def showByZaep(id: Option[Long]) = {
    views.html.ifiscore.zaepStep._nestedShow(ZaepStepQuery.byZaepId(id))
  }
}