package models
import models.extensions.RunwayExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Runway(id: Option[Long] = None /*None*/,
                  runway: String = "" /*None*/,
                  length: Int = 0 /*None*/,
                  runwayType: Int = 0 /*None*/) extends RunwayExtension{
  lazy val selectString = runway

}



class RunwayMapeo(tag: Tag) extends Table[Runway](tag, "runway") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def runway = column[String]("runway", O.Default(""))
  def length = column[Int]("length", O.Default(0))
  def runwayType = column[Int]("runway_type", O.Default(0))

  def * = (id.?, runway, length, runwayType).shaped <> (Runway.tupled, Runway.unapply)
}

class RunwayConsulta extends BaseDAO[Runway] {
  type DBTable = RunwayMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class RunwayFormData(obj: Runway){
  def update(updatedObj: Runway = obj)(implicit session: Session) = {

    RunwayConsulta.actualizarOInsertar(updatedObj)
  }
  def insert(insertedObj: Runway)(implicit session: Session) = {
    val id = RunwayConsulta.insertar(insertedObj)

    id
  }
}

object RunwayForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "runway" -> text,
      "length" -> number,
      "runwayType" -> number
    )/*(Runway.apply)(Runway.unapply)*/
    ((id,runway,length,runwayType) => {
      RunwayFormData(Runway(id, runway, length, runwayType))
    })((formData: RunwayFormData) => {
      Some((formData.obj.id, formData.obj.runway, formData.obj.length, formData.obj.runwayType))
    })
  )
}