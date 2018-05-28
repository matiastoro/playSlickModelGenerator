package models
import models.extensions.PerfilUsuarioExtension
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

case class PerfilUsuario(id: Option[Long] = None /*None*/,
                         perfilId: Long = 0 /*None*/,
                         usuarioId: Long = 0 /*None*/) extends PerfilUsuarioExtension{
  implicit val jsonFormat = Json.format[PerfilUsuario]
  def toJson = Json.toJson(this)
         

  lazy val selectString = id
  def getPerfil(implicit session: Session) = PerfilQuery.byId(perfilId)
  def getUsuario(implicit session: Session) = UsuarioQuery.byId(usuarioId)
}



class PerfilUsuarios(tag: Tag) extends Table[PerfilUsuario](tag, "perfil_usuario") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def perfilId = column[Long]("perfil_id", O.Default(0))
  def perfil = foreignKey("perfil_id_fk", perfilId, PerfilQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def usuarioId = column[Long]("usuario_id", O.Default(0))
  def usuario = foreignKey("usuario_id_fk", usuarioId, UsuarioQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)

  def * = (id.?, perfilId, usuarioId).shaped <> (PerfilUsuario.tupled, PerfilUsuario.unapply)
}

class PerfilUsuarioQueryBase extends BaseDAO[PerfilUsuario] {
  type DBTable = PerfilUsuarioMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byPerfilId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.perfilId===i).list
    }.getOrElse(List())
  }
                            

  def byUsuarioId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.usuarioId===i).list
    }.getOrElse(List())
  }
                            
}
case class PerfilUsuarioFormData(obj: PerfilUsuario){
  def update(updatedObj: PerfilUsuario = obj)(implicit session: Session) = {

    PerfilUsuarioQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: PerfilUsuario)(implicit session: Session) = {
    val id = PerfilUsuarioQuery.insert(insertedObj)

    id
  }
}

object PerfilUsuarioForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "perfilId" -> longNumber,
      "usuarioId" -> optional(longNumber)
    )/*(PerfilUsuario.apply)(PerfilUsuario.unapply)*/
    ((id,perfilId,usuarioId) => {
      PerfilUsuarioFormData(PerfilUsuario(id, perfilId, usuarioId.getOrElse(0)))
    })((formData: PerfilUsuarioFormData) => {
      Some((formData.obj.id, formData.obj.perfilId, Some(formData.obj.usuarioId)))
    })
  )
}