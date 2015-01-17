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
              case _ => "DateTime" //createdAt: ~, updatedAt: ~
            })
            Column(underscoreToCamel(col), col, tpe, optional)
          }
        }
      case _ => throw new Exception("Parsing error")
    }.toList
  }

  val columns: List[AbstractColumn] = getColumns(args)

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
    else if(s == "~" && col != "createdAt" && col != "updatedAt")
      "Long"
    else if(s == "~")
      "DateTime"
    else
      throw new Exception("No encontre el tipo '"+s+"' para '"+col+"'"))

    if(withOption && isOptional(ps))
      "Option["+tpe+"]"
    else tpe

  }

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
    "Boolean" -> "checked",
    "DateTime" -> "jodaDate"
  )
}
import GeneratorMappings._
case class Column(override val name: String, rawName: String, tpe: String, optional: Boolean) extends AbstractColumn(name){
  lazy val tpeWithOption = if(optional) "Option["+tpe+"]" else tpe

  lazy val formMappingTpe = specialMappings.get((name, tpe)).getOrElse(formMappings.getOrElse(tpe, "text"))
  lazy val formMapping: String = if(optional) "optional("+formMappingTpe+")" else formMappingTpe

}

case class ColumnType(tpe: String)
