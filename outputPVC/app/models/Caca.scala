package models
import models.extensions.CacaExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Caca(id: Option[Long] = None /*None*/) extends CacaExtension{
  lazy val selectString = id

}



class CacaMapping(tag: Tag) extends Table[Caca](tag, "caca") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def * = (id.?).shaped <> (Caca.tupled, Caca.unapply)
}

class CacaQueryBase extends DatabaseClient[Caca] {
  type DBTable = CacaMapping

  private[models] val all = {
    TableQuery[DBTable]
  }

}
case class CacaFormData(obj: Caca){
  def update(updatedObj: Caca = obj) = {

    CacaQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Caca) = {
    val id = CacaQuery.insert(insertedObj)

    id
  }
}

object CacaForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber)
    )/*(Caca.apply)(Caca.unapply)*/
    ((id) => {
      CacaFormData(Caca(id))
    })((formData: CacaFormData) => {
      Some((formData.obj.id))
    })
  )
}