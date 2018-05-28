package models
import models.extensions.UserAerodromeExtension
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

case class UserAerodrome(id: Option[Long] = None /*None*/,
                         userId: Long = 0 /*None*/,
                         aerodromeId: Long = 0 /*None*/) extends UserAerodromeExtension{
  implicit val jsonFormat = Json.format[UserAerodrome]
  def toJson = Json.toJson(this)
         

  lazy val selectString = id
  def getUser(implicit session: Session) = UserQuery.byId(userId)
  def getAerodrome(implicit session: Session) = AerodromeQuery.byId(aerodromeId)
}



class UserAerodromes(tag: Tag) extends Table[UserAerodrome](tag, "user_aerodrome") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId", O.Default(0))
  def user = foreignKey("userId_fk", userId, UserQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def aerodromeId = column[Long]("aerodromeId", O.Default(0))
  def aerodrome = foreignKey("aerodromeId_fk", aerodromeId, AerodromeQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)

  def * = (id.?, userId, aerodromeId).shaped <> (UserAerodrome.tupled, UserAerodrome.unapply)
}

class UserAerodromeQueryBase extends BaseDAO[UserAerodrome] {
  type DBTable = UserAerodromeMapeo

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
case class UserAerodromeFormData(obj: UserAerodrome){
  def update(updatedObj: UserAerodrome = obj)(implicit session: Session) = {

    UserAerodromeQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: UserAerodrome)(implicit session: Session) = {
    val id = UserAerodromeQuery.insert(insertedObj)

    id
  }
}

object UserAerodromeForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "userId" -> optional(longNumber),
      "aerodromeId" -> longNumber
    )/*(UserAerodrome.apply)(UserAerodrome.unapply)*/
    ((id,userId,aerodromeId) => {
      UserAerodromeFormData(UserAerodrome(id, userId.getOrElse(0), aerodromeId))
    })((formData: UserAerodromeFormData) => {
      Some((formData.obj.id, Some(formData.obj.userId), formData.obj.aerodromeId))
    })
  )
}