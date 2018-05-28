package models
import models.extensions.OrganizacionExtension
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

case class Organizacion(id: Option[Long] = None /*None*/,
                        organizacion: String = "" /*None*/) extends OrganizacionExtension{
  implicit val jsonFormat = Json.format[Organizacion]
  def toJson = Json.toJson(this)
         

  lazy val selectString = organizacion

}



class OrganizacionMapeo(tag: Tag) extends Table[Organizacion](tag, "organizacion") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def organizacion = column[String]("organizacion", O.Default(""))

  def * = (id.?, organizacion).shaped <> (Organizacion.tupled, Organizacion.unapply)
}

class OrganizacionConsultaBase extends BaseDAO[Organizacion] {
  type DBTable = OrganizacionMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class OrganizacionFormData(obj: Organizacion){
  def update(updatedObj: Organizacion = obj)(implicit session: Session) = {

    OrganizacionConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: Organizacion)(implicit session: Session) = {
    val id = OrganizacionConsulta.insertar(insertedObj)

    id
  }
}

object OrganizacionForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "organizacion" -> text
    )/*(Organizacion.apply)(Organizacion.unapply)*/
    ((id,organizacion) => {
      OrganizacionFormData(Organizacion(id, organizacion))
    })((formData: OrganizacionFormData) => {
      Some((formData.obj.id, formData.obj.organizacion))
    })
  )
}