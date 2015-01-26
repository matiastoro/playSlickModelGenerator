package controllers
//import play.api._
import play.api.mvc._
import play.api.data._

import play.api.data.Forms._
import models._
//import models._
import views._

import play.api.mvc.BodyParsers.parse
import play.api.cache.Cache





trait Secured extends Handlers{

  /**
   * Retrieve the connected user username.
   */
  private def username(request: RequestHeader) = request.session.get(Security.username)

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.UserController.login).withSession((request.session - "access_uri") + ("access_uri" -> request.uri))

  /**
   * Action for authenticated users.
   */
  def withAuth[A](p: BodyParser[A])(f: => String => Request[A] => Result) = Security.Authenticated(username, onUnauthorized) { user =>
    Action(p) { implicit request => f(user)(request)}
  }
  
  def withAuthId[A](p: BodyParser[A])(f: => String => Long => Request[A] => Result) = withAuth(p) { user => request =>
    request.session.get("id") match {
      case Some(id) => f(user)(id.toLong)(request)
      case None => handleForbidden(request)
    }
  }
  
  def withPAuthId[A](p: BodyParser[A])(profiles: List[String])(f: => String => Long => List[String] => Request[A] => Result) = withAuth(p) { user => request =>
    request.session.get("id") match {
      case Some(id) => {
        
        val userProfiles = UserProfiles.getProfileStringsByUserId(id.toLong)
        if(UserProfiles.hasProfile(userProfiles, profiles))
        	f(user)(id.toLong)(userProfiles)(request)
        else handleForbidden(request)
      }
      case None => handleForbidden(request)
    }
  }
  
  
  def withAuth(f: => String => Request[AnyContent] => Result) = Security.Authenticated(username, onUnauthorized) { user =>
    Action(implicit request => f(user)(request))
  }
  
  
  def withAuthId(f: => String => Long => Request[AnyContent] => Result) = withAuth { user => request =>
    request.session.get("id") match {
      case Some(id) => f(user)(id.toLong)(request)
      case None => handleForbidden(request)
    }
  }
  
  def withPAuthId(profiles: List[String])(f: => String => Long => List[String] => Request[AnyContent] => Result) = withAuth { user => request =>
    request.session.get("id") match {
      case Some(id) => {
        val userProfiles = UserProfiles.getProfileStringsByUserId(id.toLong)
        if(UserProfiles.hasProfile(userProfiles, profiles))
        	f(user)(id.toLong)(userProfiles)(request)
        else handleForbidden(request)
      }
      case None => handleForbidden(request)
    }
  }

}



