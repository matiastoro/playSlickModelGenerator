package via56.slickGenerator.crud.controller

import scala.collection.immutable.ListMap
import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass
import via56.slickGenerator.Table

case class ControllerGenerator(table: Table) extends CodeGenerator{
  def generate: String = {
    val objectSignature = """object """+table.className+"""Controller extends ApplicationController {"""
    val l = List(imports, objectSignature, index(), show(), form(), create, save, edit, update)
    println(table.columns)
    l.mkString("\n")+"\n}"
  }

  val imports =
    """package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Akka
import actors._

import play.api.Play.current
import models._
import models.extensions._


import org.joda.time.{DateTimeZone, DateTime}
import play.api.i18n.Messages"""

  def index(): String = {

    """
/*"""+routes+"""*/
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

  def form(): String= {
    """  val form = """+table.className+"""Form.form"""

  }
  val fks = table.foreignColumns.map{fk =>
    "    val "+fk.table+" = "+fk.className+"Query.getAll"
  }.mkString("\n")
  val params = table.foreignColumns.map{fk => ", "+fk.table}.mkString("")

  def edit(): String = {

    """
  def edit(id: Long) = Action{ implicit request =>
    """+table.queryName+""".byId(id).map{ """+table.objName+ """ =>
"""+fks+"""
      Ok(views.html."""+table.viewsPackage+""".edit(form.fill("""+table.objName+""")"""+params+""", """+table.objName+"""))
    }.getOrElse(NotFound)
  }"""
  }

  def create(): String = {

    """
  def create() = Action{ implicit request =>
"""+fks+"""
    Ok(views.html."""+table.viewsPackage+""".create(form"""+params+"""))

  }"""
  }

  def update = {
    """
  def update(id: Long) = Action{ implicit request =>
    """+table.queryName+""".byId(id).map{ """+table.objName+""" =>
      form.bindFromRequest.fold(
        formWithErrors => {
          """+fks+"""
          BadRequest(views.html."""+table.viewsPackage+""".edit(formWithErrors"""+params+""", """+table.objName+"""))
        }, obj => {
          """+table.queryName+""".update(obj.copy(id = """+table.objName+""".id)).map{ id =>
            Redirect(routes."""+table.className+"""Controller.show("""+table.objName+""".id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }"""
  }
  def save = { """
  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        """+fks+ """
        BadRequest(views.html."""+table.viewsPackage+""".create(formWithErrors"""+params+"""))
      }, obj => {
        val id = """+table.queryName+""".insert(obj)
        Redirect(routes."""+table.className+"""Controller.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  """
  }
  
  def routes = {
    """
GET         /"""+table.objName+"""/                  controllers."""+table.className+"""Controller.index(page: Int = 1, pageLength: Int = 20)
GET         /"""+table.objName+"""/show/:id          controllers."""+table.className+"""Controller.show(id: Long)
GET         /"""+table.objName+"""/edit/:id          controllers."""+table.className+"""Controller.edit(id: Long)
GET         /"""+table.objName+"""/create            controllers."""+table.className+"""Controller.create()
POST        /"""+table.objName+"""/save              controllers."""+table.className+"""Controller.save()
POST        /"""+table.objName+"""/update/:id        controllers."""+table.className+"""Controller.update(id: Long)
GET         /"""+table.objName+"""/:page/:pageLength controllers."""+table.className+"""Controller.index(page: Int, pageLength: Int)
GET         /"""+table.objName+"""/:page             controllers."""+table.className+"""Controller.index(page: Int, pageLength: Int = 20)
"""
  }
}
