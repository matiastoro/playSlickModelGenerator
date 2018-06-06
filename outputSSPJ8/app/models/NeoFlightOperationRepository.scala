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
class NeoFlightOperationRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, flight_crewRepo: Provider[FlightCrewRepository], type_of_airspeedRepo: TypeOfAirspeedRepository, flight_ruleRepo: FlightRuleRepository, aircraft_manufacturer_modelRepo: AircraftManufacturerModelRepository, detection_phaseRepo: DetectionPhaseRepository, aircraft_categoryRepo: AircraftCategoryRepository, neo_flight_operation_event_type_phaseRepo: Provider[NeoFlightOperationEventTypePhaseRepository], operation_typeRepo: OperationTypeRepository, neo_flight_operation_ocurrence_categoryRepo: Provider[NeoFlightOperationOcurrenceCategoryRepository], aircraft_mass_groupRepo: AircraftMassGroupRepository, ocurrence_classRepo: OcurrenceClassRepository, aircraft_wake_turb_categoryRepo: AircraftWakeTurbCategoryRepository, neo_report_statusRepo: NeoReportStatusRepository, aircraft_propulsion_typeRepo: AircraftPropulsionTypeRepository, instrument_appr_typeRepo: InstrumentApprTypeRepository, reporting_entityRepo: ReportingEntityRepository, aerodromeRepo: AerodromeRepository, phaseRepo: PhaseRepository, traffic_typeRepo: TrafficTypeRepository, aircraft_landing_gear_typeRepo: AircraftLandingGearTypeRepository, state_areaRepo: StateAreaRepository, operatorRepo: OperatorRepository)(implicit ec: ExecutionContext) extends DatabaseClient[NeoFlightOperation] with NeoFlightOperationQuery{

  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  import models.NeoFlightOperationPartition._



  class NeoFlightOperationTable(tag: Tag) extends Table[NeoFlightOperation](tag, "neo_flight_operation"){
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
    //displayField for field stateArea is not defined in YML,
    "registration" -> {(q,s) => q.sortBy(_.registration)(s)},
    "manufacturerModelId" -> {(q,s) => q.join(aircraft_manufacturer_modelRepo.all).on(_.manufacturerModelId === _.id).sortBy(_._2.manufacturerModel)(s).map{_._1}},
    "serialNumber" -> {(q,s) => q.sortBy(_.serialNumber)(s)},
    "yearBuilt" -> {(q,s) => q.sortBy(_.yearBuilt)(s)},
    "categoryId" -> {(q,s) => q.join(aircraft_categoryRepo.all).on(_.categoryId === _.id).sortBy(_._2.category)(s).map{_._1}},
    "massGroupId" -> {(q,s) => q.join(aircraft_mass_groupRepo.all).on(_.massGroupId === _.id).sortBy(_._2.massGroup)(s).map{_._1}},
    "propulsionTypeId" -> {(q,s) => q.join(aircraft_propulsion_typeRepo.all).on(_.propulsionTypeId === _.id).sortBy(_._2.propulsionType)(s).map{_._1}},
    "wakeTurbCategoryId" -> {(q,s) => q.join(aircraft_wake_turb_categoryRepo.all).on(_.wakeTurbCategoryId === _.id).sortBy(_._2.wakeTurbCategory)(s).map{_._1}},
    "numberOfEngines" -> {(q,s) => q.sortBy(_.numberOfEngines)(s)},
    "landingGearTypeId" -> {(q,s) => q.join(aircraft_landing_gear_typeRepo.all).on(_.landingGearTypeId === _.id).sortBy(_._2.landingGearType)(s).map{_._1}},
    "maximumMass" -> {(q,s) => q.sortBy(_.maximumMass)(s)},
    "departureId" -> {(q,s) => q.join(aerodromeRepo.all).on(_.departureId === _.id).sortBy(_._2.icaoCode)(s).map{_._1}},
    "destinationId" -> {(q,s) => q.join(aerodromeRepo.all).on(_.destinationId === _.id).sortBy(_._2.icaoCode)(s).map{_._1}},
    "phaseId" -> {(q,s) => q.join(phaseRepo.all).on(_.phaseId === _.id).sortBy(_._2.phase)(s).map{_._1}},
    //displayField for field operator is not defined in YML,
    //displayField for field operationType is not defined in YML,
    "callsign" -> {(q,s) => q.sortBy(_.callsign)(s)},
    "flightNumber" -> {(q,s) => q.sortBy(_.flightNumber)(s)},
    "numberPersons" -> {(q,s) => q.sortBy(_.numberPersons)(s)},
    "aircraftAltitude" -> {(q,s) => q.sortBy(_.aircraftAltitude)(s)},
    "aircraftFlightLevel" -> {(q,s) => q.sortBy(_.aircraftFlightLevel)(s)},
    "speed" -> {(q,s) => q.sortBy(_.speed)(s)},
    "typeOfAirspeedId" -> {(q,s) => q.join(type_of_airspeedRepo.all).on(_.typeOfAirspeedId === _.id).sortBy(_._2.typeOfAirspeed)(s).map{_._1}},
    //displayField for field flightRule is not defined in YML,
    //displayField for field trafficType is not defined in YML,
    //displayField for field instrumentApprType is not defined in YML,
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

  def filter(formData: NeoFlightOperationFilter) = {
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
      .filteredBy( formData.aircraftIdentification.stateOfRegistryId.collect{case x if x>0L => x} )( _.stateOfRegistryId === _ )
      .filteredBy(formData.aircraftIdentification.registration)((x,y) => x.registration.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy( formData.aircraftIdentification.manufacturerModelId.collect{case x if x>0L => x} )( _.manufacturerModelId === _ )
      .filteredBy(formData.aircraftIdentification.serialNumber)((x,y) => x.serialNumber.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.aircraftIdentification.yearBuilt)((x,y) => x.yearBuilt.getOrElse(0) === y)
      .filteredBy( formData.aircraftDescription.categoryId.collect{case x if x>0L => x} )( _.categoryId === _ )
      .filteredBy( formData.aircraftDescription.massGroupId.collect{case x if x>0L => x} )( _.massGroupId === _ )
      .filteredBy( formData.aircraftDescription.propulsionTypeId.collect{case x if x>0L => x} )( _.propulsionTypeId === _ )
      .filteredBy( formData.aircraftDescription.wakeTurbCategoryId.flatMap{x => if(x>0L) Some(x) else None} )( _.wakeTurbCategoryId.getOrElse(0L) === _ )
      .filteredBy(formData.aircraftDescription.numberOfEngines)((x,y) => x.numberOfEngines.getOrElse(0) === y)
      .filteredBy( formData.aircraftDescription.landingGearTypeId.flatMap{x => if(x>0L) Some(x) else None} )( _.landingGearTypeId.getOrElse(0L) === _ )
      .filteredBy(formData.aircraftDescription.maximumMass)((x,y) => x.maximumMass.getOrElse(0.0) === y)
      .filteredBy( formData.flightDetails.departureId.collect{case x if x>0L => x} )( _.departureId === _ )
      .filteredBy( formData.flightDetails.destinationId.collect{case x if x>0L => x} )( _.destinationId === _ )
      .filteredBy( formData.flightDetails.phaseId.collect{case x if x>0L => x} )( _.phaseId === _ )
      .filteredBy( formData.flightDetails.operatorId.collect{case x if x>0L => x} )( _.operatorId === _ )
      .filteredBy( formData.flightDetails.operationTypeId.collect{case x if x>0L => x} )( _.operationTypeId === _ )
      .filteredBy(formData.flightDetails.callsign)((x,y) => x.callsign.toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.flightDetails.flightNumber)((x,y) => x.flightNumber.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.flightDetails.numberPersons)((x,y) => x.numberPersons.getOrElse(0) === y)
      .filteredBy(formData.operationalInformation.aircraftAltitude)((x,y) => x.aircraftAltitude.getOrElse(0.0) === y)
      .filteredBy(formData.operationalInformation.aircraftFlightLevel)((x,y) => x.aircraftFlightLevel.getOrElse("").toUpperCase like ('%'+y.toUpperCase+'%'))
      .filteredBy(formData.operationalInformation.speed)((x,y) => x.speed.getOrElse(0.0) === y)
      .filteredBy( formData.operationalInformation.typeOfAirspeedId.flatMap{x => if(x>0L) Some(x) else None} )( _.typeOfAirspeedId.getOrElse(0L) === _ )
      .filteredBy( formData.operationalInformation.flightRuleId.flatMap{x => if(x>0L) Some(x) else None} )( _.flightRuleId.getOrElse(0L) === _ )
      .filteredBy( formData.operationalInformation.trafficTypeId.flatMap{x => if(x>0L) Some(x) else None} )( _.trafficTypeId.getOrElse(0L) === _ )
      .filteredBy( formData.operationalInformation.instrumentApprTypeId.flatMap{x => if(x>0L) Some(x) else None} )( _.instrumentApprTypeId.getOrElse(0L) === _ )
      .oneToManyFilterfilter(formData.flightCrews)((x,o) => flight_crewRepo.get().filter(o).filter(y => y.neoFlightOperationId === x.id).exists)
      .filteredBy( formData.classification.ocurrenceClassId.collect{case x if x>0L => x} )( _.ocurrenceClassId === _ )
      .filteredBy( formData.classification.detectionPhaseId.collect{case x if x>0L => x} )( _.detectionPhaseId === _ )
      .oneToManyFilterfilter(formData.neoFlightOperationOcurrenceCategorys)((x,o) => neo_flight_operation_ocurrence_categoryRepo.get().filter(o).filter(y => y.neoFlightOperationId === x.id).exists)
      .oneToManyFilterfilter(formData.neoFlightOperationEventTypePhases)((x,o) => neo_flight_operation_event_type_phaseRepo.get().filter(o).filter(y => y.neoFlightOperationId === x.id).exists)
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