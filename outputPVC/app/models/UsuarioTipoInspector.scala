package models
import models.extensions.UsuarioTipoInspectorExtension
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

case class UsuarioTipoInspector(id: Option[Long] = None /*None*/,
                                usuarioId: Long = 0 /*None*/,
                                tipoInspectorId: Long = 0 /*None*/) extends UsuarioTipoInspectorExtension{
  implicit val jsonFormat = Json.format[UsuarioTipoInspector]
  def toJson = Json.toJson(this)
         

  lazy val selectString = id
  def getUsuario(implicit session: Session) = UsuarioQuery.byId(usuarioId)
  def getTipoInspector(implicit session: Session) = TipoInspectorQuery.byId(tipoInspectorId)
}



class UsuarioTipoInspectors(tag: Tag) extends Table[UsuarioTipoInspector](tag, "usuario_tipo_inspector") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def usuarioId = column[Long]("usuario_id", O.Default(0))
  def usuario = foreignKey("usuario_id_fk", usuarioId, UsuarioQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def tipoInspectorId = column[Long]("tipo_inspector_id", O.Default(0))
  def tipoInspector = foreignKey("tipo_inspector_id_fk", tipoInspectorId, TipoInspectorQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)

  def * = (id.?, usuarioId, tipoInspectorId).shaped <> (UsuarioTipoInspector.tupled, UsuarioTipoInspector.unapply)
}

class UsuarioTipoInspectorQueryBase extends BaseDAO[UsuarioTipoInspector] {
  type DBTable = UsuarioTipoInspectorMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byUsuarioId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.usuarioId===i).list
    }.getOrElse(List())
  }
                            

  def byTipoInspectorId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.tipoInspectorId===i).list
    }.getOrElse(List())
  }
                            
}
case class UsuarioTipoInspectorFormData(obj: UsuarioTipoInspector){
  def update(updatedObj: UsuarioTipoInspector = obj)(implicit session: Session) = {

    UsuarioTipoInspectorQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: UsuarioTipoInspector)(implicit session: Session) = {
    val id = UsuarioTipoInspectorQuery.insert(insertedObj)

    id
  }
}

object UsuarioTipoInspectorForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "usuarioId" -> optional(longNumber),
      "tipoInspectorId" -> longNumber
    )/*(UsuarioTipoInspector.apply)(UsuarioTipoInspector.unapply)*/
    ((id,usuarioId,tipoInspectorId) => {
      UsuarioTipoInspectorFormData(UsuarioTipoInspector(id, usuarioId.getOrElse(0), tipoInspectorId))
    })((formData: UsuarioTipoInspectorFormData) => {
      Some((formData.obj.id, Some(formData.obj.usuarioId), formData.obj.tipoInspectorId))
    })
  )
}