package via56.slickGenerator.crud

import via56.slickGenerator._

/**
  * Created by jcarrasco on 9/4/2018.
  */
case class SqlGenerator(table: Table, tablesOneToMany: List[Table] = List())(implicit langHash: Map[String,String]) extends CodeGenerator {

  def generateTableUps: String = {

    val columns: List[AbstractColumn] = table.columns
    val cols = generateColumnsUpTagTable(columns)
    s"""
       |
       |CREATE TABLE "${table.tableNameDB}" (
       |  $cols
       |);
""".stripMargin

  }
  
  def generateTableDowns: String = {
    s"""DROP TABLE IF EXISTS "${table.tableNameDB}";""".stripMargin
  }

  def generateIndexesUp(columns: List[AbstractColumn]): String = {

    columns.flatMap{
      case c: Column => {
        if (c.rawName == "id") {
          None
        } else {



          c.foreignKey.map { fk =>


            s"""CREATE INDEX "${table.tableNameDB}_${c.rawName}_idx"
              |  ON "${table.tableNameDB}"
              |  USING btree
              |  ("${c.rawName}");
              |""".stripMargin
            }
          //colMap

        }
      }
      case s: SubClass => Some(generateIndexesUp(s.cols))
      case _ => None

    }.mkString("\n")
  }

  def generateIndexesDown(columns: List[AbstractColumn]): String = {

    columns.flatMap{
      case c: Column => {
        if (c.rawName == "id") {
          None
        } else {
          c.foreignKey.map { fk => s"""DROP INDEX IF EXISTS "${table.tableNameDB}_${c.rawName}_idx";""".stripMargin}
        }
      }
      case s: SubClass => Some(generateIndexesDown(s.cols))
      case _ => None
    }.mkString("\n")
  }

  def generateConstraintsUp(columns: List[AbstractColumn]): String = {
    columns.flatMap{
      case c: Column => {
        if (c.rawName == "id") {
          //Some(s"""ALTER TABLE IF EXISTS "${table.tableNameDB}" ADD CONSTRAINT "${table.tableNameDB}_pkey" PRIMARY KEY (id);""")
          None
        } else {
          c.foreignKey.map { fk =>
            val onDelete = fk.onDelete match{
              case Some("cascade") => "CASCADE"
              case _ => "SET NULL"
            }
              s"""ALTER TABLE IF EXISTS "${table.tableNameDB}" ADD CONSTRAINT "${table.tableNameDB}_${fk.table}_fk" FOREIGN KEY ("${c.rawName}")
                  |  REFERENCES "${fk.table}" ("${fk.reference}") MATCH SIMPLE
                  |  ON UPDATE NO ACTION ON DELETE $onDelete;""".stripMargin
          }
        }
      }
      case s: SubClass => Some(generateConstraintsUp(s.cols))
      case _ => None

    }.mkString("\n")
  }

  def generateConstraintsDown(columns: List[AbstractColumn]): String = {
    columns.flatMap{
      case c: Column => {
        if (c.rawName == "id") {
          None//Some(s"""ALTER TABLE "${table.tableNameDB}" DROP CONSTRAINT IF EXISTS "${table.tableNameDB}_pkey";""")
        } else {
          c.foreignKey.map { fk =>
              s"""ALTER TABLE IF EXISTS "${table.tableNameDB}" DROP CONSTRAINT IF EXISTS  "${table.tableNameDB}_${fk.table}_fk";""".stripMargin
          }
        }
      }

      case s: SubClass => Some(generateConstraintsDown(s.cols))
      case _ => None

    }.reverse.mkString("\n")
  }


  def generateColumnsUpTagTable(columns: List[AbstractColumn]): String = {
    columns.collect{
      case c: Column => {
        if (c.rawName == "id") {
          "  id bigserial NOT NULL primary key"
        } else {

          val colMap = s"""  "${c.rawName}" ${c.sqlTpe} ${if(c.optional){"NULL"}else{"NOT NULL"}}"""
          colMap

        }
      }

      case s: SubClass =>
        generateColumnsUpTagTable(s.cols)

    }.mkString(",\n")
  }
}
