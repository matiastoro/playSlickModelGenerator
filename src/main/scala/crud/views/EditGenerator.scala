package via56.slickGenerator.crud.views

import via56.slickGenerator._
import via56.slickGenerator.Table

case class EditGenerator(table: Table) extends CodeGenerator{

  def generate: String = {
    val params = table.foreignColumns.map{fk => ", "+fk.table}.mkString("")
    val args = table.foreignColumns.map{fk => ", "+fk.table+": List[models."+fk.className+"]"}.mkString("")
    val html = """@(frm: Form[models."""+table.className+"""]"""+args+""", """+table.objName+""": models."""+table.className+""")(implicit flash: Flash, lang: Lang, session: Session, context: ApplicationContext)
@import helper._
@views.html."""+table.viewsPackage+""".main(Html(Messages("home.title")))(sidebar(1)) {
    <div style="width:100%">
        @form(routes."""+table.className+"""Controller.update("""+table.objName+""".id.getOrElse(0)), 'id -> """"+table.objName+"""Form", 'role -> "form") {
            @views.html."""+table.viewsPackage+"""._form(frm"""+params+""", Some("""+table.objName+"""))
        }

    </div>
}
    """

    html
  }
}
