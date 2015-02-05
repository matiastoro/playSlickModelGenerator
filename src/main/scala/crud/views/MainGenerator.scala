package via56.slickGenerator.crud.views

import via56.slickGenerator._
import via56.slickGenerator.Table

case class MainGenerator(table: Table, submodulePackageString: String) extends CodeGenerator{

  def generate: String = {

    val html = """@(title: Html, query: String = "")(sidebar: Html)(content: Html)(implicit lang: Lang, flash: Flash, session: Session, context: controllers"""+submodulePackageString+""".ApplicationContext)
@navBar() = {}
@views.html"""+submodulePackageString+""".main(title,List("bootstrap3-typeahead.min.js","itinerary/main.js"),true)(sidebar)(content)(navBar())"""

    html
  }
}
