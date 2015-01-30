package via56.slickGenerator.crud.views

import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass
import via56.slickGenerator.Table

case class ShowGenerator(table: Table, tablesOneToMany: List[Table] = List()) extends ShowCodeGenerator{

  def generate: String = {
    val cols = generateCols(table.columns, table.objName)


    val html = """@("""+table.objName+""": models."""+table.className+""")(implicit lang: Lang, flash: Flash, session: Session, context: ApplicationContext)

@views.html."""+table.viewsPackage+""".main(Html(Messages("home.title")))(sidebar(1)) {

        <h1>@Messages(""""+table.objName+""".show.title")</h3>

                                         """+cols+"""
        @"""+table.objName+""".id.map{ id =>
            <a href="@routes."""+table.className+
      """Controller.edit(id)">@Messages("edit")</a>
         | <a href="javascript:;" onclick="if(confirm('are you sure?')) window.location = '@routes."""+table.className+"""Controller.delete(id)';">@Messages("delete")</a>
        }
        | <a href="@routes."""+table.className+"""Controller.index(1,20)">@Messages("list")</a>

}"""

    html
  }
}
