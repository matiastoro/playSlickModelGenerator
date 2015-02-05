package via56.slickGenerator.crud.views

import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass
import via56.slickGenerator.Table

case class NestedShowGenerator(table: Table, tablesOneToMany: List[Table] = List(), submodulePackageString: String = "") extends ShowCodeGenerator{

  def generate: String = {
    val cols = generateCols(table.columns, table.objName, submodulePackageString)


    val html = """@("""+table.objName+"""s: List[models."""+table.className+"""])

        <h2>@Messages(""""+table.objName+""".list")</h2>
        @for("""+table.objName+""" <- """+table.objName+"""s){
"""+cols+"""
        }
"""


    html
  }
}
