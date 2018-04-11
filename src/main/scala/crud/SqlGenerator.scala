package via56.slickGenerator.crud

import via56.slickGenerator._

/**
  * Created by jcarrasco on 9/4/2018.
  */
case class SqlGenerator(table: Table, tablesOneToMany: List[Table] = List())(implicit langHash: Map[String,String]) extends CodeGenerator {

  def generate: String = {

    val className = table.className
    val columns: List[AbstractColumn] = table.columns

    val cols = generateColumnsTagTable(columns)
    val constraints = generateConstraints(columns, table.tableNameDB)
    s"""
      CREATE TABLE ${table.tableNameDB} (
        $cols,

      );
    """.stripMargin

  }


  def generateConstraints(columns: List[AbstractColumn], tableName: String): String = {
    columns.collect{
      case c: Column => {
        if (c.name == "id") {
          s"CONSTRAINT ${tableName}_pkey PRIMARY KEY (id)"
        } else {


          c.foreignKey.map { fk =>
            s"""CONSTRAINT itinerary_company_fk FOREIGN KEY (company_id)
            REFERENCES compania_aerea (id) MATCH SIMPLE
            ON UPDATE NO ACTION ON DELETE NO ACTION"""
            //val onDelete = fk.onDelete.map { od => ", onDelete=ForeignKeyAction." + od.capitalize }.getOrElse("")
            //colMap + "\n  def " + guessFkName(c.name) + " = foreignKey(\"" + c.rawName + "_fk\", " + c.name + ", " + underscoreToCamel(fk.table).capitalize + s"${langHash("Query")}.tableQ)(_." + fk.reference + onDelete + ")"
          }
          //colMap

        }
      }
      /*case s: SubClass =>
        hasSubClasses = true
        "\n"+generateColumnsTagTable(s.cols)+"\n  val "+s.name+"Cols = "+generateStars(s.cols)+"\n"*/

    }.mkString(",\n")
  }


  def generateColumnsTagTable(columns: List[AbstractColumn]): String = {
    columns.collect{
      case c: Column => {
        if (c.name == "id") {
          "  id bigserial NOT NULL,"
        } else {

          val colMap = s"${c.name} ${c.sqlTpe} ${if(c.optional){"NULL"}else{"NOT NULL"}}"
          colMap

        }
      }
      /*case s: SubClass =>
        hasSubClasses = true
        "\n"+generateColumnsTagTable(s.cols)+"\n  val "+s.name+"Cols = "+generateStars(s.cols)+"\n"*/

    }.mkString(",\n")
  }
}
