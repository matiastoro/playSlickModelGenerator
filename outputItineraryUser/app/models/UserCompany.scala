package models
import models.extensions.UserCompanyExtension
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

case class UserCompany(id: Option[Long] = None /*None*/,
                       userId: Long = 0 /*None*/,
                       companyId: Long = 0 /*None*/) extends UserCompanyExtension{
  implicit val jsonFormat = Json.format[UserCompany]
  def toJson = Json.toJson(this)
         

  lazy val selectString = id
  def getUser(implicit session: Session) = UserQuery.byId(userId)
  def getCompany(implicit session: Session) = CompanyQuery.byId(companyId)
}



class UserCompanys(tag: Tag) extends Table[UserCompany](tag, "user_company") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId", O.Default(0))
  def user = foreignKey("userId_fk", userId, UserQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)
  def companyId = column[Long]("companyId", O.Default(0))
  def company = foreignKey("companyId_fk", companyId, CompanyQuery.tableQ)(_.id, onDelete=ForeignKeyAction.Cascade)

  def * = (id.?, userId, companyId).shaped <> (UserCompany.tupled, UserCompany.unapply)
}

class UserCompanyQueryBase extends BaseDAO[UserCompany] {
  type DBTable = UserCompanyMapeo

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
case class UserCompanyFormData(obj: UserCompany){
  def update(updatedObj: UserCompany = obj)(implicit session: Session) = {

    UserCompanyQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: UserCompany)(implicit session: Session) = {
    val id = UserCompanyQuery.insert(insertedObj)

    id
  }
}

object UserCompanyForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "userId" -> optional(longNumber),
      "companyId" -> longNumber
    )/*(UserCompany.apply)(UserCompany.unapply)*/
    ((id,userId,companyId) => {
      UserCompanyFormData(UserCompany(id, userId.getOrElse(0), companyId))
    })((formData: UserCompanyFormData) => {
      Some((formData.obj.id, Some(formData.obj.userId), formData.obj.companyId))
    })
  )
}