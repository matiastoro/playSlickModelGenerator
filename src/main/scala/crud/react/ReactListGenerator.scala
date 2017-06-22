
package via56.slickGenerator.crud.react

import via56.slickGenerator._
import via56.slickGenerator.Table

case class ReactListGenerator(table: Table, tablesOneToMany: List[Table] = List(), submodulePackageString: String) extends CodeGenerator{

  def generateInputs(columns: List[AbstractColumn], prefix: String = ""): String = {
    val groupTab = (" "*10)
    val tab = (" "*12)

    columns.collect{
      case c: Column if !c.synthetic && c.display != DisplayType.Hidden && !c.isId =>
        c.showHelper
    }.mkString("\n")
  }


  def generate: String = {
    val imports = s"""import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested
"""
    val inputs = generateInputs(table.columns)
    val withOptions = table.foreignColumns.length>0

    val result =
      s"""$imports

/*
<Route path="/${table.objName}/" component={${table.className}List} />
<Route path="/${table.objName}/new" component={${table.className}Form} />
<Route path="/${table.objName}/:id" component={${table.className}Form} />
<Route path="/${table.objName}/:page/:pageLength" component={${table.className}List} />
*/
export default class ${table.className}Form extends GList{
    showUrl =  '/${table.tableName}/'
    apiGetUrl =  '/${table.tableName}/'
    apiCreateUrl = '/${table.tableName}/save'
    apiDeleteUrl = '/${table.tableName}/delete/'
    ${if(withOptions) s"""apiOptionsUrl = '/${table.tableName}/options'""" else ""}

    objsStr = '${table.className}s'

    renderPrimaryText = (obj) => {
        return obj.${table.selectCol}
    }
    renderSecondaryText = (obj) => {
        return <p>
            ${inputs}
        </p>
    }
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        ${if(withOptions) "const hide = this.props.hide || []" else ""}
        return <div>

        </div>
    }

}
      """
    result

  }
}