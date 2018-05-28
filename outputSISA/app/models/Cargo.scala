package models
import models.extensions.CargoExtension
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

case class Cargo(id: Option[Long] = None /*None*/,
                 cargo: String = "" /*None*/,
                 codigo: String = "" /*None*/,
                 organizacionId: Option[Long] = None /*None*/) extends CargoExtension{
  implicit val jsonFormat = Json.format[Cargo]
  def toJson = Json.toJson(this)
         

  lazy val selectString = cargo
  def getOrganizacion(implicit session: Session) = if(organizacionId.isDefined) OrganizacionConsulta.porId(organizacionId.get) else None
}



class CargoMapeo(tag: Tag) extends Table[Cargo](tag, "cargo") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def cargo = column[String]("cargo", O.Default(""))
  def codigo = column[String]("codigo", O.Default(""))
  def organizacionId = column[Option[Long]]("organizacion_id", O.Default(None))
  def organizacion = foreignKey("organizacion_id_fk", organizacionId, OrganizacionConsulta.tableQ)(_.id)

  def * = (id.?, cargo, codigo, organizacionId).shaped <> (Cargo.tupled, Cargo.unapply)
}

class CargoConsultaBase extends BaseDAO[Cargo] {
  type DBTable = CargoMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byOrganizacionId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.organizacionId===i).list
    }.getOrElse(List())
  }
                            
}
case class CargoFormData(obj: Cargo){
  def update(updatedObj: Cargo = obj)(implicit session: Session) = {

    CargoConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: Cargo)(implicit session: Session) = {
    val id = CargoConsulta.insertar(insertedObj)

    id
  }
}

object CargoForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "cargo" -> text,
      "codigo" -> text,
      "organizacionId" -> optional(longNumber)
    )/*(Cargo.apply)(Cargo.unapply)*/
    ((id,cargo,codigo,organizacionId) => {
      CargoFormData(Cargo(id, cargo, codigo, organizacionId))
    })((formData: CargoFormData) => {
      Some((formData.obj.id, formData.obj.cargo, formData.obj.codigo, formData.obj.organizacionId))
    })
  )
}