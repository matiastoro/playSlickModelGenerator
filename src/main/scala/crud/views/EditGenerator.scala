package via56.slickGenerator.crud.views

import via56.slickGenerator._
import via56.slickGenerator.Table

case class EditGenerator(table: Table, submodulePackageString: String) extends CodeGenerator{

  def generate: String = {
    val params = table.foreignKeys.map{fk => ", "+fk.table}.mkString("")
    val args = table.foreignKeys.map{fk => ", "+fk.table+": List[models."+fk.className+"]"}.mkString("")
    val formClass = table.className+"FormData"//if(table.hasOneToMany) table.className+"FormData" else table.className
    val html = """@(frm: Form[models."""+formClass+"""]"""+args+""", """+table.objName+""": models."""+table.className+""")(implicit flash: Flash, lang: Lang, session: Session, context: controllers"""+submodulePackageString+""".ApplicationContext, request: Request[AnyContent])
@import helper._
@views.html"""+submodulePackageString+"""."""+table.viewsPackage+""".main(Html(Messages("home.title")))(sidebar(1)) {
    <div style="width:100%">
        @form(controllers"""+submodulePackageString+""".routes."""+table.className+"""Controller.update("""+table.objName+""".id.getOrElse(0)), 'id -> """"+table.objName+"""Form", 'role -> "form") {
            @views.html"""+submodulePackageString+"""."""+table.viewsPackage+"""._form(frm"""+params+""", Some("""+table.objName+"""))
        }

    </div>
}
    """

    html
  }
}
