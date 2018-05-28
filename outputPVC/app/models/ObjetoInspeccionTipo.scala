package models
import models.extensions.ObjetoInspeccionTipoExtension
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

case class ObjetoInspeccionTipo(id: Option[Long] = None /*None*/,
                                cantidadMes: Int = 0 /*None*/,
                                abreviacion: String = "" /*None*/) extends ObjetoInspeccionTipoExtension{
  implicit val jsonFormat = Json.format[ObjetoInspeccionTipo]
  def toJson = Json.toJson(this)
         

  lazy val selectString = abreviacion

}



class ObjetoInspeccionTipos(tag: Tag) extends Table[ObjetoInspeccionTipo](tag, "objeto_inspeccion_tipo") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def cantidadMes = column[Int]("cantidad_mes", O.Default(0))
  def abreviacion = column[String]("abreviacion", O.Default(""))

  def * = (id.?, cantidadMes, abreviacion).shaped <> (ObjetoInspeccionTipo.tupled, ObjetoInspeccionTipo.unapply)
}

class ObjetoInspeccionTipoQueryBase extends BaseDAO[ObjetoInspeccionTipo] {
  type DBTable = ObjetoInspeccionTipoMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class ObjetoInspeccionTipoFormData(obj: ObjetoInspeccionTipo){
  def update(updatedObj: ObjetoInspeccionTipo = obj)(implicit session: Session) = {

    ObjetoInspeccionTipoQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: ObjetoInspeccionTipo)(implicit session: Session) = {
    val id = ObjetoInspeccionTipoQuery.insert(insertedObj)

    id
  }
}

object ObjetoInspeccionTipoForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "cantidadMes" -> number,
      "abreviacion" -> text
    )/*(ObjetoInspeccionTipo.apply)(ObjetoInspeccionTipo.unapply)*/
    ((id,cantidadMes,abreviacion) => {
      ObjetoInspeccionTipoFormData(ObjetoInspeccionTipo(id, cantidadMes, abreviacion))
    })((formData: ObjetoInspeccionTipoFormData) => {
      Some((formData.obj.id, formData.obj.cantidadMes, formData.obj.abreviacion))
    })
  )
}