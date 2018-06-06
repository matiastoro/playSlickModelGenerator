package via56.slickGenerator

import _root_.crud.views.NestedFormGenerator

import scala.util.parsing.combinator._
import collection.immutable.ListMap
import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets

import via56.slickGenerator.crud.SqlGenerator
import via56.slickGenerator.crud.config.MessageGenerator
import via56.slickGenerator.crud.controller.ControllerGenerator
import via56.slickGenerator.crud.views._
import via56.slickGenerator.crud.react._


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
    //println(parseAll(column, reader))
  }
}*/

import scala.io.Source
object parser {
  def main(args: Array[String]){
    //println("USAGE: run (model|controller|view|config|all) filename [destination_folder] [submodule_name]")

    val buildType = args(0)
    val reader = Source.fromFile(args(1)).getLines.mkString("\n")
    val pathName = if(args.size > 2) args(2) else "output"
    val submoduleName = if(args.size > 3) args(3) else ""
    implicit val lang: String = if(args.size > 4) args(4) else "en"
    implicit val langHash: Map[String, String] = Map(
      "Query" -> {if(lang=="es") "Consulta" else "Query"},
      "allQuery" -> {if(lang=="es") "todosConsulta" else "allQuery"},
      "getAll" -> {if(lang=="es") "todos" else "getAll"},
      "byId" -> {if(lang=="es") "porId" else "byId"},
      "delete" -> {if(lang=="es") "eliminar" else "delete"},
      "Mapping" -> {if(lang=="es") "Mapeo" else "Table"},
      "updateOrInsert" -> {if(lang=="es") "actualizarOInsertar" else "updateOrInsert"},
      "insert" -> {if(lang=="es") "insertar" else "insert"}

    )

    val path = Paths.get(pathName)
    val pathExtensions  = Paths.get(path+"/app/models/extensions")
    val pathControllers = Paths.get(path+"/app/controllers")
    //val pathViews       = Paths.get(path+"/app/views/"+submoduleName)
    val pathReact       = Paths.get(path+"/react/"+submoduleName)
    YAMLParser.parse(reader).map{result =>
      Files.createDirectories(path)
      Files.createDirectories(pathExtensions)
      Files.createDirectories(pathControllers)
      Files.createDirectories(Paths.get(path + "/conf"))
      //Files.createDirectories(pathViews)
      Files.createDirectories(pathReact)

      if(buildType == "all" || buildType == "config") {
        Files.write(Paths.get(path + "/conf/messages.raw"), "".getBytes(StandardCharsets.UTF_8))
      }
      result match{
        case tbles : ListMap[String, ListMap[String, Any]] =>
          val tables = tbles.map{ table =>
            //println("Table: "+table._1)
            Table(table._1, table._2)
          }.toList

          //println("TABLES ARE ", tables.map{_.className})
          tables.map{t =>
            writeToFile(pathName, submoduleName, t, tablesOneToMany(t, tables), buildType, tables)
          }

          writeSql(tables, pathName, submoduleName, buildType)

        case _ : List[Any] => //println("lala2")
        case _ => //println("no")
      }
    }
  }
  def tablesOneToMany(t: Table, tables: List[Table]): List[Table] = {
    //println("calculating tablesOneToMay of ", t.yamlName, t.className)
    val fks = t.columns.collect{ //collect all columns that have foreignKeys
      case c: Column if c.foreignKey.isDefined => c.foreignKey.get
    }
    //println("la tabla",t)
    //println("fks", fks)

    val tab = for{
      fk <- fks
      table <- tables if table.yamlName == fk.table
    } yield table
    //println("tablenames", tables.map{_.yamlName}, fks.map{_.table})
    //println("LATABLAOEZI: "+t.className+" fks: "+fks+"  tab: "+tab)
    //println("BOMBINBOMBOM", tab.map(_.columns))
    val esto = tab.filter(_.columns.exists{
      case o: OneToMany if o.foreignTable.capitalize == t.className => true
      //case c: Column if c.foreignKey.isDefined => true
      case _ => false
    })

    //println("resultado, ", esto)
    esto

  }



  def writeSql(tables: List[Table], path: String, submoduleName: String, buildType: String)(implicit langHash: Map[String,String]): Unit ={
    /*sql*/
    val generators = tables.map(table => SqlGenerator(table, tablesOneToMany(table, tables)))

    val tableUps = generators.map(g => g.generateTableUps).mkString("\n")
    val tableDowns = generators.map(g => g.generateTableDowns).mkString("\n")

    val indexesUp = generators.map(g => g.generateIndexesUp(g.table.columns)).mkString("\n")
    val indexesDown = generators.map(g => g.generateIndexesDown(g.table.columns)).mkString("\n")
    val constraintsUp = generators.map(g => g.generateConstraintsUp(g.table.columns)).mkString("\n")
    val constraintsDown = generators.map(g => g.generateConstraintsDown(g.table.columns)).mkString("\n")

    val sql = s"""
          |# --- !Ups
         |$tableUps
         |$indexesUp
         |$constraintsUp

         |# --- !Downs
         |$constraintsDown
         |$indexesDown
         |$tableDowns

       """.stripMargin
    if(buildType == "all" || buildType == "sql") {
      //println("Appending SQL:  "+path + "/database.sql")
      Files.write(Paths.get(path + "/database.sql"), sql.getBytes(StandardCharsets.UTF_8))
    }
  }


  def writeToFile(path: String, submoduleName: String, table: Table, tablesOneToMany: List[Table], buildType: String, tables: List[Table])(implicit langHash: Map[String,String]) = {




    val submodulePath = if (submoduleName == "") "" else submoduleName + "/"
    val submodulePackageString = if (submoduleName == "") "" else "." + submoduleName

    val fileName = table.className
    val currentContent = getCurrentContent(path + "/models/" + fileName + ".scala")

    val mg = ModelGenerator(table, tablesOneToMany, tables)
    val content = mg.generate
    /*model*/
    if (currentContent.size != content.size && (buildType == "all" || buildType == "model")) {
      //println("Building Model("+table.viewsPackage+"): "+path + "/app/models/" + fileName + ".scala")
      Files.write(Paths.get(path + "/app/models/" + fileName + ".scala"), content.getBytes(StandardCharsets.UTF_8))
      Files.write(Paths.get(path + "/app/models/" + fileName + "Repository.scala"), mg.generateRepository.getBytes(StandardCharsets.UTF_8))
    }


    /*extension*/
    if(/*!Files.exists(Paths.get(path+"/app/models/extensions/"+fileName+"Extension.scala")) && */(buildType == "all" || buildType == "model")){
      //println("Creating Model Extension("+table.viewsPackage+"): "+path+"/app/models/extensions/"+fileName+"Extension.scala")
      Files.write(Paths.get(path+"/app/models/extensions/"+fileName+"Extension.scala"), mg.generateExtension.getBytes(StandardCharsets.UTF_8))
    }

    /*config*/
    if(buildType == "all" || buildType == "config") {
      //println("Appending Config("+table.viewsPackage+"):  "+path + "/conf/messages.raw")
      Files.write(Paths.get(path + "/conf/messages.raw"), MessageGenerator(table, tablesOneToMany, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8), java.nio.file.StandardOpenOption.APPEND)
    }


    /*controller*/
    if(buildType == "all" || buildType == "controller") {
      //println("Building Controller("+table.viewsPackage+"): "+path + "/app/controllers/" + fileName + "Controller.scala")
      Files.write(Paths.get(path + "/app/controllers/" + fileName + "Controller.scala"), ControllerGenerator(table, tablesOneToMany, submodulePackageString, tables).generate.getBytes(StandardCharsets.UTF_8))
    }

    if(buildType == "all" || buildType == "react"){
      //println("Building React("+table.viewsPackage+"): "+path + "/react/*" + submodulePath + table.viewsPackage + "/")
      val r = Files.createDirectories(Paths.get(path + "/react/" + submodulePath + table.viewsPackage))

      Files.write(Paths.get(path + "/react/" + submodulePath + table.viewsPackage +  s"/${table.className}Form.jsx"), ReactFormGenerator(table, tablesOneToMany, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
      Files.write(Paths.get(path + "/react/" + submodulePath + table.viewsPackage +  s"/${table.className}Filter.jsx"), ReactFilterGenerator(table, tablesOneToMany, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
      Files.write(Paths.get(path + "/react/" + submodulePath + table.viewsPackage +  s"/${table.className}List.jsx"), ReactListGenerator(table, tablesOneToMany, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
      Files.write(Paths.get(path + "/react/" + submodulePath + table.viewsPackage +  s"/${table.className}Table.jsx"), ReactTableGenerator(table, tablesOneToMany, submodulePackageString).generate.getBytes(StandardCharsets.UTF_8))
    }

    /*views DEPRECATED*/
    /**if(buildType == "all" || buildType == "view") {
      //println("Building Views("+table.viewsPackage+"): "+path + "/app/views/" + submodulePath + table.viewsPackage + "/")
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
    }**/
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