package models
import models.extensions.LalaExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Lala(id: Option[Long] = None /*None*/) extends LalaExtension{
  lazy val selectString = id

}



class LalaMapping(tag: Tag) extends Table[Lala](tag, "lala") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def * = (id.?).shaped <> (Lala.tupled, Lala.unapply)
}

class LalaQueryBase extends DatabaseClient[Lala] {
  type DBTable = LalaMapping

  private[models] val all = {
    TableQuery[DBTable]
  }

}
case class LalaFormData(obj: Lala, Cacas: List[CacaFormData]){
  def update(updatedObj: Lala = obj) = {
    //Delete elements that are not part of the form but they do exists in the database.
    CacaQuery.byLalaId(obj.id).filterNot{o => Cacas.exists(_.obj.id == o.id)}.map{CacaQuery.delete(_)}
    Cacas.map{o => o.update(o.obj.copy(lalaId = obj.id.get))}
    LalaQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Lala) = {
    val id = LalaQuery.insert(insertedObj)
    Cacas.map{o => o.insert(o.obj.copy(lalaId = id))}
    id
  }
}
object LalaFormData{
  def apply(obj: Lala) = {
    val Cacas = CacaQuery.byLalaId(obj.id).map(CacaFormData(_))
    new LalaFormData(obj, Cacas)
  }
}
object LalaForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "Cacas" -> list(models.CacaForm.form.mapping)
    )/*(Lala.apply)(Lala.unapply)*/
    ((id,Cacas) => {
      LalaFormData(Lala(id), Cacas)
    })((formData: LalaFormData) => {
      Some((formData.obj.id, formData.Cacas))
    })
  )
}