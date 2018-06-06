
package via56.slickGenerator.crud.react
import via56.slickGenerator._
import via56.slickGenerator.Table


case class ReactFilterGenerator(table: Table, tablesOneToMany: List[Table] = List(), submodulePackageString: String) extends CodeGenerator{

  def generateInputs(columns: List[AbstractColumn], prefix: String = "")(implicit inline: Boolean = false): List[String] = {
    //println("GENERATING REACT FORM ", table)
    //println("AA",tablesOneToMany)
    //val groupTab = (" "*10)
    //val tab = (" "*12)

    columns.flatMap{ col => col match{
      case c: Column if !c.synthetic && c.display != DisplayType.Hidden =>
        List(if(c.isId) /* The ID is going to be autogenerated */
          c.formHelperReact(prefix, forFilter = true)
        else
          c.formHelperReact(prefix, forFilter = true))
      case s: SubClass => generateInputs(s.cols, s.name+".")//throw new Exception("Subclas")//generateInputs(s.cols, s.name+".")
      case o: OneToMany => List(o.formHelper(submodulePackageString, Some(table)))
      case _ => List("")
    }}
  }
  def generateOneToMany(inputs: String): String ={
    s"""
export class ${table.className}FormInline extends GFormInline{
    apiOptionsUrl = "/${table.tableName}/options"
    renderForm(){
        const obj = this.props.obj;
        const readOnly = this.props.readOnly
        const errors = this.props.errors
        const hide = this.props.hide || []
        const i = this.props.i
        return <div>
${inputs}
        </div>
    }

}
    """
  }

  def generate: String = {


    val oneToManiesImps = table.oneToManies.map{c => {
      s"""import {${c.className}FormInline} from '../${c.name}/${c.className}Form'"""
    }}.mkString("\n")
    val oneToManiesImports = if(table.oneToManies.length>0){
      s"""import {GNestedForms} from '../gforms/GForm';
        |${oneToManiesImps}
      """.stripMargin
    } else {
      ""
    }

    val imports = s"""import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GFilter from '../gforms/GFilter';
${if(tablesOneToMany.length>0) "import {GFormInline} from '../gforms/GForm';" else ""}
${oneToManiesImports}
import { Row, Col } from 'react-flexbox-grid';

//inputs de nested
"""
    val tab = (" "*12)
    val inputs = generateInputs(table.columns).grouped(6).map{xs => {
      val row = xs.map{x => {
        tab+"    <Col xs={6} sm={2}>\n"+
          tab+"        "+x+"\n"+
          tab+"    </Col>"
      }}.mkString("\n")
      tab+"<Row>\n"+row+"\n"+tab+"</Row>"
    }}.mkString("\n")

    val inputsInline = generateInputs(table.columns)(true).mkString("\n")


    val oneToMany = if(tablesOneToMany.length>0) generateOneToMany(inputsInline) else ""
    val withOptions = table.foreignColumns.length>0
    val result =
      s"""$imports



export default class ${table.className}Filter extends GFilter{

    renderFilter(){
        const readOnly = false
        const obj = this.props.query?JSON.parse(this.props.query):{}
        const errors = {}
        const options = this.props.options
        const selectDefault = "-1"
        return <div>
${inputs}
        </div>
    }

}

${oneToMany}
      """
    result

  }
}
