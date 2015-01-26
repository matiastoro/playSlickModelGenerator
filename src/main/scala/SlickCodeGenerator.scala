package via56.slickGenerator

import scala.util.parsing.combinator._
import collection.immutable.ListMap
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets
import via56.slickGenerator.crud.controller.ControllerGenerator
import via56.slickGenerator.crud.views._


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
    val pathName = if(args.size > 1) args(1)+"/" else "output"
    val path = Paths.get(pathName)
    val pathExtensions = Paths.get(path+"/models/extensions")
    val pathControllers = Paths.get(path+"/controllers")
    val pathViews = Paths.get(path+"/views")
    YAMLParser.parse(reader).map{result =>
      Files.createDirectories(path)
      Files.createDirectories(pathExtensions)
      Files.createDirectories(pathControllers)
      Files.createDirectories(pathViews)
      result match{
        case tables : ListMap[String, ListMap[String, Any]] =>
          tables.map{ table =>
            println(table._1)
            val t = Table(table._1, table._2)

            writeToFile(pathName, t)
          }
        case _ : List[Any] => println("lala2")
        case _ => println("no")
      }
    }
  }

  def writeToFile(path: String, table: Table) = {
    val fileName = underscoreToCamel(table.tableName.capitalize)
    val currentContent = getCurrentContent(path+"/models/"+fileName+".scala")

    val mg = ModelGenerator(table)
    val content = mg.generate
    if(currentContent.size != content.size)
      Files.write(Paths.get(path+"/models/"+fileName+".scala"), content.getBytes(StandardCharsets.UTF_8))



    if(!Files.exists(Paths.get(path+"/models/extensions/"+fileName+"Extension.scala"))){
      Files.write(Paths.get(path+"/models/extensions/"+fileName+"Extension.scala"), mg.generateExtension.getBytes(StandardCharsets.UTF_8))
    }

    Files.write(Paths.get(path+"/controllers/"+fileName+"Controller.scala"), ControllerGenerator(table).generate.getBytes(StandardCharsets.UTF_8))

    /*views*/
    Files.createDirectories(Paths.get(path+"/views/"+table.viewsPackage))
    Files.write(Paths.get(path+"/views/"+table.viewsPackage+"/_form.scala.html"), FormGenerator(table).generate.getBytes(StandardCharsets.UTF_8))
    Files.write(Paths.get(path+"/views/"+table.viewsPackage+"/create.scala.html"), CreateGenerator(table).generate.getBytes(StandardCharsets.UTF_8))
    Files.write(Paths.get(path+"/views/"+table.viewsPackage+"/edit.scala.html"), EditGenerator(table).generate.getBytes(StandardCharsets.UTF_8))
    Files.write(Paths.get(path+"/views/"+table.viewsPackage+"/index.scala.html"), IndexGenerator(table).generate.getBytes(StandardCharsets.UTF_8))
    Files.write(Paths.get(path+"/views/"+table.viewsPackage+"/main.scala.html"), MainGenerator(table).generate.getBytes(StandardCharsets.UTF_8))
    Files.write(Paths.get(path+"/views/"+table.viewsPackage+"/show.scala.html"), ShowGenerator(table).generate.getBytes(StandardCharsets.UTF_8))
    Files.write(Paths.get(path+"/views/"+table.viewsPackage+"/sidebar.scala.html"), SidebarGenerator(table).generate.getBytes(StandardCharsets.UTF_8))

  }

  def getCurrentContent(path: String) = {
    if(Files.exists(Paths.get(path)))
      new String(Files.readAllBytes(Paths.get(path)),StandardCharsets.UTF_8)
    else
      ""
  }

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