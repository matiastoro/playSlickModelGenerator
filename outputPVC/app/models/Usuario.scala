package models
import models.extensions.UsuarioExtension
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

case class Usuario(id: Option[Long] = None /*None*/,
                   nombre: String = "" /*None*/,
                   email: String = "" /*None*/,
                   contrasena: String = "" /*None*/,
                   estado: Int = 0 /*None*/,
                   rut: Option[String] = None /*None*/) extends UsuarioExtension{
  implicit val jsonFormat = Json.format[Usuario]
  def toJson(implicit session: Session) = Json.toJson(this).as[JsObject] + 
      ("perfilUsuarios" -> Json.toJson(PerfilUsuarioQuery.byUsuarioId(id).map(_.toJson)))+
     ("departamentoUsuarios" -> Json.toJson(DepartamentoUsuarioQuery.byUsuarioId(id).map(_.toJson)))+
     ("usuarioTipoInspectors" -> Json.toJson(UsuarioTipoInspectorQuery.byUsuarioId(id).map(_.toJson)))
         

  lazy val selectString = nombre

}



class Usuarios(tag: Tag) extends Table[Usuario](tag, "usuario") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def nombre = column[String]("nombre", O.Default(""))
  def email = column[String]("email", O.Default(""))
  def contrasena = column[String]("contrasena", O.Default(""))
  def estado = column[Int]("estado", O.Default(0))
  def rut = column[Option[String]]("rut", O.Default(None))

  def * = (id.?, nombre, email, contrasena, estado, rut).shaped <> (Usuario.tupled, Usuario.unapply)
}

class UsuarioQueryBase extends BaseDAO[Usuario] {
  type DBTable = UsuarioMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class UsuarioFormData(obj: Usuario, perfilUsuarios: List[PerfilUsuarioFormData], departamentoUsuarios: List[DepartamentoUsuarioFormData], usuarioTipoInspectors: List[UsuarioTipoInspectorFormData]){
  def update(updatedObj: Usuario = obj)(implicit session: Session) = {
    //Delete elements that are not part of the form but they do exists in the database.
    PerfilUsuarioQuery.byUsuarioId(obj.id).filterNot{o => perfilUsuarios.exists(_.obj.id == o.id)}.map{PerfilUsuarioQuery.eliminar(_)}
    perfilUsuarios.map{o => o.update(o.obj.copy(usuarioId = obj.id.get))}
    //Delete elements that are not part of the form but they do exists in the database.
    DepartamentoUsuarioQuery.byUsuarioId(obj.id).filterNot{o => departamentoUsuarios.exists(_.obj.id == o.id)}.map{DepartamentoUsuarioQuery.eliminar(_)}
    departamentoUsuarios.map{o => o.update(o.obj.copy(usuarioId = obj.id.get))}
    //Delete elements that are not part of the form but they do exists in the database.
    UsuarioTipoInspectorQuery.byUsuarioId(obj.id).filterNot{o => usuarioTipoInspectors.exists(_.obj.id == o.id)}.map{UsuarioTipoInspectorQuery.eliminar(_)}
    usuarioTipoInspectors.map{o => o.update(o.obj.copy(usuarioId = obj.id.get))}
    UsuarioQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Usuario)(implicit session: Session) = {
    val id = UsuarioQuery.insert(insertedObj)
    perfilUsuarios.map{o => o.insert(o.obj.copy(usuarioId = id))}
    departamentoUsuarios.map{o => o.insert(o.obj.copy(usuarioId = id))}
    usuarioTipoInspectors.map{o => o.insert(o.obj.copy(usuarioId = id))}
    id
  }
}
object UsuarioFormData{
  def apply(obj: Usuario)(implicit session: Session) = {
    val perfilUsuarios = PerfilUsuarioQuery.byUsuarioId(obj.id).map(PerfilUsuarioFormData(_))
    val departamentoUsuarios = DepartamentoUsuarioQuery.byUsuarioId(obj.id).map(DepartamentoUsuarioFormData(_))
    val usuarioTipoInspectors = UsuarioTipoInspectorQuery.byUsuarioId(obj.id).map(UsuarioTipoInspectorFormData(_))
    new UsuarioFormData(obj, perfilUsuarios, departamentoUsuarios, usuarioTipoInspectors)
  }
}
object UsuarioForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "nombre" -> text,
      "email" -> text,
      "contrasena" -> text,
      "estado" -> number,
      "rut" -> optional(text),
      "perfilUsuarios" -> list(models.PerfilUsuarioForm.form.mapping),
      "departamentoUsuarios" -> list(models.DepartamentoUsuarioForm.form.mapping),
      "usuarioTipoInspectors" -> list(models.UsuarioTipoInspectorForm.form.mapping)
    )/*(Usuario.apply)(Usuario.unapply)*/
    ((id,nombre,email,contrasena,estado,rut,perfilUsuarios,departamentoUsuarios,usuarioTipoInspectors) => {
      UsuarioFormData(Usuario(id, nombre, email, contrasena, estado, rut), perfilUsuarios, departamentoUsuarios, usuarioTipoInspectors)
    })((formData: UsuarioFormData) => {
      Some((formData.obj.id, formData.obj.nombre, formData.obj.email, formData.obj.contrasena, formData.obj.estado, formData.obj.rut, formData.perfilUsuarios, formData.departamentoUsuarios, formData.usuarioTipoInspectors))
    })
  )
}