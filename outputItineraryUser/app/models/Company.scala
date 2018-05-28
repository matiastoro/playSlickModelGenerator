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
import play.api.libs.json._

case class Company(id: Option[Long] = None /*None*/,
                   company: String = "" /*None*/) extends CompanyExtension{
  implicit val jsonFormat = Json.format[Company]
  def toJson = Json.toJson(this)
         

  lazy val selectString = company

}



class Companys(tag: Tag) extends Table[Company](tag, "company") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def company = column[String]("company", O.Default(""))

  def * = (id.?, company).shaped <> (Company.tupled, Company.unapply)
}

class CompanyQueryBase extends BaseDAO[Company] {
  type DBTable = CompanyMapeo

  val tableQ = {
    TableQuery[DBTable]
  }

}
case class CompanyFormData(obj: Company){
  def update(updatedObj: Company = obj)(implicit session: Session) = {

    CompanyQuery.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Company)(implicit session: Session) = {
    val id = CompanyQuery.insert(insertedObj)

    id
  }
}

object CompanyForm{
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "company" -> text
    )/*(Company.apply)(Company.unapply)*/
    ((id,company) => {
      CompanyFormData(Company(id, company))
    })((formData: CompanyFormData) => {
      Some((formData.obj.id, formData.obj.company))
    })
  )
}