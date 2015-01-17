package via56.slickGenerator

import scala.util.parsing.combinator._
import collection.immutable.ListMap
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets




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
object parser {
  def main(args: Array[String]){
    val reader = Source.fromFile(args(0)).getLines.mkString("\n")
    val pathName = if(args.size > 1) args(1)+"/models" else "models"
    val path = Paths.get(pathName)
    YAMLParser.parse(reader).map{result =>
      Files.createDirectories(path)
      result match{
        case tables : ListMap[String, ListMap[String, Any]] =>
          tables.map{ table =>
            println(table._1)
            val t = Table(table._1, table._2)
            CrudGenerator.generate(t)
            writeToFile(pathName, table._1, generateTable(table._1, table._2))
          }
        case _ : List[Any] => println("lala2")
        case _ => println("no")
      }
    }
  }

  def writeToFile(path: String, tableName: String, content: String) = {
    val fileName = underscoreToCamel(tableName.capitalize)
    val currentContent = getCurrentContent(path+"/"+fileName+".scala")
    val customCode = rescueOldCode(currentContent)
    val finalContent = insertOldCode(content, customCode)
    if(currentContent.size != content.size)
      Files.write(Paths.get(path+"/"+fileName+".scala"), finalContent.getBytes(StandardCharsets.UTF_8))
  }

  def getCurrentContent(path: String) = {
    if(Files.exists(Paths.get(path)))
      new String(Files.readAllBytes(Paths.get(path)),StandardCharsets.UTF_8)
    else
      ""
  }


  def generateTable(name: String, columnsMap: ListMap[String, Any]): String = {

    val table = Table(name, columnsMap)
    val className = table.className
    val columns: List[AbstractColumn] = table.columns

    def generateClass(className: String, columns: List[AbstractColumn]): String = {
      val head: String = """case class """+className+"""("""
      val cols = columns.map{
        case c: Column => c.name+": "+c.tpeWithOption
        case c: SubClass => c.name+": "+c.name.capitalize
      }

      head + cols.mkString(",\n"+(" "*head.length))+"""){
/*==============ADD YOUR """+className+""" CODE FROM HERE==============*/
/*=========================TO HERE=========================*/
}"""
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

    val tableClassHead = """class """+className+"""Mapping(tag: Tag) extends Table["""+className+"""](tag, """"+name+"""") {"""
    val tableClass = tableClassHead +"\n"+ tableCols+ "\n\n"+star+ shaped + "\n}"


    val objectHead ="""

object """+className+"""Query extends DatabaseClient["""+className+"""] {
  type DBTable = """+className+"""Mapping

  private[models] val all = database.withSession { implicit db: Session =>
    TableQuery[DBTable]
/*==============ADD YOUR """+className+"""Query CODE FROM HERE==============*/
/*=========================TO HERE=========================*/
  }
}"""




    baseClass+tableClass + objectHead + extraClasses
  }



  val imports = """package models
import play.api.db.slick.Config.driver.simple._
import play.api.db._
import play.api.Play.current
/*==============ADD YOUR ADDITIONAL IMPORTS FROM HERE==============*/
/*=========================TO HERE=========================*/
"""

  val extraClasses = """
/*==============ADD YOUR ADDITIONAL CLASSES FROM HERE==============*/
/*=========================TO HERE=========================*/"""


  //function to transform from this_format to thisFormat
  def underscoreToCamel(name: String) = "_([a-z\\d])".r.replaceAllIn(name, {m =>
    m.group(1).toUpperCase()
  })

  def rescueOldCode(source: String) = {
    val start = """[\s\S]*[/*][=]+ADD YOUR (?:ADDITIONAL[ ])?([A-Za-z]+)(?:[ ])(?:CODE[ ])?FROM HERE[=]+[*/][\s\S]*""".r
    val end = """[\s\S]*[/*][=]+TO HERE[=]+[*/][\s\S]*""".r
    var key = ""
    var code = ""
    val codes = scala.collection.mutable.HashMap.empty[String,String]
    source.lines.toList.map { l =>
      l match {
        case start(z) => code = "";key = z
        case end() => codes += key -> code; code = ""
        case s : String => code += (if(s != "") s +"\n" else "")
      }
    }
    codes
  }
  def insertOldCode(newSource: String, code: scala.collection.mutable.HashMap[String,String]) = {
    val start = """[\s\S]*[/*][=]+ADD YOUR (?:ADDITIONAL[ ])?([A-Za-z]+)(?:[ ])(?:CODE[ ])?FROM HERE[=]+[*/][\s\S]*""".r
    var newCode = ""
    newSource.lines.toList.map { l =>
      l match {
        case start(z) => newCode += l +"\n"; newCode += code.getOrElse(z,"") +"\n"
        case s : String => newCode += s + "\n"
      }
    }
    newCode
  }
}