package models
import models.extensions.SeasonExtension
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

case class Season(id: Option[Long] = None /*None*/,
                  season: Int = 0 /*None*/,
                  from: DateTime = new DateTime() /*None*/,
                  to: DateTime = new DateTime() /*None*/) extends SeasonExtension{
  implicit val jsonFormat = Json.format[Season]
  def toJson = Json.toJson(this)
         

  lazy val selectString = season

}



class Seasons(tag: Tag) extends Table[Season](tag, "season") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def season = column[Int]("season", O.Default(0))
  def from = column[DateTime]("from", O.Default(new DateTime()))
  def to = column[DateTime]("to", O.Default(new DateTime()))

  def * = (id.?, season, from, to).shaped <> (Season.tupled, Season.unapply)
}

class SeasonQueryBase extends BaseDAO[Season] {
  type DBTable = SeasonMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class SeasonFormData(obj: Season){
  def update(updatedObj: Season = obj)(implicit session: Session) = {

    SeasonQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Season)(implicit session: Session) = {
    val id = SeasonQuery.insert(insertedObj)

    id
  }
}

object SeasonForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "season" -> number,
      "from" -> jodaDate,
      "to" -> jodaDate
    )/*(Season.apply)(Season.unapply)*/
    ((id,season,from,to) => {
      SeasonFormData(Season(id, season, from, to))
    })((formData: SeasonFormData) => {
      Some((formData.obj.id, formData.obj.season, formData.obj.from, formData.obj.to))
    })
  )
}