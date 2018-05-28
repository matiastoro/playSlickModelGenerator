package models
import models.extensions.UsuarioPerfilExtension
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

case class UsuarioPerfil(id: Option[Long] = None /*None*/,
                         perfilId: Long = 0 /*None*/,
                         usuarioId: Long = 0 /*None*/) extends UsuarioPerfilExtension{
  implicit val jsonFormat = Json.format[UsuarioPerfil]
  def toJson = Json.toJson(this)
         

  lazy val selectString = id
  def getPerfil(implicit session: Session) = PerfilConsulta.porId(perfilId)
  def getUsuario(implicit session: Session) = UsuarioConsulta.porId(usuarioId)
}



class UsuarioPerfilMapeo(tag: Tag) extends Table[UsuarioPerfil](tag, "usuario_perfil") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def perfilId = column[Long]("perfil_id", O.Default(0))
  def perfil = foreignKey("perfil_id_fk", perfilId, PerfilConsulta.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def usuarioId = column[Long]("usuario_id", O.Default(0))
  def usuario = foreignKey("usuario_id_fk", usuarioId, UsuarioConsulta.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)

  def * = (id.?, perfilId, usuarioId).shaped <> (UsuarioPerfil.tupled, UsuarioPerfil.unapply)
}

class UsuarioPerfilConsultaBase extends BaseDAO[UsuarioPerfil] {
  type DBTable = UsuarioPerfilMapeo

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
case class UsuarioPerfilFormData(obj: UsuarioPerfil){
  def update(updatedObj: UsuarioPerfil = obj)(implicit session: Session) = {

    UsuarioPerfilConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: UsuarioPerfil)(implicit session: Session) = {
    val id = UsuarioPerfilConsulta.insertar(insertedObj)

    id
  }
}

object UsuarioPerfilForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "perfilId" -> longNumber,
      "usuarioId" -> optional(longNumber)
    )/*(UsuarioPerfil.apply)(UsuarioPerfil.unapply)*/
    ((id,perfilId,usuarioId) => {
      UsuarioPerfilFormData(UsuarioPerfil(id, perfilId, usuarioId.getOrElse(0)))
    })((formData: UsuarioPerfilFormData) => {
      Some((formData.obj.id, formData.obj.perfilId, Some(formData.obj.usuarioId)))
    })
  )
}