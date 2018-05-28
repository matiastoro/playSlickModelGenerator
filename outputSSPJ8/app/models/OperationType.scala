package models
//import models.extensions.OperationTypeExtension
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



case class OperationType(id: Option[Long] = None /*None*/,
                         operationType: String = "" /*None*/,
                         operationTypeId: Option[Long] = None /*None*/) extends OperationTypeExtension{

}

object OperationType {
  implicit val format = Json.format[OperationType]
  val tupled = (this.apply _).tupled
}




case class OperationTypeFormData(obj: OperationType){
  def update(updatedObj: OperationType = obj)(implicit repo: OperationTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: OperationType)(implicit repo: OperationTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object OperationTypeFormData{
  def fapply(obj: OperationType)(implicit ec: ExecutionContext) = {
    Future{
      new OperationTypeFormData(obj)
    }
  }
}
object OperationTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "operationType" -> nonEmptyText,
      "operationTypeId" -> optional(longNumber)
    )/*(OperationType.apply)(OperationType.unapply)*/
    ((id,operationType,operationTypeId) => {
      OperationTypeFormData(OperationType(id, operationType, operationTypeId))
    })((formData: OperationTypeFormData) => {
      Some((formData.obj.id, formData.obj.operationType, formData.obj.operationTypeId))
    })
  )
}