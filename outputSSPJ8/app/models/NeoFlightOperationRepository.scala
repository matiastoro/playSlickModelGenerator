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
class NeoFlightOperationRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, type_of_airspeedRepo: TypeOfAirspeedRepository, flight_ruleRepo: FlightRuleRepository, aircraft_manufacturer_modelRepo: AircraftManufacturerModelRepository, detection_phaseRepo: DetectionPhaseRepository, aircraft_categoryRepo: AircraftCategoryRepository, operation_typeRepo: OperationTypeRepository, aircraft_mass_groupRepo: AircraftMassGroupRepository, ocurrence_classRepo: OcurrenceClassRepository, aircraft_wake_turb_categoryRepo: AircraftWakeTurbCategoryRepository, neo_report_statusRepo: NeoReportStatusRepository, aircraft_propulsion_typeRepo: AircraftPropulsionTypeRepository, instrument_appr_typeRepo: InstrumentApprTypeRepository, reporting_entityRepo: ReportingEntityRepository, aerodromeRepo: AerodromeRepository, phaseRepo: PhaseRepository, traffic_typeRepo: TrafficTypeRepository, aircraft_landing_gear_typeRepo: AircraftLandingGearTypeRepository, state_areaRepo: StateAreaRepository, operatorRepo: OperatorRepository)(implicit ec: ExecutionContext) extends DatabaseClient[NeoFlightOperation] with NeoFlightOperationQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  import models.NeoFlightOperationPartition._



  class NeoFlightOperationTable(tag: Tag) extends Table[NeoFlightOperation](tag, "neo_flight_operation") {
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
  
  
    def stateOfRegistryId = column[Long]("state_of_registry_id", O.Default(0L))
    def registration = column[String]("registration", O.Default(""))
    def manufacturerModelId = column[Long]("manufacturer_model_id", O.Default(0L))
    def serialNumber = column[String]("serial_number", O.Default(""))
    def yearBuilt = column[Option[Int]]("year_built", O.Default(None))
    val aircraftIdentificationCols = (stateOfRegistryId, registration, manufacturerModelId, serialNumber, yearBuilt)
  
  
    def categoryId = column[Long]("category_id", O.Default(0L))
    def massGroupId = column[Long]("mass_group_id", O.Default(0L))
    def propulsionTypeId = column[Long]("propulsion_type_id", O.Default(0L))
    def wakeTurbCategoryId = column[Option[Long]]("wake_turb_category_id", O.Default(None))
    def numberOfEngines = column[Option[Int]]("number_of_engines", O.Default(None))
    def landingGearTypeId = column[Option[Long]]("landing_gear_type_id", O.Default(None))
    def maximumMass = column[Option[Double]]("maximum_mass", O.Default(None))
    val aircraftDescriptionCols = (categoryId, massGroupId, propulsionTypeId, wakeTurbCategoryId, numberOfEngines, landingGearTypeId, maximumMass)
  
  
    def departureId = column[Long]("departure_id", O.Default(0L))
    def destinationId = column[Long]("destination_id", O.Default(0L))
    def phaseId = column[Long]("phase_id", O.Default(0L))
    def operatorId = column[Long]("operator_id", O.Default(0L))
    def operationTypeId = column[Long]("operation_type_id", O.Default(0L))
    def callsign = column[String]("callsign", O.Default(""))
    def flightNumber = column[Option[String]]("flight_number", O.Default(None))
    def numberPersons = column[Option[Int]]("number_persons", O.Default(None))
    val flightDetailsCols = (departureId, destinationId, phaseId, operatorId, operationTypeId, callsign, flightNumber, numberPersons)
  
  
    def aircraftAltitude = column[Option[Double]]("aircraft_altitude", O.Default(None))
    def aircraftFlightLevel = column[Option[String]]("aircraft_flight_level", O.Default(None))
    def speed = column[Option[Double]]("speed", O.Default(None))
    def typeOfAirspeedId = column[Option[Long]]("type_of_airspeed_id", O.Default(None))
    def flightRuleId = column[Option[Long]]("flight_rule_id", O.Default(None))
    def trafficTypeId = column[Option[Long]]("traffic_type_id", O.Default(None))
    def instrumentApprTypeId = column[Option[Long]]("instrument_appr_type_id", O.Default(None))
    val operationalInformationCols = (aircraftAltitude, aircraftFlightLevel, speed, typeOfAirspeedId, flightRuleId, trafficTypeId, instrumentApprTypeId)
  
  
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
    def * = (id.?, reportingEntityId, reportingEntityName, generalInformationCols, aircraftIdentificationCols, aircraftDescriptionCols, flightDetailsCols, operationalInformationCols, classificationCols, riskCols, assessmentCols, reportManagementCols, attachments).shaped <> 
      ({
        case (id, reportingEntityId, reportingEntityName, generalInformation, aircraftIdentification, aircraftDescription, flightDetails, operationalInformation, classification, risk, assessment, reportManagement, attachments) =>
        NeoFlightOperation(id, reportingEntityId, reportingEntityName, GeneralInformation.tupled.apply(generalInformation), AircraftIdentification.tupled.apply(aircraftIdentification), AircraftDescription.tupled.apply(aircraftDescription), FlightDetails.tupled.apply(flightDetails), OperationalInformation.tupled.apply(operationalInformation), Classification.tupled.apply(classification), Risk.tupled.apply(risk), Assessment.tupled.apply(assessment), ReportManagement.tupled.apply(reportManagement), attachments)
      }, {o: NeoFlightOperation =>
        Some((o.id,o.reportingEntityId,o.reportingEntityName,GeneralInformation.unapply(o.generalInformation).get,AircraftIdentification.unapply(o.aircraftIdentification).get,AircraftDescription.unapply(o.aircraftDescription).get,FlightDetails.unapply(o.flightDetails).get,OperationalInformation.unapply(o.operationalInformation).get,Classification.unapply(o.classification).get,Risk.unapply(o.risk).get,Assessment.unapply(o.assessment).get,ReportManagement.unapply(o.reportManagement).get,o.attachments))
      })
  }

  /**
  * The starting point for all queries on the people table.
  */
  type DBTable = NeoFlightOperationTable
  val all = TableQuery[NeoFlightOperationTable]

  def byReportingEntityId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.reportingEntityId===id).result
  }}.getOrElse{Future(List())}
                            

  def byWhereStateAreaId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.whereStateAreaId===id).result
  }}.getOrElse{Future(List())}
                            

  def byStateOfRegistryId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.stateOfRegistryId===id).result
  }}.getOrElse{Future(List())}
                            

  def byManufacturerModelId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.manufacturerModelId===id).result
  }}.getOrElse{Future(List())}
                            

  def byCategoryId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.categoryId===id).result
  }}.getOrElse{Future(List())}
                            

  def byMassGroupId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.massGroupId===id).result
  }}.getOrElse{Future(List())}
                            

  def byPropulsionTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.propulsionTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byWakeTurbCategoryId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.wakeTurbCategoryId===id).result
  }}.getOrElse{Future(List())}
                            

  def byLandingGearTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.landingGearTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byDepartureId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.departureId===id).result
  }}.getOrElse{Future(List())}
                            

  def byDestinationId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.destinationId===id).result
  }}.getOrElse{Future(List())}
                            

  def byPhaseId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.phaseId===id).result
  }}.getOrElse{Future(List())}
                            

  def byOperatorId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.operatorId===id).result
  }}.getOrElse{Future(List())}
                            

  def byOperationTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.operationTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byTypeOfAirspeedId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.typeOfAirspeedId===id).result
  }}.getOrElse{Future(List())}
                            

  def byFlightRuleId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.flightRuleId===id).result
  }}.getOrElse{Future(List())}
                            

  def byTrafficTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.trafficTypeId===id).result
  }}.getOrElse{Future(List())}
                            

  def byInstrumentApprTypeId(id: Option[Long]) = id.map{id => db.run{
      all.filter(_.instrumentApprTypeId===id).result
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
                            


  def getReportingEntity(obj: NeoFlightOperation) = reporting_entityRepo.byId(obj.reportingEntityId)
  def getWhereStateArea(obj: NeoFlightOperation) = state_areaRepo.byId(obj.generalInformation.whereStateAreaId)
  def getStateOfRegistry(obj: NeoFlightOperation) = state_areaRepo.byId(obj.aircraftIdentification.stateOfRegistryId)
  def getManufacturerModel(obj: NeoFlightOperation) = aircraft_manufacturer_modelRepo.byId(obj.aircraftIdentification.manufacturerModelId)
  def getCategory(obj: NeoFlightOperation) = aircraft_categoryRepo.byId(obj.aircraftDescription.categoryId)
  def getMassGroup(obj: NeoFlightOperation) = aircraft_mass_groupRepo.byId(obj.aircraftDescription.massGroupId)
  def getPropulsionType(obj: NeoFlightOperation) = aircraft_propulsion_typeRepo.byId(obj.aircraftDescription.propulsionTypeId)
  def getWakeTurbCategory(obj: NeoFlightOperation) = if(obj.aircraftDescription.wakeTurbCategoryId.isDefined) aircraft_wake_turb_categoryRepo.byId(obj.aircraftDescription.wakeTurbCategoryId.get) else None
  def getLandingGearType(obj: NeoFlightOperation) = if(obj.aircraftDescription.landingGearTypeId.isDefined) aircraft_landing_gear_typeRepo.byId(obj.aircraftDescription.landingGearTypeId.get) else None
  def getDeparture(obj: NeoFlightOperation) = aerodromeRepo.byId(obj.flightDetails.departureId)
  def getDestination(obj: NeoFlightOperation) = aerodromeRepo.byId(obj.flightDetails.destinationId)
  def getPhase(obj: NeoFlightOperation) = phaseRepo.byId(obj.flightDetails.phaseId)
  def getOperator(obj: NeoFlightOperation) = operatorRepo.byId(obj.flightDetails.operatorId)
  def getOperationType(obj: NeoFlightOperation) = operation_typeRepo.byId(obj.flightDetails.operationTypeId)
  def getTypeOfAirspeed(obj: NeoFlightOperation) = if(obj.operationalInformation.typeOfAirspeedId.isDefined) type_of_airspeedRepo.byId(obj.operationalInformation.typeOfAirspeedId.get) else None
  def getFlightRule(obj: NeoFlightOperation) = if(obj.operationalInformation.flightRuleId.isDefined) flight_ruleRepo.byId(obj.operationalInformation.flightRuleId.get) else None
  def getTrafficType(obj: NeoFlightOperation) = if(obj.operationalInformation.trafficTypeId.isDefined) traffic_typeRepo.byId(obj.operationalInformation.trafficTypeId.get) else None
  def getInstrumentApprType(obj: NeoFlightOperation) = if(obj.operationalInformation.instrumentApprTypeId.isDefined) instrument_appr_typeRepo.byId(obj.operationalInformation.instrumentApprTypeId.get) else None
  def getOcurrenceClass(obj: NeoFlightOperation) = ocurrence_classRepo.byId(obj.classification.ocurrenceClassId)
  def getDetectionPhase(obj: NeoFlightOperation) = detection_phaseRepo.byId(obj.classification.detectionPhaseId)
  def getReportStatus(obj: NeoFlightOperation) = if(obj.reportManagement.reportStatusId.isDefined) neo_report_statusRepo.byId(obj.reportManagement.reportStatusId.get) else None
}