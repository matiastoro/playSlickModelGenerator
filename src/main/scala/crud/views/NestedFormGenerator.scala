package crud.views

import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass
import via56.slickGenerator.Table

case class NestedFormGenerator(table: Table, tablesOneToMany: List[Table] = List()) extends CodeGenerator{
  def generateInputs(columns: List[AbstractColumn], prefix: String = ""): String = {
    val groupTab = (" "*10)
    val tab = (" "*12)
    columns.map{ col => col match{
      case c: Column if !c.synthetic=>
        val input = if(c.isId) /* The ID is going to be autogenerated */
          tab+hiddenInput(c)
        else{
          c.foreignKey.map{fk =>
            if(tablesOneToMany.exists{t => t.tableName == fk.table})
              tab+hiddenInput(c)
            else formGroup(tab+c.formHelper(prefix), groupTab)
          }.getOrElse(formGroup(tab+c.formHelper(prefix), groupTab))
        }
       input
      case s: SubClass => generateInputs(s.cols, s.name+".")
      case o: OneToMany => o.formHelper

      case _ => ""
    }}.mkString("\n")
  }

  def hiddenInput(c: Column) = """<input type="hidden" id="@frm(""""+c.name+"""").id" name="@frm(""""+c.name+"""").name" value="@frm(""""+c.name+"""").value" />"""
  def formGroup(s: String, groupTab: String) = groupTab+"<div class=\"form-group\">\n"+s+"\n"+groupTab+"</div>"

  def generate: String = {
    val inputs = generateInputs(table.columns)
    val args = table.foreignKeys.map{fk => ", "+fk.table+": List[models."+fk.className+"]"}.mkString("")
    val formClass = if(table.hasOneToMany) table.className+"FormData" else table.className
    val html = """@(frm: play.api.data.Field"""+args+""", """+table.objName+"""Opt: Option[models."""+table.className+"""] = None)(implicit flash: Flash, lang: Lang, session: Session, context: ApplicationContext, request: Request[AnyContent])
@import myHelper._
@import helper._

<div class="panel panel-default" id="nestedParent@frm.id">
    <div class="panel-heading">
        <a class="pull-right glyphicon glyphicon-remove" href="javascript:;" onclick="if(confirm('are you sure?')) $('#nestedParent@frm.id').remove()"></a>
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

    </div>
</div>
    """

    html
  }
}
