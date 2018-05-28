package via56.slickGenerator

import scala.collection.immutable.ListMap

/**
 * Created by matias on 1/26/15.
 */
case class ModelGenerator(table: Table, tablesOneToMany: List[Table] = List(), tables: List[Table] = List())(implicit langHash: Map[String,String]) extends CodeGenerator {


  def imports(className: String) = s"""package models
//import models.extensions.${className}Extension
import extensions._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import play.api.data.Form
import play.api.data.Forms._
//support for joda is now a separate project
import play.api.data.JodaForms._
import play.api.data.format.Formats._
import org.joda.time.{DateTime, LocalDate, DateTimeZone}
//import com.github.tototoshi.slick.H2JodaSupport._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._
//import scala.concurrent.ExecutionContext.Implicits.global

/*import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._


import play.api.libs.json._*/

""" /*TODO: only import classes relatives to the table*/


  def getters = table.foreignColumns.map{fc =>
    fc.foreignKey.map{ fk =>
      val name = underscoreToCamel(fc.name).capitalize
      val fname = if(name.indexOf("Id")>0) name.substring(0,name.length-2) else name

      """  def get"""+fname+s"""(obj: ${table.className}) = """+{
        val fkTable = if(fk.table == table.yamlName) "this" else fk.table+"Repo"
        val fcname = (fc.subClass.map{sc => sc.name+"."}.getOrElse("")+fc.name)
        if(!fc.optional)
          fkTable+s""".${langHash("byId")}(obj."""+fcname+""")"""
        else
          """if(obj."""+fcname+""".isDefined) """+fkTable+s""".${langHash("byId")}(obj."""+fcname+""".get) else None"""
      }
    }.getOrElse("")
  }.mkString("\n")


  def filterRec(columns: List[AbstractColumn]): List[String] = {
    s"""
  def filter(formData: EccairsFilter) = {
    all
      .filteredBy( formData.number )((x,y) =>  x.number like ('%'+y+'%') )
      .filteredBy( formData.date_from )( _.date >= _ )
      .filteredBy( formData.date_to )( _.date <= _ )
      .filteredBy( formData.stateAreaId.collect{case x if x>0L => x} )( _.stateAreaId === _ )
      .filteredBy( formData.ocurrenceClassId.collect{case x if x>0L => x} )( _.ocurrenceClassId === _ )
      .filteredBy( formData.injuryLevelId.flatMap{x => if(x>0L) Some(x) else None} )( _.injuryLevelId.getOrElse(0L) === _ )
      .filteredBy( formData.massGroupId.flatMap{x => if(x>0L) Some(x) else None} )( _.massGroupId.getOrElse(0L) === _ )
      .filteredBy( formData.categoryId.flatMap{x => if(x>0L) Some(x) else None} )( _.categoryId.getOrElse(0L) === _ )
      .filteredBy( formData.registry )((x,y) =>  x.registry.getOrElse("") like ('%'+y+'%') )
      .filteredBy( formData.operationTypeId.flatMap{x => if(x>0L) Some(x) else None} )( _.operationTypeId.getOrElse(0L) === _ )
      .filteredBy( formData.operatorTypeId.flatMap{x => if(x>0L) Some(x) else None} )( _.operatorTypeId.getOrElse(0L) === _ )
      .filteredBy( formData.operatorId.flatMap{x => if(x>0L) Some(x) else None} )( _.operatorId.getOrElse(0L) === _ )
      .filteredBy( formData.weatherConditionId.flatMap{x => if(x>0L) Some(x) else None} )( _.weatherConditionId.getOrElse(0L) === _ )
      .oneToManyFilterfilter(formData.eccairsOcurrenceCategorys)((x,o) => eccairs_ocurrence_categoryRepo.get().all.filter(y => y.eccairsId === x.id && y.ocurrenceCategoryId === o.ocurrenceCategoryId).exists)
  }
      """

    columns.flatMap{ col => col match{
      case c: Column if !c.synthetic && c.display != DisplayType.Hidden =>
        c.tpe match {
          case "String" | "Text" => s""".filteredBy(formData.${c.name})((x,y) => x.${c.name}.toUpperCase like ('%'+y.toUpperCase+'%')"""
          case _ => //TODO
        }
        List("")
      case s: SubClass => filterRec(s.cols)//throw new Exception("Subclas")//generateInputs(s.cols, s.name+".")
      case o: OneToMany => //o.formHelper(submodulePackageString, Some(table))
        //.oneToManyFilterfilter(formData.eccairsOcurrenceCategorys)((x,o) => eccairs_ocurrence_categoryRepo.get().all.filter(y => y.eccairsId === x.id && y.ocurrenceCategoryId === o.ocurrenceCategoryId).exists)
        List("")
      case _ => List("")
    }}
  }

  def filter = {
    filterRec(table.columns).mkString("\n")
  }

  def generate: String = {


    val className = table.className
    val columns: List[AbstractColumn] = table.columns

    def generateClass(className: String, columns: List[AbstractColumn], isSubClass: Boolean = false): String = {
      val head: String = """case class """+className+"""("""
      val cols = columns.collect{
        case c: Column =>
          c.name+": "+c.tpeWithOption+{
          c.default match{
            case Some(default) =>
              val formattedDefault = if(c.tpe == "String") /*"\"" +*/ default /*+ "\""*/ else default
              if(c.optional) {" = Some("+formattedDefault+")"} else {" = "+formattedDefault}
            case None if c.optional => " = None"
            case None => ""
          }
          //if(c.default.isDefined) " = "+ c.default.get else ""
        }+" /*"+c.display.toString()+"*/"
        case c: SubClass => c.name+": "+s"${className}Partition.${c.className}"
      }




      val selectString = "" //  lazy val selectString = "+selectCol

      /*val toJson = {
        val toJsonDef = if(table.oneToManies.length>0){
          val otmsLists = table.oneToManies.map{otm =>
            s"""     ("${otm.lstName}" -> Json.toJson(${otm.queryName}.by${table.className}Id(id).map(_.toJson)))"""
          }.mkString("+\n")

          s"def toJson(implicit session: Session) = Json.toJson(this).as[JsObject] + \n ${otmsLists}"
        } else
          "def toJson = Json.toJson(this)"
        s"""  implicit val jsonFormat = Json.format[${table.className}]
           |  ${toJsonDef}
         """.stripMargin
      }*/

      val generatedClass = head + cols.mkString(",\n"+(" "*head.length))+ s") extends ${className}Extension{\n"+selectString+"\n}"//{if(!isSubClass) getters else ""}+"\n}"


      val subClasses = columns.collect{
        case c: SubClass => c
      }

      val objectClass = s"""object ${className} {
  implicit val format = Json.format[${className}]
  val tupled = (this.apply _).tupled
}"""
      (if(subClasses.length>0) s"""object ${className}Partition{"""+"\nimport extensions."+className+"Partition._\n" else "")+
      subClasses.map{sc => generateClass(sc.className, sc.cols, true)}.mkString("\n\n")+ (if(subClasses.length>0) "}\n" else "")+"\n\n"+ generatedClass + "\n\n"+objectClass + "\n\n"
    }


    /*val head: String = """case class """+className+"""("""

    val subClasses = columns.flatMap{
      case c: SubClass => List(generateClass(c.className, c.cols))
      case _ => List()
    }.mkString("\n\n")+"\n\n"*/

    val baseClass = imports(className)+generateClass(className, columns)

    def generateStars(columns: List[AbstractColumn]): String ={
      "("+columns.collect{
        case c: Column =>
          c.name
        case s: SubClass => s.name+"Cols"
      }.mkString(", ")+")"
    }

    var hasSubClasses = false
    def guessFkName(name: String): String = {
      val pos = name.indexOf("Id")
      if(pos>0)
        name.substring(0,pos)
      else
        name+"Rel"
    }
    def generateColumnsTagTable(columns: List[AbstractColumn]): String = {
      columns.collect{
        case c: Column =>
          if(c.name=="id"){
            "  def id = column[Long](\"id\", O.PrimaryKey, O.AutoInc)"
          } else {

            val colMap = "  def "+c.name+" = column["+c.tpeWithOption+"](\""+c.rawName+"\""+(
              if(c.optional)
              ", O.Default(None)"
            else
                c.default match{
                  case Some(x) => ", O.Default("+x+")"
                  case _ => ""
                }
            )+")"

            c.foreignKey.map{ fk =>
              val onDelete = fk.onDelete.map{od => ", onDelete=ForeignKeyAction."+od.capitalize}.getOrElse("")
              colMap + "\n  def "+guessFkName(c.name)+" = foreignKey(\""+c.rawName+"_fk\", "+c.name+", "+underscoreToCamel(fk.table).capitalize+s"${langHash("Query")}.tableQ)(_."+fk.reference+onDelete+")"
            }.getOrElse(colMap)

          }
        case s: SubClass =>
          hasSubClasses = true
          "\n"+generateColumnsTagTable(s.cols)+"\n  val "+s.name+"Cols = "+generateStars(s.cols)+"\n"

      }.mkString("\n")
    }

    val tableCols = generateColumnsTagTable(columns)


    val star = "  def * = "+generateStars(columns)

    val shaped = ".shaped <> " +
      (if(hasSubClasses){
        val fields = "("+columns.collect{
          case s: Column => s.name
          case s: SubClass => s.name
        }.mkString(", ")+") =>\n"
        val obj = className+"("+columns.collect{
          case c: Column => c.name
          case s: SubClass => s.name.capitalize+".tupled.apply("+s.name+")"
        }.mkString(", ")+")"
        val unapplies = "      Some((" + columns.collect{
          case c: Column => "o."+c.name
          case s: SubClass => s.name.capitalize+".unapply(o."+s.name+").get"
        }.mkString(",")+"))"
        "\n    ({\n      case "+fields + "      "+obj+"\n    }, {o: "+className+" =>\n"+unapplies+"\n    })"
      }
      else
        "("+className+".tupled, "+className+".unapply)")

    //def * = (id.?, fir, name, latitude, longitude) <> (Fir.tupled, Fir.unapply)

    val tableClassHead = """class """+className+s"""${langHash("Mapping")}(tag: Tag) extends Table["""+className+"""](tag, """"+table.tableNameDB+"""") {"""
    val tableClass = tableClassHead +"\n"+ tableCols+ "\n\n"+star+ shaped + "\n}"

    val foreignKeyFilters = (table.foreignColumns.toSet).map{ c: Column =>
      c.foreignKey.map{fk =>
        """  def by"""+fk.underscoreToCamel(c.name)+"""Id(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_."""+c.name+"""===i).list
    }.getOrElse(List())
  }
                            """
      }.getOrElse("")
    }.mkString("\n\n")

    val objectHead ="""

class """+className+s"""${langHash("Query")}Base extends BaseDAO["""+className+"""] {
  type DBTable = """+className+"""Mapeo

  val tableQ = {
    TableQuery[DBTable]
  }
"""+foreignKeyFilters+"""
}"""

    baseClass+"\n\n"+formData()+form()//+tableClass + objectHead + formData() + form()
  }

  def isColumnOneToManyMap(c: Column): Boolean = {
    c.foreignKey.map{fk => tablesOneToMany.exists{t => t.tableName == fk.table}}.getOrElse(false)
  }

  def getFields(columns: List[AbstractColumn], className: String, lvl: Int = 1): Option[String] = {
    val margin = (" "*(4+lvl*2))
    val margin_1 = (" "*(4+(lvl-1)*2))

    val list = columns.collect{
      case c : Column if !c.synthetic && c.display != DisplayType.Hidden => margin+"\""+c.name+"\" -> "+{
        println(c, c.formMapping)
        if(isColumnOneToManyMap(c)) "optional("+c.formMapping+")" else c.formMapping
      }
      case s @ SubClass(name, cols) if(getFields(cols, s.className, lvl+1).isDefined)  =>
        getFields(cols, s.className, lvl+1).map{ a =>
          margin+"\""+name+"\" -> "+a
        }.get
      case o: OneToMany => margin+"\""+o.name+"s\" -> list(models."+o.className+"Form.form.mapping)"
    }
    val hasOneToMany = lvl <= 1 //&& table.hasOneToMany



    val optionalList = columns.collect{
      case c: Column if c.synthetic => "Some(new DateTime())"
      case c: Column if c.display != DisplayType.Hidden => if(!isColumnOneToManyMap(c)) c.name else c.name+".getOrElse(0)"
      case c: Column if c.display == DisplayType.Hidden =>
        if(c.optional)
          "Some("+c.defaultValue+")"
        else
          c.defaultValue
      case s @ SubClass(name, cols) =>
        if(getFields(cols, s.className, lvl+1).isDefined)
          s.name
        else
          s.name.capitalize+"()"
    }.mkString(", ")

    val nonSynthList = columns.collect{
      case c: Column if !c.synthetic && c.display != DisplayType.Hidden => c.name
      case s @ SubClass(name, cols) if(getFields(cols, s.className, lvl+1).isDefined) => s.name
      case c: OneToMany => c.name+"s"
    }.mkString(",")

    val prefix = if(hasOneToMany) "obj." else ""
    val optionalListObj = columns.collect{
      case c: Column if (!c.synthetic && c.display != DisplayType.Hidden)   => if(!isColumnOneToManyMap(c)) "formData."+prefix+c.name else "Some(formData."+prefix+c.name+")"
      case s @ SubClass(name, cols) if(getFields(cols, s.className, lvl+1).isDefined) => "formData."+prefix+s.name
      case o: OneToMany => "formData."+o.name+"s"
    }.mkString(", ")
    val advancedForm = true//table.createdAt || table.updatedAt || table.oneToManies.size>0

    val formClass = if(hasOneToMany) table.className+"""FormData""" else className

    val embeddedStart = if(hasOneToMany) table.className+"""FormData(""" else ""

    val embeddedEnd = if(hasOneToMany) (if(table.hasOneToMany) ", " else "")+table.oneToManies.map{otm => otm.name+"s"}.mkString(", ")+")" else ""

    val optionalMapping = List(margin_1+(if(advancedForm) "" else "/*")+"""(("""+nonSynthList+""") => {""",
      margin_1+"  "+embeddedStart+className+"""("""+optionalList+""")"""+embeddedEnd,
      margin_1+"""})((formData: """+formClass+ """) => {""",
      margin_1+"""  Some(("""+optionalListObj+"""))""",
      margin_1+"""})"""+(if(advancedForm) "" else "*/"))

    val defaultMapping = (if(advancedForm) "/*("+className+".apply)("+className+".unapply)*/" else "("+className+".apply)("+className+".unapply)")

    if(!list.isEmpty)
      Some("mapping(\n"+list.mkString(",\n")+"\n"+margin_1+")"+defaultMapping+"\n"+optionalMapping.mkString("\n"))
    else
      None



  }

  def getOneToManyRepos(otm: OneToMany): List[(String, String)] = {
    //search for table
    val otms = tables.find(t => t.yamlName == otm.rawForeignTable).map{ t =>
      if(t.oneToManies.length>0){
        t.oneToManies.flatMap { otm =>
          getOneToManyRepos(otm)
        }
      } else List()
    }.getOrElse(List())
    //println("para ", otm, "encontre, ",otms)
    (List((otm.foreignTable, otm.className)) ++ otms).toSet.toList
  }

  /*def hasOneToManies(otm: OneToMany): Boolean = {

  }*/

  def formData(): String = {
    val otms: String = if(table.oneToManies.size>0) ", "+table.oneToManies.map{otm => otm.foreignTable+"s: List["+otm.className+"FormData]"}.mkString(", ") else ""
    val otmsUpdates = table.oneToManies.map{otm =>
      "    //Delete elements that are not part of the form but they do exists in the database.\n"+
      """    """+otm.foreignTable+"""Repo.by"""+table.className+"""Id(obj.id).map{ l =>  l.filterNot{o => """+otm.foreignTable+"""s.exists(_.obj.id == o.id)}.map{"""+otm.foreignTable+"""Repo.delete(_)}}"""+"\n"+
      """    """+otm.foreignTable+"""s.map{o => o.update(o.obj.copy("""+table.tableName+"""Id = obj.id.get))}"""
    }.mkString("\n")
    val otmsInserts = table.oneToManies.map{otm => """    """+otm.foreignTable+"""s.map{o => o.insert(o.obj.copy("""+table.tableName+"""Id = id))}""" }.mkString("++")

    val otmsRepos = if(table.oneToManies.length>0){
      ", "+table.oneToManies.flatMap { otm =>
        getOneToManyRepos(otm).map(p => p._1+"Repo: "+p._2+"Repository")
      }.mkString(", ")
    } else ""


    val alternativeConstructor = if(table.oneToManies.size>0){
      val otmsLists = table.oneToManies.map{otm =>
        """      """+otm.foreignTable+"""s <- """+otm.foreignTable+"""Repo.by"""+table.className+"""Id(obj.id).flatMap(l => Future.sequence(l.map("""+otm.className+"""FormData.fapply(_))))"""
      }.mkString("\n")
      val otmsArgs = table.oneToManies.map{otm => otm.foreignTable+"s.toList"}.mkString(", ")
"""object """+table.className+"""FormData{
  def fapply(obj: """+table.className+s""")(implicit repo: ${table.className}Repository${otmsRepos}, ec: ExecutionContext) = {
    for{
"""+otmsLists+"""
    } yield{
      new """+table.className+"""FormData(obj, """+otmsArgs+""")
    }
  }
}"""
    } else
"""object """+table.className+"""FormData{
  def fapply(obj: """+table.className+s""")(implicit ec: ExecutionContext) = {
    Future{
      new """+table.className+"""FormData(obj)
    }
  }
}"""



    """
case class """+table.className+"""FormData(obj: """+table.className+otms+"""){
  def update(updatedObj: """+table.className+s""" = obj)(implicit repo: ${table.className}Repository${otmsRepos}, ec: ExecutionContext) = {
"""+otmsUpdates+"""
    """+s"""repo.${langHash("updateOrInsert")}(updatedObj)
  }
  def insert(insertedObj: """+table.className+s""")(implicit repo: ${table.className}Repository${otmsRepos}, ec: ExecutionContext) = {
    repo.${langHash("insert")}(insertedObj).flatMap{ id=>
  """+(if(otmsInserts.length>0){ s"""Future.sequence(${otmsInserts}).map{ x =>
        id
      }
    """} else {"""Future{id}"""}) + """
    }
  }
}
"""+alternativeConstructor
  }
  def form(): String = {
   """
object """+table.className+s"""Form{
  ${if(table.subClasses.length>0) s"""import ${table.className}Partition._""" else ""}
  val form = Form(
            """+getFields(table.columns, table.className).getOrElse("")+"""
  )
}"""
  }


  def generateExtension: String = {
    val classes = table.columns.collect{
      case c: SubClass => c.className

    }
    val className = table.className
    val classesCode = if(classes.length>0) {
      s"""object ${table.className}Partition{""" + "\n"+s"""import models.${table.className}Partition._"""+"\n"+
        classes.map { c =>
          s"""
trait ${c}Extension{ this: $c =>

}
      """.trim
        }.mkString("\n\n") + "\n" +
        """}"""
    } else ""

    val mainClass = s"""
trait ${table.className}Extension{ this: ${table.className} =>

}
      """.trim


    s"""
package models.extensions

import models._

$mainClass

$classesCode

trait ${className}${langHash("Query")}{
  this: DatabaseClient[${className}] =>
}
    """.trim

  }

  def generateStars(columns: List[AbstractColumn]): String ={
    "("+columns.collect{col => col match{
      case c: Column =>
        if(c.name=="id")
          "id.?"
        else
          c.name
      case s: SubClass => s.name+"Cols"
    }}.mkString(", ")+")"
  }



  def generateRepository: String = {
    var hasSubClasses = false
    def guessFkName(name: String): String = {
      val pos = name.indexOf("Id")
      if(pos>0)
        name.substring(0,pos)
      else
        name+"Rel"
    }
    def generateColumnsTagTable(columns: List[AbstractColumn]): String = {
      columns.collect{ col => col match{
        case c: Column =>
          if(c.name=="id"){
            "  def id = column[Long](\"id\", O.PrimaryKey, O.AutoInc)"
          } else {

            val colMap = "  def "+c.name+" = column["+c.tpeWithOption+"](\""+c.rawName+"\""+(
              if(c.optional)
                ", O.Default(None)"
              else
                c.default match{
                  case Some(x) => ", O.Default("+x+")"
                  case _ => ""
                }
              )+")"

            /*c.foreignKey.map{ fk =>
              val onDelete = fk.onDelete.map{od => ", onDelete=ForeignKeyAction."+od.capitalize}.getOrElse("")
              colMap + "\n  def "+guessFkName(c.name)+" = foreignKey(\""+c.rawName+"_fk\", "+c.name+", "+underscoreToCamel(fk.table).capitalize+s"${langHash("Query")}.tableQ)(_."+fk.reference+onDelete+")"
            }.getOrElse(colMap)*/
            colMap

          }
        case s: SubClass =>
          hasSubClasses = true
          "\n"+generateColumnsTagTable(s.cols)+"\n  val "+s.name+"Cols = "+generateStars(s.cols)+"\n"
      }
      }.mkString("\n")
    }

    val className = table.className
    val columns: List[AbstractColumn] = table.columns

    val tableCols = generateColumnsTagTable(columns)


    val star = "  def * = "+generateStars(columns)

    val shaped = ".shaped <> " +
      (if(hasSubClasses){
        val fields = "("+columns.collect{
          case s: Column => s.name
          case s: SubClass => s.name
        }.mkString(", ")+") =>\n"
        val obj = className+"("+columns.collect{
          case c: Column => c.name
          case s: SubClass => s.name.capitalize+".tupled.apply("+s.name+")"
        }.mkString(", ")+")"
        val unapplies = "      Some((" + columns.collect{
          case c: Column => "o."+c.name
          case s: SubClass => s.name.capitalize+".unapply(o."+s.name+").get"
        }.mkString(",")+"))"
        "\n    ({\n      case "+fields + "      "+obj+"\n    }, {o: "+className+" =>\n"+unapplies+"\n    })"
      }
      else
        "("+className+".tupled, "+className+".unapply)")


    val tableClassHead = """class """+className+s"""${langHash("Mapping")}(tag: Tag) extends Table["""+className+"""](tag, """"+table.tableNameDB+"""") {"""
    val tableClass = tableClassHead +"\n"+ tableCols+ "\n\n"+"""/**
* This is the tables default "projection".
*
* It defines how the columns are converted to and from the User object.
*
* In this case, we are simply passing the id, name and page parameters to the User case classes
* apply and unapply methods.
*/"""+"\n"+star+ shaped + "\n}"

    val foreignKeyFilters = table.foreignColumns.map{ c =>
      c.foreignKey.map{fk =>
        """  def by"""+underscoreToCamel(c.name).capitalize+"""(id: Option[Long]) = id.map{id => db.run{
      all.filter(_."""+c.name+"""===id).result
  }}.getOrElse{Future(List())}
                            """
      }.getOrElse("")
    }.mkString("\n\n")

    val objectHead ="""

class """+className+s"""${langHash("Query")}Base extends BaseDAO["""+className+"""] {
  type DBTable = """+className+"""Mapeo

  val tableQ = {
    TableQuery[DBTable]
  }
"""+foreignKeyFilters+"""
}"""

    val fkRepositories: List[String] = if(table.foreignColumns.length>0){
      table.foreignColumns.filter(fc => fc.foreignKey.map{fk => fk.className != table.className}.getOrElse(false)).map { fc =>
        fc.foreignKey.map { fk =>
          (fk.table, fk.className)
        }.getOrElse(("",""))
      }.toSet[(String,String)].map{ fk =>
        fk._1 + "Repo: " +fk._2+"Repository"
      }.toList
    } else List()

    /*val toJson = {
      if(table.oneToManies.length>0){
        val forOtmsLists = table.oneToManies.map { otm =>
          val name = otm.foreignTable + "Repo: " +otm.className+"Repository"
          if(!fkRepositories.contains(name))
            fkRepositories = fkRepositories ++ List(name)
          s"""      ${otm.foreignTable}s <- ${otm.foreignTable}Repo.by${table.className}Id(obj.id)"""
        }.mkString("\n")

        val otmsLists = table.oneToManies.map{otm =>
          s"""     ("${otm.lstName}" -> Future.sequence(${otm.foreignTable}s.map{l => ${otm.foreignTable}Repo.toJson(l)}).map{z => z})"""
        }.mkString("+\n")


        s"""  def toJson(obj: ${className}) = {
    for{
${forOtmsLists}
    } yield{
      Json.toJson(obj).as[JsObject] +
${otmsLists}
    }
  }
          """
      } else
        s"def toJson(obj: ${className}) = Future{ Json.toJson(obj) }"
    }*/

    val repos = if(fkRepositories.length>0){
      s""", """+fkRepositories.mkString(", ")
    } else ""
    val dbTables = s"""  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = ${className}Table
  val all = TableQuery[${className}Table]

${foreignKeyFilters}
"""

    s"""package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._

import scala.concurrent.{ Future, ExecutionContext }
import models.extensions._
import org.joda.time.{DateTime, LocalDate}
import com.github.tototoshi.slick.H2JodaSupport._

@Singleton
class ${className}Repository @Inject() (dbConfigProvider: DatabaseConfigProvider${repos})(implicit ec: ExecutionContext) extends DatabaseClient[${className}] with ${className}${langHash("Query")}{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  ${if(table.subClasses.length>0) s"""import models.${table.className}Partition._"""}

""" +"\n\n"+
    tableClass.split("\n").map{s => "  "+s}.mkString("\n") + "\n\n"+ dbTables + "\n\n"+getters+"\n\n"+filter+ "\n"+ //+toJson+"\n"+
    """}"""
  }
}
