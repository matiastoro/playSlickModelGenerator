package via56.slickGenerator.crud.views

import via56.slickGenerator._
import via56.slickGenerator.Column
import via56.slickGenerator.SubClass
import via56.slickGenerator.Table

case class CreateGenerator(table: Table, submodulePackageString: String) extends CodeGenerator{

  def generate: String = {
    val params = table.foreignKeys.map{fk => ", "+fk.table}.mkString("")
    val args = table.foreignKeys.map{fk => ", "+fk.table+": List[models."+fk.className+"]"}.mkString("")
    val formClass = table.className+"FormData"//if(table.hasOneToMany) table.className+"FormData" else table.className
    val html = """@(frm: Form[models."""+formClass+"""]"""+args+""")(implicit flash: Flash, lang: Lang, session: Session, context: controllers.AuthHandler, request: Request[AnyContent])
@import helper._
@views.html"""+submodulePackageString+"""."""+table.viewsPackage+""".main(Html(Messages("home.title")))(sidebar(1)) {
    <div style="width:100%">
        @form(controllers"""+submodulePackageString+""".routes."""+table.className+"""Controller.save(), 'id -> """"+table.objName+"""Form", 'role -> "form") {
            @views.html"""+submodulePackageString+"""."""+table.viewsPackage+"""._form(frm"""+params+""")
        }

    </div>
}
    """

    html
  }
}
