package models
import models.extensions.AttachmentExtension
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

case class Attachment(id: Option[Long] = None /*None*/,
                      attachment: Option[String] = None /*None*/,
                      estado: Option[Int] = None /*None*/,
                      targetTable: String = "" /*None*/,
                      d: String = "" /*None*/) extends AttachmentExtension{
  implicit val jsonFormat = Json.format[Attachment]
  def toJson = Json.toJson(this)
         

  lazy val selectString = attachment

}



class AttachmentMapeo(tag: Tag) extends Table[Attachment](tag, "attachment") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def attachment = column[Option[String]]("attachment", O.Default(None))
  def estado = column[Option[Int]]("estado", O.Default(None))
  def targetTable = column[String]("target_table", O.Default(""))
  def d = column[String]("d", O.Default(""))

  def * = (id.?, attachment, estado, targetTable, d).shaped <> (Attachment.tupled, Attachment.unapply)
}

class AttachmentConsultaBase extends BaseDAO[Attachment] {
  type DBTable = AttachmentMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class AttachmentFormData(obj: Attachment){
  def update(updatedObj: Attachment = obj)(implicit session: Session) = {

    AttachmentConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: Attachment)(implicit session: Session) = {
    val id = AttachmentConsulta.insertar(insertedObj)

    id
  }
}

object AttachmentForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "attachment" -> optional(text),
      "estado" -> optional(number),
      "targetTable" -> text,
      "d" -> text
    )/*(Attachment.apply)(Attachment.unapply)*/
    ((id,attachment,estado,targetTable,d) => {
      AttachmentFormData(Attachment(id, attachment, estado, targetTable, d))
    })((formData: AttachmentFormData) => {
      Some((formData.obj.id, formData.obj.attachment, formData.obj.estado, formData.obj.targetTable, formData.obj.d))
    })
  )
}