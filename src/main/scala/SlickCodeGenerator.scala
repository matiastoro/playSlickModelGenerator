package via56.slickGenerator

import _root_.crud.views.NestedFormGenerator
import scala.util.parsing.combinator._
import collection.immutable.ListMap
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets
import via56.slickGenerator.crud.config.MessageGenerator
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
    println("USAGE: run (model|controller|view|config|all) filename [destination_folder] [submodule_name]")
    val buildType = args(0)
    val reader = Source.fromFile(args(1)).getLines.mkString("\n")
    val pathName = if(args.size > 2) args(2) else "output"
    val submoduleName = if(args.size > 3) args(3) else ""
    val path = Paths.get(pathName)
    val pathExtensions  = Paths.get(path+"/app/models/extensions")
    val pathControllers = Paths.get(path+"/app/controllers")
    val pathViews       = Paths.get(path+"/app/views/"+submoduleName)
    YAMLParser.parse(reader).map{result =>
      Files.createDirectories(path)
      Files.createDirectories(pathExtensions)
      Files.createDirectories(pathControllers)
      Files.createDirectories(Paths.get(path + "/conf"))
      Files.createDirectories(pathViews)
      if(buildType == "all" || buildType == "config") {
        Files.write(Paths.get(path + "/conf/messages.raw"), "".getBytes(StandardCharsets.UTF_8))
      }
      result match{
        case tbles : ListMap[String, ListMap[String, Any]] =>
          val tables = tbles.map{ table =>
            println("Table: "+table._1)
            Table(table._1, table._2)
          }.toList

          tables.map{t =>
            writeToFile(pathName, submoduleName, t, tablesOneToMany(t, tables), buildType)
          }
        case _ : List[Any] => println("lala2")
        case _ => println("no")
      }
    }
  }
  def tablesOneToMany(t: Table, tables: List[Table]): List[Table] = {
    val fks = t.columns.collect{ //collect all columns that have foreignKeys
      case c: Column if c.foreignKey.isDefined => c.foreignKey.get
    }

    val tab = for{
      fk <- fks
      table <- tables if table.tableName == fk.table
    } yield table
      //println("LATABLAOEZI: "+t.className+" fks: "+fks+"  tab: "+tab)
    tab.filter(_.columns.exists{
      case o: OneToMany if o.foreignTable == t.className => true
      //case c: Column if c.foreignKey.isDefined => true
      case _ => false
    })

  }

  def writeToFile(path: String, submoduleName: String, table: Table, tablesOneToMany: List[Table], buildType: String) = {
    val submodulePath = if (submoduleName == "") "" else submoduleName + "/"
    val submodulePackageString = if (submoduleName == "") "" else "." + submoduleName

    val fileName = table.className
    val currentContent = getCurrentContent(path + "/models/" + fileName + ".scala")

    val mg = ModelGenerator(table, tablesOneToMany)
    val content = mg.generate
    /*model*/
    if (currentContent.size != content.size && (buildType == "all" || buildType == "model")) {
      println("Building Model("+table.viewsPackage+"): "+path + "/app/models/" + fileName + ".scala")
      Files.write(Paths.get(path + "/app/models/" + fileName + ".scala"), content.getBytes(StandardCharsets.UTF_8))
    }


    /*extension*/
    if(!Files.exists(Paths.get(path+"/app/models/extensions/"+fileName+"Extension.scala")) && (buildType == "all" || buildType == "model")){
      println("Creating Model Extension("+table.viewsPackage+"): "+path+"/app/models/extensions/"+fileName+"Extension.scala")
      Files.write(Paths.get(path+"/app/models/extensions/"+fileName+"Extension.scala"), mg.generateExtension.getBytes(StandardCharsets.UTF_8))
    }

    /*config*/
    if(buildType == "all" || buildType == "config") {
      println("Appending Config("+table.viewsPackage+"):  "+path + "/conf/messages.raw")
      Files.write(Paths.get(path + "/conf/messages.raw"), MessageGenerator(table, tablesOneToMany, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8), java.nio.file.StandardOpenOption.APPEND)
    }


    /*controller*/
    if(buildType == "all" || buildType == "controller") {
      println("Building Controller("+table.viewsPackage+"): "+path + "/app/controllers/" + fileName + "Controller.scala")
      Files.write(Paths.get(path + "/app/controllers/" + fileName + "Controller.scala"), ControllerGenerator(table, tablesOneToMany, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
    }

    /*views*/
    if(buildType == "all" || buildType == "view") {
      println("Building Views("+table.viewsPackage+"): "+path + "/app/views/*" + submodulePath + table.viewsPackage + "/")
      Files.createDirectories(Paths.get(path + "/app/views/" + submodulePath + table.viewsPackage))
      Files.write(Paths.get(path + "/app/views/" + submodulePath + table.viewsPackage + "/_form.scala.html"), FormGenerator(table, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
      Files.write(Paths.get(path + "/app/views/" + submodulePath + table.viewsPackage + "/create.scala.html"), CreateGenerator(table, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
      Files.write(Paths.get(path + "/app/views/" + submodulePath + table.viewsPackage + "/edit.scala.html"), EditGenerator(table, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
      Files.write(Paths.get(path + "/app/views/" + submodulePath + table.viewsPackage + "/index.scala.html"), IndexGenerator(table, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
      Files.write(Paths.get(path + "/app/views/" + submodulePath + table.viewsPackage + "/main.scala.html"), MainGenerator(table, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
      Files.write(Paths.get(path + "/app/views/" + submodulePath + table.viewsPackage + "/show.scala.html"), ShowGenerator(table, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
      Files.write(Paths.get(path + "/app/views/" + submodulePath + table.viewsPackage + "/sidebar.scala.html"), SidebarGenerator(table, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))

      if (tablesOneToMany.size > 0) {
        Files.write(Paths.get(path + "/app/views/" + submodulePath + table.viewsPackage + "/_nestedForm.scala.html"), NestedFormGenerator(table, tablesOneToMany, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
        Files.write(Paths.get(path + "/app/views/" + submodulePath + table.viewsPackage + "/_nestedShow.scala.html"), NestedShowGenerator(table, tablesOneToMany, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
      }
    }
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