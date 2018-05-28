package models
//import models.extensions.LanguageExtension
import extensions._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import play.api.data.Form
import play.api.data.Forms._
//support for joda is now a separate project
import play.api.data.JodaForms._
import play.api.data.format.Formats._
import org.joda.time.{DateTime, LocalDate, DateTimeZone}
//import com.github.tototoshi.slick.H2JodaSupport._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._
//import scala.concurrent.ExecutionContext.Implicits.global

/*import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JdbcJodaSupport._
import org.joda.time.{DateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import extensions._


import play.api.libs.json._*/



case class Language(id: Option[Long] = None /*None*/,
                    language: String = "" /*None*/) extends LanguageExtension{

}

object Language {
  implicit val format = Json.format[Language]
  val tupled = (this.apply _).tupled
}




case class LanguageFormData(obj: Language){
  def update(updatedObj: Language = obj)(implicit repo: LanguageRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Language)(implicit repo: LanguageRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object LanguageFormData{
  def fapply(obj: Language)(implicit ec: ExecutionContext) = {
    Future{
      new LanguageFormData(obj)
    }
  }
}
object LanguageForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "language" -> nonEmptyText
    )/*(Language.apply)(Language.unapply)*/
    ((id,language) => {
      LanguageFormData(Language(id, language))
    })((formData: LanguageFormData) => {
      Some((formData.obj.id, formData.obj.language))
    })
  )
}