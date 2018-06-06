package models

import javax.inject.{Inject, Singleton, Provider}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._

import scala.concurrent.{ Future, ExecutionContext }
import models.extensions._
import org.joda.time.{DateTime, LocalDate}
import com.github.tototoshi.slick.H2JodaSupport._

@Singleton
class NeoAerodromeRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, aerodrome_locationRepo: AerodromeLocationRepository, helicopter_landing_area_area_configurationRepo: HelicopterLandingAreaAreaConfigurationRepository, runwayRepo: RunwayRepository, detection_phaseRepo: DetectionPhaseRepository, helicopter_landing_area_typeRepo: HelicopterLandingAreaTypeRepository, helicopter_landing_area_surface_typeRepo: HelicopterLandingAreaSurfaceTypeRepository, aerodrome_statusRepo: AerodromeStatusRepository, aerodrome_typeRepo: AerodromeTypeRepository, ocurrence_classRepo: OcurrenceClassRepository, neo_report_statusRepo: NeoReportStatusRepository, neo_event_type_phaseRepo: Provider[NeoEventTypePhaseRepository], reporting_entityRepo: ReportingEntityRepository, aerodromeRepo: AerodromeRepository, runway_surface_typeRepo: RunwaySurfaceTypeRepository, neo_aerodrome_ocurrence_categoryRepo: Provider[NeoAerodromeOcurrenceCategoryRepository], state_areaRepo: StateAreaRepository)(implicit ec: ExecutionContext) extends DatabaseClient[NeoAerodrome] with NeoAerodromeQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  import models.NeoAerodromePartition._



  class NeoAerodromeTable(tag: Tag) extends Table[NeoAerodrome](tag, "neo_aerodrome"){
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def reportingEntityId = column[Long]("reporting_entity_id", O.Default(0L))
    def reportingEntityName = column[String]("reporting_entity_name", O.Default(""))
  
    def when = column[DateTime]("when", O.Default(new DateTime()))
    def whenLocal = column[Option[DateTime]]("when_local", O.Default(None))
    def whereStateAreaId = column[Long]("where_state_area_id", O.Default(0L))
    def whereLocationName = column[String]("where_location_name", O.Default(""))
    def whereLatitudeOcc = column[Option[String]]("where_latitude_occ", O.Default(None))
    def whereLongitudeOcc = column[Option[String]]("where_longitude_occ", O.Default(None))
    def whatHeadline = column[String]("what_headline", O.Default(""))
    def whatNarrativeLanguaje = column[String]("what_narrative_languaje", O.Default(""))
    def whatNarrative = column[String]("what_narrative", O.Default(""))
    val generalInformationCols = (when, whenLocal, whereStateAreaId, whereLocationName, whereLatitudeOcc, whereLongitudeOcc, whatHeadline, whatNarrativeLanguaje, whatNarrative)
  
  
    def aerodromeId = column[Long]("aerodrome_id", O.Default(0L))
    def aerodromeLocationId = column[Long]("aerodrome_location_id", O.Default(0L))
    def aerodromeStatusId = column[Long]("aerodrome_status_id", O.Default(0L))
    def aerodromeTypeId = column[Long]("aerodrome_type_id", O.Default(0L))
    def latitude = column[Option[String]]("latitude", O.Default(None))
    def longitude = column[Option[String]]("longitude", O.Default(None))
    def elevationAboveMsl = column[Option[Double]]("elevation_above_msl", O.Default(None))
    def runwayId = column[Option[Long]]("runway_id", O.Default(None))
    def runwaySurfaceTypeId = column[Option[Long]]("runway_surface_type_id", O.Default(None))
    def helicopterLandingAreaTypeId = column[Option[Long]]("helicopter_landing_area_type_id", O.Default(None))
    def helicopterLandingAreaAreaConfigurationId = column[Option[Long]]("helicopter_landing_area_area_configuration_id", O.Default(None))
    def helicopterLandingAreaSurfaceTypeId = column[Option[Long]]("helicopter_landing_area_surface_type_id", O.Default(None))
    val aerodromeDescriptionCols = (aerodromeId, aerodromeLocationId, aerodromeStatusId, aerodromeTypeId, latitude, longitude, elevationAboveMsl, runwayId, runwaySurfaceTypeId, helicopterLandingAreaTypeId, helicopterLandingAreaAreaConfigurationId, helicopterLandingAreaSurfaceTypeId)
  
  
    def ocurrenceClassId = column[Long]("ocurrence_class_id", O.Default(0L))
    def detectionPhaseId = column[Long]("detection_phase_id", O.Default(0L))
    val classificationCols = (ocurrenceClassId, detectionPhaseId)
  
  
    def riskClassification = column[String]("risk_classification", O.Default(""))
    def riskMethodology = column[Option[String]]("risk_methodology", O.Default(None))
    def riskAssessment = column[Option[String]]("risk_assessment", O.Default(None))
    val riskCols = (riskClassification, riskMethodology, riskAssessment)
  
  
    def analysisFollowUp = column[Option[String]]("analysis_follow_up", O.Default(None))
    def correctiveActions = column[Option[String]]("corrective_actions", O.Default(None))
    def conclusions = column[Option[String]]("conclusions", O.Default(None))
    val assessmentCols = (analysisFollowUp, correctiveActions, conclusions)
  
  
    def reportStatusId = column[Option[Long]]("report_status_id", O.Default(None))
    def reportVersion = column[Option[String]]("report_version", O.Default(None))
    val reportManagementCols = (reportStatusId, reportVersion)
  
    def attachments = column[Option[String]]("attachments", O.Default(None))
  
  /**
  * This is the tables default "projection".
  *
  * It defines how the columns are converted to and from the User object.
  *
  * In this case, we are simply passing the id, name and page parameters to the User case classes
  * apply and unapply methods.
  */
    def * = (id.?, reportingEntityId, reportingEntityName, generalInformationCols, aerodromeDescriptionCols, classificationCols, riskCols, assessmentCols, reportManagementCols, attachments).shaped <> 
      ({
        case (id, reportingEntityId, reportingEntityName, generalInformation, aerodromeDescription, classification, risk, assessment, reportManagement, attachments) =>
        NeoAerodrome(id, reportingEntityId, reportingEntityName, GeneralInformation.tupled.apply(generalInformation), AerodromeDescription.tupled.apply(aerodromeDescription), Classification.tupled.apply(classification), Risk.tupled.apply(risk), Assessment.tupled.apply(assessment), ReportManagement.tupled.apply(reportManagement), attachments)
      }, {o: NeoAerodrome =>
        Some((o.id,o.reportingEntityId,o.reportingEntityName,GeneralInformation.unapply(o.generalInformation).get,AerodromeDescription.unapply(o.aerodromeDescription).get,Classification.unapply(o.classification).get,Risk.unapply(o.risk).get,Assessment.unapply(o.assessment).get,ReportManagement.unapply(o.reportManagement).get,o.attachments))
      })
  }

  override val sortMap = Map[String, SortMapType](
    "id" -> {(q,s) => q.sortBy(_.id)(s)},
    //displayField for field reportingEntity is not defined in YML,
    "reportingEntityName" -> {(q,s) => q.sortBy(_.reportingEntityName)(s)},
    "when" -> {(q,s) => q.sortBy(_.when)(s)},
    "whenLocal" -> {(q,s) => q.sortBy(_.whenLocal)(s)},
    //displayField for field stateArea is not defined in YML,
    "whereLocationName" -> {(q,s) => q.sortBy(_.whereLocationName)(s)},
    "whereLatitudeOcc" -> {(q,s) => q.sortBy(_.whereLatitudeOcc)(s)},
    "whereLongitudeOcc" -> {(q,s) => q.sortBy(_.whereLongitudeOcc)(s)},
    "whatHeadline" -> {(q,s) => q.sortBy(_.whatHeadline)(s)},
    "whatNarrativeLanguaje" -> {(q,s) => q.sortBy(_.whatNarrativeLanguaje)(s)},
    "whatNarrative" -> {(q,s) => q.sortBy(_.whatNarrative)(s)},
    "aerodromeId" -> {(q,s) => q.join(aerodromeRepo.all).on(_.aerodromeId === _.id).sortBy(_._2.icaoCode)(s).map{_._1}},
    "aerodromeLocationId" -> {(q,s) => q.join(aerodrome_locationRepo.all).on(_.aerodromeLocationId === _.id).sortBy(_._2.location)(s).map{_._1}},
    "aerodromeStatusId" -> {(q,s) => q.join(aerodrome_statusRepo.all).on(_.aerodromeStatusId === _.id).sortBy(_._2.status)(s).map{_._1}},
    "aerodromeTypeId" -> {(q,s) => q.join(aerodrome_typeRepo.all).on(_.aerodromeTypeId === _.id).sortBy(_._2.tpe)(s).map{_._1}},
    "latitude" -> {(q,s) => q.sortBy(_.latitude)(s)},
    "longitude" -> {(q,s) => q.sortBy(_.longitude)(s)},
    "elevationAboveMsl" -> {(q,s) => q.sortBy(_.elevationAboveMsl)(s)},
    //displayField for field runway is not defined in YML,
    "runwaySurfaceTypeId" -> {(q,s) => q.join(runway_surface_typeRepo.all).on(_.runwaySurfaceTypeId === _.id).sortBy(_._2.surfaceType)(s).map{_._1}},
    "helicopterLandingAreaTypeId" -> {(q,s) => q.join(helicopter_landing_area_typeRepo.all).on(_.helicopterLandingAreaTypeId === _.id).sortBy(_._2.tpe)(s).map{_._1}},
    "helicopterLandingAreaAreaConfigurationId" -> {(q,s) => q.join(helicopter_landing_area_area_configurationRepo.all).on(_.helicopterLandingAreaAreaConfigurationId === _.id).sortBy(_._2.areaConfiguration)(s).map{_._1}},
    "helicopterLandingAreaSurfaceTypeId" -> {(q,s) => q.join(helicopter_landing_area_surface_typeRepo.all).on(_.helicopterLandingAreaSurfaceTypeId === _.id).sortBy(_._2.surfaceType)(s).map{_._1}},
    //displayField for field ocurrenceClass is not defined in YML,
    //displayField for field detectionPhase is not defined in YML,
    "riskClassification" -> {(q,s) => q.sortBy(_.riskClassification)(s)},
    "riskMethodology" -> {(q,s) => q.sortBy(_.riskMethodology)(s)},
    "riskAssessment" -> {(q,s) => q.sortBy(_.riskAssessment)(s)},
    "analysisFollowUp" -> {(q,s) => q.sortBy(_.analysisFollowUp)(s)},
    "correctiveActions" -> {(q,s) => q.sortBy(_.correctiveActions)(s)},
    "conclusions" -> {(q,s) => q.sortBy(_.conclusions)(s)},
    "reportStatusId" -> {(q,s) => q.join(neo_report_statusRepo.all).on(_.reportStatusId === _.id).sortBy(_._2.reportStatus)(s).map{_._1}},
    "reportVersion" -> {(q,s) => q.sortBy(_.reportVersion)(s)},
    "attachments" -> {(q,s) => q.sortBy(_.attachments)(s)}
  )
      

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = NeoAerodromeTable
  val all = TableQuery[NeoAerodromeTable]

  def byReportingEntityId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.reportingEntityId===id).result
  }}.getOrElse{Future(List())}
                            

  def byWhereStateAreaId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.whereStateAreaId===id).result
  }}.getOrElse{Future(List())}
                            

  def byAerodromeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.aerodromeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byAerodromeLocationId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.aerodromeLocationId===id).result
  }}.getOrElse{Future(List())}
                            

  def byAerodromeStatusId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.aerodromeStatusId===id).result
  }}.getOrElse{Future(List())}
                            

  def byAerodromeTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.aerodromeTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byRunwayId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.runwayId===id).result
  }}.getOrElse{Future(List())}
                            

  def byRunwaySurfaceTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.runwaySurfaceTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byHelicopterLandingAreaTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.helicopterLandingAreaTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byHelicopterLandingAreaAreaConfigurationId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.helicopterLandingAreaAreaConfigurationId===id).result
  }}.getOrElse{Future(List())}
                            

  def byHelicopterLandingAreaSurfaceTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.helicopterLandingAreaSurfaceTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byOcurrenceClassId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.ocurrenceClassId===id).result
  }}.getOrElse{Future(List())}
                            

  def byDetectionPhaseId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.detectionPhaseId===id).result
  }}.getOrElse{Future(List())}
                            

  def byReportStatusId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.reportStatusId===id).result
  }}.getOrElse{Future(List())}
                            


  def getReportingEntity(obj: NeoAerodrome) = reporting_entityRepo.byId(obj.reportingEntityId)
  def getWhereStateArea(obj: NeoAerodrome) = state_areaRepo.byId(obj.generalInformation.whereStateAreaId)
  def getAerodrome(obj: NeoAerodrome) = aerodromeRepo.byId(obj.aerodromeDescription.aerodromeId)
  def getAerodromeLocation(obj: NeoAerodrome) = aerodrome_locationRepo.byId(obj.aerodromeDescription.aerodromeLocationId)
  def getAerodromeStatus(obj: NeoAerodrome) = aerodrome_statusRepo.byId(obj.aerodromeDescription.aerodromeStatusId)
  def getAerodromeType(obj: NeoAerodrome) = aerodrome_typeRepo.byId(obj.aerodromeDescription.aerodromeTypeId)
  def getRunway(obj: NeoAerodrome) = if(obj.aerodromeDescription.runwayId.isDefined) runwayRepo.byId(obj.aerodromeDescription.runwayId.get) else None
  def getRunwaySurfaceType(obj: NeoAerodrome) = if(obj.aerodromeDescription.runwaySurfaceTypeId.isDefined) runway_surface_typeRepo.byId(obj.aerodromeDescription.runwaySurfaceTypeId.get) else None
  def getHelicopterLandingAreaType(obj: NeoAerodrome) = if(obj.aerodromeDescription.helicopterLandingAreaTypeId.isDefined) helicopter_landing_area_typeRepo.byId(obj.aerodromeDescription.helicopterLandingAreaTypeId.get) else None
  def getHelicopterLandingAreaAreaConfiguration(obj: NeoAerodrome) = if(obj.aerodromeDescription.helicopterLandingAreaAreaConfigurationId.isDefined) helicopter_landing_area_area_configurationRepo.byId(obj.aerodromeDescription.helicopterLandingAreaAreaConfigurationId.get) else None
  def getHelicopterLandingAreaSurfaceType(obj: NeoAerodrome) = if(obj.aerodromeDescription.helicopterLandingAreaSurfaceTypeId.isDefined) helicopter_landing_area_surface_typeRepo.byId(obj.aerodromeDescription.helicopterLandingAreaSurfaceTypeId.get) else None
  def getOcurrenceClass(obj: NeoAerodrome) = ocurrence_classRepo.byId(obj.classification.ocurrenceClassId)
  def getDetectionPhase(obj: NeoAerodrome) = detection_phaseRepo.byId(obj.classification.detectionPhaseId)
  def getReportStatus(obj: NeoAerodrome) = if(obj.reportManagement.reportStatusId.isDefined) neo_report_statusRepo.byId(obj.reportManagement.reportStatusId.get) else None

  def filter(formData: NeoAerodromeFilter) = {
    all
      .filteredBy( formData.id.flatMap{x => if(x>0L) Some(x) else None} )( _.id === _ )
      .filteredBy( formData.reportingEntityId.collect{case x if x>0L => x} )( _.reportingEntityId === _ )
      .filteredBy(formData.reportingEntityName)((x,y) => x.reportingEntityName.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.generalInformation.whenFrom )( _.when >= _ )
      .filteredBy( formData.generalInformation.whenTo )( _.when <= _ )
      .filteredBy( formData.generalInformation.whenLocalFrom )((x,y) => x.whenLocal.map{d => d >= y}.getOrElse(false) )
      .filteredBy( formData.generalInformation.whenLocalTo )((x,y) => x.whenLocal.map{d => d <= y}.getOrElse(false) )
      .filteredBy( formData.generalInformation.whereStateAreaId.collect{case x if x>0L => x} )( _.whereStateAreaId === _ )
      .filteredBy(formData.generalInformation.whereLocationName)((x,y) => x.whereLocationName.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.generalInformation.whereLatitudeOcc)((x,y) => x.whereLatitudeOcc.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.generalInformation.whereLongitudeOcc)((x,y) => x.whereLongitudeOcc.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.generalInformation.whatHeadline)((x,y) => x.whatHeadline.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.generalInformation.whatNarrativeLanguaje)((x,y) => x.whatNarrativeLanguaje.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.generalInformation.whatNarrative)((x,y) => x.whatNarrative.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.aerodromeDescription.aerodromeId.collect{case x if x>0L => x} )( _.aerodromeId === _ )
      .filteredBy( formData.aerodromeDescription.aerodromeLocationId.collect{case x if x>0L => x} )( _.aerodromeLocationId === _ )
      .filteredBy( formData.aerodromeDescription.aerodromeStatusId.collect{case x if x>0L => x} )( _.aerodromeStatusId === _ )
      .filteredBy( formData.aerodromeDescription.aerodromeTypeId.collect{case x if x>0L => x} )( _.aerodromeTypeId === _ )
      .filteredBy(formData.aerodromeDescription.latitude)((x,y) => x.latitude.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.aerodromeDescription.longitude)((x,y) => x.longitude.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.aerodromeDescription.elevationAboveMsl)((x,y) => x.elevationAboveMsl.getOrElse(0.0) === y)
      .filteredBy( formData.aerodromeDescription.runwayId.flatMap{x => if(x>0L) Some(x) else None} )( _.runwayId.getOrElse(0L) === _ )
      .filteredBy( formData.aerodromeDescription.runwaySurfaceTypeId.flatMap{x => if(x>0L) Some(x) else None} )( _.runwaySurfaceTypeId.getOrElse(0L) === _ )
      .filteredBy( formData.aerodromeDescription.helicopterLandingAreaTypeId.flatMap{x => if(x>0L) Some(x) else None} )( _.helicopterLandingAreaTypeId.getOrElse(0L) === _ )
      .filteredBy( formData.aerodromeDescription.helicopterLandingAreaAreaConfigurationId.flatMap{x => if(x>0L) Some(x) else None} )( _.helicopterLandingAreaAreaConfigurationId.getOrElse(0L) === _ )
      .filteredBy( formData.aerodromeDescription.helicopterLandingAreaSurfaceTypeId.flatMap{x => if(x>0L) Some(x) else None} )( _.helicopterLandingAreaSurfaceTypeId.getOrElse(0L) === _ )
      .filteredBy( formData.classification.ocurrenceClassId.collect{case x if x>0L => x} )( _.ocurrenceClassId === _ )
      .filteredBy( formData.classification.detectionPhaseId.collect{case x if x>0L => x} )( _.detectionPhaseId === _ )
      .oneToManyFilterfilter(formData.neoAerodromeOcurrenceCategorys)((x,o) => neo_aerodrome_ocurrence_categoryRepo.get().filter(o).filter(y => y.neoAerodromeId === x.id).exists)
      .oneToManyFilterfilter(formData.neoEventTypePhases)((x,o) => neo_event_type_phaseRepo.get().filter(o).filter(y => y.neoAerodromeId === x.id).exists)
      .filteredBy(formData.risk.riskClassification)((x,y) => x.riskClassification.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.risk.riskMethodology)((x,y) => x.riskMethodology.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.risk.riskAssessment)((x,y) => x.riskAssessment.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.assessment.analysisFollowUp)((x,y) => x.analysisFollowUp.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.assessment.correctiveActions)((x,y) => x.correctiveActions.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.assessment.conclusions)((x,y) => x.conclusions.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.reportManagement.reportStatusId.flatMap{x => if(x>0L) Some(x) else None} )( _.reportStatusId.getOrElse(0L) === _ )
      .filteredBy(formData.reportManagement.reportVersion)((x,y) => x.reportVersion.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.attachments)((x,y) => x.attachments.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
  }
}