package via56.slickGenerator.crud.views

import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass

trait ShowCodeGenerator extends CodeGenerator{
  val table: Table
  val tablesOneToMany: List[Table]



  def generateCols(columns: List[AbstractColumn], obj: String): String = {
    val groupTab = (" "*10)
    val tab = (" "*12)
    columns.map{ col => col match{
      case c: Column =>
        val default = """            <div class="form-group">
                <label class="col-sm-2 control-label">"""+c.name.capitalize+"""</label>
                <div class="col-sm-10">
                    <p class="form-control-static">@"""+obj+"""."""+c.name+"""</p>
                </div>
            </div>"""

        c.foreignKey.map{fk =>
          if(tablesOneToMany.exists{t => t.tableName == fk.table})
            ""
          else default
        }.getOrElse(default)
      case s: SubClass => generateCols(s.cols, obj+"."+s.name)
      case o: OneToMany => tab+"""@controllers."""+o.className+"""Controller.showBy"""+table.className+"""("""+obj+""".id)"""
    }}.mkString("\n")
  }
}
