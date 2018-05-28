package models
import models.extensions.ItineraryUserCompanyExtension
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

case class ItineraryUserCompany(id: Option[Long] = None /*None*/,
                                userId: Long = 0 /*None*/,
                                companyId: Long = 0 /*None*/) extends ItineraryUserCompanyExtension{
  implicit val jsonFormat = Json.format[ItineraryUserCompany]
  def toJson = Json.toJson(this)
         

  lazy val selectString = id
  def getUser(implicit session: Session) = UserQuery.byId(userId)
  def getCompany(implicit session: Session) = CompanyQuery.byId(companyId)
}



class ItineraryUserCompanys(tag: Tag) extends Table[ItineraryUserCompany](tag, "itinerary_user_company") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId", O.Default(0))
  def user = foreignKey("userId_fk", userId, UserQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def companyId = column[Long]("companyId", O.Default(0))
  def company = foreignKey("companyId_fk", companyId, CompanyQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)

  def * = (id.?, userId, companyId).shaped <> (ItineraryUserCompany.tupled, ItineraryUserCompany.unapply)
}

class ItineraryUserCompanyQueryBase extends BaseDAO[ItineraryUserCompany] {
  type DBTable = ItineraryUserCompanyMapeo

  val tableQ = {
    TableQuery[DBTable]
  }
  def byUserId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.userId===i).list
    }.getOrElse(List())
  }
                            

  def byCompanyId(id: Option[Long])(implicit session: Session) = {
    id.map{i =>
      tableQ.filter(_.companyId===i).list
    }.getOrElse(List())
  }
                            
}
case class ItineraryUserCompanyFormData(obj: ItineraryUserCompany){
  def update(updatedObj: ItineraryUserCompany = obj)(implicit session: Session) = {

    ItineraryUserCompanyQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: ItineraryUserCompany)(implicit session: Session) = {
    val id = ItineraryUserCompanyQuery.insert(insertedObj)

    id
  }
}

object ItineraryUserCompanyForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "userId" -> optional(longNumber),
      "companyId" -> longNumber
    )/*(ItineraryUserCompany.apply)(ItineraryUserCompany.unapply)*/
    ((id,userId,companyId) => {
      ItineraryUserCompanyFormData(ItineraryUserCompany(id, userId.getOrElse(0), companyId))
    })((formData: ItineraryUserCompanyFormData) => {
      Some((formData.obj.id, Some(formData.obj.userId), formData.obj.companyId))
    })
  )
}