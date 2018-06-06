package models
//import models.extensions.OperatorExtension
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



case class Operator(id: Option[Long] = None /*None*/,
                    operator: String = "" /*None*/,
                    operatorTypeId: Option[Long] = None /*None*/) extends OperatorExtension{

}

object Operator {
  implicit val format = Json.format[Operator]
  val tupled = (this.apply _).tupled
}


case class OperatorFormData(obj: Operator){
  def update(updatedObj: Operator = obj)(implicit repo: OperatorRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Operator)(implicit repo: OperatorRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object OperatorFormData{
  def fapply(obj: Operator)(implicit ec: ExecutionContext) = {
    Future{
      new OperatorFormData(obj)
    }
  }
}
object OperatorForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "operator" -> nonEmptyText,
      "operatorTypeId" -> optional(longNumber)
    )/*(Operator.apply)(Operator.unapply)*/
    ((id,operator,operatorTypeId) => {
      OperatorFormData(Operator(id, operator, operatorTypeId))
    })((formData: OperatorFormData) => {
      Some((formData.obj.id, formData.obj.operator, formData.obj.operatorTypeId))
    })
  )
}



case class OperatorFilter(id: Option[Long] = None,
                          operator: Option[String] = None,
                          operatorTypeId: Option[Long] = None)

object OperatorFilter {
  val tupled = (this.apply _).tupled
}


object OperatorFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "operator" -> optional(nonEmptyText),
      "operatorTypeId" -> optional(longNumber)
    )/*(OperatorFilter.apply)(OperatorFilter.unapply)*/
    ((id,operator,operatorTypeId) => {
      OperatorFilter(id, operator, operatorTypeId)
    })((formData: OperatorFilter) => {
      Some((formData.id, formData.operator, formData.operatorTypeId))
    })
  )
}

