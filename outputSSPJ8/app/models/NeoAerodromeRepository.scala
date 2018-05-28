package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._

import scala.concurrent.{ Future, ExecutionContext }
import models.extensions._
import org.joda.time.{DateTime, LocalDate}
import com.github.tototoshi.slick.H2JodaSupport._

@Singleton
class NeoAerodromeRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, aerodrome_locationRepo: AerodromeLocationRepository, helicopter_landing_area_area_configurationRepo: HelicopterLandingAreaAreaConfigurationRepository, runwayRepo: RunwayRepository, detection_phaseRepo: DetectionPhaseRepository, helicopter_landing_area_typeRepo: HelicopterLandingAreaTypeRepository, helicopter_landing_area_surface_typeRepo: HelicopterLandingAreaSurfaceTypeRepository, aerodrome_statusRepo: AerodromeStatusRepository, aerodrome_typeRepo: AerodromeTypeRepository, ocurrence_classRepo: OcurrenceClassRepository, neo_report_statusRepo: NeoReportStatusRepository, reporting_entityRepo: ReportingEntityRepository, aerodromeRepo: AerodromeRepository, runway_surface_typeRepo: RunwaySurfaceTypeRepository, state_areaRepo: StateAreaRepository)(implicit ec: ExecutionContext) extends DatabaseClient[NeoAerodrome] with NeoAerodromeQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  import models.NeoAerodromePartition._



  class NeoAerodromeTable(tag: Tag) extends Table[NeoAerodrome](tag, "neo_aerodrome") {
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
    def runwayId = column[Option[Long]]("runway_id", O.Default(None))
    def runwaySurfaceTypeId = column[Option[Long]]("runway_surface_type_id", O.Default(None))
    def helicopterLandingAreaTypeId = column[Option[Long]]("helicopter_landing_area_type_id", O.Default(None))
    def helicopterLandingAreaAreaConfigurationId = column[Option[Long]]("helicopter_landing_area_area_configuration_id", O.Default(None))
    def helicopterLandingAreaSurfaceTypeId = column[Option[Long]]("helicopter_landing_area_surface_type_id", O.Default(None))
    val aerodromeDescriptionCols = (aerodromeId, aerodromeLocationId, aerodromeStatusId, aerodromeTypeId, latitude, longitude, runwayId, runwaySurfaceTypeId, helicopterLandingAreaTypeId, helicopterLandingAreaAreaConfigurationId, helicopterLandingAreaSurfaceTypeId)
  
  
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
}