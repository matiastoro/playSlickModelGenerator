package controllers


import play.api.mvc.{Results, Request, Controller}
import play.api.cache.Cache
import play.api.Play.current
import play.api.data.Form



case class ApplicationContext(id: Option[Long], applicationName: String = ""){

}

trait ApplicationController extends Controller with Handlers with CookieLang {
  implicit def context[A](implicit request: Request[A]): ApplicationContext = {
    request.session.get("id") match {
      case Some(id) =>
        ApplicationContext(Some(id.toLong), applicationName)
      case None => ApplicationContext(None, applicationName)
    }
  }

  /* fillAndValidate does not validate global errors when used in certain ways. This is a fix for that*/
  def fillAndValidate[T](form: Form[T], obj: T) = {
    form.bind(form.fill(obj).data)
  }

  val applicationName = ""
}
