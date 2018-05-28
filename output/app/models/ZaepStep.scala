package models
import models.extensions.ZaepStepExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class ZaepStep(id: Option[Long] = None /*None*/,
                    zaepId: Long = 0 /*None*/,
                    tpe: Int = 0 /*None*/,
                    latitude: Double = 0 /*None*/,
                    longitude: Double = 0 /*None*/,
                    radius: Option[Double] = None /*None*/,
                    orientation: Option[Int] = None /*None*/,
                    number: Int = 0 /*None*/) extends ZaepStepExtension{
  lazy val selectString = id
  def getZaep = ZaepQuery.byId(zaepId)
}



class ZaepStepMapping(tag: Tag) extends Table[ZaepStep](tag, "zona_prohibida_paso") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def zaepId = column[Long]("zona_prohibida_id", O.Default(0))
  def zaep = foreignKey("zona_prohibida_id_fk", zaepId, ZaepQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)
  def tpe = column[Int]("tipo", O.Default(0))
  def latitude = column[Double]("latitud", O.Default(0))
  def longitude = column[Double]("longitud", O.Default(0))
  def radius = column[Option[Double]]("radio", O.Default(None))
  def orientation = column[Option[Int]]("orientacion", O.Default(None))
  def number = column[Int]("numero", O.Default(0))

  def * = (id.?, zaepId, tpe, latitude, longitude, radius, orientation, number).shaped <> (ZaepStep.tupled, ZaepStep.unapply)
}

class ZaepStepQueryBase extends DatabaseClient[ZaepStep] {
  type DBTable = ZaepStepMapping

  private[models] val all = {
    TableQuery[DBTable]
  }
  def byZaepId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.zaepId===i).list
    }.getOrElse(List())
  }
                            
}
case class ZaepStepFormData(obj: ZaepStep){
  def update(updatedObj: ZaepStep = obj) = {

    ZaepStepQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: ZaepStep) = {
    val id = ZaepStepQuery.insert(insertedObj)

    id
  }
}

object ZaepStepForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "zaepId" -> optional(longNumber),
      "tpe" -> number,
      "latitude" -> of(doubleFormat),
      "longitude" -> of(doubleFormat),
      "radius" -> optional(of(doubleFormat)),
      "orientation" -> optional(number),
      "number" -> number
    )/*(ZaepStep.apply)(ZaepStep.unapply)*/
    ((id,zaepId,tpe,latitude,longitude,radius,orientation,number) => {
      ZaepStepFormData(ZaepStep(id, zaepId.getOrElse(0), tpe, latitude, longitude, radius, orientation, number))
    })((formData: ZaepStepFormData) => {
      Some((formData.obj.id, Some(formData.obj.zaepId), formData.obj.tpe, formData.obj.latitude, formData.obj.longitude, formData.obj.radius, formData.obj.orientation, formData.obj.number))
    })
  )
}