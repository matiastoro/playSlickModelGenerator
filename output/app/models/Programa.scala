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

case class Programa(id: Option[Long] = None /*None*/,
                    programa: String = "" /*None*/,
                    acronimo: String = "" /*None*/,
                    departamentoId: Long = 0 /*None*/,
                    objetoInspeccionTipoId: Long = 0 /*None*/) extends ProgramaExtension{
  lazy val selectString = programa

}



class ProgramaMapping(tag: Tag) extends Table[Programa](tag, "programa") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def programa = column[String]("programa", O.Default(""))
  def acronimo = column[String]("acronimo", O.Default(""))
  def departamentoId = column[Long]("departamentoId", O.Default(0))
  def objetoInspeccionTipoId = column[Long]("objeto_inspeccion_tipo_id", O.Default(0))

  def * = (id.?, programa, acronimo, departamentoId, objetoInspeccionTipoId).shaped <> (Programa.tupled, Programa.unapply)
}

class ProgramaQueryBase extends DatabaseClient[Programa] {
  type DBTable = ProgramaMapping

  private[models] val all = {
    TableQuery[DBTable]
  }

}
case class ProgramaFormData(obj: Programa, ProgramaTipoInspectors: List[ProgramaTipoInspectorFormData]){
  def update(updatedObj: Programa = obj) = {
    //Delete elements that are not part of the form but they do exists in the database.
    ProgramaTipoInspectorQuery.byProgramaId(obj.id).filterNot{o => ProgramaTipoInspectors.exists(_.obj.id == o.id)}.map{ProgramaTipoInspectorQuery.delete(_)}
    ProgramaTipoInspectors.map{o => o.update(o.obj.copy(programaId = obj.id.get))}
    ProgramaQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Programa) = {
    val id = ProgramaQuery.insert(insertedObj)
    ProgramaTipoInspectors.map{o => o.insert(o.obj.copy(programaId = id))}
    id
  }
}
object ProgramaFormData{
  def apply(obj: Programa) = {
    val ProgramaTipoInspectors = ProgramaTipoInspectorQuery.byProgramaId(obj.id).map(ProgramaTipoInspectorFormData(_))
    new ProgramaFormData(obj, ProgramaTipoInspectors)
  }
}
object ProgramaForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "programa" -> text,
      "acronimo" -> text,
      "departamentoId" -> longNumber,
      "objetoInspeccionTipoId" -> longNumber,
      "ProgramaTipoInspectors" -> list(models.ProgramaTipoInspectorForm.form.mapping)
    )/*(Programa.apply)(Programa.unapply)*/
    ((id,programa,acronimo,departamentoId,objetoInspeccionTipoId,ProgramaTipoInspectors) => {
      ProgramaFormData(Programa(id, programa, acronimo, departamentoId, objetoInspeccionTipoId), ProgramaTipoInspectors)
    })((formData: ProgramaFormData) => {
      Some((formData.obj.id, formData.obj.programa, formData.obj.acronimo, formData.obj.departamentoId, formData.obj.objetoInspeccionTipoId, formData.ProgramaTipoInspectors))
    })
  )
}