package via56.slickGenerator

import scala.util.parsing.combinator._
import collection.immutable.ListMap

abstract class Column
case class ColumnType(tpe: String) extends Column

class SlickCodeGenerator extends JavaTokenParsers {

  def column: Parser[Map[String, Any]] = stringLiteral~":"~"{"~>repsep(columnAttribute, ",")<~"}" ^^ (Map() ++ _)

  def columnAttribute: Parser[(String, Any)] = typeAttr | dbNameAttr

  def typeAttr: Parser[(String, Any)] = "type"~":"~>"""(\w+)""".r ^^ ("type" -> _)

  def dbNameAttr: Parser[(String, Any)] = "dbName"~":"~>"""(\w+)""".r ^^ ("dbName" -> _)


  /*def value : Parser[Any] = obj | arr |
    stringLiteral |
    floatingPointNumber |
    "null" | "true" | "false"

  def obj   : Parser[Any] = "{"~repsep(member, ",")~"}"

  def arr   : Parser[Any] = "["~repsep(value, ",")~"]"

  def member: Parser[Any] = column | stringLiteral~":"~value
  */

}

/*
import java.io.FileReader

object ParseJSON extends SlickCodeGenerator {
  def main(args: Array[String]) {
    val reader = new FileReader(args(0))
    println(parseAll(column, reader))
  }
}*/

import scala.io.Source
object parser{
  def main(args: Array[String]){
    val reader = Source.fromFile(args(0)).getLines.mkString("\n")
    YAMLParser.parse(reader).map{result =>

      result match{
        case tables : ListMap[String, ListMap[String, Any]] =>
          tables.map{ table => println(table._1); println(generateTable(table._1, table._2))}
        case _ : List[Any] => println("lala2")
        case _ => println("no")
      }
    }
  }

  abstract class AbstractColumn(val name: String)

  case class SubClass(override val name: String, cols: List[AbstractColumn]) extends AbstractColumn(name)

  case class Column(override val name: String, rawName: String, tpe: String, optional: Boolean) extends AbstractColumn(name){
    lazy val tpeWithOption = if(optional) "Option["+tpe+"]" else tpe
  }


  def isSubClass(props: Any): Boolean = {
    props match{
      case ps: ListMap[String, Any] =>
        ps.exists(pair =>
          pair match {
            case (prop, value) =>  value.isInstanceOf[Map[_,_]]
          }
        )
      case _ => false
    }
  }
  type columnProps = ListMap[String, String]
  type subClass = ListMap[String, ListMap[String, String]]
  def generateTable(name: String, columnsMap: ListMap[String, Any]): String = {
    val className = underscoreToCamel(name).capitalize


     def getColumns(columnsMap: ListMap[String, Any]): List[AbstractColumn] = {
       columnsMap.map{
         case (col, props) =>

            if(col=="id"){
              "Option[Long]"
              Column("id", "id", "Long", true)
            } else {
              var optional = false

              if(isSubClass(props)){
                props match{
                  case ps: subClass =>
                    println(ps)
                    SubClass(underscoreToCamel(col), getColumns(ps))
                  case _ => throw new Exception("parsing error")
                }
              } else {
                val tpe = (props match{
                  case ps: columnProps =>
                      optional = isOptional(ps)
                      getScalaType(col, ps,false)
                  case _ if col != "created_at" && col != "updated_at" => "Long"
                  case _ => "java.sql.Timestamp" //createdAt: ~, updatedAt: ~
                })
                Column(underscoreToCamel(col), col, tpe, optional)
              }
            }
        case _ => throw new Exception("Parsing error")
      }.toList
     }

    val columns: List[AbstractColumn] = getColumns(columnsMap)

    def generateClass(className: String, columns: List[AbstractColumn]): String = {
      val head: String = """case class """+className+"""("""
      val cols = columns.map{
        case c: Column => c.name+": "+c.tpeWithOption
        case c: SubClass => c.name+": "+c.name.capitalize
      }

      head + cols.mkString(",\n"+(" "*head.length))+")"
    }


    val head: String = """case class """+className+"""("""

    val subClasses = columns.flatMap{
      case c: SubClass => List(generateClass(c.name.capitalize, c.cols))
      case _ => List()
    }.mkString("\n\n")+"\n\n"

    val baseClass = imports+generateClass(className, columns)+"\n\n"+subClasses

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
    def generateColumnsTagTable(columns: List[AbstractColumn]): String = {
      columns.map{ col => col match{
          case c: Column =>
            if(c.name=="id"){
              "  def id = column[Long](\"id\", O.PrimaryKey, O.AutoInc)"
            } else {
              "  def "+c.name+" = column["+c.tpeWithOption+"](\""+c.rawName+"\""+(if(c.optional) ", O.Nullable)" else ")")
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

    val tableClassHead = """class """+className+"""s(tag: Tag) extends Table["""+className+"""](tag, """"+name+"""") {"""
    val tableClass = tableClassHead +"\n"+ tableCols+ "\n\n"+star+ shaped + "\n}"


    val objectHead ="""

object """+className+"""s extends DatabaseClient["""+className+"""] {
  type DBTable = """+className+"""s

  private[models] val all = database.withSession { implicit db: Session =>
    TableQuery[DBTable]
  }
}"""




    baseClass+tableClass + objectHead
  }
  def isOptional(ps: ListMap[String,String]): Boolean = {
    ps.getOrElse("required", "true") != "true"
  }
  def getScalaType(col: String, ps: ListMap[String,String], withOption: Boolean = true): String = {
    val s = ps.getOrElse("type", "")

    val tpe = (if("""varchar.*""".r.findFirstIn(s).isDefined)
      "String"
    else if("""integer.*""".r.findFirstIn(s).isDefined){
      if(ps.isDefinedAt("foreignTable"))
        "Long"
      else
        "Int"
    }
    else if("""boolean.*""".r.findFirstIn(s).isDefined)
      "Boolean"
    else if("""timestamp.*""".r.findFirstIn(s).isDefined)
      "java.sql.Timestamp"
    else if("""date.*""".r.findFirstIn(s).isDefined)
      "java.sql.Date"
    else if(s == "~" && col != "createdAt" && col != "updatedAt")
      "Long"
    else if(s == "~")
      "java.sql.Timestamp"
    else
      throw new Exception("No encontre el tipo '"+s+"' para '"+col+"'"))

    if(withOption && isOptional(ps))
      "Option["+tpe+"]"
    else tpe

  }

  val imports = """package models
import play.api.db.slick.Config.driver.simple._
import play.api.db._
import play.api.Play.current

"""


  def underscoreToCamel(name: String) = "_([a-z\\d])".r.replaceAllIn(name, {m =>
    m.group(1).toUpperCase()
  })
}