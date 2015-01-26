package via56.slickGenerator.crud.views

import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass
import via56.slickGenerator.Table

case class ShowGenerator(table: Table) extends CodeGenerator{
  def generateCols(columns: List[AbstractColumn], obj: String): String = {
    val groupTab = (" "*10)
    val tab = (" "*12)
    columns.map{ col => col match{
      case c: Column =>
"""            <div class="form-group">
                <label class="col-sm-2 control-label">"""+c.name.capitalize+"""</label>
                <div class="col-sm-10">
                    <p class="form-control-static">@"""+obj+"""."""+c.name+"""</p>
                </div>
            </div>"""
      case s: SubClass => generateCols(s.cols, obj+"."+s.name)
    }}.mkString("\n")
  }


  def generate: String = {
    val cols = generateCols(table.columns, table.objName)


    val html = """@("""+table.objName+""": models."""+table.className+""")(implicit lang: Lang, flash: Flash, session: Session, context: ApplicationContext)

@views.html."""+table.viewsPackage+""".main(Html(Messages("home.title")))(sidebar(1)) {
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">@Messages(""""+table.objName+""".show.title")</h3>
    </div>
    <div class="panel-body">
"""+cols+"""
        @"""+table.objName+""".id.map{ id =>
            <a href="@routes."""+table.className+"""Controller.edit(id)">@Messages("edit")</a>
        }
        | <a href="@routes."""+table.className+"""Controller.index(1,20)">@Messages("list")</a>
    </div>
</div>
}"""

    html
  }
}
