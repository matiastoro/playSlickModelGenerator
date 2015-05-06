package via56.slickGenerator

import scala.collection.immutable.ListMap
import via56.slickGenerator.crud.controller.ControllerGenerator

object CrudGenerator extends CodeGenerator{
  def generate(table: Table) {
    println("generated:" /*+ ControllerGenerator(table).generate*/)
  }
}
