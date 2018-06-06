package models
import models.extensions.PreguntaHabilidadCognitivaExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current

case class PreguntaHabilidadCognitiva(id: Option[Long],
                                      preguntaId: Long,
                                      habilidadCognitivaId: Long) extends PreguntaHabilidadCognitivaExtension



class PreguntaHabilidadCognitivaMapping(tag: Tag) extends Table[PreguntaHabilidadCognitiva](tag, "pregunta_habilidad_cognitiva") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def preguntaId = column[Long]("pregunta_id")
  def habilidadCognitivaId = column[Long]("habilidad_cognitiva_id")

  def * = (id.?, preguntaId, habilidadCognitivaId).shaped <> (PreguntaHabilidadCognitiva.tupled, PreguntaHabilidadCognitiva.unapply)
}

class PreguntaHabilidadCognitivaQueryBase extends DatabaseClient[PreguntaHabilidadCognitiva] {
  type DBTable = PreguntaHabilidadCognitivaMapping

  private[models] val all = database.withSession { implicit db: Session =>
    TableQuery[DBTable]
  }
}