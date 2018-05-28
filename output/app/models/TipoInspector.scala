package models
import models.extensions.TipoInspectorExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class TipoInspector(id: Option[Long] = None /*None*/,
                         tipo: String = "" /*None*/) extends TipoInspectorExtension{
  lazy val selectString = tipo

}



class TipoInspectorMapping(tag: Tag) extends Table[TipoInspector](tag, "tipo_inspector") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def tipo = column[String]("tipo", O.Default(""))

  def * = (id.?, tipo).shaped <> (TipoInspector.tupled, TipoInspector.unapply)
}

class TipoInspectorQueryBase extends DatabaseClient[TipoInspector] {
  type DBTable = TipoInspectorMapping

  private[models] val all = {
    TableQuery[DBTable]
  }

}
case class TipoInspectorFormData(obj: TipoInspector){
  def update(updatedObj: TipoInspector = obj) = {

    TipoInspectorQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: TipoInspector) = {
    val id = TipoInspectorQuery.insert(insertedObj)

    id
  }
}

object TipoInspectorForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "tipo" -> text
    )/*(TipoInspector.apply)(TipoInspector.unapply)*/
    ((id,tipo) => {
      TipoInspectorFormData(TipoInspector(id, tipo))
    })((formData: TipoInspectorFormData) => {
      Some((formData.obj.id, formData.obj.tipo))
    })
  )
}