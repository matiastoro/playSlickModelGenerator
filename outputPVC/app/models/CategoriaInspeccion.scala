package models
import models.extensions.CategoriaInspeccionExtension
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

case class CategoriaInspeccion(id: Option[Long] = None /*None*/) extends CategoriaInspeccionExtension{
  implicit val jsonFormat = Json.format[CategoriaInspeccion]
  def toJson = Json.toJson(this)
         

  lazy val selectString = id

}



class CategoriaInspeccions(tag: Tag) extends Table[CategoriaInspeccion](tag, "categoria_inspeccion") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def * = (id.?).shaped <> (CategoriaInspeccion.tupled, CategoriaInspeccion.unapply)
}

class CategoriaInspeccionQueryBase extends BaseDAO[CategoriaInspeccion] {
  type DBTable = CategoriaInspeccionMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class CategoriaInspeccionFormData(obj: CategoriaInspeccion){
  def update(updatedObj: CategoriaInspeccion = obj)(implicit session: Session) = {

    CategoriaInspeccionQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: CategoriaInspeccion)(implicit session: Session) = {
    val id = CategoriaInspeccionQuery.insert(insertedObj)

    id
  }
}

object CategoriaInspeccionForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber)
    )/*(CategoriaInspeccion.apply)(CategoriaInspeccion.unapply)*/
    ((id) => {
      CategoriaInspeccionFormData(CategoriaInspeccion(id))
    })((formData: CategoriaInspeccionFormData) => {
      Some((formData.obj.id))
    })
  )
}