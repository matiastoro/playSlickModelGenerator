package models
import models.extensions.AerodromeExtension
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

case class Aerodrome(id: Option[Long] = None /*None*/,
                     icaoCode: String = "" /*None*/) extends AerodromeExtension{
  implicit val jsonFormat = Json.format[Aerodrome]
  def toJson = Json.toJson(this)
         

  lazy val selectString = icaoCode

}



class Aerodromes(tag: Tag) extends Table[Aerodrome](tag, "aerodrome") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def icaoCode = column[String]("icaoCode", O.Default(""))

  def * = (id.?, icaoCode).shaped <> (Aerodrome.tupled, Aerodrome.unapply)
}

class AerodromeQueryBase extends BaseDAO[Aerodrome] {
  type DBTable = AerodromeMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class AerodromeFormData(obj: Aerodrome){
  def update(updatedObj: Aerodrome = obj)(implicit session: Session) = {

    AerodromeQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Aerodrome)(implicit session: Session) = {
    val id = AerodromeQuery.insert(insertedObj)

    id
  }
}

object AerodromeForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "icaoCode" -> text
    )/*(Aerodrome.apply)(Aerodrome.unapply)*/
    ((id,icaoCode) => {
      AerodromeFormData(Aerodrome(id, icaoCode))
    })((formData: AerodromeFormData) => {
      Some((formData.obj.id, formData.obj.icaoCode))
    })
  )
}