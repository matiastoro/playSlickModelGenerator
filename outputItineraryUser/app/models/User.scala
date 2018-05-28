package models
import models.extensions.UserExtension
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

case class User(id: Option[Long] = None /*None*/,
                username: String = "" /*None*/,
                password: String = "" /*None*/,
                name: String = "" /*None*/,
                lastName: String = "" /*None*/,
                lastName2: String = "" /*None*/,
                email: String = "" /*None*/,
                phone: String = "" /*None*/,
                secretQuestion: String = "" /*None*/,
                secretAnswer: String = "" /*None*/) extends UserExtension{
  implicit val jsonFormat = Json.format[User]
  def toJson(implicit session: Session) = Json.toJson(this).as[JsObject] + 
      ("userProfiles" -> Json.toJson(UserProfileQuery.byUserId(id).map(_.toJson)))+
     ("itineraryUserAerodromes" -> Json.toJson(ItineraryUserAerodromeQuery.byUserId(id).map(_.toJson)))+
     ("itineraryUserCompanys" -> Json.toJson(ItineraryUserCompanyQuery.byUserId(id).map(_.toJson)))
         

  lazy val selectString = name

}



class Users(tag: Tag) extends Table[User](tag, "user") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username", O.Default(""))
  def password = column[String]("password", O.Default(""))
  def name = column[String]("name", O.Default(""))
  def lastName = column[String]("lastName", O.Default(""))
  def lastName2 = column[String]("lastName2", O.Default(""))
  def email = column[String]("email", O.Default(""))
  def phone = column[String]("phone", O.Default(""))
  def secretQuestion = column[String]("secretQuestion", O.Default(""))
  def secretAnswer = column[String]("secretAnswer", O.Default(""))

  def * = (id.?, username, password, name, lastName, lastName2, email, phone, secretQuestion, secretAnswer).shaped <> (User.tupled, User.unapply)
}

class UserQueryBase extends BaseDAO[User] {
  type DBTable = UserMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class UserFormData(obj: User, userProfiles: List[UserProfileFormData], itineraryUserAerodromes: List[ItineraryUserAerodromeFormData], itineraryUserCompanys: List[ItineraryUserCompanyFormData]){
  def update(updatedObj: User = obj)(implicit session: Session) = {
    //Delete elements that are not part of the form but they do exists in the database.
    UserProfileQuery.byUserId(obj.id).filterNot{o => userProfiles.exists(_.obj.id == o.id)}.map{UserProfileQuery.eliminar(_)}
    userProfiles.map{o => o.update(o.obj.copy(userId = obj.id.get))}
    //Delete elements that are not part of the form but they do exists in the database.
    ItineraryUserAerodromeQuery.byUserId(obj.id).filterNot{o => itineraryUserAerodromes.exists(_.obj.id == o.id)}.map{ItineraryUserAerodromeQuery.eliminar(_)}
    itineraryUserAerodromes.map{o => o.update(o.obj.copy(userId = obj.id.get))}
    //Delete elements that are not part of the form but they do exists in the database.
    ItineraryUserCompanyQuery.byUserId(obj.id).filterNot{o => itineraryUserCompanys.exists(_.obj.id == o.id)}.map{ItineraryUserCompanyQuery.eliminar(_)}
    itineraryUserCompanys.map{o => o.update(o.obj.copy(userId = obj.id.get))}
    UserQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: User)(implicit session: Session) = {
    val id = UserQuery.insert(insertedObj)
    userProfiles.map{o => o.insert(o.obj.copy(userId = id))}
    itineraryUserAerodromes.map{o => o.insert(o.obj.copy(userId = id))}
    itineraryUserCompanys.map{o => o.insert(o.obj.copy(userId = id))}
    id
  }
}
object UserFormData{
  def apply(obj: User)(implicit session: Session) = {
    val userProfiles = UserProfileQuery.byUserId(obj.id).map(UserProfileFormData(_))
    val itineraryUserAerodromes = ItineraryUserAerodromeQuery.byUserId(obj.id).map(ItineraryUserAerodromeFormData(_))
    val itineraryUserCompanys = ItineraryUserCompanyQuery.byUserId(obj.id).map(ItineraryUserCompanyFormData(_))
    new UserFormData(obj, userProfiles, itineraryUserAerodromes, itineraryUserCompanys)
  }
}
object UserForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "username" -> text,
      "password" -> text,
      "name" -> text,
      "lastName" -> text,
      "lastName2" -> text,
      "email" -> text,
      "phone" -> text,
      "secretQuestion" -> text,
      "secretAnswer" -> text,
      "userProfiles" -> list(models.UserProfileForm.form.mapping),
      "itineraryUserAerodromes" -> list(models.ItineraryUserAerodromeForm.form.mapping),
      "itineraryUserCompanys" -> list(models.ItineraryUserCompanyForm.form.mapping)
    )/*(User.apply)(User.unapply)*/
    ((id,username,password,name,lastName,lastName2,email,phone,secretQuestion,secretAnswer,userProfiles,itineraryUserAerodromes,itineraryUserCompanys) => {
      UserFormData(User(id, username, password, name, lastName, lastName2, email, phone, secretQuestion, secretAnswer), userProfiles, itineraryUserAerodromes, itineraryUserCompanys)
    })((formData: UserFormData) => {
      Some((formData.obj.id, formData.obj.username, formData.obj.password, formData.obj.name, formData.obj.lastName, formData.obj.lastName2, formData.obj.email, formData.obj.phone, formData.obj.secretQuestion, formData.obj.secretAnswer, formData.userProfiles, formData.itineraryUserAerodromes, formData.itineraryUserCompanys))
    })
  )
}