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
      ("usuarioCargos" -> Json.toJson(UsuarioCargoConsulta.byUsuarioId(id).map(_.toJson)))+
     ("perfilUsuarios" -> Json.toJson(PerfilUsuarioConsulta.byUsuarioId(id).map(_.toJson)))
         

  lazy val selectString = nombre

}



class UsuarioMapeo(tag: Tag) extends Table[Usuario](tag, "usuario") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def nombre = column[String]("nombre", O.Default(""))
  def email = column[String]("email", O.Default(""))
  def contrasena = column[String]("contrasena", O.Default(""))
  def estado = column[Int]("estado", O.Default(0))
  def rut = column[Option[String]]("rut", O.Default(None))

  def * = (id.?, nombre, email, contrasena, estado, rut).shaped <> (Usuario.tupled, Usuario.unapply)
}

class UsuarioConsultaBase extends BaseDAO[Usuario] {
  type DBTable = UsuarioMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class UsuarioFormData(obj: Usuario, usuarioCargos: List[UsuarioCargoFormData], perfilUsuarios: List[PerfilUsuarioFormData]){
  def update(updatedObj: Usuario = obj)(implicit session: Session) = {
    //Delete elements that are not part of the form but they do exists in the database.
    UsuarioCargoConsulta.byUsuarioId(obj.id).filterNot{o => usuarioCargos.exists(_.obj.id == o.id)}.map{UsuarioCargoConsulta.eliminar(_)}
    usuarioCargos.map{o => o.update(o.obj.copy(usuarioId = obj.id.get))}
    //Delete elements that are not part of the form but they do exists in the database.
    PerfilUsuarioConsulta.byUsuarioId(obj.id).filterNot{o => perfilUsuarios.exists(_.obj.id == o.id)}.map{PerfilUsuarioConsulta.eliminar(_)}
    perfilUsuarios.map{o => o.update(o.obj.copy(usuarioId = obj.id.get))}
    UsuarioConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: Usuario)(implicit session: Session) = {
    val id = UsuarioConsulta.insertar(insertedObj)
    usuarioCargos.map{o => o.insert(o.obj.copy(usuarioId = id))}
    perfilUsuarios.map{o => o.insert(o.obj.copy(usuarioId = id))}
    id
  }
}
object UsuarioFormData{
  def apply(obj: Usuario)(implicit session: Session) = {
    val usuarioCargos = UsuarioCargoConsulta.byUsuarioId(obj.id).map(UsuarioCargoFormData(_))
    val perfilUsuarios = PerfilUsuarioConsulta.byUsuarioId(obj.id).map(PerfilUsuarioFormData(_))
    new UsuarioFormData(obj, usuarioCargos, perfilUsuarios)
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
      "usuarioCargos" -> list(models.UsuarioCargoForm.form.mapping),
      "perfilUsuarios" -> list(models.PerfilUsuarioForm.form.mapping)
    )/*(Usuario.apply)(Usuario.unapply)*/
    ((id,nombre,email,contrasena,estado,rut,usuarioCargos,perfilUsuarios) => {
      UsuarioFormData(Usuario(id, nombre, email, contrasena, estado, rut), usuarioCargos, perfilUsuarios)
    })((formData: UsuarioFormData) => {
      Some((formData.obj.id, formData.obj.nombre, formData.obj.email, formData.obj.contrasena, formData.obj.estado, formData.obj.rut, formData.usuarioCargos, formData.perfilUsuarios))
    })
  )
}