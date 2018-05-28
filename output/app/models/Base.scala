package models
import models.extensions.BaseExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Base(id: Option[Long] = None /*None*/,
                locationId: Option[Long] = None /*None*/,
                chapel: Option[String] = None /*None*/) extends BaseExtension{
  lazy val selectString = chapel
  def getLocation = if(locationId.isDefined) LocationConsulta.byId(locationId.get) else None
}



class BaseMapeo(tag: Tag) extends Table[Base](tag, "base") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def locationId = column[Option[Long]]("location_id", O.Default(None))
  def location = foreignKey("location_id_fk", locationId, LocationQuery.all)(_.id, onDelete=ForeignKeyAction.Cascade)
  def chapel = column[Option[String]]("chapel", O.Default(None))

  def * = (id.?, locationId, chapel).shaped <> (Base.tupled, Base.unapply)
}

class BaseConsulta extends BaseDAO[Base] {
  type DBTable = BaseMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byLocationId(id: Option[Long]) = database.withSession { implicit db: Session =>
    id.map{i =>
      all.filter(_.locationId===i).list
    }.getOrElse(List())
  }
                            
}
case class BaseFormData(obj: Base){
  def update(updatedObj: Base = obj)(implicit session: Session) = {

    BaseConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: Base)(implicit session: Session) = {
    val id = BaseConsulta.insertar(insertedObj)

    id
  }
}

object BaseForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "locationId" -> optional(longNumber),
      "chapel" -> optional(text)
    )/*(Base.apply)(Base.unapply)*/
    ((id,locationId,chapel) => {
      BaseFormData(Base(id, locationId, chapel))
    })((formData: BaseFormData) => {
      Some((formData.obj.id, formData.obj.locationId, formData.obj.chapel))
    })
  )
}