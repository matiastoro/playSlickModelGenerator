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
import play.api.libs.json._

case class ProgramaTipoInspector(id: Option[Long] = None /*None*/,
                                 programaId: Option[Long] = None /*None*/,
                                 tipoInspectorId: Option[Long] = None /*None*/) extends ProgramaTipoInspectorExtension{
  implicit val jsonFormat = Json.format[ProgramaTipoInspector]
  def toJson = Json.toJson(this)
         

  lazy val selectString = id
  def getPrograma(implicit session: Session) = if(programaId.isDefined) ProgramaQuery.byId(programaId.get) else None
  def getTipoInspector(implicit session: Session) = if(tipoInspectorId.isDefined) TipoInspectorQuery.byId(tipoInspectorId.get) else None
}



class ProgramaTipoInspectors(tag: Tag) extends Table[ProgramaTipoInspector](tag, "programa_tipo_inspector") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def programaId = column[Option[Long]]("programa_id", O.Default(None))
  def programa = foreignKey("programa_id_fk", programaId, ProgramaQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def tipoInspectorId = column[Option[Long]]("tipo_inspector_id", O.Default(None))
  def tipoInspector = foreignKey("tipo_inspector_id_fk", tipoInspectorId, TipoInspectorQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)

  def * = (id.?, programaId, tipoInspectorId).shaped <> (ProgramaTipoInspector.tupled, ProgramaTipoInspector.unapply)
}

class ProgramaTipoInspectorQueryBase extends BaseDAO[ProgramaTipoInspector] {
  type DBTable = ProgramaTipoInspectorMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byProgramaId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.programaId===i).list
    }.getOrElse(List())
  }
                            

  def byTipoInspectorId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.tipoInspectorId===i).list
    }.getOrElse(List())
  }
                            
}
case class ProgramaTipoInspectorFormData(obj: ProgramaTipoInspector){
  def update(updatedObj: ProgramaTipoInspector = obj)(implicit session: Session) = {

    ProgramaTipoInspectorQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: ProgramaTipoInspector)(implicit session: Session) = {
    val id = ProgramaTipoInspectorQuery.insert(insertedObj)

    id
  }
}

object ProgramaTipoInspectorForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "programaId" -> optional(optional(longNumber)),
      "tipoInspectorId" -> optional(longNumber)
    )/*(ProgramaTipoInspector.apply)(ProgramaTipoInspector.unapply)*/
    ((id,programaId,tipoInspectorId) => {
      ProgramaTipoInspectorFormData(ProgramaTipoInspector(id, programaId.getOrElse(0), tipoInspectorId))
    })((formData: ProgramaTipoInspectorFormData) => {
      Some((formData.obj.id, Some(formData.obj.programaId), formData.obj.tipoInspectorId))
    })
  )
}