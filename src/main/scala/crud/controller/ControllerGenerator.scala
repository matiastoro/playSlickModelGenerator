package via56.slickGenerator.crud.controller

import scala.collection.immutable.ListMap
import via56.slickGenerator.{Table, CodeGenerator}

/**
 * Created by matias on 1/15/15.
 */
case class ControllerGenerator(table: Table) extends CodeGenerator{
  def generate: String = {
    val objectSignature = """object """+table.className+"""Controller extends ApplicationController {"""
    val l = List(imports, objectSignature, index(), show(), form())
    println(table.args)
    l.mkString("\n")
  }

  val imports =
    """package controllers

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
import play.api.i18n.Messages"""

  def index(): String = {
    """
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = """+table.queryName+""".paginate("""+table.queryName+""".allQuery,pageLength,page)
    Ok(views.html."""+table.viewsPackage+""".index(pagination.results, pagination.count, page, pageLength))
  }"""
  }

  def show(): String = {
    """
  def show(id: Long) = Action{ implicit request =>
    """+table.queryName+""".byId(id).map{ """+table.objName+""" =>
      Ok(views.html."""+table.viewsPackage+""".show("""+table.objName+"""))
    }.getOrElse(NotFound)
  }"""
  }

  def form(): String = {


  """
  val form = Form(
    mapping(
      "id" -> optional(longNumber),
      "cursoId" -> of[Long],
      "preguntaId" -> of[Long]
      )
      (PreguntaCurso.apply)(PreguntaCurso.unapply))
      /*((id, cursoId, preguntaId) => {
        PreguntaCurso(id, cursoId, preguntaId)
      })((obj: PreguntaCurso) => {
        Some(obj.id, obj.cursoId, obj.preguntaId)
      }))*/
  """
  }




}
