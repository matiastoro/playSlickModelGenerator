package models
//import models.extensions.OperatorTypeExtension
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



case class OperatorType(id: Option[Long] = None /*None*/,
                        operatorType: String = "" /*None*/,
                        operatorTypeId: Option[Long] = None /*None*/) extends OperatorTypeExtension{

}

object OperatorType {
  implicit val format = Json.format[OperatorType]
  val tupled = (this.apply _).tupled
}




case class OperatorTypeFormData(obj: OperatorType){
  def update(updatedObj: OperatorType = obj)(implicit repo: OperatorTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: OperatorType)(implicit repo: OperatorTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object OperatorTypeFormData{
  def fapply(obj: OperatorType)(implicit ec: ExecutionContext) = {
    Future{
      new OperatorTypeFormData(obj)
    }
  }
}
object OperatorTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "operatorType" -> nonEmptyText,
      "operatorTypeId" -> optional(longNumber)
    )/*(OperatorType.apply)(OperatorType.unapply)*/
    ((id,operatorType,operatorTypeId) => {
      OperatorTypeFormData(OperatorType(id, operatorType, operatorTypeId))
    })((formData: OperatorTypeFormData) => {
      Some((formData.obj.id, formData.obj.operatorType, formData.obj.operatorTypeId))
    })
  )
}