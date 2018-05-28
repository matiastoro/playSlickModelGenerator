package models
import models.extensions.ActividadExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Actividad(id: Option[Long] = None /*None*/,
                     estado: Int = 0 /*None*/,
                     orden: Int = 0 /*None*/,
                     numeroInspectores: Int = 0 /*None*/,
                     programada: Boolean = false /*None*/,
                     bloqueHorarioId: Option[Int] = None /*None*/,
                     duracion: Option[Int] = None /*None*/,
                     tipoActividadId: Option[Int] = None /*None*/,
                     mes: Option[Int] = None /*None*/,
                     anio: Option[Int] = None /*None*/,
                     observaciones: Option[String] = None /*None*/) extends ActividadExtension{
  lazy val selectString = observaciones

}



class ActividadMapeo(tag: Tag) extends Table[Actividad](tag, "actividad") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def estado = column[Int]("estado", O.Default(0))
  def orden = column[Int]("orden", O.Default(0))
  def numeroInspectores = column[Int]("numero_inspectores", O.Default(0))
  def programada = column[Boolean]("programada", O.Default(false))
  def bloqueHorarioId = column[Option[Int]]("bloque_horario_id", O.Default(None))
  def duracion = column[Option[Int]]("duracion", O.Default(None))
  def tipoActividadId = column[Option[Int]]("tipo_actividad_id", O.Default(None))
  def mes = column[Option[Int]]("mes", O.Default(None))
  def anio = column[Option[Int]]("anio", O.Default(None))
  def observaciones = column[Option[String]]("observaciones", O.Default(None))

  def * = (id.?, estado, orden, numeroInspectores, programada, bloqueHorarioId, duracion, tipoActividadId, mes, anio, observaciones).shaped <> (Actividad.tupled, Actividad.unapply)
}

class ActividadConsulta extends BaseDAO[Actividad] {
  type DBTable = ActividadMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class ActividadFormData(obj: Actividad, Inspeccions: List[InspeccionFormData]){
  def update(updatedObj: Actividad = obj) = {
    //Delete elements that are not part of the form but they do exists in the database.
    InspeccionConsulta.byActividadId(obj.id).filterNot{o => Inspeccions.exists(_.obj.id == o.id)}.map{InspeccionConsulta.delete(_)}
    Inspeccions.map{o => o.update(o.obj.copy(actividadId = obj.id.get))}
    ActividadConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: Actividad) = {
    val id = ActividadConsulta.insertar(insertedObj)
    Inspeccions.map{o => o.insertar(o.obj.copy(actividadId = id))}
    id
  }
}
object ActividadFormData{
  def apply(obj: Actividad) = {
    val Inspeccions = InspeccionConsulta.byActividadId(obj.id).map(InspeccionFormData(_))
    new ActividadFormData(obj, Inspeccions)
  }
}
object ActividadForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "estado" -> number,
      "orden" -> number,
      "numeroInspectores" -> number,
      "programada" -> boolean,
      "bloqueHorarioId" -> optional(number),
      "duracion" -> optional(number),
      "tipoActividadId" -> optional(number),
      "mes" -> optional(number),
      "anio" -> optional(number),
      "observaciones" -> optional(text),
      "Inspeccions" -> list(models.InspeccionForm.form.mapping)
    )/*(Actividad.apply)(Actividad.unapply)*/
    ((id,estado,orden,numeroInspectores,programada,bloqueHorarioId,duracion,tipoActividadId,mes,anio,observaciones,Inspeccions) => {
      ActividadFormData(Actividad(id, estado, orden, numeroInspectores, programada, bloqueHorarioId, duracion, tipoActividadId, mes, anio, observaciones), Inspeccions)
    })((formData: ActividadFormData) => {
      Some((formData.obj.id, formData.obj.estado, formData.obj.orden, formData.obj.numeroInspectores, formData.obj.programada, formData.obj.bloqueHorarioId, formData.obj.duracion, formData.obj.tipoActividadId, formData.obj.mes, formData.obj.anio, formData.obj.observaciones, formData.Inspeccions))
    })
  )
}