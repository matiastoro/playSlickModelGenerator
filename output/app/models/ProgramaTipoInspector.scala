package models
import models.extensions.ProgramaTipoInspectorExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class ProgramaTipoInspector(id: Option[Long] = None /*None*/,
                                 programaId: Long = 0 /*None*/,
                                 tipoInspectorId: Long = 0 /*None*/) extends ProgramaTipoInspectorExtension{
  lazy val selectString = id

}



class ProgramaTipoInspectorMapping(tag: Tag) extends Table[ProgramaTipoInspector](tag, "programa_tipo_inspector") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def programaId = column[Long]("programa_id", O.Default(0))
  def tipoInspectorId = column[Long]("tipo_inspector_id", O.Default(0))

  def * = (id.?, programaId, tipoInspectorId).shaped <> (ProgramaTipoInspector.tupled, ProgramaTipoInspector.unapply)
}

class ProgramaTipoInspectorQueryBase extends DatabaseClient[ProgramaTipoInspector] {
  type DBTable = ProgramaTipoInspectorMapping

  private[models] val all = {
    TableQuery[DBTable]
  }

}
case class ProgramaTipoInspectorFormData(obj: ProgramaTipoInspector){
  def update(updatedObj: ProgramaTipoInspector = obj) = {

    ProgramaTipoInspectorQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: ProgramaTipoInspector) = {
    val id = ProgramaTipoInspectorQuery.insert(insertedObj)

    id
  }
}

object ProgramaTipoInspectorForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "programaId" -> longNumber,
      "tipoInspectorId" -> longNumber
    )/*(ProgramaTipoInspector.apply)(ProgramaTipoInspector.unapply)*/
    ((id,programaId,tipoInspectorId) => {
      ProgramaTipoInspectorFormData(ProgramaTipoInspector(id, programaId, tipoInspectorId))
    })((formData: ProgramaTipoInspectorFormData) => {
      Some((formData.obj.id, formData.obj.programaId, formData.obj.tipoInspectorId))
    })
  )
}