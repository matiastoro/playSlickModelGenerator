package via56.slickGenerator.crud.views

import via56.slickGenerator._
import via56.slickGenerator.Table

case class SidebarGenerator(table: Table, submodulePackageString: String = "") extends CodeGenerator{

  def generate: String = {

    val html = """@(activePosition: Int = 1)(implicit lang: Lang, flash: Flash, session: Session, context: controllers.AuthHandler)

<div class="col-md-3" id="sidebar" role="navigation">
    <ul class="sidebar nav nav-sidebar">
        <li @if(activePosition == 1){class="active"}><a href="@controllers"""+submodulePackageString+""".routes."""+table.className+"""Controller.index(1,20)">@Messages("list")</a></li>
    </ul>
</div>"""

    html
  }
}
