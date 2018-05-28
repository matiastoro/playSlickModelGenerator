package models
import models.extensions.ItineraryUserAerodromeExtension
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

case class ItineraryUserAerodrome(id: Option[Long] = None /*None*/,
                                  userId: Long = 0 /*None*/,
                                  aerodromeId: Long = 0 /*None*/) extends ItineraryUserAerodromeExtension{
  implicit val jsonFormat = Json.format[ItineraryUserAerodrome]
  def toJson = Json.toJson(this)
         

  lazy val selectString = id
  def getUser(implicit session: Session) = UserQuery.byId(userId)
  def getAerodrome(implicit session: Session) = AerodromeQuery.byId(aerodromeId)
}



class ItineraryUserAerodromes(tag: Tag) extends Table[ItineraryUserAerodrome](tag, "itinerary_user_aerodrome") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId", O.Default(0))
  def user = foreignKey("userId_fk", userId, UserQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def aerodromeId = column[Long]("aerodromeId", O.Default(0))
  def aerodrome = foreignKey("aerodromeId_fk", aerodromeId, AerodromeQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)

  def * = (id.?, userId, aerodromeId).shaped <> (ItineraryUserAerodrome.tupled, ItineraryUserAerodrome.unapply)
}

class ItineraryUserAerodromeQueryBase extends BaseDAO[ItineraryUserAerodrome] {
  type DBTable = ItineraryUserAerodromeMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byUserId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.userId===i).list
    }.getOrElse(List())
  }
                            

  def byAerodromeId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.aerodromeId===i).list
    }.getOrElse(List())
  }
                            
}
case class ItineraryUserAerodromeFormData(obj: ItineraryUserAerodrome){
  def update(updatedObj: ItineraryUserAerodrome = obj)(implicit session: Session) = {

    ItineraryUserAerodromeQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: ItineraryUserAerodrome)(implicit session: Session) = {
    val id = ItineraryUserAerodromeQuery.insert(insertedObj)

    id
  }
}

object ItineraryUserAerodromeForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "userId" -> optional(longNumber),
      "aerodromeId" -> longNumber
    )/*(ItineraryUserAerodrome.apply)(ItineraryUserAerodrome.unapply)*/
    ((id,userId,aerodromeId) => {
      ItineraryUserAerodromeFormData(ItineraryUserAerodrome(id, userId.getOrElse(0), aerodromeId))
    })((formData: ItineraryUserAerodromeFormData) => {
      Some((formData.obj.id, Some(formData.obj.userId), formData.obj.aerodromeId))
    })
  )
}