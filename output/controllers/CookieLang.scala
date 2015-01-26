package controllers

import play.api.mvc.{Action, Controller}
import play.api.i18n.Lang
import play.api.Play.current


trait CookieLang extends Controller {


  def changeLocale(lang: String) = Action {
    implicit request =>
      val referrer = request.headers.get(REFERER).getOrElse(HOME_URL)
      val DEFAULT_LANG = "es"

      Lang.availables.find(_.language == lang) match {
        case Some(_) => Redirect(referrer).withSession(request.session + ("lang" -> lang))
        case None => Redirect(referrer).withSession(request.session + ("lang" -> DEFAULT_LANG))
      }
  }



  protected val HOME_URL = "/"
}