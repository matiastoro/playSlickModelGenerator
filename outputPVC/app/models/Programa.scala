package models
import models.extensions.ProgramaExtension
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

case class Programa(id: Option[Long] = None /*None*/,
                    programa: String = "" /*None*/,
                    acronimo: String = "" /*None*/,
                    departamentoId: Option[Long] = None /*None*/,
                    categoriaInspeccionId: Option[Long] = None /*None*/,
                    objetoInspeccionTipoId: Option[Long] = None /*None*/) extends ProgramaExtension{
  implicit val jsonFormat = Json.format[Programa]
  def toJson(implicit session: Session) = Json.toJson(this).as[JsObject] + 
      ("programaTipoInspectors" -> Json.toJson(ProgramaTipoInspectorQuery.byProgramaId(id).map(_.toJson)))
         

  lazy val selectString = programa
  def getDepartamento(implicit session: Session) = if(departamentoId.isDefined) DepartamentoQuery.byId(departamentoId.get) else None
  def getCategoriaInspeccion(implicit session: Session) = if(categoriaInspeccionId.isDefined) CategoriaInspeccionQuery.byId(categoriaInspeccionId.get) else None
  def getObjetoInspeccionTipo(implicit session: Session) = if(objetoInspeccionTipoId.isDefined) ObjetoInspeccionTipoQuery.byId(objetoInspeccionTipoId.get) else None
}



class Programas(tag: Tag) extends Table[Programa](tag, "programa") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def programa = column[String]("programa", O.Default(""))
  def acronimo = column[String]("acronimo", O.Default(""))
  def departamentoId = column[Option[Long]]("departamento_id", O.Default(None))
  def departamento = foreignKey("departamento_id_fk", departamentoId, DepartamentoQuery.tableQ)(_.id)
  def categoriaInspeccionId = column[Option[Long]]("categoria_inspeccion_id", O.Default(None))
  def categoriaInspeccion = foreignKey("categoria_inspeccion_id_fk", categoriaInspeccionId, CategoriaInspeccionQuery.tableQ)(_.id)
  def objetoInspeccionTipoId = column[Option[Long]]("objeto_inspeccion_tipo_id", O.Default(None))
  def objetoInspeccionTipo = foreignKey("objeto_inspeccion_tipo_id_fk", objetoInspeccionTipoId, ObjetoInspeccionTipoQuery.tableQ)(_.id)

  def * = (id.?, programa, acronimo, departamentoId, categoriaInspeccionId, objetoInspeccionTipoId).shaped <> (Programa.tupled, Programa.unapply)
}

class ProgramaQueryBase extends BaseDAO[Programa] {
  type DBTable = ProgramaMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byDepartamentoId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.departamentoId===i).list
    }.getOrElse(List())
  }
                            

  def byCategoriaInspeccionId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.categoriaInspeccionId===i).list
    }.getOrElse(List())
  }
                            

  def byObjetoInspeccionTipoId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.objetoInspeccionTipoId===i).list
    }.getOrElse(List())
  }
                            
}
case class ProgramaFormData(obj: Programa, programaTipoInspectors: List[ProgramaTipoInspectorFormData]){
  def update(updatedObj: Programa = obj)(implicit session: Session) = {
    //Delete elements that are not part of the form but they do exists in the database.
    ProgramaTipoInspectorQuery.byProgramaId(obj.id).filterNot{o => programaTipoInspectors.exists(_.obj.id == o.id)}.map{ProgramaTipoInspectorQuery.eliminar(_)}
    programaTipoInspectors.map{o => o.update(o.obj.copy(programaId = obj.id.get))}
    ProgramaQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Programa)(implicit session: Session) = {
    val id = ProgramaQuery.insert(insertedObj)
    programaTipoInspectors.map{o => o.insert(o.obj.copy(programaId = id))}
    id
  }
}
object ProgramaFormData{
  def apply(obj: Programa)(implicit session: Session) = {
    val programaTipoInspectors = ProgramaTipoInspectorQuery.byProgramaId(obj.id).map(ProgramaTipoInspectorFormData(_))
    new ProgramaFormData(obj, programaTipoInspectors)
  }
}
object ProgramaForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "programa" -> text,
      "acronimo" -> text,
      "departamentoId" -> optional(longNumber),
      "categoriaInspeccionId" -> optional(longNumber),
      "objetoInspeccionTipoId" -> optional(longNumber),
      "programaTipoInspectors" -> list(models.ProgramaTipoInspectorForm.form.mapping)
    )/*(Programa.apply)(Programa.unapply)*/
    ((id,programa,acronimo,departamentoId,categoriaInspeccionId,objetoInspeccionTipoId,programaTipoInspectors) => {
      ProgramaFormData(Programa(id, programa, acronimo, departamentoId, categoriaInspeccionId, objetoInspeccionTipoId), programaTipoInspectors)
    })((formData: ProgramaFormData) => {
      Some((formData.obj.id, formData.obj.programa, formData.obj.acronimo, formData.obj.departamentoId, formData.obj.categoriaInspeccionId, formData.obj.objetoInspeccionTipoId, formData.programaTipoInspectors))
    })
  )
}