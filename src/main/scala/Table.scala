package via56.slickGenerator
import scala.collection.immutable.ListMap

case class Table(tableName: String, args: ListMap[String, Any]) extends CodeGenerator{
  val className = underscoreToCamel(tableName).capitalize
  val mappingName = className+"Mapping"
  val queryName = className+"Query"
  val objName = underscoreToCamel(tableName)
  val viewsPackage = underscoreToCamel(tableName)

  type columnProps = ListMap[String, String]
  type subClass = ListMap[String, ListMap[String, String]]


  def isOptional(ps: ListMap[String,String]): Boolean = {
    ps.getOrElse("required", "true") != "true"
  }

  def getColumns(columnsMap: ListMap[String, Any]): List[AbstractColumn] = {
    columnsMap.map{
      case (col, props) =>

        if(col=="id"){
          "Option[Long]"
          Column("id", "id", "Long", true)
        } else {


          if(isSubClass(props)){
            props match{
              case ps: subClass =>
                println(ps)
                SubClass(underscoreToCamel(col), getColumns(ps))
              case _ => throw new Exception("parsing error")
            }
          } else {
            var optional = false
            var fk: Option[ForeignKey] = None
            var synth = false
            val tpe = (props match{
              case ps: columnProps =>
                optional = isOptional(ps)
                fk = getForeignKey(ps)
                getScalaType(col, ps,false)
              case _ if col != "created_at" && col != "updated_at" => "Long"
              case _ =>
                optional = true
                synth = true
                "DateTime" //createdAt: ~, updatedAt: ~
            })
            Column(underscoreToCamel(col), col, tpe, optional, fk, synth)
          }
        }
      case _ => throw new Exception("Parsing error")
    }.toList
  }
  def getForeignKey(ps: ListMap[String, String]): Option[ForeignKey] = {
    for{
      foreignTable <- ps.get("foreignTable")
      foreignReference <- ps.get("foreignReference")
    } yield ForeignKey(foreignTable, foreignReference, onDelete = ps.get("onDelete"))
  }

  val columns: List[AbstractColumn] = getColumns(args)


  val createdAt = columns.exists({
    case c: Column => c.name == "createdAt" && c.synthetic
    case _ => false
  })
  val updatedAt = columns.exists({
    case c: Column => c.name == "updatedAt" && c.synthetic
    case _ => false
  })

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
      "DateTime"
    else if("""date.*""".r.findFirstIn(s).isDefined)
      "LocalDate"

    else
      throw new Exception("No encontre el tipo '"+s+"' para '"+col+"'"))

    if(withOption && isOptional(ps))
      "Option["+tpe+"]"
    else tpe

  }

  def getForeignColumns(cols: List[AbstractColumn]): List[ForeignKey] = {
    val list = cols.map{
      case c: Column => c.foreignKey.map{ fk =>
        List(fk)
      }.getOrElse(List())
      case s: SubClass => getForeignColumns(s.cols)
    }
    list.flatten
  }
  lazy val foreignColumns = getForeignColumns(columns)

}

abstract class AbstractColumn(val name: String)

case class SubClass(override val name: String, cols: List[AbstractColumn]) extends AbstractColumn(name) with CodeGenerator{
  val className = underscoreToCamel(name).capitalize
}

object GeneratorMappings {
  val specialMappings = Map[(String, String), String](
    ("createdAt", "DateTime") -> "jodaDate",
    ("updatedAt", "DateTime") -> "jodaDate"
  )
  val formMappings = Map[String, String](
    "String" -> "text",
    "Int" -> "number",
    "Long" -> "longNumber",
    "Boolean" -> "boolean",
    "DateTime" -> "jodaDate",
    "Date" -> "jodaDate"
  )
}
import GeneratorMappings._
case class Column(override val name: String, rawName: String, tpe: String, optional: Boolean, foreignKey: Option[ForeignKey] = None, synthetic: Boolean = false) extends AbstractColumn(name){
  lazy val tpeWithOption = if(optional) "Option["+tpe+"]" else tpe

  lazy val formMappingTpe = specialMappings.get((name, tpe)).getOrElse(formMappings.getOrElse(tpe, "text"))
  lazy val formMapping: String = if(optional) "optional("+formMappingTpe+")" else formMappingTpe

  lazy val isId: Boolean = name.toLowerCase == "id"

  def inputDefault(prefix: String) = """@myInputText(frm(""""+prefix+name+""""), '_label -> """"+name.capitalize+"""")"""
  /* it was generated with a Map but we are choosing this ways giving that probably every helper is going to receive different parameters*/
  def formHelper(prefix: String = "") = tpe match {
    case "Long" if foreignKey.isDefined => foreignKeyInput(prefix, foreignKey)
    case "String" => inputDefault(prefix)
    case "Int" => inputDefault(prefix)
    case "Long" => inputDefault(prefix)
    case "Boolean" => """@checkbox(frm(""""+prefix+name+""""), '_label -> """"+name.capitalize+"""")"""
    case "DateTime" => """@inputDate(frm(""""+prefix+name+""""), '_label -> """"+name.capitalize+"""")"""
    case "Date" => """@inputDate(frm(""""+prefix+name+""""), '_label -> """"+name.capitalize+"""")"""
    case _ => inputDefault(prefix)
  }


  def foreignKeyInput(prefix: String, foreignKey: Option[ForeignKey]) = {
    foreignKey.map{ fk =>
      val options = fk.table+".map(o => o.id.getOrElse(\"0\").toString -> o.selectString)"
      """@select(frm(""""+prefix+name+""""),"""+options+""", '_label -> """"+name.capitalize+"""")"""
    }.getOrElse(inputDefault(prefix))
  }

}
case class ForeignKey(table: String, reference: String, onDelete: Option[String]) extends CodeGenerator{
  val className = underscoreToCamel(table).capitalize
  val queryName = className+"Query"
}
case class ColumnType(tpe: String)
