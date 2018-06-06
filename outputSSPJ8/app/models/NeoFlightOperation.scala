package models
//import models.extensions.NeoFlightOperationExtension
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

object NeoFlightOperationPartition{
  import extensions.NeoFlightOperationPartition._
  
  
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

  
  
  case class AircraftIdentification(stateOfRegistryId: Long = 0L /*None*/,
                                    registration: String = "" /*None*/,
                                    manufacturerModelId: Long = 0L /*None*/,
                                    serialNumber: String = "" /*None*/,
                                    yearBuilt: Option[Int] = None /*None*/) extends AircraftIdentificationExtension{
  
  }
  
  object AircraftIdentification {
    implicit val format = Json.format[AircraftIdentification]
    val tupled = (this.apply _).tupled
  }

  
  
  case class AircraftDescription(categoryId: Long = 0L /*None*/,
                                 massGroupId: Long = 0L /*None*/,
                                 propulsionTypeId: Long = 0L /*None*/,
                                 wakeTurbCategoryId: Option[Long] = None /*None*/,
                                 numberOfEngines: Option[Int] = None /*None*/,
                                 landingGearTypeId: Option[Long] = None /*None*/,
                                 maximumMass: Option[Double] = None /*None*/) extends AircraftDescriptionExtension{
  
  }
  
  object AircraftDescription {
    implicit val format = Json.format[AircraftDescription]
    val tupled = (this.apply _).tupled
  }

  
  
  case class FlightDetails(departureId: Long = 0L /*None*/,
                           destinationId: Long = 0L /*None*/,
                           phaseId: Long = 0L /*None*/,
                           operatorId: Long = 0L /*None*/,
                           operationTypeId: Long = 0L /*None*/,
                           callsign: String = "" /*None*/,
                           flightNumber: Option[String] = None /*None*/,
                           numberPersons: Option[Int] = None /*None*/) extends FlightDetailsExtension{
  
  }
  
  object FlightDetails {
    implicit val format = Json.format[FlightDetails]
    val tupled = (this.apply _).tupled
  }

  
  
  case class OperationalInformation(aircraftAltitude: Option[Double] = None /*None*/,
                                    aircraftFlightLevel: Option[String] = None /*None*/,
                                    speed: Option[Double] = None /*None*/,
                                    typeOfAirspeedId: Option[Long] = None /*None*/,
                                    flightRuleId: Option[Long] = None /*None*/,
                                    trafficTypeId: Option[Long] = None /*None*/,
                                    instrumentApprTypeId: Option[Long] = None /*None*/) extends OperationalInformationExtension{
  
  }
  
  object OperationalInformation {
    implicit val format = Json.format[OperationalInformation]
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


case class NeoFlightOperation(id: Option[Long] = None /*None*/,
                              reportingEntityId: Long = 0L /*None*/,
                              reportingEntityName: String = "" /*None*/,
                              generalInformation: NeoFlightOperationPartition.GeneralInformation,
                              aircraftIdentification: NeoFlightOperationPartition.AircraftIdentification,
                              aircraftDescription: NeoFlightOperationPartition.AircraftDescription,
                              flightDetails: NeoFlightOperationPartition.FlightDetails,
                              operationalInformation: NeoFlightOperationPartition.OperationalInformation,
                              classification: NeoFlightOperationPartition.Classification,
                              risk: NeoFlightOperationPartition.Risk,
                              assessment: NeoFlightOperationPartition.Assessment,
                              reportManagement: NeoFlightOperationPartition.ReportManagement,
                              attachments: Option[String] = None /*None*/) extends NeoFlightOperationExtension{

}

object NeoFlightOperation {
  implicit val format = Json.format[NeoFlightOperation]
  val tupled = (this.apply _).tupled
}


case class NeoFlightOperationFormData(obj: NeoFlightOperation, flightCrews: List[FlightCrewFormData], neoFlightOperationOcurrenceCategorys: List[NeoFlightOperationOcurrenceCategoryFormData], neoFlightOperationEventTypePhases: List[NeoFlightOperationEventTypePhaseFormData]){
  def update(updatedObj: NeoFlightOperation = obj)(implicit repo: NeoFlightOperationRepository, flightCrewRepo: FlightCrewRepository, flightCrewLicenseRepo: FlightCrewLicenseRepository, neoFlightOperationOcurrenceCategoryRepo: NeoFlightOperationOcurrenceCategoryRepository, neoFlightOperationEventTypePhaseRepo: NeoFlightOperationEventTypePhaseRepository, ec: ExecutionContext) = {
    //Delete elements that are not part of the form but they do exists in the database.
    flightCrewRepo.byNeoFlightOperationId(obj.id).map{ l =>  l.filterNot{o => flightCrews.exists(_.obj.id == o.id)}.map{flightCrewRepo.delete(_)}}
    flightCrews.map{o => o.update(o.obj.copy(neoFlightOperationId = obj.id.get))}
    //Delete elements that are not part of the form but they do exists in the database.
    neoFlightOperationOcurrenceCategoryRepo.byNeoFlightOperationId(obj.id).map{ l =>  l.filterNot{o => neoFlightOperationOcurrenceCategorys.exists(_.obj.id == o.id)}.map{neoFlightOperationOcurrenceCategoryRepo.delete(_)}}
    neoFlightOperationOcurrenceCategorys.map{o => o.update(o.obj.copy(neoFlightOperationId = obj.id.get))}
    //Delete elements that are not part of the form but they do exists in the database.
    neoFlightOperationEventTypePhaseRepo.byNeoFlightOperationId(obj.id).map{ l =>  l.filterNot{o => neoFlightOperationEventTypePhases.exists(_.obj.id == o.id)}.map{neoFlightOperationEventTypePhaseRepo.delete(_)}}
    neoFlightOperationEventTypePhases.map{o => o.update(o.obj.copy(neoFlightOperationId = obj.id.get))}
    repo.updateOrInsert(updatedObj)
  }
  def insert(insertedObj: NeoFlightOperation)(implicit repo: NeoFlightOperationRepository, flightCrewRepo: FlightCrewRepository, flightCrewLicenseRepo: FlightCrewLicenseRepository, neoFlightOperationOcurrenceCategoryRepo: NeoFlightOperationOcurrenceCategoryRepository, neoFlightOperationEventTypePhaseRepo: NeoFlightOperationEventTypePhaseRepository, ec: ExecutionContext) = {
    repo.insert(insertedObj).flatMap{ id=>
  Future.sequence(    flightCrews.map{o => o.insert(o.obj.copy(neoFlightOperationId = id))}++    neoFlightOperationOcurrenceCategorys.map{o => o.insert(o.obj.copy(neoFlightOperationId = id))}++    neoFlightOperationEventTypePhases.map{o => o.insert(o.obj.copy(neoFlightOperationId = id))}).map{ x =>
        id
      }
    
    }
  }
}
object NeoFlightOperationFormData{
  def fapply(obj: NeoFlightOperation)(implicit repo: NeoFlightOperationRepository, flightCrewRepo: FlightCrewRepository, flightCrewLicenseRepo: FlightCrewLicenseRepository, neoFlightOperationOcurrenceCategoryRepo: NeoFlightOperationOcurrenceCategoryRepository, neoFlightOperationEventTypePhaseRepo: NeoFlightOperationEventTypePhaseRepository, ec: ExecutionContext) = {
    for{
      flightCrews <- flightCrewRepo.byNeoFlightOperationId(obj.id).flatMap(l => Future.sequence(l.map(FlightCrewFormData.fapply(_))))
      neoFlightOperationOcurrenceCategorys <- neoFlightOperationOcurrenceCategoryRepo.byNeoFlightOperationId(obj.id).flatMap(l => Future.sequence(l.map(NeoFlightOperationOcurrenceCategoryFormData.fapply(_))))
      neoFlightOperationEventTypePhases <- neoFlightOperationEventTypePhaseRepo.byNeoFlightOperationId(obj.id).flatMap(l => Future.sequence(l.map(NeoFlightOperationEventTypePhaseFormData.fapply(_))))
    } yield{
      new NeoFlightOperationFormData(obj, flightCrews.toList, neoFlightOperationOcurrenceCategorys.toList, neoFlightOperationEventTypePhases.toList)
    }
  }
}
object NeoFlightOperationForm{
  import NeoFlightOperationPartition._
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
      "aircraftIdentification" -> mapping(
        "stateOfRegistryId" -> longNumber,
        "registration" -> nonEmptyText,
        "manufacturerModelId" -> longNumber,
        "serialNumber" -> nonEmptyText,
        "yearBuilt" -> optional(number)
      )/*(AircraftIdentification.apply)(AircraftIdentification.unapply)*/
      ((stateOfRegistryId,registration,manufacturerModelId,serialNumber,yearBuilt) => {
        AircraftIdentification(stateOfRegistryId, registration, manufacturerModelId, serialNumber, yearBuilt)
      })((formData: AircraftIdentification) => {
        Some((formData.stateOfRegistryId, formData.registration, formData.manufacturerModelId, formData.serialNumber, formData.yearBuilt))
      }),
      "aircraftDescription" -> mapping(
        "categoryId" -> longNumber,
        "massGroupId" -> longNumber,
        "propulsionTypeId" -> longNumber,
        "wakeTurbCategoryId" -> optional(longNumber),
        "numberOfEngines" -> optional(number),
        "landingGearTypeId" -> optional(longNumber),
        "maximumMass" -> optional(of(doubleFormat))
      )/*(AircraftDescription.apply)(AircraftDescription.unapply)*/
      ((categoryId,massGroupId,propulsionTypeId,wakeTurbCategoryId,numberOfEngines,landingGearTypeId,maximumMass) => {
        AircraftDescription(categoryId, massGroupId, propulsionTypeId, wakeTurbCategoryId, numberOfEngines, landingGearTypeId, maximumMass)
      })((formData: AircraftDescription) => {
        Some((formData.categoryId, formData.massGroupId, formData.propulsionTypeId, formData.wakeTurbCategoryId, formData.numberOfEngines, formData.landingGearTypeId, formData.maximumMass))
      }),
      "flightDetails" -> mapping(
        "departureId" -> longNumber,
        "destinationId" -> longNumber,
        "phaseId" -> longNumber,
        "operatorId" -> longNumber,
        "operationTypeId" -> longNumber,
        "callsign" -> nonEmptyText,
        "flightNumber" -> optional(nonEmptyText),
        "numberPersons" -> optional(number)
      )/*(FlightDetails.apply)(FlightDetails.unapply)*/
      ((departureId,destinationId,phaseId,operatorId,operationTypeId,callsign,flightNumber,numberPersons) => {
        FlightDetails(departureId, destinationId, phaseId, operatorId, operationTypeId, callsign, flightNumber, numberPersons)
      })((formData: FlightDetails) => {
        Some((formData.departureId, formData.destinationId, formData.phaseId, formData.operatorId, formData.operationTypeId, formData.callsign, formData.flightNumber, formData.numberPersons))
      }),
      "operationalInformation" -> mapping(
        "aircraftAltitude" -> optional(of(doubleFormat)),
        "aircraftFlightLevel" -> optional(nonEmptyText),
        "speed" -> optional(of(doubleFormat)),
        "typeOfAirspeedId" -> optional(longNumber),
        "flightRuleId" -> optional(longNumber),
        "trafficTypeId" -> optional(longNumber),
        "instrumentApprTypeId" -> optional(longNumber)
      )/*(OperationalInformation.apply)(OperationalInformation.unapply)*/
      ((aircraftAltitude,aircraftFlightLevel,speed,typeOfAirspeedId,flightRuleId,trafficTypeId,instrumentApprTypeId) => {
        OperationalInformation(aircraftAltitude, aircraftFlightLevel, speed, typeOfAirspeedId, flightRuleId, trafficTypeId, instrumentApprTypeId)
      })((formData: OperationalInformation) => {
        Some((formData.aircraftAltitude, formData.aircraftFlightLevel, formData.speed, formData.typeOfAirspeedId, formData.flightRuleId, formData.trafficTypeId, formData.instrumentApprTypeId))
      }),
      "flightCrews" -> list(models.FlightCrewForm.form.mapping),
      "classification" -> mapping(
        "ocurrenceClassId" -> longNumber,
        "detectionPhaseId" -> longNumber
      )/*(Classification.apply)(Classification.unapply)*/
      ((ocurrenceClassId,detectionPhaseId) => {
        Classification(ocurrenceClassId, detectionPhaseId)
      })((formData: Classification) => {
        Some((formData.ocurrenceClassId, formData.detectionPhaseId))
      }),
      "neoFlightOperationOcurrenceCategorys" -> list(models.NeoFlightOperationOcurrenceCategoryForm.form.mapping),
      "neoFlightOperationEventTypePhases" -> list(models.NeoFlightOperationEventTypePhaseForm.form.mapping),
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
    )/*(NeoFlightOperation.apply)(NeoFlightOperation.unapply)*/
    ((id,reportingEntityId,reportingEntityName,generalInformation,aircraftIdentification,aircraftDescription,flightDetails,operationalInformation,flightCrews,classification,neoFlightOperationOcurrenceCategorys,neoFlightOperationEventTypePhases,risk,assessment,reportManagement,attachments) => {
      NeoFlightOperationFormData(NeoFlightOperation(id, reportingEntityId, reportingEntityName, generalInformation, aircraftIdentification, aircraftDescription, flightDetails, operationalInformation, classification, risk, assessment, reportManagement, attachments), flightCrews, neoFlightOperationOcurrenceCategorys, neoFlightOperationEventTypePhases)
    })((formData: NeoFlightOperationFormData) => {
      Some((formData.obj.id, formData.obj.reportingEntityId, formData.obj.reportingEntityName, formData.obj.generalInformation, formData.obj.aircraftIdentification, formData.obj.aircraftDescription, formData.obj.flightDetails, formData.obj.operationalInformation, formData.flightCrews, formData.obj.classification, formData.neoFlightOperationOcurrenceCategorys, formData.neoFlightOperationEventTypePhases, formData.obj.risk, formData.obj.assessment, formData.obj.reportManagement, formData.obj.attachments))
    })
  )
}

object NeoFlightOperationPartitionFilter{
  
  
  case class GeneralInformationFilter(whenFrom: Option[DateTime] = None,
                                      whenTo: Option[DateTime] = None,
                                      whenLocalFrom: Option[DateTime] = None,
                                      whenLocalTo: Option[DateTime] = None,
                                      whereStateAreaId: Option[Long] = None,
                                      whereLocationName: Option[String] = None,
                                      whereLatitudeOcc: Option[String] = None,
                                      whereLongitudeOcc: Option[String] = None,
                                      whatHeadline: Option[String] = None,
                                      whatNarrativeLanguaje: Option[String] = None,
                                      whatNarrative: Option[String] = None)
  
  object GeneralInformationFilter {
    val tupled = (this.apply _).tupled
  }

  
  
  case class AircraftIdentificationFilter(stateOfRegistryId: Option[Long] = None,
                                          registration: Option[String] = None,
                                          manufacturerModelId: Option[Long] = None,
                                          serialNumber: Option[String] = None,
                                          yearBuilt: Option[Int] = None)
  
  object AircraftIdentificationFilter {
    val tupled = (this.apply _).tupled
  }

  
  
  case class AircraftDescriptionFilter(categoryId: Option[Long] = None,
                                       massGroupId: Option[Long] = None,
                                       propulsionTypeId: Option[Long] = None,
                                       wakeTurbCategoryId: Option[Long] = None,
                                       numberOfEngines: Option[Int] = None,
                                       landingGearTypeId: Option[Long] = None,
                                       maximumMass: Option[Double] = None)
  
  object AircraftDescriptionFilter {
    val tupled = (this.apply _).tupled
  }

  
  
  case class FlightDetailsFilter(departureId: Option[Long] = None,
                                 destinationId: Option[Long] = None,
                                 phaseId: Option[Long] = None,
                                 operatorId: Option[Long] = None,
                                 operationTypeId: Option[Long] = None,
                                 callsign: Option[String] = None,
                                 flightNumber: Option[String] = None,
                                 numberPersons: Option[Int] = None)
  
  object FlightDetailsFilter {
    val tupled = (this.apply _).tupled
  }

  
  
  case class OperationalInformationFilter(aircraftAltitude: Option[Double] = None,
                                          aircraftFlightLevel: Option[String] = None,
                                          speed: Option[Double] = None,
                                          typeOfAirspeedId: Option[Long] = None,
                                          flightRuleId: Option[Long] = None,
                                          trafficTypeId: Option[Long] = None,
                                          instrumentApprTypeId: Option[Long] = None)
  
  object OperationalInformationFilter {
    val tupled = (this.apply _).tupled
  }

  
  
  case class ClassificationFilter(ocurrenceClassId: Option[Long] = None,
                                  detectionPhaseId: Option[Long] = None)
  
  object ClassificationFilter {
    val tupled = (this.apply _).tupled
  }

  
  
  case class RiskFilter(riskClassification: Option[String] = None,
                        riskMethodology: Option[String] = None,
                        riskAssessment: Option[String] = None)
  
  object RiskFilter {
    val tupled = (this.apply _).tupled
  }

  
  
  case class AssessmentFilter(analysisFollowUp: Option[String] = None,
                              correctiveActions: Option[String] = None,
                              conclusions: Option[String] = None)
  
  object AssessmentFilter {
    val tupled = (this.apply _).tupled
  }

  
  
  case class ReportManagementFilter(reportStatusId: Option[Long] = None,
                                    reportVersion: Option[String] = None)
  
  object ReportManagementFilter {
    val tupled = (this.apply _).tupled
  }
}


case class NeoFlightOperationFilter(id: Option[Long] = None,
                                    reportingEntityId: Option[Long] = None,
                                    reportingEntityName: Option[String] = None,
                                    generalInformation: NeoFlightOperationPartitionFilter.GeneralInformationFilter,
                                    aircraftIdentification: NeoFlightOperationPartitionFilter.AircraftIdentificationFilter,
                                    aircraftDescription: NeoFlightOperationPartitionFilter.AircraftDescriptionFilter,
                                    flightDetails: NeoFlightOperationPartitionFilter.FlightDetailsFilter,
                                    operationalInformation: NeoFlightOperationPartitionFilter.OperationalInformationFilter,
                                    flightCrews: List[FlightCrewFilter] = List(),
                                    classification: NeoFlightOperationPartitionFilter.ClassificationFilter,
                                    neoFlightOperationOcurrenceCategorys: List[NeoFlightOperationOcurrenceCategoryFilter] = List(),
                                    neoFlightOperationEventTypePhases: List[NeoFlightOperationEventTypePhaseFilter] = List(),
                                    risk: NeoFlightOperationPartitionFilter.RiskFilter,
                                    assessment: NeoFlightOperationPartitionFilter.AssessmentFilter,
                                    reportManagement: NeoFlightOperationPartitionFilter.ReportManagementFilter,
                                    attachments: Option[String] = None)

object NeoFlightOperationFilter {
  val tupled = (this.apply _).tupled
}


object NeoFlightOperationFilterForm{
  import NeoFlightOperationPartitionFilter._
  val filterForm = Form(
            mapping(
      "id" -> optional(longNumber),
      "reportingEntityId" -> optional(longNumber),
      "reportingEntityName" -> optional(nonEmptyText),
      "generalInformation" -> mapping(
        "whenFrom" -> optional(jodaDate("YYYY-MM-dd HH:mm")),
        "whenTo" -> optional(jodaDate("YYYY-MM-dd HH:mm")),
        "whenLocalFrom" -> optional(jodaDate("YYYY-MM-dd HH:mm")),
        "whenLocalTo" -> optional(jodaDate("YYYY-MM-dd HH:mm")),
        "whereStateAreaId" -> optional(longNumber),
        "whereLocationName" -> optional(nonEmptyText),
        "whereLatitudeOcc" -> optional(nonEmptyText),
        "whereLongitudeOcc" -> optional(nonEmptyText),
        "whatHeadline" -> optional(nonEmptyText),
        "whatNarrativeLanguaje" -> optional(nonEmptyText),
        "whatNarrative" -> optional(nonEmptyText)
      )/*(GeneralInformationFilter.apply)(GeneralInformationFilter.unapply)*/
      ((whenFrom,whenTo,whenLocalFrom,whenLocalTo,whereStateAreaId,whereLocationName,whereLatitudeOcc,whereLongitudeOcc,whatHeadline,whatNarrativeLanguaje,whatNarrative) => {
        GeneralInformationFilter(whenFrom, whenTo, whenLocalFrom, whenLocalTo, whereStateAreaId, whereLocationName, whereLatitudeOcc, whereLongitudeOcc, whatHeadline, whatNarrativeLanguaje, whatNarrative)
      })((formData: GeneralInformationFilter) => {
        Some((formData.whenFrom, formData.whenTo, formData.whenLocalFrom, formData.whenLocalTo, formData.whereStateAreaId, formData.whereLocationName, formData.whereLatitudeOcc, formData.whereLongitudeOcc, formData.whatHeadline, formData.whatNarrativeLanguaje, formData.whatNarrative))
      }),
      "aircraftIdentification" -> mapping(
        "stateOfRegistryId" -> optional(longNumber),
        "registration" -> optional(nonEmptyText),
        "manufacturerModelId" -> optional(longNumber),
        "serialNumber" -> optional(nonEmptyText),
        "yearBuilt" -> optional(number)
      )/*(AircraftIdentificationFilter.apply)(AircraftIdentificationFilter.unapply)*/
      ((stateOfRegistryId,registration,manufacturerModelId,serialNumber,yearBuilt) => {
        AircraftIdentificationFilter(stateOfRegistryId, registration, manufacturerModelId, serialNumber, yearBuilt)
      })((formData: AircraftIdentificationFilter) => {
        Some((formData.stateOfRegistryId, formData.registration, formData.manufacturerModelId, formData.serialNumber, formData.yearBuilt))
      }),
      "aircraftDescription" -> mapping(
        "categoryId" -> optional(longNumber),
        "massGroupId" -> optional(longNumber),
        "propulsionTypeId" -> optional(longNumber),
        "wakeTurbCategoryId" -> optional(longNumber),
        "numberOfEngines" -> optional(number),
        "landingGearTypeId" -> optional(longNumber),
        "maximumMass" -> optional(of(doubleFormat))
      )/*(AircraftDescriptionFilter.apply)(AircraftDescriptionFilter.unapply)*/
      ((categoryId,massGroupId,propulsionTypeId,wakeTurbCategoryId,numberOfEngines,landingGearTypeId,maximumMass) => {
        AircraftDescriptionFilter(categoryId, massGroupId, propulsionTypeId, wakeTurbCategoryId, numberOfEngines, landingGearTypeId, maximumMass)
      })((formData: AircraftDescriptionFilter) => {
        Some((formData.categoryId, formData.massGroupId, formData.propulsionTypeId, formData.wakeTurbCategoryId, formData.numberOfEngines, formData.landingGearTypeId, formData.maximumMass))
      }),
      "flightDetails" -> mapping(
        "departureId" -> optional(longNumber),
        "destinationId" -> optional(longNumber),
        "phaseId" -> optional(longNumber),
        "operatorId" -> optional(longNumber),
        "operationTypeId" -> optional(longNumber),
        "callsign" -> optional(nonEmptyText),
        "flightNumber" -> optional(nonEmptyText),
        "numberPersons" -> optional(number)
      )/*(FlightDetailsFilter.apply)(FlightDetailsFilter.unapply)*/
      ((departureId,destinationId,phaseId,operatorId,operationTypeId,callsign,flightNumber,numberPersons) => {
        FlightDetailsFilter(departureId, destinationId, phaseId, operatorId, operationTypeId, callsign, flightNumber, numberPersons)
      })((formData: FlightDetailsFilter) => {
        Some((formData.departureId, formData.destinationId, formData.phaseId, formData.operatorId, formData.operationTypeId, formData.callsign, formData.flightNumber, formData.numberPersons))
      }),
      "operationalInformation" -> mapping(
        "aircraftAltitude" -> optional(of(doubleFormat)),
        "aircraftFlightLevel" -> optional(nonEmptyText),
        "speed" -> optional(of(doubleFormat)),
        "typeOfAirspeedId" -> optional(longNumber),
        "flightRuleId" -> optional(longNumber),
        "trafficTypeId" -> optional(longNumber),
        "instrumentApprTypeId" -> optional(longNumber)
      )/*(OperationalInformationFilter.apply)(OperationalInformationFilter.unapply)*/
      ((aircraftAltitude,aircraftFlightLevel,speed,typeOfAirspeedId,flightRuleId,trafficTypeId,instrumentApprTypeId) => {
        OperationalInformationFilter(aircraftAltitude, aircraftFlightLevel, speed, typeOfAirspeedId, flightRuleId, trafficTypeId, instrumentApprTypeId)
      })((formData: OperationalInformationFilter) => {
        Some((formData.aircraftAltitude, formData.aircraftFlightLevel, formData.speed, formData.typeOfAirspeedId, formData.flightRuleId, formData.trafficTypeId, formData.instrumentApprTypeId))
      }),
      "flightCrews" -> list(models.FlightCrewFilterForm.filterForm.mapping),
      "classification" -> mapping(
        "ocurrenceClassId" -> optional(longNumber),
        "detectionPhaseId" -> optional(longNumber)
      )/*(ClassificationFilter.apply)(ClassificationFilter.unapply)*/
      ((ocurrenceClassId,detectionPhaseId) => {
        ClassificationFilter(ocurrenceClassId, detectionPhaseId)
      })((formData: ClassificationFilter) => {
        Some((formData.ocurrenceClassId, formData.detectionPhaseId))
      }),
      "neoFlightOperationOcurrenceCategorys" -> list(models.NeoFlightOperationOcurrenceCategoryFilterForm.filterForm.mapping),
      "neoFlightOperationEventTypePhases" -> list(models.NeoFlightOperationEventTypePhaseFilterForm.filterForm.mapping),
      "risk" -> mapping(
        "riskClassification" -> optional(nonEmptyText),
        "riskMethodology" -> optional(nonEmptyText),
        "riskAssessment" -> optional(nonEmptyText)
      )/*(RiskFilter.apply)(RiskFilter.unapply)*/
      ((riskClassification,riskMethodology,riskAssessment) => {
        RiskFilter(riskClassification, riskMethodology, riskAssessment)
      })((formData: RiskFilter) => {
        Some((formData.riskClassification, formData.riskMethodology, formData.riskAssessment))
      }),
      "assessment" -> mapping(
        "analysisFollowUp" -> optional(nonEmptyText),
        "correctiveActions" -> optional(nonEmptyText),
        "conclusions" -> optional(nonEmptyText)
      )/*(AssessmentFilter.apply)(AssessmentFilter.unapply)*/
      ((analysisFollowUp,correctiveActions,conclusions) => {
        AssessmentFilter(analysisFollowUp, correctiveActions, conclusions)
      })((formData: AssessmentFilter) => {
        Some((formData.analysisFollowUp, formData.correctiveActions, formData.conclusions))
      }),
      "reportManagement" -> mapping(
        "reportStatusId" -> optional(longNumber),
        "reportVersion" -> optional(nonEmptyText)
      )/*(ReportManagementFilter.apply)(ReportManagementFilter.unapply)*/
      ((reportStatusId,reportVersion) => {
        ReportManagementFilter(reportStatusId, reportVersion)
      })((formData: ReportManagementFilter) => {
        Some((formData.reportStatusId, formData.reportVersion))
      }),
      "attachments" -> optional(nonEmptyText)
    )/*(NeoFlightOperationFilter.apply)(NeoFlightOperationFilter.unapply)*/
    ((id,reportingEntityId,reportingEntityName,generalInformation,aircraftIdentification,aircraftDescription,flightDetails,operationalInformation,flightCrews,classification,neoFlightOperationOcurrenceCategorys,neoFlightOperationEventTypePhases,risk,assessment,reportManagement,attachments) => {
      NeoFlightOperationFilter(id, reportingEntityId, reportingEntityName, generalInformation, aircraftIdentification, aircraftDescription, flightDetails, operationalInformation, flightCrews, classification, neoFlightOperationOcurrenceCategorys, neoFlightOperationEventTypePhases, risk, assessment, reportManagement, attachments)
    })((formData: NeoFlightOperationFilter) => {
      Some((formData.id, formData.reportingEntityId, formData.reportingEntityName, formData.generalInformation, formData.aircraftIdentification, formData.aircraftDescription, formData.flightDetails, formData.operationalInformation, formData.flightCrews, formData.classification, formData.neoFlightOperationOcurrenceCategorys, formData.neoFlightOperationEventTypePhases, formData.risk, formData.assessment, formData.reportManagement, formData.attachments))
    })
  )
}

