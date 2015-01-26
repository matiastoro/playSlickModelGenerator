package via56.slickGenerator

import scala.collection.immutable.ListMap

/**
 * Created by matias on 1/26/15.
 */
case class ModelGenerator(table: Table) extends CodeGenerator {


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

  def generate: String = {


    val className = table.className
    val columns: List[AbstractColumn] = table.columns

    def generateClass(className: String, columns: List[AbstractColumn], isSubClass: Boolean = false): String = {
      val head: String = """case class """+className+"""("""
      val cols = columns.map{
        case c: Column => c.name+": "+c.tpeWithOption
        case c: SubClass => c.name+": "+c.className
      }

      val selectCol = columns.filter(_.name == table.objName).headOption.getOrElse(columns.head).name
      val selectString = "  lazy val selectString = "+selectCol
      val generatedClass = head + cols.mkString(",\n"+(" "*head.length))+ s") extends ${className}Extension{\n"+selectString+"\n}"


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
      "("+columns.map{col => col match{
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
      columns.map{ col => col match{
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
        val fields = "("+columns.map{ _.name }.mkString(", ")+") =>\n"
        val obj = className+"("+columns.map{
          case c: Column => c.name
          case s: SubClass => s.name.capitalize+".tupled.apply("+s.name+")"
        }.mkString(", ")+")"
        val unapplies = "      Some((" + columns.map{
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


    val objectHead ="""

class """+className+"""QueryBase extends DatabaseClient["""+className+"""] {
  type DBTable = """+className+"""Mapping

  private[models] val all = database.withSession { implicit db: Session =>
    TableQuery[DBTable]
  }
}"""

    baseClass+"\n\n"+tableClass + objectHead + form()
  }

  def getFields(columns: List[AbstractColumn], className: String, lvl: Int = 1): String = {
    val margin = (" "*(4+lvl*2))
    val margin_1 = (" "*(4+(lvl-1)*2))

    val list = columns.collect{
      case c : Column if !c.synthetic=> margin+"\""+c.name+"\" -> "+c.formMapping
      case s @ SubClass(name, cols) => margin+"\""+name+"\" -> "+getFields(cols, s.className, lvl+1)
    }



    val optionalList = columns.map{
      case c: Column if c.synthetic => "Some(new DateTime())"
      case c => c.name
    }.mkString(", ")

    val nonSynthList = columns.collect{
      case c: Column if !c.synthetic => c.name
      case c: SubClass => c.name
    }.mkString(",")

    val optionalListObj = columns.collect{
      case c: Column if !c.synthetic => "obj."+c.name
      case c: SubClass => "obj."+c.name
    }.mkString(", ")
    val withSynth = table.createdAt || table.updatedAt

    val optionalMapping = List(margin_1+(if(withSynth) "" else "/*")+"""(("""+nonSynthList+""") => {""",
      margin_1+"  "+className+"""("""+optionalList+""")""",
      margin_1+"""})((obj: """+className+ """) => {""",
      margin_1+"""  Some("""+optionalListObj+""")""",
      margin_1+"""})"""+(if(withSynth) "" else "*/"))

    val defaultMapping = (if(withSynth) "/*("+className+".apply)("+className+".unapply)*/" else "("+className+".apply)("+className+".unapply)")
    "mapping(\n"+list.mkString(",\n")+"\n"+margin_1+")"+defaultMapping+"\n"+optionalMapping.mkString("\n")



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
