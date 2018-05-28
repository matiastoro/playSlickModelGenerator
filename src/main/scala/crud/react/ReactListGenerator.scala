
package via56.slickGenerator.crud.react

import via56.slickGenerator._
import via56.slickGenerator.Table

case class ReactListGenerator(table: Table, tablesOneToMany: List[Table] = List(), submodulePackageString: String) extends CodeGenerator{

  def generateInputs(columns: List[AbstractColumn], prefix: String = ""): String = {
    val groupTab = (" "*10)
    val tab = (" "*12)

    columns.collect{
      case c: Column if !c.synthetic && c.display != DisplayType.Hidden && !c.isId && c.name != table.selectCol =>
        tab+c.showHelper
    }.mkString(" -- \n")
  }


  def generate: String = {
    val imports = s"""import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import ${table.className}Filter from './${table.className}Filter'

//inputs de nested
"""
    val inputs = generateInputs(table.columns)
    val withOptions = table.foreignColumns.length>0

    val secondaryText = if(inputs.length>0)
      s"""
    renderSecondaryText = (obj) => {
        return <p>
${inputs}
        </p>
    }
       """
    else ""

    val result =
      s"""$imports

/*
<Route path="/${table.objName}/" component={${table.className}List} />
<Route path="/${table.objName}/new" component={${table.className}Form} />
<Route path="/${table.objName}/:id" component={${table.className}Form} />
<Route path="/${table.objName}/:page/:pageLength" component={${table.className}List} />
*/
export default class ${table.className}List extends GList{
    showUrl =  '/${table.tableName}/'
    apiGetUrl =  '/${table.tableName}/'
    apiCreateUrl = '/${table.tableName}/save'
    apiDeleteUrl = '/${table.tableName}/delete/'
    ${if(withOptions) s"""apiOptionsUrl = '/${table.tableName}/options'""" else ""}
    listUrl = '/${table.tableName}/'

    objsStr = '${table.label}s'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.${table.selectCol}
    }
    ${secondaryText}

    renderFilter = () => {
        return <div>
            <${table.className}Filter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      """
    result

  }
}
