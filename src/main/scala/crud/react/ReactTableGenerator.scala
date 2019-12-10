
package via56.slickGenerator.crud.react

import via56.slickGenerator._
import via56.slickGenerator.Table

case class ReactTableGenerator(table: Table, tablesOneToMany: List[Table] = List(), submodulePackageString: String) extends CodeGenerator{

  def generateInputs(columns: List[AbstractColumn], prefix: String = ""): String = {
    val groupTab = (" "*10)
    val tab = (" "*12)

    columns.collect{
      case c: Column  =>
        tab+c.tableHelper(prefix)
      case s: SubClass =>
        generateInputs(s.cols, s.name+".")
    }.mkString(",\n")
  }


  def generate: String = {
    val imports = s"""import React from 'react';
import GTable from '../gforms/GTable'
import ${table.className}Filter from './${table.className}Filter'

//inputs de nested
"""
    val inputs = generateInputs(table.columns)
    val withOptions = table.foreignColumns.length>0

    val fieldMapping = if(inputs.length>0)
      s"""
    fieldMapping = [
${inputs}
    ]
       """
    else ""

    val result =
      s"""$imports

/*
import ${table.className}Form from './components/${table.objName}/${table.className}Form'
import ${table.className}List from './components/${table.objName}/${table.className}List'
import ${table.className}Table from './components/${table.objName}/${table.className}Table'

<Route path="/${table.objName}/" component={${table.className}List} />
<Route path="/${table.objName}/" component={${table.className}Table} />
<Route path="/${table.objName}/new" component={${table.className}Form} />
<Route path="/${table.objName}/:id" component={${table.className}Form} />
<Route path="/${table.objName}/:page/:pageLength" component={${table.className}List} />
<Route path="/${table.objName}/:page/:pageLength/:query" component={${table.className}List} />
<Route path="/${table.objName}/:page/:pageLength" component={${table.className}Table} />
<Route path="/${table.objName}/:page/:pageLength/:query" component={${table.className}Table} />
<Route path="/${table.objName}/:page/:pageLength/:sortColumn/:sortOrder" component={${table.className}Table} />
<Route path="/${table.objName}/:page/:pageLength/:sortColumn/:sortOrder/:query" component={${table.className}Table} />
*/
export default class ${table.className}Table extends GTable{
    showUrl =  '/${table.tableName}/'
    apiGetUrl =  '/${table.tableName}/'
    apiCreateUrl = '/${table.tableName}/save'
    apiDeleteUrl = '/${table.tableName}/delete/'
    ${if(withOptions) s"""apiOptionsUrl = '/${table.tableName}/options'""" else ""}
    listUrl = '/${table.tableName}/'

    objsStr = '${table.label}s'


    ${fieldMapping}

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
