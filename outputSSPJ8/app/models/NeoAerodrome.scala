package models
//import models.extensions.NeoAerodromeExtension
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

object NeoAerodromePartition{
import extensions.NeoAerodromePartition._


case class GeneralInformation(when: DateTime = new DateTime() /*None*/,
                              whenLocal: Option[DateTime] = None /*None*/,
                              whereStateAreaId: Long = 0L /*None*/,
                              whereLocationName: String = "" /*None*/,
                              whereLatitudeOcc: Option[String] = None /*None*/,
                              whereLongitudeOcc: Option[String] = None /*None*/,
                              whatHeadline: String = "" /*None*/,
                              whatNarrativeLanguaje: String = "" /*None*/,
                              whatNarrative: String = "" /*None*/) extends GeneralInformationExtension{

}

object GeneralInformation {
  implicit val format = Json.format[GeneralInformation]
  val tupled = (this.apply _).tupled
}





case class AerodromeDescription(aerodromeId: Long = 0L /*None*/,
                                aerodromeLocationId: Long = 0L /*None*/,
                                aerodromeStatusId: Long = 0L /*None*/,
                                aerodromeTypeId: Long = 0L /*None*/,
                                latitude: Option[String] = None /*None*/,
                                longitude: Option[String] = None /*None*/,
                                runwayId: Option[Long] = None /*None*/,
                                runwaySurfaceTypeId: Option[Long] = None /*None*/,
                                helicopterLandingAreaTypeId: Option[Long] = None /*None*/,
                                helicopterLandingAreaAreaConfigurationId: Option[Long] = None /*None*/,
                                helicopterLandingAreaSurfaceTypeId: Option[Long] = None /*None*/) extends AerodromeDescriptionExtension{

}

object AerodromeDescription {
  implicit val format = Json.format[AerodromeDescription]
  val tupled = (this.apply _).tupled
}





case class Classification(ocurrenceClassId: Long = 0L /*None*/,
                          detectionPhaseId: Long = 0L /*None*/) extends ClassificationExtension{

}

object Classification {
  implicit val format = Json.format[Classification]
  val tupled = (this.apply _).tupled
}





case class Risk(riskClassification: String = "" /*None*/,
                riskMethodology: Option[String] = None /*None*/,
                riskAssessment: Option[String] = None /*None*/) extends RiskExtension{

}

object Risk {
  implicit val format = Json.format[Risk]
  val tupled = (this.apply _).tupled
}





case class Assessment(analysisFollowUp: Option[String] = None /*None*/,
                      correctiveActions: Option[String] = None /*None*/,
                      conclusions: Option[String] = None /*None*/) extends AssessmentExtension{

}

object Assessment {
  implicit val format = Json.format[Assessment]
  val tupled = (this.apply _).tupled
}





case class ReportManagement(reportStatusId: Option[Long] = None /*None*/,
                            reportVersion: Option[String] = None /*None*/) extends ReportManagementExtension{

}

object ReportManagement {
  implicit val format = Json.format[ReportManagement]
  val tupled = (this.apply _).tupled
}

}


case class NeoAerodrome(id: Option[Long] = None /*None*/,
                        reportingEntityId: Long = 0L /*None*/,
                        reportingEntityName: String = "" /*None*/,
                        generalInformation: NeoAerodromePartition.GeneralInformation,
                        aerodromeDescription: NeoAerodromePartition.AerodromeDescription,
                        classification: NeoAerodromePartition.Classification,
                        risk: NeoAerodromePartition.Risk,
                        assessment: NeoAerodromePartition.Assessment,
                        reportManagement: NeoAerodromePartition.ReportManagement,
                        attachments: Option[String] = None /*None*/) extends NeoAerodromeExtension{

}

object NeoAerodrome {
  implicit val format = Json.format[NeoAerodrome]
  val tupled = (this.apply _).tupled
}




case class NeoAerodromeFormData(obj: NeoAerodrome, neoAerodromeOcurrenceCategorys: List[NeoAerodromeOcurrenceCategoryFormData], neoEventTypePhases: List[NeoEventTypePhaseFormData]){
  def update(updatedObj: NeoAerodrome = obj)(implicit repo: NeoAerodromeRepository, neoAerodromeOcurrenceCategoryRepo: NeoAerodromeOcurrenceCategoryRepository, neoEventTypePhaseRepo: NeoEventTypePhaseRepository, ec: ExecutionContext) = {
    //Delete elements that are not part of the form but they do exists in the database.
    neoAerodromeOcurrenceCategoryRepo.byNeoAerodromeId(obj.id).map{ l =>  l.filterNot{o => neoAerodromeOcurrenceCategorys.exists(_.obj.id == o.id)}.map{neoAerodromeOcurrenceCategoryRepo.delete(_)}}
    neoAerodromeOcurrenceCategorys.map{o => o.update(o.obj.copy(neoAerodromeId = obj.id.get))}
    //Delete elements that are not part of the form but they do exists in the database.
    neoEventTypePhaseRepo.byNeoAerodromeId(obj.id).map{ l =>  l.filterNot{o => neoEventTypePhases.exists(_.obj.id == o.id)}.map{neoEventTypePhaseRepo.delete(_)}}
    neoEventTypePhases.map{o => o.update(o.obj.copy(neoAerodromeId = obj.id.get))}
    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: NeoAerodrome)(implicit repo: NeoAerodromeRepository, neoAerodromeOcurrenceCategoryRepo: NeoAerodromeOcurrenceCategoryRepository, neoEventTypePhaseRepo: NeoEventTypePhaseRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future.sequence(    neoAerodromeOcurrenceCategorys.map{o => o.insert(o.obj.copy(neoAerodromeId = id))}++    neoEventTypePhases.map{o => o.insert(o.obj.copy(neoAerodromeId = id))}).map{ x =>
        id
      }
    
    }
  }
}
object NeoAerodromeFormData{
  def fapply(obj: NeoAerodrome)(implicit repo: NeoAerodromeRepository, neoAerodromeOcurrenceCategoryRepo: NeoAerodromeOcurrenceCategoryRepository, neoEventTypePhaseRepo: NeoEventTypePhaseRepository, ec: ExecutionContext) = {
    for{
      neoAerodromeOcurrenceCategorys <- neoAerodromeOcurrenceCategoryRepo.byNeoAerodromeId(obj.id).flatMap(l => Future.sequence(l.map(NeoAerodromeOcurrenceCategoryFormData.fapply(_))))
      neoEventTypePhases <- neoEventTypePhaseRepo.byNeoAerodromeId(obj.id).flatMap(l => Future.sequence(l.map(NeoEventTypePhaseFormData.fapply(_))))
    } yield{
      new NeoAerodromeFormData(obj, neoAerodromeOcurrenceCategorys.toList, neoEventTypePhases.toList)
    }
  }
}
object NeoAerodromeForm{
  import NeoAerodromePartition._
  val form = Form(
            mapping(
      "id" -> optional(longNumber),
      "reportingEntityId" -> longNumber,
      "reportingEntityName" -> nonEmptyText,
      "generalInformation" -> mapping(
        "when" -> jodaDate("YYYY-MM-dd HH:mm"),
        "whenLocal" -> optional(jodaDate("YYYY-MM-dd HH:mm")),
        "whereStateAreaId" -> longNumber,
        "whereLocationName" -> nonEmptyText,
        "whereLatitudeOcc" -> optional(nonEmptyText),
        "whereLongitudeOcc" -> optional(nonEmptyText),
        "whatHeadline" -> nonEmptyText,
        "whatNarrativeLanguaje" -> nonEmptyText,
        "whatNarrative" -> nonEmptyText
      )/*(GeneralInformation.apply)(GeneralInformation.unapply)*/
      ((when,whenLocal,whereStateAreaId,whereLocationName,whereLatitudeOcc,whereLongitudeOcc,whatHeadline,whatNarrativeLanguaje,whatNarrative) => {
        GeneralInformation(when, whenLocal, whereStateAreaId, whereLocationName, whereLatitudeOcc, whereLongitudeOcc, whatHeadline, whatNarrativeLanguaje, whatNarrative)
      })((formData: GeneralInformation) => {
        Some((formData.when, formData.whenLocal, formData.whereStateAreaId, formData.whereLocationName, formData.whereLatitudeOcc, formData.whereLongitudeOcc, formData.whatHeadline, formData.whatNarrativeLanguaje, formData.whatNarrative))
      }),
      "aerodromeDescription" -> mapping(
        "aerodromeId" -> longNumber,
        "aerodromeLocationId" -> longNumber,
        "aerodromeStatusId" -> longNumber,
        "aerodromeTypeId" -> longNumber,
        "latitude" -> optional(nonEmptyText),
        "longitude" -> optional(nonEmptyText),
        "runwayId" -> optional(longNumber),
        "runwaySurfaceTypeId" -> optional(longNumber),
        "helicopterLandingAreaTypeId" -> optional(longNumber),
        "helicopterLandingAreaAreaConfigurationId" -> optional(longNumber),
        "helicopterLandingAreaSurfaceTypeId" -> optional(longNumber)
      )/*(AerodromeDescription.apply)(AerodromeDescription.unapply)*/
      ((aerodromeId,aerodromeLocationId,aerodromeStatusId,aerodromeTypeId,latitude,longitude,runwayId,runwaySurfaceTypeId,helicopterLandingAreaTypeId,helicopterLandingAreaAreaConfigurationId,helicopterLandingAreaSurfaceTypeId) => {
        AerodromeDescription(aerodromeId, aerodromeLocationId, aerodromeStatusId, aerodromeTypeId, latitude, longitude, runwayId, runwaySurfaceTypeId, helicopterLandingAreaTypeId, helicopterLandingAreaAreaConfigurationId, helicopterLandingAreaSurfaceTypeId)
      })((formData: AerodromeDescription) => {
        Some((formData.aerodromeId, formData.aerodromeLocationId, formData.aerodromeStatusId, formData.aerodromeTypeId, formData.latitude, formData.longitude, formData.runwayId, formData.runwaySurfaceTypeId, formData.helicopterLandingAreaTypeId, formData.helicopterLandingAreaAreaConfigurationId, formData.helicopterLandingAreaSurfaceTypeId))
      }),
      "classification" -> mapping(
        "ocurrenceClassId" -> longNumber,
        "detectionPhaseId" -> longNumber
      )/*(Classification.apply)(Classification.unapply)*/
      ((ocurrenceClassId,detectionPhaseId) => {
        Classification(ocurrenceClassId, detectionPhaseId)
      })((formData: Classification) => {
        Some((formData.ocurrenceClassId, formData.detectionPhaseId))
      }),
      "neoAerodromeOcurrenceCategorys" -> list(models.NeoAerodromeOcurrenceCategoryForm.form.mapping),
      "neoEventTypePhases" -> list(models.NeoEventTypePhaseForm.form.mapping),
      "risk" -> mapping(
        "riskClassification" -> nonEmptyText,
        "riskMethodology" -> optional(nonEmptyText),
        "riskAssessment" -> optional(nonEmptyText)
      )/*(Risk.apply)(Risk.unapply)*/
      ((riskClassification,riskMethodology,riskAssessment) => {
        Risk(riskClassification, riskMethodology, riskAssessment)
      })((formData: Risk) => {
        Some((formData.riskClassification, formData.riskMethodology, formData.riskAssessment))
      }),
      "assessment" -> mapping(
        "analysisFollowUp" -> optional(nonEmptyText),
        "correctiveActions" -> optional(nonEmptyText),
        "conclusions" -> optional(nonEmptyText)
      )/*(Assessment.apply)(Assessment.unapply)*/
      ((analysisFollowUp,correctiveActions,conclusions) => {
        Assessment(analysisFollowUp, correctiveActions, conclusions)
      })((formData: Assessment) => {
        Some((formData.analysisFollowUp, formData.correctiveActions, formData.conclusions))
      }),
      "reportManagement" -> mapping(
        "reportStatusId" -> optional(longNumber),
        "reportVersion" -> optional(nonEmptyText)
      )/*(ReportManagement.apply)(ReportManagement.unapply)*/
      ((reportStatusId,reportVersion) => {
        ReportManagement(reportStatusId, reportVersion)
      })((formData: ReportManagement) => {
        Some((formData.reportStatusId, formData.reportVersion))
      }),
      "attachments" -> optional(nonEmptyText)
    )/*(NeoAerodrome.apply)(NeoAerodrome.unapply)*/
    ((id,reportingEntityId,reportingEntityName,generalInformation,aerodromeDescription,classification,neoAerodromeOcurrenceCategorys,neoEventTypePhases,risk,assessment,reportManagement,attachments) => {
      NeoAerodromeFormData(NeoAerodrome(id, reportingEntityId, reportingEntityName, generalInformation, aerodromeDescription, classification, risk, assessment, reportManagement, attachments), neoAerodromeOcurrenceCategorys, neoEventTypePhases)
    })((formData: NeoAerodromeFormData) => {
      Some((formData.obj.id, formData.obj.reportingEntityId, formData.obj.reportingEntityName, formData.obj.generalInformation, formData.obj.aerodromeDescription, formData.obj.classification, formData.neoAerodromeOcurrenceCategorys, formData.neoEventTypePhases, formData.obj.risk, formData.obj.assessment, formData.obj.reportManagement, formData.obj.attachments))
    })
  )
}