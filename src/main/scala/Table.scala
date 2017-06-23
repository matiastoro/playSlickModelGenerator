package via56.slickGenerator

import via56.slickGenerator.DisplayType.DisplayType

import scala.collection.immutable.ListMap

case class Table(yamlName: String, args: ListMap[String, Any]) extends CodeGenerator{
  val tableName = underscoreToCamel(yamlName)
  val className = tableName.capitalize

  val attributes = args.getOrElse("_attributes", ListMap[String, Any]())
  val tableNameDB: String = attributes match{
    case l: ListMap[String, Any] =>
      l.getOrElse("table_name", yamlName) match{
        case s:String =>  s
        case _ => yamlName
      }
    case _ => yamlName
  }

  println("TableNameDB; "+tableName)
  val mappingName = className+"Mapeo"
  val queryName = className+"Consulta"
  val objName = tableName
  val viewsPackage = tableName

  type columnProps = ListMap[String, String]
  type subClass = ListMap[String, ListMap[String, String]]



  def getDefault(ps: ListMap[String,String]): Option[String] = {
    val res = ps.getOrElse("default", None) match {
      case s:String => s.replaceAll("""(^\"|\"$)""", "")
      case _ => null
    }

    Option(res/*ps.getOrElse("default", null)*/)
  }

  def isOptional(ps: ListMap[String,String]): Boolean = {
    ps.getOrElse("required", "true") != "true"
  }

  def getColumns(columnsMap: ListMap[String, Any]): List[AbstractColumn] = {
    columnsMap.map{
      case (col, props) =>

        if(col=="id"){
          Column("id", "id", "Long", true)
        } else {
          if(isSubClass(props)){
            props match{
              case ps: subClass =>
                //println("SubClass: "+ps)
                SubClass(underscoreToCamel(col), getColumns(ps))
              case _ => throw new Exception("parsing error")
            }
          }
          else {
            var optional = false
            var fk: Option[ForeignKey] = None
            var synth = false
            (props match{
              case ps: columnProps =>
                optional = isOptional(ps)
                fk = getForeignKey(ps)
                getScalaType(col, ps,false) match{
                  case Some(tpe) => Column(underscoreToCamel(col), getRawName(col, ps), tpe, optional, fk, false, getDisplayType(ps), getDefault(ps))
                  case _ => OneToMany(underscoreToCamel(ps.getOrElse("foreignTable", "ERROR")))
                }
              case _ if col != "created_at" && col != "updated_at" => Column(underscoreToCamel(col), col, "Long", false, None, false)
              case _ => Column(underscoreToCamel(col), col, "DateTime", true, None, true, DisplayType.Hidden) //createdAt: ~, updatedAt: ~
            })

          }
        }
      case _ => throw new Exception("Parsing error")
    }.toList
  }

  def getDisplayType(ps: ListMap[String,String]) = {
    val stringType = ps.getOrElse("display", "None").capitalize
    stringType match{
      case "Hidden" => DisplayType.Hidden
      case "None" => DisplayType.None
      case _ => DisplayType.None
    }

  }

  def selectCol = columns.filter(_.name == objName).headOption.getOrElse{
    columns.filter(c => c.name == "name" || c.name == "nombre").headOption.getOrElse{
      columns.collect{case c: Column if c.tpe=="String" => c}.headOption.getOrElse{
        columns.head
      }
    }
  }.name

  def getRawName(col: String, ps: ListMap[String,String]): String = {
    ps.getOrElse("rawName", col)
  }

  def getForeignKey(ps: ListMap[String, String]): Option[ForeignKey] = {
    for{
      foreignTable <- ps.get("foreignTable")
      foreignReference <- ps.get("foreignReference")
    } yield ForeignKey(foreignTable, foreignReference, onDelete = ps.get("onDelete"))
  }

  val columns: List[AbstractColumn] = getColumns(args.filterNot(pair => pair._1 == "_attributes")) //omit _attributes, its not a column


  val createdAt = columns.exists({
    case c: Column => c.name == "createdAt" && c.synthetic
    case _ => false
  })
  val updatedAt = columns.exists({
    case c: Column => c.name == "updatedAt" && c.synthetic
    case _ => false
  })

  def getScalaType(col: String, ps: ListMap[String,String], withOption: Boolean = true): Option[String] = {
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
    else if("""double.*""".r.findFirstIn(s).isDefined)
      "Double"
    else if("""timestamp.*""".r.findFirstIn(s).isDefined)
      "DateTime"
    else if("""date.*""".r.findFirstIn(s).isDefined)
      "LocalDate"
    else if("""oneToMany.*""".r.findFirstIn(s).isDefined)
      "OneToMany"
    else
      throw new Exception("No encontre el tipo '"+s+"' para '"+col+"'"))

    if(tpe == "OneToMany") None
    else Some({
      if(withOption && isOptional(ps))
        "Option["+tpe+"]"
      else tpe
    })

  }

  def getForeignKeys(cols: List[AbstractColumn]): List[ForeignKey] = {
    val list = cols.map{
      case c: Column => c.foreignKey.map{ fk =>
        List(fk)
      }.getOrElse(List())
      case s: SubClass => getForeignKeys(s.cols)
      case _ => List()
    }
    list.flatten
  }
  lazy val foreignKeys = getForeignKeys(columns)

  def getForeignColumns(cols: List[AbstractColumn]): List[Column] = {
    val list = cols.map{
      case c: Column => c.foreignKey.map{ fk =>
        List(c)
      }.getOrElse(List())
      case s: SubClass => getForeignColumns(s.cols)
      case _ => List()
    }
    list.flatten
  }
  lazy val foreignColumns = getForeignColumns(columns)

  def getOneToManies(cols: List[AbstractColumn]): List[OneToMany] = {
    cols.map{
      case c: OneToMany => List(c)
      case s: SubClass => getOneToManies(s.cols)
      case _ => List()
    }.flatten
  }
  lazy val oneToManies = getOneToManies(columns)
  lazy val hasOneToMany = oneToManies.size>0

}

abstract class AbstractColumn(val name: String) extends CodeGenerator

case class SubClass(override val name: String, cols: List[AbstractColumn]) extends AbstractColumn(name){
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
    "Double" -> "of(doubleFormat)",
    "DateTime" -> "jodaDate",
    "Date" -> "jodaDate"
  )
}
import GeneratorMappings._

case class OneToMany(foreignTable: String) extends AbstractColumn(foreignTable){
  val className = underscoreToCamel(foreignTable).capitalize
  val objName = underscoreToCamel(foreignTable)
  val lstName = objName+"s"
  val queryName = className+"Consulta"
  def formHelper(submodulePackageString: String = "", ref: Option[Table] = None) ={
    ref.map{table =>
      s"""            <GNestedForms ref={(i) => this._inputs["${objName}s"] = i} description="${className}s" prefix="${className}s" readOnly={readOnly} objs={obj.${className}s} renderNested={(nobj, k, refFunc) => <${className}FormInline i={k} obj={Object.assign({${table.objName}Id: obj.id},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["${table.objName}Id"]} />}/>"""
    }.getOrElse{

    """          <div id=""""+objName+"""sDiv_@frm(""""+objName+"""s").id">
              <h2>@Messages(""""+objName+""".list")</h2>
              @repeat(frm(""""+foreignTable+"""s"), min = 0) { field =>
                  @controllers"""+submodulePackageString+"""."""+className+"""Controller.nestedForm(field)
              }
          </div>
          <input type="hidden" id="n"""+objName+"""s_@frm(""""+objName+"""s").id" value="@frm(""""+objName+"""s").indexes.size" />
          <div class="form-group">
              <a id="addNested"""+objName+"""_@frm(""""+objName+"""s").id" href="javascript:;" onclick="addNested"""+className+"""_@{frm(""""+objName+"""s").id}()"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> @Messages(""""+objName+""".related.add")</a>
              <span id="loadingNested_"""+objName+"""@frm(""""+objName+"""s").id" style="display:none;">Loading...</span>
          </div>

          <script>
              function addNested"""+className+"""_@{frm(""""+objName+"""s").id}() {
                    $('#addNested"""+objName+"""_@frm(""""+objName+"""s").id').hide(); $('#loadingNested_"""+objName+"""@frm(""""+objName+"""s").id').show();
                    var i = parseInt($('#n"""+objName+"""s_@frm(""""+objName+"""s").id').val());
                    $.ajax({
                      url: "@controllers"""+submodulePackageString+""".routes."""+className+"""Controller.createNested()",
                      data: { i: i, name: '@frm(""""+objName+"""s").id' }
                    }).done(function(msg) {
                        $('#"""+objName+"""sDiv_@frm(""""+objName+"""s").id').append(msg)
                        $('#n"""+objName+"""s_@frm(""""+objName+"""s").id' ).val(i+1);
                        $('#addNested"""+objName+"""_@frm(""""+objName+"""s").id').show(); $('#loadingNested_"""+objName+"""@frm(""""+objName+"""s").id').hide();
                    });
              }
          </script>"""
    }
  }

}

object DisplayType extends Enumeration {
  type DisplayType = Value
  val None, Hidden = Value
}

object Columns{
  val defaultValues = Map[String, String](
   "Long" -> "0",
   "String" -> "",
   "Int" -> "0",
   "Boolean" -> "false",
   "Double" -> "0",
   "DateTime" -> "",
   "Date" -> ""
  )
}

case class Column(override val name: String, rawName: String, tpe: String, optional: Boolean, foreignKey: Option[ForeignKey] = None, synthetic: Boolean = false, display: DisplayType = DisplayType.None, defaultString: Option[String] = None) extends AbstractColumn(name){
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
    case "Double" => inputDefault(prefix)
    case "DateTime" => """@inputDate(frm(""""+prefix+name+""""), '_label -> """"+name.capitalize+"""")"""
    case "Date" => """@inputDate(frm(""""+prefix+name+""""), '_label -> """"+name.capitalize+"""")"""
    case _ => inputDefault(prefix)
  }

  def showHelper = tpe match {
    case "Long" if foreignKey.isDefined => {
      val fk = foreignKey.get
      println("FK", foreignKey.get)
      s"""{obj.${name} && this.state.indexedOptions?this.state.indexedOptions.${fk.table}s[obj.${name}].${fk.table}:null}"""
    }
    case _ => s"""{obj.${name}}"""
  }

  val ref = (name: String) => s"""ref={(input) => this._inputs["${name}"] = input}"""

  def inputDefaultReact(prefix: String) = {
    val inputName = prefix+name
    s"""<TextField ${ref(inputName)}  name="${inputName}" defaultValue={obj.${inputName} || ""} floatingLabelText="${name.capitalize}" readOnly={readOnly} required={${!optional}} errors={errors && errors.${inputName}}/>"""
  }

  def inputReact(control: String, prefix: String, extra: Option[String] = None) = {
    val inputName = prefix + name
    s"""<${control} ${ref(inputName)}  name="${inputName}" defaultValue={obj.${inputName} || ""} floatingLabelText="${name.capitalize}" readOnly={readOnly} required={${!optional}} errors={errors && errors.${inputName}} ${extra.getOrElse("")}/>"""
  }

  def inputDateReact(control: String, prefix: String, extra: Option[String] = None) = {
    val inputName = prefix + name
    s"""<${control} ${ref(inputName)}  name="${inputName}" defaultValue={obj.${inputName}} floatingLabelText="${name.capitalize}" readOnly={readOnly} required={${!optional}} errors={errors && errors.${inputName}} ${extra.getOrElse("")}/>"""
  }



  def formHelperReact(prefix: String = "", hidden: Boolean = false): String = {
    if(hidden) s"""<HiddenField ${ref(prefix+name)} name="${prefix+name}" defaultValue={obj.${prefix+name} || ""} readOnly={readOnly} />"""
    else{
      tpe match {
        case "Long" if foreignKey.isDefined => foreignKeyInputReact(prefix, foreignKey)
        case "String" => inputDefaultReact(prefix)
        case "Int" => inputDefaultReact(prefix)
        case "Long" => inputDefaultReact(prefix)
        case "Boolean" => inputReact("Checkbox", prefix, Some("singlecontrol"))
        case "Double" => inputDefaultReact(prefix)
        case "DateTime" => inputDateReact("DateTime", prefix)
        case "Date" => inputDateReact("DatePicker", prefix)
        case "LocalDate" => inputDateReact("DatePicker", prefix)
        case _ => inputDefaultReact(prefix)
      }
    }
  }

  val defaultValue = {
    val d = if(defaultString.isDefined) defaultString.get else Columns.defaultValues.getOrElse(tpe, "0")
    tpe match {
      case "Long" => d
      case "String" => "\""+d+"\""
      case "Int" => d
      case "Boolean" => d
      case "Double" => d
      case "DateTime" => "new DateTime("+d+")"
      case "Date" => "new Date("+d+")"
      case _ => d
    }
  }

  val default: Option[String] = if(!optional || defaultString.isDefined) Some(defaultValue) else None

  def foreignKeyInput(prefix: String, foreignKey: Option[ForeignKey]) = {
    foreignKey.map{ fk =>
      val options = fk.table+".map(o => o.id.getOrElse(\"0\").toString -> o.selectString)"
      """@select(frm(""""+prefix+name+""""),"""+options+""", '_label -> """"+name.capitalize+"""")"""
    }.getOrElse(inputDefault(prefix))
  }

  def foreignKeyInputReact(prefix: String, foreignKey: Option[ForeignKey]) = {
    foreignKey.map{ fk =>
      //val options = fk.table+".map(o => o.id.getOrElse(\"0\").toString -> o.selectString)"
      val inputName = prefix+name
      println("a ver cual", fk)
      val select = s"""<SelectField ${ref(inputName)}  name="${inputName}" defaultValue={obj.${inputName} || ""} options={this.state.options.${fk.table}s.map(o => {return {"value": o.id, "label": o.${fk.toStringName}}})} floatingLabelText="${name.capitalize}" readOnly={readOnly} required={${!optional}} errors={errors && errors.${inputName}} />"""
      s"""{hide.includes("${prefix+name}")?${formHelperReact(prefix, hidden = true)}:${select}}"""
    }.getOrElse(inputDefault(prefix))
  }

}
case class ForeignKey(table: String, reference: String, onDelete: Option[String]) extends CodeGenerator{
  val className = underscoreToCamel(table).capitalize
  val queryName = className+"Consulta"
  val toStringName = underscoreToCamel(table)
}
case class ColumnType(tpe: String)
