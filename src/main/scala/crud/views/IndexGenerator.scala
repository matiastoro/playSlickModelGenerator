package via56.slickGenerator.crud.views

import scala.collection.immutable.ListMap
import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass
import via56.slickGenerator.Table

case class IndexGenerator(table: Table) extends CodeGenerator{
  def generateColumnsHeader(columns: List[AbstractColumn]): String = {
    columns.collect{ col => col match{
      case c: Column => """                        <th>@Messages(""""+table.objName+"""."""+c.name+"""")</th>"""
      case s: SubClass => generateColumnsHeader(s.cols)
    }}.mkString("\n")
  }

  def generateColumnsValues(obj: String, columns: List[AbstractColumn]): String = {
    columns.collect{ col => col match{
      case c: Column => """                        <td>@"""+obj+"""."""+c.name+"""</td>"""
      case s: SubClass => generateColumnsValues(obj+"."+s.name, s.cols)
    }}.mkString("\n")
  }

  def generate: String = {
    val header = generateColumnsHeader(table.columns)
    val values = generateColumnsValues("obj", table.columns)

    val html = """@(list: List[models."""+table.className+"""], total: Int, page: Int, pageLength: Int)(implicit lang: Lang, flash: Flash, session: Session, context: ApplicationContext)

@views.html."""+table.viewsPackage+""".main(Html(Messages("home.title")))(sidebar(1)) {

    <div class="panel panel-default">
        <div class="panel-heading">
            <a class="btn btn-success pull-right" data-dismiss="modal" href="@routes."""+table.className+"""Controller.create()">@Messages("new")</a>
            <h2 class="panel-title">
                @Messages(""""+table.viewsPackage+""".list.title")
            </h2>
            <div class="clearfix"></div>
        </div>
        <div class="panel-body">
            @{Messages(""""+table.objName+""".list.legend1")+" "+total+" "+Messages(""""+table.objName+""".list.legend2")+" "+((page-1)*20)+" "+Messages(""""+table.objName+""".list.legend3")+" "+(math.min(page*20, total))}
            @paginator(total, page, pageLength, {(x: Int, y: Int) => routes."""+table.className+"""Controller.index(x, y)})
            <table class="table table-striped table-condensed">
                <thead>
                    <tr>
                                                                                                """+header+"""
                        <th>@Messages("Actions")</th>
                    </tr>
                </thead>
                <tbody>
                @for(obj <- list) {
                    <tr class="if(it.state==1){warning}else{}">
                                                                                                           """+values+"""
                        <td>
                            <a href="@routes."""+table.className+"""Controller.show(obj.id.get)">@Messages("show")</a>
                            <a href="@routes."""+table.className+"""Controller.edit(obj.id.get)">@Messages("edit")</a>
                            <a href="javascript:;" onclick="if(confirm('are you sure?')) window.location = '@routes."""+table.className+"""Controller.delete(obj.id.get)';">@Messages("delete")</a>
                        </td>
                    </tr>
                }
                </tbody>
            </table>
            @paginator(total, page, pageLength, {(x: Int, y: Int) => routes."""+table.className+"""Controller.index(x, y)})

        </div>
    </div>
}
    """

    html
  }
}
