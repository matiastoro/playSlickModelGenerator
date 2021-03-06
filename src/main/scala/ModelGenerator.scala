package via56.slickGenerator

import scala.collection.immutable.ListMap

/**
 * Created by matias on 1/26/15.
 */
case class ModelGenerator(table: Table, tablesOneToMany: List[Table] = List()) extends CodeGenerator {


  def imports(className: String) = s"""package models
import models.extensions.${className}Extension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

""" /*TODO: only import classes relatives to the table*/


  def getters = table.foreignColumns.map{fc =>
    fc.foreignKey.map{ fk =>
      """  def get"""+fk.className+""" = """+fk.queryName+""".byId("""+fc.name+""")"""
    }.getOrElse("")
  }.mkString("\n")

  def generate: String = {


    val className = table.className
    val columns: List[AbstractColumn] = table.columns

    def generateClass(className: String, columns: List[AbstractColumn], isSubClass: Boolean = false): String = {
      val head: String = """case class """+className+"""("""
      val cols = columns.collect{
        case c: Column => c.name+": "+c.tpeWithOption
        case c: SubClass => c.name+": "+c.className
      }

      val selectCol = columns.filter(_.name == table.objName).headOption.getOrElse{
        columns.filter(c => c.name == "name" || c.name == "nombre").headOption.getOrElse{
          columns.collect{case c: Column if c.tpe=="String" => c}.headOption.getOrElse{
            columns.head
          }
        }
      }.name

      val selectString = "  lazy val selectString = "+selectCol

      val generatedClass = head + cols.mkString(",\n"+(" "*head.length))+ s") extends ${className}Extension{\n"+selectString+"\n"+getters+"\n}"


      val subClasses = columns.collect{
        case c: SubClass => c
      }

      generatedClass + "\n\n"+subClasses.map{sc => generateClass(sc.className, sc.cols)}.mkString("\n\n")
    }


    /*val head: String = """case class """+className+"""("""

    val subClasses = columns.flatMap{
      case c: SubClass => List(generateClass(c.className, c.cols))
      case _ => List()
    }.mkString("\n\n")+"\n\n"*/

    val baseClass = imports(className)+generateClass(className, columns)

    def generateStars(columns: List[AbstractColumn]): String ={
      "("+columns.collect{col => col match{
        case c: Column =>
          if(c.name == "id")
            "id.?"
          else
            c.name
        case s: SubClass => s.name+"Cols"
      }}.mkString(", ")+")"
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
      columns.collect{ col => col match{
        case c: Column =>
          if(c.name=="id"){
            "  def id = column[Long](\"id\", O.PrimaryKey, O.AutoInc)"
          } else {
            val colMap = "  def "+c.name+" = column["+c.tpeWithOption+"](\""+c.rawName+"\""+(if(c.optional) ", O.Default(None))" else ")")

            c.foreignKey.map{ fk =>
              val onDelete = fk.onDelete.map{od => ", onDelete=ForeignKeyAction."+od.capitalize}.getOrElse("")
              colMap + "\n  def "+guessFkName(c.name)+" = foreignKey(\""+c.rawName+"_fk\", "+c.name+", "+underscoreToCamel(fk.table).capitalize+"Query.all)(_."+fk.reference+onDelete+")"
            }.getOrElse(colMap)

          }
        case s: SubClass =>
          hasSubClasses = true
          "\n"+generateColumnsTagTable(s.cols)+"\n  val "+s.name+"Cols = "+generateStars(s.cols)+"\n"
      }
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

    val tableClassHead = """class """+className+"""Mapping(tag: Tag) extends Table["""+className+"""](tag, """"+table.tableName+"""") {"""
    val tableClass = tableClassHead +"\n"+ tableCols+ "\n\n"+star+ shaped + "\n}"

    val foreignKeyFilters = table.foreignColumns.map{ c =>
      c.foreignKey.map{fk =>
        """  def by"""+fk.className+"""Id(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_."""+c.name+"""===i).list
    }.getOrElse(List())
  }
                            """
      }.getOrElse("")
    }.mkString("\n\n")

    val objectHead ="""

class """+className+"""QueryBase extends DatabaseClient["""+className+"""] {
  type DBTable = """+className+"""Mapping

  private[models] val all = {
    TableQuery[DBTable]
  }
"""+foreignKeyFilters+"""
}"""

    baseClass+"\n\n"+tableClass + objectHead + formData() + form()
  }

  def isColumnOneToManyMap(c: Column): Boolean = {
    c.foreignKey.map{fk => tablesOneToMany.exists{t => t.tableName == fk.table}}.getOrElse(false)
  }

  def getFields(columns: List[AbstractColumn], className: String, lvl: Int = 1): String = {
    val margin = (" "*(4+lvl*2))
    val margin_1 = (" "*(4+(lvl-1)*2))

    val list = columns.collect{
      case c : Column if !c.synthetic=> margin+"\""+c.name+"\" -> "+{
        if(isColumnOneToManyMap(c)) "optional("+c.formMapping+")" else c.formMapping
      }
      case s @ SubClass(name, cols) => margin+"\""+name+"\" -> "+getFields(cols, s.className, lvl+1)
      case o: OneToMany => margin+"\""+o.name+"s\" -> list(models."+o.className+"Form.form.mapping)"
    }
    val hasOneToMany = lvl <= 1 //&& table.hasOneToMany



    val optionalList = columns.collect{
      case c: Column if c.synthetic => "Some(new DateTime())"
      case c: Column => if(!isColumnOneToManyMap(c)) c.name else c.name+".getOrElse(0)"
      case c: SubClass => c.name
    }.mkString(", ")

    val nonSynthList = columns.collect{
      case c: Column if !c.synthetic => c.name
      case c: SubClass => c.name
      case c: OneToMany => c.name+"s"
    }.mkString(",")

    val prefix = if(hasOneToMany) "obj." else ""
    val optionalListObj = columns.collect{
      case c: Column if !c.synthetic => if(!isColumnOneToManyMap(c)) "formData."+prefix+c.name else "Some(formData."+prefix+c.name+")"
      case c: SubClass => "formData."+prefix+c.name
      case o: OneToMany => "formData."+o.name+"s"
    }.mkString(", ")
    val advancedForm = true//table.createdAt || table.updatedAt || table.oneToManies.size>0

    val formClass = if(hasOneToMany) table.className+"""FormData""" else className

    val embeddedStart = if(hasOneToMany) table.className+"""FormData(""" else ""

    val embeddedEnd = if(hasOneToMany) (if(table.hasOneToMany) ", " else "")+table.oneToManies.map{otm => otm.name+"s"}.mkString(", ")+")" else ""

    val optionalMapping = List(margin_1+(if(advancedForm) "" else "/*")+"""(("""+nonSynthList+""") => {""",
      margin_1+"  "+embeddedStart+className+"""("""+optionalList+""")"""+embeddedEnd,
      margin_1+"""})((formData: """+formClass+ """) => {""",
      margin_1+"""  Some("""+optionalListObj+""")""",
      margin_1+"""})"""+(if(advancedForm) "" else "*/"))

    val defaultMapping = (if(advancedForm) "/*("+className+".apply)("+className+".unapply)*/" else "("+className+".apply)("+className+".unapply)")
    "mapping(\n"+list.mkString(",\n")+"\n"+margin_1+")"+defaultMapping+"\n"+optionalMapping.mkString("\n")



  }


  def formData(): String = {
    val otms: String = if(table.oneToManies.size>0) ", "+table.oneToManies.map{otm => otm.foreignTable+"s: List["+otm.className+"FormData]"}.mkString(", ") else ""
    val otmsUpdates = table.oneToManies.map{otm =>
      "    //Delete elements that are not part of the form but they do exists in the database.\n"+
      """    """+otm.queryName+""".by"""+table.className+"""Id(obj.id).filterNot{o => """+otm.foreignTable+"""s.exists(_.obj.id == o.id)}.map{"""+otm.queryName+""".delete(_)}"""+"\n"+
      """    """+otm.foreignTable+"""s.map{o => o.update(o.obj.copy("""+table.tableName+"""Id = obj.id.get))}"""
    }.mkString("\n")
    val otmsInserts = table.oneToManies.map{otm => """    """+otm.foreignTable+"""s.map{o => o.insert(o.obj.copy("""+table.tableName+"""Id = id))}""" }.mkString("\n")

    val alternativeConstructor = if(table.oneToManies.size>0){
      val otmsLists = table.oneToManies.map{otm =>
        """    val """+otm.foreignTable+"""s = """+otm.queryName+""".by"""+table.className+"""Id(obj.id).map("""+otm.className+"""FormData(_))"""
      }.mkString("\n")
      val otmsArgs = table.oneToManies.map{otm => otm.foreignTable+"s"}.mkString(", ")
"""object """+table.className+"""FormData{
  def apply(obj: """+table.className+""") = {
"""+otmsLists+"""
    new """+table.className+"""FormData(obj, """+otmsArgs+""")
  }
}"""
    } else ""

    """
case class """+table.className+"""FormData(obj: """+table.className+otms+"""){
  def update(updatedObj: """+table.className+""" = obj) = {
"""+otmsUpdates+"""
    """+table.queryName+""".updateOrInsert(updatedObj)
  }
  def insert(insertedObj: """+table.className+""") = {
    val id = """+table.queryName+""".insert(insertedObj)
"""+otmsInserts+"""
    id
  }
}
"""+alternativeConstructor
  }
  def form(): String = {
   """
object """+table.className+"""Form{
  val form = Form(
            """+getFields(table.columns, table.className)+"""
  )
}"""
  }


  def generateExtension: String = {
    val classes = table.className :: table.columns.collect{
      case c: SubClass => c.className

    }
    val className = classes.head
    val classesCode = classes.map{ c =>
      s"""
trait ${c}Extension{ this: $c =>

}
      """.trim
    }.mkString("\n\n")
    s"""
package models.extensions

import models._

$classesCode

object ${className}Query extends ${className}QueryBase{

}
    """.trim
  }
}
