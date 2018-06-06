package models
//import models.extensions.EccairsExtension
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



case class Eccairs(id: Option[Long] = None /*None*/,
                   number: String = "" /*None*/,
                   date: DateTime = new DateTime() /*None*/,
                   stateAreaId: Long = 0L /*None*/,
                   ocurrenceClassId: Long = 0L /*None*/,
                   injuryLevelId: Option[Long] = None /*None*/,
                   massGroupId: Option[Long] = None /*None*/,
                   categoryId: Option[Long] = None /*None*/,
                   registry: Option[String] = None /*None*/,
                   operationTypeId: Option[Long] = None /*None*/,
                   operatorTypeId: Option[Long] = None /*None*/,
                   operatorId: Option[Long] = None /*None*/,
                   weatherConditionId: Option[Long] = None /*None*/) extends EccairsExtension{

}

object Eccairs {
  implicit val format = Json.format[Eccairs]
  val tupled = (this.apply _).tupled
}


case class EccairsFormData(obj: Eccairs, eccairsOcurrenceCategorys: List[EccairsOcurrenceCategoryFormData]){
  def update(updatedObj: Eccairs = obj)(implicit repo: EccairsRepository, eccairsOcurrenceCategoryRepo: EccairsOcurrenceCategoryRepository, ec: ExecutionContext) = {
    //Delete elements that are not part of the form but they do exists in the database.
    eccairsOcurrenceCategoryRepo.byEccairsId(obj.id).map{ l =>  l.filterNot{o => eccairsOcurrenceCategorys.exists(_.obj.id == o.id)}.map{eccairsOcurrenceCategoryRepo.delete(_)}}
    eccairsOcurrenceCategorys.map{o => o.update(o.obj.copy(eccairsId = obj.id.get))}
    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: Eccairs)(implicit repo: EccairsRepository, eccairsOcurrenceCategoryRepo: EccairsOcurrenceCategoryRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future.sequence(    eccairsOcurrenceCategorys.map{o => o.insert(o.obj.copy(eccairsId = id))}).map{ x =>
        id
      }
    
    }
  }
}
object EccairsFormData{
  def fapply(obj: Eccairs)(implicit repo: EccairsRepository, eccairsOcurrenceCategoryRepo: EccairsOcurrenceCategoryRepository, ec: ExecutionContext) = {
    for{
      eccairsOcurrenceCategorys <- eccairsOcurrenceCategoryRepo.byEccairsId(obj.id).flatMap(l => Future.sequence(l.map(EccairsOcurrenceCategoryFormData.fapply(_))))
    } yield{
      new EccairsFormData(obj, eccairsOcurrenceCategorys.toList)
    }
  }
}
object EccairsForm{
  
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "number" -> nonEmptyText,
      "date" -> jodaDate("YYYY-MM-dd", DateTimeZone.UTC),
      "stateAreaId" -> longNumber,
      "ocurrenceClassId" -> longNumber,
      "eccairsOcurrenceCategorys" -> list(models.EccairsOcurrenceCategoryForm.form.mapping),
      "injuryLevelId" -> optional(longNumber),
      "massGroupId" -> optional(longNumber),
      "categoryId" -> optional(longNumber),
      "registry" -> optional(nonEmptyText),
      "operationTypeId" -> optional(longNumber),
      "operatorTypeId" -> optional(longNumber),
      "operatorId" -> optional(longNumber),
      "weatherConditionId" -> optional(longNumber)
    )/*(Eccairs.apply)(Eccairs.unapply)*/
    ((id,number,date,stateAreaId,ocurrenceClassId,eccairsOcurrenceCategorys,injuryLevelId,massGroupId,categoryId,registry,operationTypeId,operatorTypeId,operatorId,weatherConditionId) => {
      EccairsFormData(Eccairs(id, number, date, stateAreaId, ocurrenceClassId, injuryLevelId, massGroupId, categoryId, registry, operationTypeId, operatorTypeId, operatorId, weatherConditionId), eccairsOcurrenceCategorys)
    })((formData: EccairsFormData) => {
      Some((formData.obj.id, formData.obj.number, formData.obj.date, formData.obj.stateAreaId, formData.obj.ocurrenceClassId, formData.eccairsOcurrenceCategorys, formData.obj.injuryLevelId, formData.obj.massGroupId, formData.obj.categoryId, formData.obj.registry, formData.obj.operationTypeId, formData.obj.operatorTypeId, formData.obj.operatorId, formData.obj.weatherConditionId))
    })
  )
}



case class EccairsFilter(id: Option[Long] = None,
                         number: Option[String] = None,
                         dateFrom: Option[DateTime] = None,
                         dateTo: Option[DateTime] = None,
                         stateAreaId: Option[Long] = None,
                         ocurrenceClassId: Option[Long] = None,
                         eccairsOcurrenceCategorys: List[EccairsOcurrenceCategoryFilter] = List(),
                         injuryLevelId: Option[Long] = None,
                         massGroupId: Option[Long] = None,
                         categoryId: Option[Long] = None,
                         registry: Option[String] = None,
                         operationTypeId: Option[Long] = None,
                         operatorTypeId: Option[Long] = None,
                         operatorId: Option[Long] = None,
                         weatherConditionId: Option[Long] = None)

object EccairsFilter {
  val tupled = (this.apply _).tupled
}


object EccairsFilterForm{
  
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "number" -> optional(nonEmptyText),
      "dateFrom" -> optional(jodaDate("YYYY-MM-dd", DateTimeZone.UTC)),
      "dateTo" -> optional(jodaDate("YYYY-MM-dd", DateTimeZone.UTC)),
      "stateAreaId" -> optional(longNumber),
      "ocurrenceClassId" -> optional(longNumber),
      "eccairsOcurrenceCategorys" -> list(models.EccairsOcurrenceCategoryFilterForm.filterForm.mapping),
      "injuryLevelId" -> optional(longNumber),
      "massGroupId" -> optional(longNumber),
      "categoryId" -> optional(longNumber),
      "registry" -> optional(nonEmptyText),
      "operationTypeId" -> optional(longNumber),
      "operatorTypeId" -> optional(longNumber),
      "operatorId" -> optional(longNumber),
      "weatherConditionId" -> optional(longNumber)
    )/*(EccairsFilter.apply)(EccairsFilter.unapply)*/
    ((id,number,dateFrom,dateTo,stateAreaId,ocurrenceClassId,eccairsOcurrenceCategorys,injuryLevelId,massGroupId,categoryId,registry,operationTypeId,operatorTypeId,operatorId,weatherConditionId) => {
      EccairsFilter(id, number, dateFrom, dateTo, stateAreaId, ocurrenceClassId, eccairsOcurrenceCategorys, injuryLevelId, massGroupId, categoryId, registry, operationTypeId, operatorTypeId, operatorId, weatherConditionId)
    })((formData: EccairsFilter) => {
      Some((formData.id, formData.number, formData.dateFrom, formData.dateTo, formData.stateAreaId, formData.ocurrenceClassId, formData.eccairsOcurrenceCategorys, formData.injuryLevelId, formData.massGroupId, formData.categoryId, formData.registry, formData.operationTypeId, formData.operatorTypeId, formData.operatorId, formData.weatherConditionId))
    })
  )
}

