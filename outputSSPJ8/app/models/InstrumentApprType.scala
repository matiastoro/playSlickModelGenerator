package models
//import models.extensions.InstrumentApprTypeExtension
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



case class InstrumentApprType(id: Option[Long] = None /*None*/,
                              instrumentApprType: String = "" /*None*/) extends InstrumentApprTypeExtension{

}

object InstrumentApprType {
  implicit val format = Json.format[InstrumentApprType]
  val tupled = (this.apply _).tupled
}


case class InstrumentApprTypeFormData(obj: InstrumentApprType){
  def update(updatedObj: InstrumentApprType = obj)(implicit repo: InstrumentApprTypeRepository, ec: ExecutionContext) = {

    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: InstrumentApprType)(implicit repo: InstrumentApprTypeRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future{id}
    }
  }
}
object InstrumentApprTypeFormData{
  def fapply(obj: InstrumentApprType)(implicit ec: ExecutionContext) = {
    Future{
      new InstrumentApprTypeFormData(obj)
    }
  }
}
object InstrumentApprTypeForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "instrumentApprType" -> nonEmptyText
    )/*(InstrumentApprType.apply)(InstrumentApprType.unapply)*/
    ((id,instrumentApprType) => {
      InstrumentApprTypeFormData(InstrumentApprType(id, instrumentApprType))
    })((formData: InstrumentApprTypeFormData) => {
      Some((formData.obj.id, formData.obj.instrumentApprType))
    })
  )
}



case class InstrumentApprTypeFilter(id: Option[Long] = None,
                                    instrumentApprType: Option[String] = None)

object InstrumentApprTypeFilter {
  val tupled = (this.apply _).tupled
}


object InstrumentApprTypeFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "instrumentApprType" -> optional(nonEmptyText)
    )/*(InstrumentApprTypeFilter.apply)(InstrumentApprTypeFilter.unapply)*/
    ((id,instrumentApprType) => {
      InstrumentApprTypeFilter(id, instrumentApprType)
    })((formData: InstrumentApprTypeFilter) => {
      Some((formData.id, formData.instrumentApprType))
    })
  )
}

