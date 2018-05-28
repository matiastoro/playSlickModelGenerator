package models
import models.extensions.InspeccionExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Inspeccion(id: Option[Long] = None /*None*/,
                      inspeccion: String = "" /*None*/,
                      fechaInicio: DateTime = new DateTime() /*None*/,
                      fechaTermino: DateTime = new DateTime() /*None*/,
                      orden: Int = 0 /*None*/,
                      programaId: Option[Int] = None /*None*/,
                      actividadId: Long = 0 /*None*/,
                      objetoInspeccionId: Option[Int] = None /*None*/,
                      objetoInspeccionInstanciaId: Option[Int] = None /*None*/) extends InspeccionExtension{
  lazy val selectString = inspeccion
  def getActividad = ActividadConsulta.byId(actividadId)
}



class InspeccionMapeo(tag: Tag) extends Table[Inspeccion](tag, "inspeccion") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def inspeccion = column[String]("inspeccion", O.Default(""))
  def fechaInicio = column[DateTime]("fecha_inicio", O.Default(new DateTime()))
  def fechaTermino = column[DateTime]("fecha_termino", O.Default(new DateTime()))
  def orden = column[Int]("orden", O.Default(0))
  def programaId = column[Option[Int]]("programa_id", O.Default(None))
  def actividadId = column[Long]("actividad_id", O.Default(0))
  def actividad = foreignKey("actividad_id_fk", actividadId, ActividadQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)
  def objetoInspeccionId = column[Option[Int]]("objeto_inspeccion_id", O.Default(None))
  def objetoInspeccionInstanciaId = column[Option[Int]]("objeto_inspeccion_instancia_id", O.Default(None))

  def * = (id.?, inspeccion, fechaInicio, fechaTermino, orden, programaId, actividadId, objetoInspeccionId, objetoInspeccionInstanciaId).shaped <> (Inspeccion.tupled, Inspeccion.unapply)
}

class InspeccionConsulta extends BaseDAO[Inspeccion] {
  type DBTable = InspeccionMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byActividadId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.actividadId===i).list
    }.getOrElse(List())
  }
                            
}
case class InspeccionFormData(obj: Inspeccion){
  def update(updatedObj: Inspeccion = obj) = {

    InspeccionConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: Inspeccion) = {
    val id = InspeccionConsulta.insertar(insertedObj)

    id
  }
}

object InspeccionForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "inspeccion" -> text,
      "fechaInicio" -> jodaDate,
      "fechaTermino" -> jodaDate,
      "orden" -> number,
      "programaId" -> optional(number),
      "actividadId" -> optional(longNumber),
      "objetoInspeccionId" -> optional(number),
      "objetoInspeccionInstanciaId" -> optional(number)
    )/*(Inspeccion.apply)(Inspeccion.unapply)*/
    ((id,inspeccion,fechaInicio,fechaTermino,orden,programaId,actividadId,objetoInspeccionId,objetoInspeccionInstanciaId) => {
      InspeccionFormData(Inspeccion(id, inspeccion, fechaInicio, fechaTermino, orden, programaId, actividadId.getOrElse(0), objetoInspeccionId, objetoInspeccionInstanciaId))
    })((formData: InspeccionFormData) => {
      Some((formData.obj.id, formData.obj.inspeccion, formData.obj.fechaInicio, formData.obj.fechaTermino, formData.obj.orden, formData.obj.programaId, Some(formData.obj.actividadId), formData.obj.objetoInspeccionId, formData.obj.objetoInspeccionInstanciaId))
    })
  )
}