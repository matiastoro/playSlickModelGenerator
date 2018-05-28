package models
import models.extensions.UsuarioCargoExtension
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

case class UsuarioCargo(id: Option[Long] = None /*None*/,
                        cargoId: Long = 0 /*None*/,
                        usuarioId: Long = 0 /*None*/,
                        aerodromo: Option[String] = None /*None*/) extends UsuarioCargoExtension{
  implicit val jsonFormat = Json.format[UsuarioCargo]
  def toJson = Json.toJson(this)
         

  lazy val selectString = aerodromo
  def getCargo(implicit session: Session) = CargoConsulta.porId(cargoId)
  def getUsuario(implicit session: Session) = UsuarioConsulta.porId(usuarioId)
}



class UsuarioCargoMapeo(tag: Tag) extends Table[UsuarioCargo](tag, "usuario_cargo") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def cargoId = column[Long]("cargo_id", O.Default(0))
  def cargo = foreignKey("cargo_id_fk", cargoId, CargoConsulta.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def usuarioId = column[Long]("usuario_id", O.Default(0))
  def usuario = foreignKey("usuario_id_fk", usuarioId, UsuarioConsulta.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def aerodromo = column[Option[String]]("aerodromo", O.Default(None))

  def * = (id.?, cargoId, usuarioId, aerodromo).shaped <> (UsuarioCargo.tupled, UsuarioCargo.unapply)
}

class UsuarioCargoConsultaBase extends BaseDAO[UsuarioCargo] {
  type DBTable = UsuarioCargoMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byCargoId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.cargoId===i).list
    }.getOrElse(List())
  }
                            

  def byUsuarioId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.usuarioId===i).list
    }.getOrElse(List())
  }
                            
}
case class UsuarioCargoFormData(obj: UsuarioCargo){
  def update(updatedObj: UsuarioCargo = obj)(implicit session: Session) = {

    UsuarioCargoConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: UsuarioCargo)(implicit session: Session) = {
    val id = UsuarioCargoConsulta.insertar(insertedObj)

    id
  }
}

object UsuarioCargoForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "cargoId" -> longNumber,
      "usuarioId" -> optional(longNumber),
      "aerodromo" -> optional(text)
    )/*(UsuarioCargo.apply)(UsuarioCargo.unapply)*/
    ((id,cargoId,usuarioId,aerodromo) => {
      UsuarioCargoFormData(UsuarioCargo(id, cargoId, usuarioId.getOrElse(0), aerodromo))
    })((formData: UsuarioCargoFormData) => {
      Some((formData.obj.id, formData.obj.cargoId, Some(formData.obj.usuarioId), formData.obj.aerodromo))
    })
  )
}