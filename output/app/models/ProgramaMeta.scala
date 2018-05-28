package models
import models.extensions.ProgramaMetaExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class ProgramaMeta(id: Option[Long] = None /*None*/,
                        mes: Int = 0 /*None*/,
                        anio: Int = 0 /*None*/,
                        cantidad: Int = 0 /*None*/,
                        programaId: Long = 0 /*None*/) extends ProgramaMetaExtension{
  lazy val selectString = id
  def getPrograma = ProgramaQuery.byId(programaId)
}



class ProgramaMetaMapping(tag: Tag) extends Table[ProgramaMeta](tag, "programa_meta") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def mes = column[Int]("mes", O.Default(0))
  def anio = column[Int]("anio", O.Default(0))
  def cantidad = column[Int]("cantidad", O.Default(0))
  def programaId = column[Long]("programa_id", O.Default(0))
  def programa = foreignKey("programa_id_fk", programaId, ProgramaQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)

  def * = (id.?, mes, anio, cantidad, programaId).shaped <> (ProgramaMeta.tupled, ProgramaMeta.unapply)
}

class ProgramaMetaQueryBase extends DatabaseClient[ProgramaMeta] {
  type DBTable = ProgramaMetaMapping

  private[models] val all = {
    TableQuery[DBTable]
  }
  def byProgramaId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.programaId===i).list
    }.getOrElse(List())
  }
                            
}
case class ProgramaMetaFormData(obj: ProgramaMeta){
  def update(updatedObj: ProgramaMeta = obj) = {

    ProgramaMetaQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: ProgramaMeta) = {
    val id = ProgramaMetaQuery.insert(insertedObj)

    id
  }
}

object ProgramaMetaForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "mes" -> number,
      "anio" -> number,
      "cantidad" -> number,
      "programaId" -> longNumber
    )/*(ProgramaMeta.apply)(ProgramaMeta.unapply)*/
    ((id,mes,anio,cantidad,programaId) => {
      ProgramaMetaFormData(ProgramaMeta(id, mes, anio, cantidad, programaId))
    })((formData: ProgramaMetaFormData) => {
      Some((formData.obj.id, formData.obj.mes, formData.obj.anio, formData.obj.cantidad, formData.obj.programaId))
    })
  )
}