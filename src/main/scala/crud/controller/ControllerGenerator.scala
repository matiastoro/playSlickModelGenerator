package via56.slickGenerator.crud.controller

import scala.collection.immutable.ListMap
import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass
import via56.slickGenerator.Table

/**
 * Created by matias on 1/15/15.
 */
case class ControllerGenerator(table: Table) extends CodeGenerator{
  def generate: String = {
    val objectSignature = """object """+table.className+"""Controller extends ApplicationController {"""
    val l = List(imports, objectSignature, index(), show(), form())
    println(table.columns)
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

  def getFields(columns: List[AbstractColumn], className: String, lvl: Int = 1): String = {
    val margin = (" "*(4+lvl*2))
    val margin_1 = (" "*(4+(lvl-1)*2))
    val list = columns.map{
      case c @ Column(name, rawName, tpe, optional) => margin+"\""+name+"\" -> "+c.formMapping
      case s @ SubClass(name, cols) => margin+"\""+name+"\" -> "+getFields(cols, s.className, lvl+1)
    }

    val optionalList = columns.map{ col => col.name}.mkString(", ")
    val optionalListObj = columns.map{ col => "obj."+col.name}.mkString(", ")

    val optionalMapping = List(margin_1+"""/*(("""+optionalList+""") => {""",
      margin_1+"  "+className+"""("""+optionalList+""")""",
      margin_1+"""})((obj: """+className+ """) => {""",
      margin_1+"""  Some("""+optionalListObj+""")""",
      margin_1+"""}))*/""")

    "mapping(\n"+list.mkString("\n")+"\n"+margin_1+"("+className+".apply)("+className+".unapply)"+"\n"+optionalMapping.mkString("\n")



  }
  def form(): String = {


    """
  val form = Form(
    """+getFields(table.columns, table.className)+"""
  )"""
  }




}
