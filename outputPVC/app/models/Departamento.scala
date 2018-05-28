package models
import models.extensions.DepartamentoExtension
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

case class Departamento(id: Option[Long] = None /*None*/,
                        departamentoId: Long = 0 /*None*/,
                        jefeId: Int = 0 /*None*/) extends DepartamentoExtension{
  implicit val jsonFormat = Json.format[Departamento]
  def toJson = Json.toJson(this)
         

  lazy val selectString = id
  def getDepartamento(implicit session: Session) = DepartamentoQuery.byId(departamentoId)
}



class Departamentos(tag: Tag) extends Table[Departamento](tag, "departamento") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def departamentoId = column[Long]("departamento_id", O.Default(0))
  def departamento = foreignKey("departamento_id_fk", departamentoId, DepartamentoQuery.tableQ)(_.id onDelete: cascade)
  def jefeId = column[Int]("jefe_id", O.Default(0))

  def * = (id.?, departamentoId, jefeId).shaped <> (Departamento.tupled, Departamento.unapply)
}

class DepartamentoQueryBase extends BaseDAO[Departamento] {
  type DBTable = DepartamentoMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byDepartamentoId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.departamentoId===i).list
    }.getOrElse(List())
  }
                            
}
case class DepartamentoFormData(obj: Departamento){
  def update(updatedObj: Departamento = obj)(implicit session: Session) = {

    DepartamentoQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Departamento)(implicit session: Session) = {
    val id = DepartamentoQuery.insert(insertedObj)

    id
  }
}

object DepartamentoForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "departamentoId" -> longNumber,
      "jefeId" -> number
    )/*(Departamento.apply)(Departamento.unapply)*/
    ((id,departamentoId,jefeId) => {
      DepartamentoFormData(Departamento(id, departamentoId, jefeId))
    })((formData: DepartamentoFormData) => {
      Some((formData.obj.id, formData.obj.departamentoId, formData.obj.jefeId))
    })
  )
}