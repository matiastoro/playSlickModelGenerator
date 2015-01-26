package via56.slickGenerator.crud.views

import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass
import via56.slickGenerator.Table

case class FormGenerator(table: Table) extends CodeGenerator{
  def generateInputs(columns: List[AbstractColumn], prefix: String = ""): String = {
    val groupTab = (" "*10)
    val tab = (" "*12)
    columns.map{ col => col match{
      case c: Column =>
        val input = if(c.isId) /* The ID is going to be autogenerated */
          tab+"""<input type="hidden" id="id" name="id" value="@frm("id").value" />"""
        else
          tab+c.formHelper(prefix)
        groupTab+"<div class=\"form-group\">\n"+input+"\n"+groupTab+"</div>"
      case s: SubClass => generateInputs(s.cols, s.name+".")
    }}.mkString("\n")
  }


  def generate: String = {
    val inputs = generateInputs(table.columns)
    val args = table.foreignColumns.map{fk => ", "+fk.table+": List[models."+fk.className+"]"}.mkString("")

    val html = """@(frm: Form[models."""+table.className+"""]"""+args+""", """+table.objName+"""Opt: Option[models."""+table.className+"""] = None)(implicit flash: Flash, lang: Lang, session: Session, context: ApplicationContext)
@import myHelper._
@import helper._

<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">@Messages(""""+table.objName+""".form.title")</h3>
    </div>
    <div class="panel-body">

        @if(frm.hasErrors){
            <div id=""""+table.objName+"""Errors" class="row alert alert-danger">
            @frm.errors.map{ formError =>
                <ul>
                    <li><strong>@formError.key:</strong> @formError.message</li>
                </ul>
            }
            </div>
        }

"""+inputs+"""



        <div class="modalButtons">
            <button type="button" class="btn btn-default" data-dismiss="modal">@Messages("close")</button>
            @"""+table.objName+"""Opt.map{ """+table.objName+""" =>
                @"""+table.objName+""".id.map{ id =>
                    <button type="button" id="btn-delete" class="btn btn-danger" onclick="loadDelete(@id);">
                    @Messages("delete")</button>
                }
            }
            <input type="submit" id="btn-transmitir" class="btn btn-success" value="@Messages("save")"/>
        </div>

    </div>
</div>
    """

    html
  }
}
