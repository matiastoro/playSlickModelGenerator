package controllers

import models.{UserProfiles, Users, User}
import play.api.mvc.{Results, Request, Controller}
import play.api.cache.Cache
import play.api.Play.current
import play.api.data.Form
import models.itinerary.ItineraryUserAerodromes


case class ApplicationContext(user: Option[User], profiles: List[String], applicationName: String = ""){
  lazy val isAipPremium: Boolean = (List("aip_premium", "administrador", "aip_admin") intersect profiles).length>0
  def hasProfiles(userProfiles: List[String]): Boolean = {
    if(userProfiles.contains("itinerary_admin") && profiles.contains("administrador")) true
    (profiles intersect userProfiles).length>0
  }

  lazy val itineraryAerodrome: Option[models.Aerodrome] = user.map{u => ItineraryUserAerodromes.getAerodromeByUserId(u.id.getOrElse(0))}.getOrElse(None)

  def itineraryHasPermission(aerodrome: String): Boolean = {
    hasProfiles(List("administrador", "itinerary_admin")) || (hasProfiles(List("itinerary_airport")) && itineraryAerodrome.map{_.icaoCode}.getOrElse("") == aerodrome)
  }
}

trait ApplicationController extends Controller with Secured with Handlers with CookieLang {
  implicit def context[A](implicit request: Request[A]): ApplicationContext = {
    request.session.get("id") match {
      case Some(id) =>
        val user = Users.byId(id.toLong)
        val profiles = Cache.getOrElse[List[String]]("user."+id+".profile"){
                        UserProfiles.getProfileStringsByUserId(id.toLong)
                      }
        ApplicationContext(user, profiles, applicationName)
      case None => ApplicationContext(None, List[String](), applicationName)
    }
  }

  /* fillAndValidate does not validate global errors when used in certain ways. This is a fix for that*/
  def fillAndValidate[T](form: Form[T], obj: T) = {
    form.bind(form.fill(obj).data)
  }

  val applicationName = ""
}
