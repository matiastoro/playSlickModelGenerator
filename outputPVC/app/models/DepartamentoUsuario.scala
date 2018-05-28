package models
import models.extensions.DepartamentoUsuarioExtension
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

case class DepartamentoUsuario(id: Option[Long] = None /*None*/,
                               departamentoId: Long = 0 /*None*/,
                               usuarioId: Long = 0 /*None*/) extends DepartamentoUsuarioExtension{
  implicit val jsonFormat = Json.format[DepartamentoUsuario]
  def toJson = Json.toJson(this)
         

  lazy val selectString = id
  def getDepartamento(implicit session: Session) = DepartamentoQuery.byId(departamentoId)
  def getUsuario(implicit session: Session) = UsuarioQuery.byId(usuarioId)
}



class DepartamentoUsuarios(tag: Tag) extends Table[DepartamentoUsuario](tag, "departamento_usuario") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def departamentoId = column[Long]("departamento_id", O.Default(0))
  def departamento = foreignKey("departamento_id_fk", departamentoId, DepartamentoQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def usuarioId = column[Long]("usuario_id", O.Default(0))
  def usuario = foreignKey("usuario_id_fk", usuarioId, UsuarioQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)

  def * = (id.?, departamentoId, usuarioId).shaped <> (DepartamentoUsuario.tupled, DepartamentoUsuario.unapply)
}

class DepartamentoUsuarioQueryBase extends BaseDAO[DepartamentoUsuario] {
  type DBTable = DepartamentoUsuarioMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byDepartamentoId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.departamentoId===i).list
    }.getOrElse(List())
  }
                            

  def byUsuarioId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.usuarioId===i).list
    }.getOrElse(List())
  }
                            
}
case class DepartamentoUsuarioFormData(obj: DepartamentoUsuario){
  def update(updatedObj: DepartamentoUsuario = obj)(implicit session: Session) = {

    DepartamentoUsuarioQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: DepartamentoUsuario)(implicit session: Session) = {
    val id = DepartamentoUsuarioQuery.insert(insertedObj)

    id
  }
}

object DepartamentoUsuarioForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "departamentoId" -> longNumber,
      "usuarioId" -> optional(longNumber)
    )/*(DepartamentoUsuario.apply)(DepartamentoUsuario.unapply)*/
    ((id,departamentoId,usuarioId) => {
      DepartamentoUsuarioFormData(DepartamentoUsuario(id, departamentoId, usuarioId.getOrElse(0)))
    })((formData: DepartamentoUsuarioFormData) => {
      Some((formData.obj.id, formData.obj.departamentoId, Some(formData.obj.usuarioId)))
    })
  )
}