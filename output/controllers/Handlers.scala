package controllers

import play.api.mvc._
import play.api.mvc.Results._



trait Handlers extends CookieLang {
  def handleNotFound(implicit request: RequestHeader) = {
    NotFound(views.html.errors.pageNotFound("Not Found: " + request.path))
    
  }

  def handleForbidden(implicit request: RequestHeader) = {
    Forbidden(views.html.errors.pageForbidden())
  }
}