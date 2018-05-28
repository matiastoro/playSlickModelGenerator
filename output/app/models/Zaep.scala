package models
import models.extensions.ZaepExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Zaep(id: Option[Long] = None /*None*/,
                identification: String = "" /*None*/,
                zaep: String = "" /*None*/,
                limits: Option[String] = None /*None*/,
                tpe: Int = 0 /*None*/,
                zoomLevel: Option[Int] = None /*None*/,
                createdAt: Option[DateTime] = None /*Hidden*/) extends ZaepExtension{
  lazy val selectString = zaep

}



class ZaepMapping(tag: Tag) extends Table[Zaep](tag, "zona_prohibida") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def identification = column[String]("identificacion", O.Default(""))
  def zaep = column[String]("nombre", O.Default(""))
  def limits = column[Option[String]]("limites", O.Default(None))
  def tpe = column[Int]("tipo", O.Default(0))
  def zoomLevel = column[Option[Int]]("zoom_level", O.Default(None))
  def createdAt = column[Option[DateTime]]("created_at", O.Default(None))

  def * = (id.?, identification, zaep, limits, tpe, zoomLevel, createdAt).shaped <> (Zaep.tupled, Zaep.unapply)
}

class ZaepQueryBase extends DatabaseClient[Zaep] {
  type DBTable = ZaepMapping

  private[models] val all = {
    TableQuery[DBTable]
  }

}
case class ZaepFormData(obj: Zaep, ZaepSteps: List[ZaepStepFormData]){
  def update(updatedObj: Zaep = obj) = {
    //Delete elements that are not part of the form but they do exists in the database.
    ZaepStepQuery.byZaepId(obj.id).filterNot{o => ZaepSteps.exists(_.obj.id == o.id)}.map{ZaepStepQuery.delete(_)}
    ZaepSteps.map{o => o.update(o.obj.copy(zaepId = obj.id.get))}
    ZaepQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Zaep) = {
    val id = ZaepQuery.insert(insertedObj)
    ZaepSteps.map{o => o.insert(o.obj.copy(zaepId = id))}
    id
  }
}
object ZaepFormData{
  def apply(obj: Zaep) = {
    val ZaepSteps = ZaepStepQuery.byZaepId(obj.id).map(ZaepStepFormData(_))
    new ZaepFormData(obj, ZaepSteps)
  }
}
object ZaepForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "identification" -> text,
      "zaep" -> text,
      "limits" -> optional(text),
      "tpe" -> number,
      "zoomLevel" -> optional(number),
      "ZaepSteps" -> list(models.ZaepStepForm.form.mapping)
    )/*(Zaep.apply)(Zaep.unapply)*/
    ((id,identification,zaep,limits,tpe,zoomLevel,ZaepSteps) => {
      ZaepFormData(Zaep(id, identification, zaep, limits, tpe, zoomLevel, Some(new DateTime())), ZaepSteps)
    })((formData: ZaepFormData) => {
      Some((formData.obj.id, formData.obj.identification, formData.obj.zaep, formData.obj.limits, formData.obj.tpe, formData.obj.zoomLevel, formData.ZaepSteps))
    })
  )
}