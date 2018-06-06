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


  def filterRec(columns: List[AbstractColumn], prefix: String = ""): List[String] = {




    columns.flatMap{ col => col match{
      case c: Column if !c.synthetic && c.display != DisplayType.Hidden =>

        c.tpe match {
          case "String" | "Text" =>  List(s""".filteredBy(formData.${prefix}${c.name})((x,y) => x.${c.name}${if(c.optional){""".getOrElse("")"""}else{""}}.toUpperCase like ('%'+y.toUpperCase+'%'))""")
          case "Long" =>
            if(c.isId)
              List(s""".filteredBy( formData.${prefix}${c.name}.flatMap{x => if(x>0L) Some(x) else None} )( _.${c.name} === _ )""")
            else if(c.optional)
              List(s""".filteredBy( formData.${prefix}${c.name}.flatMap{x => if(x>0L) Some(x) else None} )( _.${c.name}.getOrElse(0L) === _ )""")
            else
              List(s""".filteredBy( formData.${prefix}${c.name}.collect{case x if x>0L => x} )( _.${c.name} === _ )""")
          case "Date" | "DateTime" | "Timestamp" =>
            if(c.optional)
              List(s""".filteredBy( formData.${prefix}${c.name}From )((x,y) => x.${c.name}.map{d => d >= y}.getOrElse(false) )""",
                s""".filteredBy( formData.${prefix}${c.name}To )((x,y) => x.${c.name}.map{d => d <= y}.getOrElse(false) )"""
              )
            else
              List(s""".filteredBy( formData.${prefix}${c.name}From )( _.${c.name} >= _ )""", s""".filteredBy( formData.${prefix}${c.name}To )( _.${c.name} <= _ )""")


          case "Double" => List(s""".filteredBy(formData.${prefix}${c.name})((x,y) => x.${c.name}${if(c.optional){""".getOrElse(0.0)"""}else{""}} === y)""")
          case "Int" => List(s""".filteredBy(formData.${prefix}${c.name})((x,y) => x.${c.name}${if(c.optional){""".getOrElse(0)"""}else{""}} === y)""")
          case _ =>
            List(c.tpe+" not implemented")
        }
      case s: SubClass =>
        //println("EL TABLE", table.tableName)
        //println("EL S ", s.name)
        //s.cols.map{x => println(" >"+x.name)}
        filterRec(s.cols, underscoreToCamel(s.name)+".")
      case o: OneToMany =>
        //println("========================")
        //println(table.tableName)
        //println("buscando referencia backwards a "+o.foreignTable)
        val backwardsReference = tables.find(t => t.tableName == o.foreignTable).map{ t =>
          t.columns.find{case c: Column => c.foreignKey.map{fk => fk.tableName == table.tableName}.getOrElse(false)}.map{ c =>
            c.name
          }.getOrElse("No encontre referencia backwards a "+t.tableName)
        }.getOrElse(throw new Error("No encontre referencia a "+o.foreignTable))

        List(s""".oneToManyFilterfilter(formData.${prefix}${o.foreignTable}s)((x,o) => ${o.rawForeignTable}Repo.get().filter(o).filter(y => y.${backwardsReference} === x.id).exists)""")
      case _ => List("NO SE QUE HCER")
    }}
  }

  def filter = {

    val filters = filterRec(table.columns).map("      "+_).mkString("\n")
    s"""  def filter(formData: ${table.className}Filter) = {
    all
${filters}
  }"""

  }

  def generate: String = {


    val className = table.className
    val columns: List[AbstractColumn] = table.columns

    def generateClass(className: String, columns: List[AbstractColumn], isSubClass: Boolean = false, level: Int = 0): String = {
      val tab = "  "
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
      val result = (if(subClasses.length>0) s"""object ${className}Partition{"""+"\n  import extensions."+className+"Partition._\n" else "")+
      subClasses.map{sc => generateClass(sc.className, sc.cols, true, level + 1)}.mkString("\n\n")+ (if(subClasses.length>0) "\n}\n" else "")+"\n\n"+ generatedClass + "\n\n"+objectClass + "\n\n"
      result.split("\n").map{(tab*level)+_}.mkString("\n")
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

    val tableClassHead = """class """+className+s"""${langHash("Mapping")}(tag: Tag) extends Table["""+className+"""](tag, """"+table.tableNameDB+""""){"""
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

    baseClass+"\n\n"+formData()+form()+"\n\n"+filterFormData()+"\n\n"+filterForm()+"\n\n"//+tableClass + objectHead + formData() + form()
  }

  def isColumnOneToManyMap(c: Column): Boolean = {
    c.foreignKey.map{fk => tablesOneToMany.exists{t => t.tableName == fk.table}}.getOrElse(false)
  }

  def getFields(columns: List[AbstractColumn], className: String, lvl: Int = 1, filterForm: Boolean = false): Option[String] = {
    val margin = (" "*(4+lvl*2))
    val margin_1 = (" "*(4+(lvl-1)*2))


    val classNameStr = if(filterForm) className+"Filter" else className

    val columnsWithDate = if(filterForm){
      columns.flatMap{
        case c: Column if c.tpe == "DateTime" || c.tpe == "Date" || c.tpe == "Timestamp" =>
          List(c.copy(name = c.name+"From"), c.copy(name = c.name+"To"))
        case c => List(c)
      }
    } else columns

    val list = columnsWithDate.collect{
      case c : Column if !c.synthetic && c.display != DisplayType.Hidden => margin+"\""+c.name+"\" -> "+{
        if(filterForm)
          s"optional(${c.formMappingTpe})"
        else
          if(isColumnOneToManyMap(c)) "optional("+c.formMapping+")" else c.formMapping
      }
      case s @ SubClass(name, cols) if(getFields(cols, s.className, lvl+1,filterForm).isDefined)  =>
        getFields(cols, s.className, lvl+1,filterForm).map{ a =>
          margin+"\""+name+"\" -> "+a
        }.get
      case o: OneToMany =>
        if(filterForm)
          margin+"\""+o.name+"s\" -> list(models."+o.className+"FilterForm.filterForm.mapping)"
        else
          margin+"\""+o.name+"s\" -> list(models."+o.className+"Form.form.mapping)"
    }
    val hasOneToMany = lvl <= 1 //&& table.hasOneToMany



    val optionalList = columnsWithDate.collect{
      case c: Column if c.synthetic && !filterForm => "Some(new DateTime())"
      case c: Column if c.display != DisplayType.Hidden => if(!isColumnOneToManyMap(c) || filterForm) c.name else c.name+".getOrElse(0)"
      case c: Column if c.display == DisplayType.Hidden =>
        if(c.optional && !filterForm)
          "Some("+c.defaultValue+")"
        else
          c.defaultValue
      case s @ SubClass(name, cols) =>
        if(getFields(cols, s.className, lvl+1,filterForm).isDefined)
          s.name
        else
          s.name.capitalize+"()"
      case c: OneToMany if filterForm => c.foreignTable+"s"
    }.mkString(", ")

    val nonSynthList = columnsWithDate.collect{
      case c: Column if !c.synthetic && c.display != DisplayType.Hidden => c.name
      case s @ SubClass(name, cols) if(getFields(cols, s.className, lvl+1, filterForm).isDefined) => s.name
      case c: OneToMany => c.name+"s"
    }.mkString(",")

    val prefix = if(hasOneToMany && !filterForm) "obj." else ""
    val optionalListObj = columnsWithDate.collect{
      case c: Column if (!c.synthetic && c.display != DisplayType.Hidden)   => if(!isColumnOneToManyMap(c) ||  filterForm) "formData."+prefix+c.name else "Some(formData."+prefix+c.name+")"
      case s @ SubClass(name, cols) if(getFields(cols, s.className, lvl+1, filterForm).isDefined) => "formData."+prefix+s.name
      case o: OneToMany => "formData."+o.name+"s"
    }.mkString(", ")
    val advancedForm = true//table.createdAt || table.updatedAt || table.oneToManies.size>0

    val formClass = if(hasOneToMany && !filterForm) table.className+"""FormData""" else classNameStr

    val embeddedStart = if(hasOneToMany && !filterForm) table.className+"""FormData(""" else ""

    val embeddedEnd = if(hasOneToMany && !filterForm) (if(table.hasOneToMany) ", " else "")+table.oneToManies.map{otm => otm.name+"s"}.mkString(", ")+")" else ""

    val optionalMapping = List(margin_1+(if(advancedForm) "" else "/*")+"""(("""+nonSynthList+""") => {""",
      margin_1+"  "+embeddedStart+classNameStr+"""("""+optionalList+""")"""+embeddedEnd,
      margin_1+"""})((formData: """+formClass+ s""") => {""",
      margin_1+"""  Some(("""+optionalListObj+"""))""",
      margin_1+"""})"""+(if(advancedForm) "" else "*/"))

    val defaultMapping = (if(advancedForm) "/*("+classNameStr+".apply)("+classNameStr+".unapply)*/" else "("+classNameStr+".apply)("+classNameStr+".unapply)")

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
            """+getFields(table.columns, table.className).getOrElse("")+s"""
  )
}"""
  }

  val className = table.className
  val columns: List[AbstractColumn] = table.columns

  def filterFormDataRec(className: String, columns: List[AbstractColumn], isSubClass: Boolean = false, level: Int = 0): String = {

    val tab = "  "
    val head: String = """case class """+className+"""Filter("""
    val colsWithDoubleDate = columns.flatMap{
      case c: Column if c.tpe == "DateTime" || c.tpe == "Date" || c.tpe == "Timestamp" =>
        List(c.copy(name = c.name+"From"), c.copy(name = c.name+"To"))
      case c => List(c)
    }
    val cols = colsWithDoubleDate.collect{
      case c: Column => c.name+": Option["+c.tpe+"] = None"
      case c: SubClass => c.name+": "+s"${className}PartitionFilter.${c.className}Filter"
      case c: OneToMany => s"""${c.foreignTable}s: List[${c.className}Filter] = List()"""
    }


    val generatedClass = head + cols.mkString(",\n"+(" "*head.length))+ s")"//{if(!isSubClass) getters else ""}+"\n}"


    val subClasses = columns.collect{
      case c: SubClass => c
    }

    val objectClass = s"""object ${className}Filter {
  val tupled = (this.apply _).tupled
}"""
    val result =
      (if(subClasses.length>0) s"""object ${className}PartitionFilter{"""+"\n" else "")+
      (subClasses.map{sc => filterFormDataRec(sc.className, sc.cols, true, level + 1)}.mkString("\n\n")+ (if(subClasses.length>0) "\n}\n" else "")+"\n\n"+ generatedClass + "\n\n"+objectClass + "\n\n")
    result.split("\n").map{(tab*level)+_}.mkString("\n")
  }

  def filterFormData(): String = {
    val baseClass = filterFormDataRec(className, columns)
    baseClass
  }
  def filterForm(): String = {
    """
object """+table.className+s"""FilterForm{
  ${if(table.subClasses.length>0) s"""import ${table.className}PartitionFilter._""" else ""}
  val filterForm = Form(
            """+getFields(table.columns, table.className, lvl = 1, filterForm = true).getOrElse("")+"""
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


    val tableClassHead = """class """+className+s"""${langHash("Mapping")}(tag: Tag) extends Table["""+className+"""](tag, """"+table.tableNameDB+""""){"""


    def getSortColMaps(cols: List[AbstractColumn]): List[String] = {
      cols.flatMap{
        case c: Column if !c.foreignKey.isDefined =>
          List(s"""    "${c.name}" -> {(q,s) => q.sortBy(_.${c.name})(s)}""") //TODO: foreignkey
        case c: Column if c.foreignKey.isDefined =>
          val fk = c.foreignKey.get
          if(fk.displayField.isDefined){
            val repoName = if(fk.table == table.yamlName) "" else (fk.table+"Repo.")
            List(s"""    "${c.name}" -> {(q,s) => q.join(${repoName}all).on(_.${c.name} === _.id).sortBy(_._2.${underscoreToCamel(fk.displayField.getOrElse(fk.tableName))})(s).map{_._1}}""") //TODO: foreignkey
          }
          else
            List(s"""    //displayField for field ${fk.tableName} is not defined in YML""")
        case s: SubClass => getSortColMaps(s.cols)
        case _ => List()
      }
    }

    val sortColMaps = getSortColMaps(columns).mkString(",\n")
    val sortMap =
      s"""

  override val sortMap = Map[String, SortMapType](
${sortColMaps}
  )
      """

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

    val fkRepositories: List[String] = if(table.foreignColumns.length>0 || table.hasOneToMany){
      val relations = table.foreignColumns.filter(fc => fc.foreignKey.map{fk => fk.className != table.className}.getOrElse(false)).map { fc =>
        fc.foreignKey.map { fk =>
          (fk.table, fk.className+"Repository")
        }.getOrElse(("",""))
      } ++ table.oneToManies.map{o =>
        (o.rawForeignTable, s"Provider[${o.className}Repository]")
      }


      relations.toSet[(String,String)].map{ fk =>
        fk._1 + "Repo: " +fk._2
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

import javax.inject.{Inject, Singleton, Provider}
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
    tableClass.split("\n").map{s => "  "+s}.mkString("\n") + sortMap+ "\n\n"+ dbTables + "\n\n"+getters+"\n\n"+filter+ "\n"+ //+toJson+"\n"+
    """}"""
  }
}
