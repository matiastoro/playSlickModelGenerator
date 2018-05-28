package models
import models.extensions.PerfilExtension
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

case class Perfil(id: Option[Long] = None /*None*/,
                  perfil: String = "" /*None*/) extends PerfilExtension{
  implicit val jsonFormat = Json.format[Perfil]
  def toJson = Json.toJson(this)
         

  lazy val selectString = perfil

}



class Perfils(tag: Tag) extends Table[Perfil](tag, "perfil") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def perfil = column[String]("perfil", O.Default(""))

  def * = (id.?, perfil).shaped <> (Perfil.tupled, Perfil.unapply)
}

class PerfilQueryBase extends BaseDAO[Perfil] {
  type DBTable = PerfilMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class PerfilFormData(obj: Perfil){
  def update(updatedObj: Perfil = obj)(implicit session: Session) = {

    PerfilQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Perfil)(implicit session: Session) = {
    val id = PerfilQuery.insert(insertedObj)

    id
  }
}

object PerfilForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "perfil" -> text
    )/*(Perfil.apply)(Perfil.unapply)*/
    ((id,perfil) => {
      PerfilFormData(Perfil(id, perfil))
    })((formData: PerfilFormData) => {
      Some((formData.obj.id, formData.obj.perfil))
    })
  )
}