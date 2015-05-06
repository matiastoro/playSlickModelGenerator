package via56.slickGenerator.crud.controller

import scala.collection.immutable.ListMap
import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass
import via56.slickGenerator.Table

case class ControllerGenerator(table: Table, tablesOneToMany: List[Table] = List(), submodulePackageString: String) extends CodeGenerator{
  val isMany = tablesOneToMany.size>0
  def generate: String = {
    val objectSignature = """object """+table.className+"""Controller extends ApplicationController {"""
    val l = List(imports, objectSignature, index(), show(), form(), create, save, edit, delete, update)

    val lMany = if(isMany) l ++ List(nestedForm,createNested, showByManies) else l
    println(table.columns)
    lMany.mkString("\n")+"\n}"
  }

  val imports =
    """package controllers"""+submodulePackageString+"""

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Akka

import play.api.Play.current
import models._
import models.extensions._
import controllers.ApplicationController
import scala.slick.driver.H2Driver.simple._
import org.joda.time.{DateTimeZone, DateTime}
import play.api.i18n.Messages"""+(if(isMany) "\nimport play.api.data.Field" else "")

  def index(): String = {

    """
/*"""+routes+"""*/
  def index(page: Int = 1, pageLength: Int = 20) = Action { implicit request =>
    val pagination = """+table.queryName+""".paginate("""+table.queryName+""".allQuery,pageLength,page)
    Ok(views.html"""+submodulePackageString+"""."""+table.viewsPackage+""".index(pagination.results, pagination.count, page, pageLength))
  }"""
  }

  def show(): String = {
    """
  def show(id: Long) = Action{ implicit request =>
    """+table.queryName+""".byId(id).map{ """+table.objName+""" =>
      Ok(views.html"""+submodulePackageString+"."+table.viewsPackage+""".show("""+table.objName+"""))
    }.getOrElse(NotFound)
  }"""
  }

  def form(): String= {
    """  val form = """+table.className+"""Form.form"""

  }
  val fks = table.foreignKeys.map{fk =>
    "    val "+fk.table+" = "+fk.className+"Query.getAll"
  }.mkString("\n")
  val params = table.foreignKeys.map{fk => ", "+fk.table}.mkString("")

  def edit(): String = {
    val fillObj = //if(table.hasOneToMany){
      table.className+"FormData("+table.objName+")"//+(if(table.hasOneToMany) ", " else "")+table.oneToManies.map{otm => otm.queryName+".by"+table.className+"Id("+table.objName+".id)" }.mkString(", ")+")"
    //}  else table.objName
    """
  def edit(id: Long) = Action{ implicit request =>
    """+table.queryName+""".byId(id).map{ """+table.objName+ """ =>
  """+fks+"""
      Ok(views.html"""+submodulePackageString+"."+table.viewsPackage+""".edit(form.fill("""+fillObj+""")"""+params+""", """+table.objName+"""))
    }.getOrElse(NotFound)
  }"""
  }
  def delete(): String = {
    val fillObj =
      table.className+"FormData("+table.objName+")"
    //}  else table.objName
    """
  def delete(id: Long) = Action{ implicit request =>
    """+table.queryName+""".byId(id).map{ """+table.objName+ """ =>
      """+table.queryName+""".delete("""+table.objName+ """)
      Redirect(controllers"""+submodulePackageString+""".routes.""" + table.className + """Controller.index(1,20)).flashing("success" -> Messages("delete.success"))
    }.getOrElse(NotFound)
  }"""
  }

  def create(): String = {
    """
  def create() = Action{ implicit request =>
"""+fks+"""
    Ok(views.html"""+submodulePackageString+"."+table.viewsPackage+""".create(form"""+params+"""))

  }"""
  }

  def update = {
    val updateObj = if(table.hasOneToMany) "obj."+table.objName else "obj"
    val createdAt = if(table.createdAt) ", createdAt = "+table.objName+".createdAt" else ""
    /*val maniesObj = table.oneToManies.map{ otm =>
"""          formData."""+otm.name+"""s.map{ fData =>
            fData.update(fData.obj.copy("""+table.tableName+"""Id = id))
          }"""
    }.mkString("\n")*/
    """
  def update(id: Long) = Action{ implicit request =>
    """+table.queryName+""".byId(id).map{ """+table.objName+""" =>
      form.bindFromRequest.fold(
        formWithErrors => {
     """+fks+"""
          BadRequest(views.html"""+submodulePackageString+"."+table.viewsPackage+""".edit(formWithErrors"""+params+""", """+table.objName+"""))
        }, formData => {
          formData.update(formData.obj.copy(id = """+table.objName+""".id"""+createdAt+""")).map{ id =>
            Redirect(controllers"""+submodulePackageString+""".routes."""+table.className+"""Controller.show("""+table.objName+""".id.get)).flashing("success" -> Messages("save.success"))
          }.getOrElse(NotFound)
        }
      )
    }.getOrElse(NotFound)
  }"""
  }
  def save = {
    val maniesObj = table.oneToManies.map{ otm =>
"""        formData."""+otm.name+"""s.map{ fData =>
             fData.insert(fData.obj.copy("""+table.tableName+"""Id = id))
        }"""
    }.mkString("\n")
    """
  def save() = Action{ implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    """+fks+ """
        BadRequest(views.html"""+submodulePackageString+"."+table.viewsPackage+""".create(formWithErrors"""+params+"""))
      }, formData => {
        val id = formData.insert(formData.obj)
        Redirect(controllers"""+submodulePackageString+""".routes."""+table.className+"""Controller.show(id)).flashing("success" -> Messages("save.success"))
      }
    )
  }
  """
  }

  val nestedRoute = if(isMany) "\n"+"""GET         /"""+table.objName+"""/nested            controllers"""+submodulePackageString+"""."""+table.className+"""Controller.createNested()""" else ""
  def routes = {
    """
GET         /"""+table.objName+"""/                  controllers"""+submodulePackageString+"""."""+table.className+"""Controller.index(page: Int = 1, pageLength: Int = 20)
GET         /"""+table.objName+"""/show/:id          controllers"""+submodulePackageString+"""."""+table.className+"""Controller.show(id: Long)
GET         /"""+table.objName+"""/edit/:id          controllers"""+submodulePackageString+"""."""+table.className+"""Controller.edit(id: Long)
GET         /"""+table.objName+"""/delete/:id          controllers"""+submodulePackageString+"""."""+table.className+"""Controller.delete(id: Long)
GET         /"""+table.objName+"""/create            controllers"""+submodulePackageString+"""."""+table.className+"""Controller.create()"""+nestedRoute+"""
POST        /"""+table.objName+"""/save              controllers"""+submodulePackageString+"""."""+table.className+"""Controller.save()
POST        /"""+table.objName+"""/update/:id        controllers"""+submodulePackageString+"""."""+table.className+"""Controller.update(id: Long)
GET         /"""+table.objName+"""/:page/:pageLength controllers"""+submodulePackageString+"""."""+table.className+"""Controller.index(page: Int, pageLength: Int)
GET         /"""+table.objName+"""/:page             controllers"""+submodulePackageString+"""."""+table.className+"""Controller.index(page: Int, pageLength: Int = 20)
"""
  }

  def nestedForm() = { """  def nestedForm(form: Field)(implicit request: Request[AnyContent]) = {
"""+fks+ """
    views.html"""+submodulePackageString+"."+table.viewsPackage+"""._nestedForm(form"""+params+""")
  }"""
  }


  def createNested() = """
  def createNested() = Action{ implicit request =>
    val i = request.getQueryString("i").getOrElse("0").toInt
    val name = request.getQueryString("name").getOrElse(""""+table.objName+"""s")
"""+fks+ """
    Ok(views.html"""+submodulePackageString+"."+table.viewsPackage+"""._nestedForm(Field(form, name+"["+i+"]", Seq(), None, Seq(), None)"""+params+"""))
  }"""


  def showByManies() = {
    tablesOneToMany.map{ t =>
      """
  def showBy"""+t.className+"""(id: Option[Long]) = {
    views.html"""+submodulePackageString+"."+table.viewsPackage+"""._nestedShow("""+table.queryName+""".by"""+t.className+"""Id(id))
  }"""

    }.mkString("\n")
  }
}
