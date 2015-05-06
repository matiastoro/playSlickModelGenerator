## Running from SBT

To run the generator from sbt:

```
run [FILENAME] [PARENT_FOLDER] [SUB_MODULE_NAME](Optional)

FILENAME: Yaml formatted file, example below
PARENT_FOLDER: Folder where the output will be generated
```

## Output
The generated code is generated into the current folder, if no folder is indicated. For example, the following yml file:

```
pregunta_habilidad_cognitiva:
  id:                                 ~
  pregunta_id:                        { type: integer, foreignTable: pregunta, foreignReference: id,  onDelete: cascade }
  habilidad_cognitiva_id:             { type: integer, foreignTable: habilidad_cognitiva_id, foreignReference: id,  onDelete: cascade }

pregunta_curso:
  id:                                 ~
  pregunta_id:                        { type: integer, foreignTable: pregunta, foreignReference: id,  onDelete: cascade }
  curso_id:                           { type: integer, foreignTable: curso, foreignReference: id,  onDelete: cascade }
```

will generate a file for each table. All classes from a table are recommended to reside in a different file:

```
package models
import play.api.db.slick.Config.driver.simple._
import play.api.db._
import play.api.Play.current
/*==============ADD YOUR ADDITIONAL IMPORTS FROM HERE==============*/

/*=========================TO HERE=========================*/
case class PreguntaCurso(id: Option[Long],
                         preguntaId: Long,
                         cursoId: Long){
/*==============ADD YOUR PreguntaCurso CODE FROM HERE==============*/

/*=========================TO HERE=========================*/
}



class PreguntaCursoMapping(tag: Tag) extends Table[PreguntaCurso](tag, "pregunta_curso") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def preguntaId = column[Long]("pregunta_id")
  def cursoId = column[Long]("curso_id")

  def * = (id.?, preguntaId, cursoId).shaped <> (PreguntaCurso.tupled, PreguntaCurso.unapply)
}

object PreguntaCursoQuery extends DatabaseClient[PreguntaCurso] {
  type DBTable = PreguntaCursoMapping

  private[models] val all = database.withSession { implicit db: Session =>
    TableQuery[DBTable]
/*==============ADD YOUR PreguntaCursoQuery CODE FROM HERE==============*/

/*=========================TO HERE=========================*/
  }
}
/*==============ADD YOUR ADDITIONAL CLASSES FROM HERE==============*/

/*=========================TO HERE=========================*/

package models
import play.api.db.slick.Config.driver.simple._
import play.api.db._
import play.api.Play.current
/*==============ADD YOUR ADDITIONAL IMPORTS FROM HERE==============*/

/*=========================TO HERE=========================*/
case class PreguntaHabilidadCognitiva(id: Option[Long],
                                      preguntaId: Long,
                                      habilidadCognitivaId: Long){
/*==============ADD YOUR PreguntaHabilidadCognitiva CODE FROM HERE==============*/

/*=========================TO HERE=========================*/
}



class PreguntaHabilidadCognitivaMapping(tag: Tag) extends Table[PreguntaHabilidadCognitiva](tag, "pregunta_habilidad_cognitiva") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def preguntaId = column[Long]("pregunta_id")
  def habilidadCognitivaId = column[Long]("habilidad_cognitiva_id")

  def * = (id.?, preguntaId, habilidadCognitivaId).shaped <> (PreguntaHabilidadCognitiva.tupled, PreguntaHabilidadCognitiva.unapply)
}

object PreguntaHabilidadCognitivaQuery extends DatabaseClient[PreguntaHabilidadCognitiva] {
  type DBTable = PreguntaHabilidadCognitivaMapping

  private[models] val all = database.withSession { implicit db: Session =>
    TableQuery[DBTable]
/*==============ADD YOUR PreguntaHabilidadCognitivaQuery CODE FROM HERE==============*/

/*=========================TO HERE=========================*/
  }
}
/*==============ADD YOUR ADDITIONAL CLASSES FROM HERE==============*/

/*=========================TO HERE=========================*/
```

