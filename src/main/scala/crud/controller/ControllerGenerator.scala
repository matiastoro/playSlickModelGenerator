package via56.slickGenerator.crud.controller

import scala.collection.immutable.ListMap
import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass
import via56.slickGenerator.Table

case class ControllerGenerator(table: Table, tablesOneToMany: List[Table] = List(), submodulePackageString: String)(implicit langHash: Map[String, String]) extends CodeGenerator{
  val isMany = tablesOneToMany.size>0
  def generate: String = {
    val objectSignature = """object """+table.className+"""Controller extends Controller with Autorizacion {"""

    val l = List(imports, objectSignature, jsonFormats(), index(), show(), form(), objectResponse, save, delete, update)


    val lMany = if(table.foreignKeys.length>0) l ++ List(options) else l
    println(table.columns)
    println("oneToMany", tablesOneToMany)
    lMany.mkString("\n")+"\n}"
  }

  val imports =
    """package controllers"""+submodulePackageString+"""
import models._
import models.extensions._
import play.api._
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.Controller
import play.api.Play.current
import play.api.mvc.BodyParsers._
import play.api.libs.json.Json
import play.api.libs.json.Json._
import com.roundeights.hasher.Implicits._
import com.github.nscala_time.time.Imports._
import play.api.libs.concurrent.Akka
import models._
import models.extensions._
import org.joda.time.{DateTimeZone, DateTime}
import play.api.i18n.Messages"""+(if(isMany) "\nimport play.api.data.Field" else "")

  def jsonFormats(): String = {
    s"implicit val jsonFormat = Json.format[${table.className}]"
  }
  def index(): String = {

    """
/*"""+routes+"""*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = """+table.queryName+""".paginate("""+table.queryName+s""".${langHash("allQuery")},pageLength,page)
    Ok(Json.toJson(Json.obj("results" -> pagination.results, "count" -> pagination.count, "page" -> pagination.page, "pageLength" -> pagination.pageLength)))
  }"""
  }
  def indexView(): String = {

    """
/*"""+routes+"""*/
  def index(page: Int = 1, pageLength: Int = 20) = conUsuarioDB{ user =>  implicit request =>
    val pagination = """+table.queryName+""".paginate("""+table.queryName+s""".${langHash("allQuery")},pageLength,page)
    Ok(views.html"""+submodulePackageString+"""."""+table.viewsPackage+""".index(pagination.results, pagination.count, page, pageLength))
  }"""
  }

  def show(): String = {
    """
  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    objectResponse(id)
  }"""
  }
  def showView(): String = {
    """
  def show(id: Long) = conUsuarioDB{ user =>  implicit request =>
    """+table.queryName+s""".${langHash("byId")}(id).map{ """+table.objName+s""" =>
      Ok(views.html"""+submodulePackageString+"."+table.viewsPackage+""".show("""+table.objName+"""))
    }.getOrElse(NotFound)
  }"""
  }

  def form(): String= {
    """  val form = """+table.className+"""Form.form"""

  }
  val fks = table.foreignKeys.map{fk =>
    "    val "+fk.table+" = "+fk.className+s"${langHash("Query")}.${langHash("getAll")}"
  }.mkString("\n")
  val fksJson = table.foreignKeys.map{fk =>
    "    val "+fk.table+"s = "+fk.className+s"${langHash("Query")}.${langHash("getAll")}.map(_.toJson)"
  }.mkString("\n")
  val params = table.foreignKeys.map{fk => ", "+fk.table}.mkString("")

  def edit(): String = {
    val fillObj = //if(table.hasOneToMany){
      table.className+"FormData("+table.objName+")"//+(if(table.hasOneToMany) ", " else "")+table.oneToManies.map{otm => otm.queryName+".by"+table.className+"Id("+table.objName+".id)" }.mkString(", ")+")"
    //}  else table.objName
    """
  def edit(id: Long) = conUsuarioDB{ user =>  implicit request =>
    """+table.queryName+s""".${langHash("byId")}(id).map{ """+table.objName+ """ =>
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
  def delete(id: Long) = conUsuarioDB{ user =>  implicit request =>
    """+table.queryName+s""".${langHash("byId")}(id).map{ """+table.objName+ """ =>
      """+table.queryName+s""".${langHash("delete")}("""+table.objName+ """)
      Ok("ok")
    }.getOrElse(NotFound)
  }"""
  }

  def objectResponse = {
    s"""
  def objectResponse(id: Long)(implicit session: Session) = {
    ${table.queryName}.${langHash("byId")}(id).map{ ${table.objName} =>
      Ok(Json.obj("obj" -> ${table.objName}.toJson))
    }.getOrElse(NotFound)
  }
"""
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
  def update(id: Long) = conUsuarioDB{ user =>  implicit request =>
    """+table.queryName+s""".${langHash("byId")}(id).map{ """+table.objName+""" =>
      form.bindFromRequest.fold(
        formWithErrors => {
     """+fks+"""
          Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
        }, formData => {
          formData.update(formData.obj.copy(id = """+table.objName+""".id"""+createdAt+s""")).map{ id =>
            objectResponse(id)
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
  def save() = conUsuarioDB{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
    """+fks+ """
        Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))
      }, formData => {
        val id = formData.insert(formData.obj)
        objectResponse(id)
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
GET         /"""+table.objName+"""/delete/:id          controllers"""+submodulePackageString+"""."""+table.className+"""Controller.delete(id: Long)
POST        /"""+table.objName+"""/save              controllers"""+submodulePackageString+"""."""+table.className+"""Controller.save()
POST        /"""+table.objName+"""/update/:id        controllers"""+submodulePackageString+"""."""+table.className+"""Controller.update(id: Long)
GET         /"""+table.objName+"""/options             controllers"""+submodulePackageString+"""."""+table.className+"""Controller.options()
GET         /"""+table.objName+"""/:page/:pageLength controllers"""+submodulePackageString+"""."""+table.className+"""Controller.index(page: Int, pageLength: Int)
GET         /"""+table.objName+"""/:page             controllers"""+submodulePackageString+"""."""+table.className+"""Controller.index(page: Int, pageLength: Int = 20)
"""
  }

  def nestedForm() = { """  def nestedForm(form: Field)(implicit request: Request[AnyContent]) = {
"""+fks+ """
    views.html"""+submodulePackageString+"."+table.viewsPackage+"""._nestedForm(form"""+params+""")
  }"""
  }

  def options() = {
    val result = table.foreignKeys.map{fk => s""""${fk.table}s" -> ${fk.table}s"""}.mkString(", ")
    s"""  def options() = conUsuarioDB{ user =>  implicit request =>
${fksJson}
    Ok(Json.obj(${result}))
  }"""
  }


  def createNested() = """
  def createNested() = conUsuarioDB{ user =>  implicit request =>
    val i = request.getConsultaString("i").getOrElse("0").toInt
    val name = request.getConsultaString("name").getOrElse(""""+table.objName+"""s")
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
