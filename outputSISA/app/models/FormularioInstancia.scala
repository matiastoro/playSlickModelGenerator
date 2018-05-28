package models
import models.extensions.FormularioInstanciaExtension
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

case class FormularioInstancia(id: Option[Long] = None /*None*/,
                               formularioId: Long = 0 /*None*/,
                               data: String = "" /*None*/,
                               estado: String = "" /*None*/,
                               files: Option[String] = None /*None*/,
                               adjuntos: Option[String] = None /*None*/,
                               designadorOaci: Option[String] = None /*None*/,
                               createdAt: DateTime = new DateTime() /*None*/,
                               updatedAt: Option[DateTime] = None /*None*/) extends FormularioInstanciaExtension{
  implicit val jsonFormat = Json.format[FormularioInstancia]
  def toJson = Json.toJson(this)
         

  lazy val selectString = data
  def getFormulario(implicit session: Session) = FormularioConsulta.porId(formularioId)
}



class FormularioInstanciaMapeo(tag: Tag) extends Table[FormularioInstancia](tag, "formulario_instancia") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def formularioId = column[Long]("formulario_id", O.Default(0))
  def formulario = foreignKey("formulario_id_fk", formularioId, FormularioConsulta.tableQ)(_.id)
  def data = column[String]("data", O.Default(""))
  def estado = column[String]("estado", O.Default(""))
  def files = column[Option[String]]("files", O.Default(None))
  def adjuntos = column[Option[String]]("adjuntos", O.Default(None))
  def designadorOaci = column[Option[String]]("designador_oaci", O.Default(None))
  def createdAt = column[DateTime]("created_at", O.Default(new DateTime()))
  def updatedAt = column[Option[DateTime]]("updated_at", O.Default(None))

  def * = (id.?, formularioId, data, estado, files, adjuntos, designadorOaci, createdAt, updatedAt).shaped <> (FormularioInstancia.tupled, FormularioInstancia.unapply)
}

class FormularioInstanciaConsultaBase extends BaseDAO[FormularioInstancia] {
  type DBTable = FormularioInstanciaMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byFormularioId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.formularioId===i).list
    }.getOrElse(List())
  }
                            
}
case class FormularioInstanciaFormData(obj: FormularioInstancia){
  def update(updatedObj: FormularioInstancia = obj)(implicit session: Session) = {

    FormularioInstanciaConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: FormularioInstancia)(implicit session: Session) = {
    val id = FormularioInstanciaConsulta.insertar(insertedObj)

    id
  }
}

object FormularioInstanciaForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "formularioId" -> longNumber,
      "data" -> text,
      "estado" -> text,
      "files" -> optional(text),
      "adjuntos" -> optional(text),
      "designadorOaci" -> optional(text),
      "createdAt" -> jodaDate,
      "updatedAt" -> optional(jodaDate)
    )/*(FormularioInstancia.apply)(FormularioInstancia.unapply)*/
    ((id,formularioId,data,estado,files,adjuntos,designadorOaci,createdAt,updatedAt) => {
      FormularioInstanciaFormData(FormularioInstancia(id, formularioId, data, estado, files, adjuntos, designadorOaci, createdAt, updatedAt))
    })((formData: FormularioInstanciaFormData) => {
      Some((formData.obj.id, formData.obj.formularioId, formData.obj.data, formData.obj.estado, formData.obj.files, formData.obj.adjuntos, formData.obj.designadorOaci, formData.obj.createdAt, formData.obj.updatedAt))
    })
  )
}