package via56.slickGenerator.crud.controller

import scala.collection.immutable.ListMap
import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass
import via56.slickGenerator.Table

case class ControllerGenerator(table: Table, tablesOneToMany: List[Table] = List(), submodulePackageString: String, tables: List[Table])(implicit langHash: Map[String, String]) extends CodeGenerator{
  val isMany = tablesOneToMany.size>0

  def attachments = table.columns.filter{ case c: Column => c.tpe=="Attachment"; case _ => false}



  def getOneToManyRepos(otm: OneToMany): List[(String, String)] = {
    //search for table
    val otms = tables.find(t => t.yamlName == otm.rawForeignTable).map{ t =>
      val r = if(t.oneToManies.length>0){
        t.oneToManies.flatMap { otm =>
          getOneToManyRepos(otm)
        }
      } else List()

      r ++ t.inlines.flatMap(c => getInlineRepos(c))
    }.getOrElse(List())
    //println("para ", otm, "encontre, ",otms)
    (List((otm.foreignTable, otm.className)) ++ otms).toSet.toList
  }

  def getInlineRepos(c: Column): List[(String, String)] = {
    //search for table
    val otms = tables.find(t => t.yamlName == c.foreignKey.get.table).map{ t =>
      val r = if(t.inlines.length>0){
        t.inlines.flatMap { otm =>
          getInlineRepos(otm)
        }

      } else List()

      r ++ t.oneToManies.flatMap(c => getOneToManyRepos(c))
    }.getOrElse(List())
    //println("para ", otm, "encontre, ",otms)
    (List((c.foreignKey.get.tableName
      , c.foreignKey.get.className)) ++ otms).toSet.toList
  }


  def generate: String = {
    val rels = (table.foreignKeys.map{fk => (fk.tableName, fk.className)} ++
      table.inlines.flatMap(c => getInlineRepos(c)) ++
    table.oneToManies.flatMap{otm => getOneToManyRepos(otm)}).toSet
    val frels = rels.filter(r => r._2 != table.className)

    val otmsRepos = if(frels.size>0){
      ", "+frels.map { r =>
        r._1+"Repo: "+r._2+"Repository"
      }.mkString(", ")
    } else ""

    //println("controller, ", otmsRepos)

    val objectSignature = """class """+table.className+s"""Controller @Inject()(cc: MessagesControllerComponents)(implicit val repo: ${table.className}Repository${otmsRepos}, ec: ExecutionContext) extends MessagesAbstractController(cc) with Autorization {"""

    val l = List(imports, "\n", objectSignature, /*jsonFormats(),*/ index(), show(), form(), objectResponse, save, delete, update)


    val lMany = if(table.foreignKeys.length>0) l ++ List(options) else l

    val lFile = if(attachments.length>0) lMany ++ List(downloads) else lMany
    //println(table.columns)
    //println("oneToMany", tablesOneToMany)
    lFile.mkString("\n")+"\n}"
  }

  val imports =
    """package controllers"""+submodulePackageString+"""
import javax.inject._
import models._
import models.extensions._
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.Controller
import play.api.Play.current
import play.api.mvc._
import play.api.mvc.BodyParsers._
import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.concurrent.Akka
import models._
import models.extensions._
import org.joda.time.{DateTimeZone, DateTime}
import scala.concurrent.{ExecutionContext, Future}
import play.api.i18n.Messages"""+(if(isMany) "\nimport play.api.data.Field" else "")

  def jsonFormats(): String = {
    s"implicit val jsonFormat = Json.format[${table.className}]"
  }
  def index(): String = {

    """
/*"""+routes+"""*/

  def getQuery[A](implicit request: MessagesRequest[A]) = {
    if(!request.queryString.isEmpty){
      filterForm.bindFromRequest.fold(
        formWithErrors => {
          repo.all
        }, formData => {
          repo.filter(formData)
        })
    } else{
      repo.all
    }
  }

  def index(page: Int = 1, pageLength: Int = 20, sortColumn: String = "", sortOrder: String = "") = withUserAsync{ user =>  implicit request =>
    val q = getQuery
    repo.paginate(q,pageLength,page, sortColumn, sortOrder).flatMap{ pagination =>
      Future.sequence{
        pagination.results.map{ obj =>
          getJson(obj)
        }
      }.map{ r =>
        Ok(Json.toJson(Json.obj("results" -> r, "count" -> pagination.count, "page" -> page, "pageLength" -> pageLength)))
      }
    }
  }"""
  }
  def indexView(): String = {

    """
/*"""+routes+"""*/
  def index(page: Int = 1, pageLength: Int = 20) = withUserAsync{ user =>  implicit request =>
    val pagination = """+table.queryName+""".paginate("""+table.queryName+s""".${langHash("allQuery")},pageLength,page)
    Ok(views.html"""+submodulePackageString+"""."""+table.viewsPackage+""".index(pagination.results, pagination.count, page, pageLength))
  }"""
  }

  def show(): String = {
    """
  def show(id: Long) = withUserAsync{ user =>  implicit request =>
    objectResponse(id)
  }"""
  }
  def showView(): String = {
    """
  def show(id: Long) = withUserAsync{ user =>  implicit request =>
    """+table.queryName+s""".${langHash("byId")}(id).map{ """+table.objName+s""" =>
      Ok(views.html"""+submodulePackageString+"."+table.viewsPackage+""".show("""+table.objName+"""))
    }.getOrElse(NotFound)
  }"""
  }

  def form(): String= {
    s"""  val form = ${table.className}Form.form""" + "\n" +
    s"""  val filterForm = ${table.className}FilterForm.filterForm"""

  }
  val fks = table.foreignKeys.map{fk =>
    "    val "+fk.table+" = "+fk.className+s"${langHash("Query")}.${langHash("getAll")}"
  }.mkString("\n")

  def fksJson(body: String) = {
    val rows = table.foreignKeys.map{fk =>
      val fkTable = if(fk.table == table.yamlName) "repo" else (fk.tableName+"Repo")
    "      "+fk.tableName+"s <- "+fkTable+s".${langHash("getAll")}"
  }.mkString("\n")
    if(table.foreignKeys.length>0){
      s"""    for{
${rows}
      } yield {
        ${body}
      }
       """
    } else{
      body
    }
  }
  val params = table.foreignKeys.map{fk => ", "+fk.table}.mkString("")

  def edit(): String = {
    val fillObj = //if(table.hasOneToMany){
      table.className+"FormData("+table.objName+")"//+(if(table.hasOneToMany) ", " else "")+table.oneToManies.map{otm => otm.queryName+".by"+table.className+"Id("+table.objName+".id)" }.mkString(", ")+")"
    //}  else table.objName
    """
  def edit(id: Long) = withUserAsync{ user =>  implicit request =>
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
  def delete(id: Long) = withUserAsync{ user =>  implicit request =>
    repo.byId(id).map{ oobj =>
      oobj.map{ obj =>
        repo.delete(obj)
        Ok("ok")
      }.getOrElse(OrElse)
    }
  }"""
  }

  def objectResponse = {
    val others = if(table.oneToManies.length>0 || table.inlines.length>0){
      val otmsLists = table.oneToManies.map{otm =>
        s"""         ("${otm.lstName}" -> Json.toJson(${otm.lstName}))"""
      }

      val otmsListsFor = table.oneToManies.map{otm =>

        s"""          ${otm.lstName} <- ${otm.foreignTable}Repo.by${table.backRef(otm, tables).capitalize}(obj.id)"""
      }.mkString("\n")

      val inlinesLists = table.inlines.map{inline =>
        s"""         ("${inline.fkInlineName}" -> Json.toJson(${inline.fkInlineName}))"""
      }

      val inlinesListsFor = table.inlines.map{inline =>
        s"""          ${inline.fkInlineName} <- ${inline.foreignKey.get.tableName}Repo.byId(obj.${inline.name})"""
      }.mkString("\n")



      val forStmt = (b: String) => s"""for{
${inlinesListsFor}
${otmsListsFor}
       } yield {
          ${b}
       }
       """

      (forStmt, s"(Json.toJson(obj).as[JsObject] + \n ${(inlinesLists ++ otmsLists).mkString("+\n")})")
    } else
      ((b: String) => s"Future{Json.toJson(obj).as[JsObject]}","")


    s"""
  def getJson(obj: ${table.className}): Future[JsObject] = {
    ${others._1(others._2)}
  }
  def objectResponse(id: Long)(implicit session: Session) = {
    repo.byId(id).flatMap{ oobj =>
      oobj.map{obj =>
       getJson(obj).map{ x =>  Ok(Json.obj("obj" -> x)) }
      }.getOrElse(Future{OrElse})
    }
  }
"""
    /*${table.queryName}.${langHash("byId")}(id).map{ ${table.objName} =>
      Ok(Json.obj("obj" -> ${table.objName}.toJson))
    }.getOrElse(NotFound)*/
  }

  def update = {
    val updateObj = if(table.hasOneToMany) "obj."+table.objName else "obj"
    val createdAt = if(table.createdAt) ", createdAt = obj.createdAt" else ""
    val inlines = if(table.inlines.length>0) ", "+(table.inlines.map{x => s"""${x.name} = obj.${x.name}"""}.mkString(", ")) else ""
    /*val maniesObj = table.oneToManies.map{ otm =>
"""          formData."""+otm.name+"""s.map{ fData =>
            fData.update(fData.obj.copy("""+table.tableName+"""Id = id))
          }"""
    }.mkString("\n")*/
    s"""
  def update(id: Long) = withUserAsync{ user =>  implicit request =>
    repo.byId(id).flatMap { oobj =>
      oobj.map { obj =>
        form.bindFromRequest.fold(
          formWithErrors => {
            Future{Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))}
          }, formData => {
            formData.update(formData.obj.copy(id = obj.id${createdAt}${inlines})).flatMap { _ =>
              objectResponse(id)
            }
          }
        )
      }.getOrElse(Future{OrElse})
    }
  }"""
  }
  /*"""+table.queryName+s""".${langHash("byId")}(id).map{ """+table.objName+""" =>
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
    }.getOrElse(NotFound)*/
  def save = {
    val maniesObj = table.oneToManies.map{ otm =>
"""        formData."""+otm.name+"""s.map{ fData =>
             fData.insert(fData.obj.copy("""+table.tableName+"""Id = id))
        }"""
    }.mkString("\n")
    """
  def save() = withUserAsync{ user =>  implicit request =>

    form.bindFromRequest.fold(
      formWithErrors => {
        Future{Ok(Json.obj("errors" -> formWithErrors.errorsAsJson))}
      }, formData => {
        formData.insert(formData.obj).flatMap{id =>
          objectResponse(id)
        }
      }
    )
  }
  """
  }

  val nestedRoute = if(isMany) "\n"+"""GET         /"""+table.objName+"""/nested            controllers"""+submodulePackageString+"""."""+table.className+"""Controller.createNested()""" else ""

  def routes = {
    """
GET         /"""+table.objName+"""/                  controllers"""+submodulePackageString+"""."""+table.className+"""Controller.index(page: Int = 1, pageLength: Int = 20, sortColumn: String = "id", sortOrder: String = "desc")
GET         /"""+table.objName+"""/show/:id          controllers"""+submodulePackageString+"""."""+table.className+"""Controller.show(id: Long)
GET         /"""+table.objName+"""/delete/:id          controllers"""+submodulePackageString+"""."""+table.className+"""Controller.delete(id: Long)
POST        /"""+table.objName+"""/save              controllers"""+submodulePackageString+"""."""+table.className+"""Controller.save()
POST        /"""+table.objName+"""/update/:id        controllers"""+submodulePackageString+"""."""+table.className+s"""Controller.update(id: Long)
${if(table.foreignKeys.length>0) """GET         /"""+table.objName+"""/options    controllers"""+submodulePackageString+"""."""+table.className+"""Controller.options()""" else ""}
${if(attachments.length>0) """GET         /"""+table.objName+"""/download/:id/:file    controllers"""+submodulePackageString+"""."""+table.className+"""Controller.download(id: Long, file: String)""" else ""}
GET         /"""+table.objName+"""/:page/:pageLength/:sortColumn/:sortOrder controllers"""+submodulePackageString+"""."""+table.className+"""Controller.index(page: Int, pageLength: Int, sortColumn: String, sortOrder: String)
GET         /"""+table.objName+"""/:page/:pageLength controllers"""+submodulePackageString+"""."""+table.className+"""Controller.index(page: Int, pageLength: Int, sortColumn: String = "id", sortOrder: String = "desc")
GET         /"""+table.objName+"""/:page             controllers"""+submodulePackageString+"""."""+table.className+"""Controller.index(page: Int, pageLength: Int = 20, sortColumn: String = "id", sortOrder: String = "desc")
"""
  }

  def nestedForm() = { """  def nestedForm(form: Field)(implicit request: Request[AnyContent]) = {
"""+fks+ """
    views.html"""+submodulePackageString+"."+table.viewsPackage+"""._nestedForm(form"""+params+""")
  }"""
  }

  def options() = {
    val result = table.foreignKeys.map{fk => s""""${fk.tableName}s" -> Json.toJson(${fk.tableName}s)"""}.mkString(", ")
    s"""  def options() = withUserAsync{ user =>  implicit request =>
${fksJson(s"""Ok(Json.obj(${result}))""")}
  }"""
  }

  def downloads() = {
    s"""  def download(id: Long, file: String) = withUserAsync { user => implicit request =>
    repo.byId(id).map{ oobj =>
      oobj.map{ obj =>
        Ok.sendFile(new java.io.File("./"+file))
      }.getOrElse(OrElse)
    }
  }"""
  }


  def createNested() = """
  def createNested() = withUserAsync{ user =>  implicit request =>
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
