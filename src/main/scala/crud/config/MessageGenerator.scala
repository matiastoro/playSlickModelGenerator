package via56.slickGenerator.crud.config

import via56.slickGenerator._

/**
 * Created by daniel on 05-02-15.
 */
case class MessageGenerator(table: Table, tablesOneToMany: List[Table] = List(), submodulePackageString: String) extends CodeGenerator {

  def generateColumnsHeader(columns: List[AbstractColumn]): String = {
    columns.collect{ col => col match{
      case c: Column => table.objName+"""."""+c.name+""" = """+c.name
      case s: SubClass => generateColumnsHeader(s.cols)
    }}.mkString("\n")
  }

  val nested = tablesOneToMany.foldRight("")((value, acum) => acum + table.viewsPackage +".related.list = Related "+table.className)
  def generate : String =
"""
#""".stripMargin+table.className+ """
"""+table.viewsPackage+""".title = """ + table.className+""" List
"""+table.viewsPackage+""".list.title = """+ table.className+ """ List
""" + table.viewsPackage + """.form.title = """ + table.className + """ Form
"""+generateColumnsHeader(table.columns)+"""
"""+nested+"""
"""+table.viewsPackage+""".show.title = Show """+table.className+"""
"""

//TODO: ZaepStep.related.add, ZaepStep.list
}
