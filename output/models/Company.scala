package models
import models.extensions.CompanyExtension
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Company(id: Option[Long],
                   company: String,
                   description: String,
                   userData: UserData,
                   createdAt: Option[DateTime],
                   updatedAt: Option[DateTime]) extends CompanyExtension{
  lazy val selectString = company

}

case class UserData(username: String,
                    password: String,
                    email: Option[String]) extends UserDataExtension{
  lazy val selectString = username

}



class CompanyMapping(tag: Tag) extends Table[Company](tag, "company") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def company = column[String]("company")
  def description = column[String]("description")

  def username = column[String]("username")
  def password = column[String]("password")
  def email = column[Option[String]]("email", O.Default(None))
  val userDataCols = (username, password, email)

  def createdAt = column[Option[DateTime]]("created_at", O.Default(None))
  def updatedAt = column[Option[DateTime]]("updated_at", O.Default(None))

  def * = (id.?, company, description, userDataCols, createdAt, updatedAt).shaped <> 
    ({
      case (id, company, description, userData, createdAt, updatedAt) =>
      Company(id, company, description, UserData.tupled.apply(userData), createdAt, updatedAt)
    }, {o: Company =>
      Some((o.id,o.company,o.description,UserData.unapply(o.userData).get,o.createdAt,o.updatedAt))
    })
}

class CompanyQueryBase extends DatabaseClient[Company] {
  type DBTable = CompanyMapping

  private[models] val all = {
    TableQuery[DBTable]
  }

}
case class CompanyFormData(obj: Company, branchs: List[BranchFormData]){
  def update(updatedObj: Company = obj) = {
    //Delete elements that are not part of the form but they do exists in the database.
    BranchQuery.byCompanyId(obj.id).filterNot{o => branchs.exists(_.obj.id == o.id)}.map{BranchQuery.delete(_)}
    branchs.map{o => o.update(o.obj.copy(companyId = obj.id.get))}
    CompanyQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Company) = {
    val id = CompanyQuery.insert(insertedObj)
    branchs.map{o => o.insert(o.obj.copy(companyId = id))}
    id
  }
}
object CompanyFormData{
  def apply(obj: Company) = {
    val branchs = BranchQuery.byCompanyId(obj.id).map(BranchFormData(_))
    new CompanyFormData(obj, branchs)
  }
}
object CompanyForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "company" -> text,
      "description" -> text,
      "userData" -> mapping(
        "username" -> text,
        "password" -> text,
        "email" -> optional(text)
      )/*(UserData.apply)(UserData.unapply)*/
      ((username,password,email) => {
        UserData(username, password, email)
      })((formData: UserData) => {
        Some(formData.username, formData.password, formData.email)
      }),
      "branchs" -> list(models.BranchForm.form.mapping)
    )/*(Company.apply)(Company.unapply)*/
    ((id,company,description,userData,branchs) => {
      CompanyFormData(Company(id, company, description, userData, Some(new DateTime()), Some(new DateTime())), branchs)
    })((formData: CompanyFormData) => {
      Some(formData.obj.id, formData.obj.company, formData.obj.description, formData.obj.userData, formData.branchs)
    })
  )
}