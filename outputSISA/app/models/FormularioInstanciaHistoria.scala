package models
import models.extensions.FormularioInstanciaHistoriaExtension
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

case class FormularioInstanciaHistoria(id: Option[Long] = None /*None*/,
                                       formularioId: Long = 0 /*None*/,
                                       data: String = "" /*None*/,
                                       estado: String = "" /*None*/,
                                       files: Option[String] = None /*None*/,
                                       adjuntos: Option[String] = None /*None*/,
                                       usuarioId: Long = 0 /*None*/,
                                       createdAt: DateTime = new DateTime() /*None*/) extends FormularioInstanciaHistoriaExtension{
  implicit val jsonFormat = Json.format[FormularioInstanciaHistoria]
  def toJson = Json.toJson(this)
         

  lazy val selectString = data
  def getFormulario(implicit session: Session) = FormularioConsulta.porId(formularioId)
  def getUsuario(implicit session: Session) = UsuarioConsulta.porId(usuarioId)
}



class FormularioInstanciaHistoriaMapeo(tag: Tag) extends Table[FormularioInstanciaHistoria](tag, "formulario_instancia_historia") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def formularioId = column[Long]("formulario_id", O.Default(0))
  def formulario = foreignKey("formulario_id_fk", formularioId, FormularioConsulta.tableQ)(_.id)
  def data = column[String]("data", O.Default(""))
  def estado = column[String]("estado", O.Default(""))
  def files = column[Option[String]]("files", O.Default(None))
  def adjuntos = column[Option[String]]("adjuntos", O.Default(None))
  def usuarioId = column[Long]("usuario_id", O.Default(0))
  def usuario = foreignKey("usuario_id_fk", usuarioId, UsuarioConsulta.tableQ)(_.id)
  def createdAt = column[DateTime]("created_at", O.Default(new DateTime()))

  def * = (id.?, formularioId, data, estado, files, adjuntos, usuarioId, createdAt).shaped <> (FormularioInstanciaHistoria.tupled, FormularioInstanciaHistoria.unapply)
}

class FormularioInstanciaHistoriaConsultaBase extends BaseDAO[FormularioInstanciaHistoria] {
  type DBTable = FormularioInstanciaHistoriaMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byFormularioId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.formularioId===i).list
    }.getOrElse(List())
  }
                            

  def byUsuarioId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.usuarioId===i).list
    }.getOrElse(List())
  }
                            
}
case class FormularioInstanciaHistoriaFormData(obj: FormularioInstanciaHistoria){
  def update(updatedObj: FormularioInstanciaHistoria = obj)(implicit session: Session) = {

    FormularioInstanciaHistoriaConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: FormularioInstanciaHistoria)(implicit session: Session) = {
    val id = FormularioInstanciaHistoriaConsulta.insertar(insertedObj)

    id
  }
}

object FormularioInstanciaHistoriaForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "formularioId" -> longNumber,
      "data" -> text,
      "estado" -> text,
      "files" -> optional(text),
      "adjuntos" -> optional(text),
      "usuarioId" -> longNumber,
      "createdAt" -> jodaDate
    )/*(FormularioInstanciaHistoria.apply)(FormularioInstanciaHistoria.unapply)*/
    ((id,formularioId,data,estado,files,adjuntos,usuarioId,createdAt) => {
      FormularioInstanciaHistoriaFormData(FormularioInstanciaHistoria(id, formularioId, data, estado, files, adjuntos, usuarioId, createdAt))
    })((formData: FormularioInstanciaHistoriaFormData) => {
      Some((formData.obj.id, formData.obj.formularioId, formData.obj.data, formData.obj.estado, formData.obj.files, formData.obj.adjuntos, formData.obj.usuarioId, formData.obj.createdAt))
    })
  )
}