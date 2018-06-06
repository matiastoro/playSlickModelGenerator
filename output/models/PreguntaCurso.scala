package models
import models.extensions.PreguntaCursoExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current

case class PreguntaCurso(id: Option[Long],
                         preguntaId: Long,
                         cursoId: Long) extends PreguntaCursoExtension



class PreguntaCursoMapping(tag: Tag) extends Table[PreguntaCurso](tag, "pregunta_curso") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def preguntaId = column[Long]("pregunta_id")
  def cursoId = column[Long]("curso_id")

  def * = (id.?, preguntaId, cursoId).shaped <> (PreguntaCurso.tupled, PreguntaCurso.unapply)
}

class PreguntaCursoQueryBase extends DatabaseClient[PreguntaCurso] {
  type DBTable = PreguntaCursoMapping

  private[models] val all = database.withSession { implicit db: Session =>
    TableQuery[DBTable]
  }
}