## Running from SBT

To run the generator from sbt:

```
run [FILENAME]
```

## Output
The generated code is printed into the screen. For example, the following yml file:

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

will generate a "file" for each table. All classes from a  table is recommended to reside in a different file:

```
pregunta_habilidad_cognitiva
package models
import play.api.db.slick.Config.driver.simple._
import play.api.db._
import play.api.Play.current

case class PreguntaHabilidadCognitiva(id: Option[Long],
                                      preguntaId: Long,
                                      habilidadCognitivaId: Long)



class PreguntaHabilidadCognitivas(tag: Tag) extends Table[PreguntaHabilidadCognitiva](tag, "pregunta_habilidad_cognitiva") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def preguntaId = column[Long]("pregunta_id")
  def habilidadCognitivaId = column[Long]("habilidad_cognitiva_id")

  def * = (id.?, preguntaId, habilidadCognitivaId).shaped <> (PreguntaHabilidadCognitiva.tupled, PreguntaHabilidadCognitiva.unapply)
}

object PreguntaHabilidadCognitivas extends DatabaseClient[PreguntaHabilidadCognitiva] {
  type DBTable = PreguntaHabilidadCognitivas

  private[models] val all = database.withSession { implicit db: Session =>
    TableQuery[DBTable]
  }
}
pregunta_curso
package models
import play.api.db.slick.Config.driver.simple._
import play.api.db._
import play.api.Play.current

case class PreguntaCurso(id: Option[Long],
                         preguntaId: Long,
                         cursoId: Long)



class PreguntaCursos(tag: Tag) extends Table[PreguntaCurso](tag, "pregunta_curso") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def preguntaId = column[Long]("pregunta_id")
  def cursoId = column[Long]("curso_id")

  def * = (id.?, preguntaId, cursoId).shaped <> (PreguntaCurso.tupled, PreguntaCurso.unapply)
}

object PreguntaCursos extends DatabaseClient[PreguntaCurso] {
  type DBTable = PreguntaCursos

  private[models] val all = database.withSession { implicit db: Session =>
    TableQuery[DBTable]
  }
}
```

