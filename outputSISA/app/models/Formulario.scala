package models
import models.extensions.FormularioExtension
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

case class Formulario(id: Option[Long] = None /*None*/,
                      nombre: String = "" /*None*/,
                      codigo: String = "" /*None*/,
                      key: String = "" /*None*/,
                      schema: String = "" /*None*/,
                      organizacionId: Option[Long] = None /*None*/,
                      estado: Option[Int] = None /*None*/) extends FormularioExtension{
  implicit val jsonFormat = Json.format[Formulario]
  def toJson = Json.toJson(this)
         

  lazy val selectString = nombre
  def getOrganizacion(implicit session: Session) = if(organizacionId.isDefined) OrganizacionConsulta.porId(organizacionId.get) else None
}



class FormularioMapeo(tag: Tag) extends Table[Formulario](tag, "formulario") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def nombre = column[String]("nombre", O.Default(""))
  def codigo = column[String]("codigo", O.Default(""))
  def key = column[String]("key", O.Default(""))
  def schema = column[String]("schema", O.Default(""))
  def organizacionId = column[Option[Long]]("organizacion_id", O.Default(None))
  def organizacion = foreignKey("organizacion_id_fk", organizacionId, OrganizacionConsulta.tableQ)(_.id)
  def estado = column[Option[Int]]("estado", O.Default(None))

  def * = (id.?, nombre, codigo, key, schema, organizacionId, estado).shaped <> (Formulario.tupled, Formulario.unapply)
}

class FormularioConsultaBase extends BaseDAO[Formulario] {
  type DBTable = FormularioMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byOrganizacionId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.organizacionId===i).list
    }.getOrElse(List())
  }
                            
}
case class FormularioFormData(obj: Formulario){
  def update(updatedObj: Formulario = obj)(implicit session: Session) = {

    FormularioConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: Formulario)(implicit session: Session) = {
    val id = FormularioConsulta.insertar(insertedObj)

    id
  }
}

object FormularioForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "nombre" -> text,
      "codigo" -> text,
      "key" -> text,
      "schema" -> text,
      "organizacionId" -> optional(longNumber),
      "estado" -> optional(number)
    )/*(Formulario.apply)(Formulario.unapply)*/
    ((id,nombre,codigo,key,schema,organizacionId,estado) => {
      FormularioFormData(Formulario(id, nombre, codigo, key, schema, organizacionId, estado))
    })((formData: FormularioFormData) => {
      Some((formData.obj.id, formData.obj.nombre, formData.obj.codigo, formData.obj.key, formData.obj.schema, formData.obj.organizacionId, formData.obj.estado))
    })
  )
}