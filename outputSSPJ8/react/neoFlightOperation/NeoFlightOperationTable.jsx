import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoFlightOperationFilter from './NeoFlightOperationFilter'

//inputs de nested


/*
import NeoFlightOperationForm from './components/neoFlightOperation/NeoFlightOperationForm'
import NeoFlightOperationList from './components/neoFlightOperation/NeoFlightOperationList'
import NeoFlightOperationTable from './components/neoFlightOperation/NeoFlightOperationTable'

<Route path="/neoFlightOperation/" component={NeoFlightOperationList} />
<Route path="/neoFlightOperation/" component={NeoFlightOperationTable} />
<Route path="/neoFlightOperation/new" component={NeoFlightOperationForm} />
<Route path="/neoFlightOperation/:id" component={NeoFlightOperationForm} />
<Route path="/neoFlightOperation/:page/:pageLength" component={NeoFlightOperationList} />
<Route path="/neoFlightOperation/:page/:pageLength/:query" component={NeoFlightOperationList} />
<Route path="/neoFlightOperation/:page/:pageLength" component={NeoFlightOperationTable} />
<Route path="/neoFlightOperation/:page/:pageLength/:query" component={NeoFlightOperationTable} />
<Route path="/neoFlightOperation/:page/:pageLength/:sortColumn/:sortOrder" component={NeoFlightOperationTable} />
<Route path="/neoFlightOperation/:page/:pageLength/:sortColumn/:sortOrder/:query" component={NeoFlightOperationTable} />
*/
export default class NeoFlightOperationTable extends GTable{
    showUrl =  '/neoFlightOperation/'
    apiGetUrl =  '/neoFlightOperation/'
    apiCreateUrl = '/neoFlightOperation/save'
    apiDeleteUrl = '/neoFlightOperation/delete/'
    apiOptionsUrl = '/neoFlightOperation/options'
    listUrl = '/neoFlightOperation/'

    objsStr = 'Neo Flight Operations'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "reportingEntityId", rowName: "Reporting Entity", label: (o) => (this.findRelatedObject("reportingEntitys", "reportingEntityId", o, "reportingEntity")), sortable: true},
            {key: "reportingEntityName", rowName: "Reporting Entity Name", sortable: true},
            {key: "generalInformation.when", rowName: "When", label: (o, v) => this.displayDateTime(v), sortable: true},
            {key: "generalInformation.whenLocal", rowName: "When Local", label: (o, v) => this.displayDateTime(v), sortable: true},
            {key: "generalInformation.whereStateAreaId", rowName: "Where State Area", label: (o) => (this.findRelatedObject("stateAreas", "generalInformation.whereStateAreaId", o, "stateArea")), sortable: true},
            {key: "generalInformation.whereLocationName", rowName: "Where Location Name", sortable: true},
            {key: "generalInformation.whereLatitudeOcc", rowName: "Where Latitude Occ", sortable: true},
            {key: "generalInformation.whereLongitudeOcc", rowName: "Where Longitude Occ", sortable: true},
            {key: "generalInformation.whatHeadline", rowName: "What Headline", sortable: true},
            {key: "generalInformation.whatNarrativeLanguaje", rowName: "What Narrative Languaje", sortable: true},
            {key: "generalInformation.whatNarrative", rowName: "What Narrative", sortable: true},
            {key: "aircraftIdentification.stateOfRegistryId", rowName: "State Of Registry", label: (o) => (this.findRelatedObject("stateAreas", "aircraftIdentification.stateOfRegistryId", o, "stateArea")), sortable: true},
            {key: "aircraftIdentification.registration", rowName: "Registration", sortable: true},
            {key: "aircraftIdentification.manufacturerModelId", rowName: "Manufacturer Model", label: (o) => (this.findRelatedObject("aircraftManufacturerModels", "aircraftIdentification.manufacturerModelId", o, "manufacturerModel")), sortable: true},
            {key: "aircraftIdentification.serialNumber", rowName: "Serial Number", sortable: true},
            {key: "aircraftIdentification.yearBuilt", rowName: "Year Built", sortable: true},
            {key: "aircraftDescription.categoryId", rowName: "Category", label: (o) => (this.findRelatedObject("aircraftCategorys", "aircraftDescription.categoryId", o, "category")), sortable: true},
            {key: "aircraftDescription.massGroupId", rowName: "Mass Group", label: (o) => (this.findRelatedObject("aircraftMassGroups", "aircraftDescription.massGroupId", o, "massGroup")), sortable: true},
            {key: "aircraftDescription.propulsionTypeId", rowName: "Propulsion Type", label: (o) => (this.findRelatedObject("aircraftPropulsionTypes", "aircraftDescription.propulsionTypeId", o, "propulsionType")), sortable: true},
            {key: "aircraftDescription.wakeTurbCategoryId", rowName: "Wake Turb Category", label: (o) => (this.findRelatedObject("aircraftWakeTurbCategorys", "aircraftDescription.wakeTurbCategoryId", o, "wakeTurbCategory")), sortable: true},
            {key: "aircraftDescription.numberOfEngines", rowName: "Number Of Engines", sortable: true},
            {key: "aircraftDescription.landingGearTypeId", rowName: "Landing Gear Type", label: (o) => (this.findRelatedObject("aircraftLandingGearTypes", "aircraftDescription.landingGearTypeId", o, "landingGearType")), sortable: true},
            {key: "aircraftDescription.maximumMass", rowName: "Maximum Mass", sortable: true},
            {key: "flightDetails.departureId", rowName: "Departure", label: (o) => (this.findRelatedObject("aerodromes", "flightDetails.departureId", o, "icaoCode")), sortable: true},
            {key: "flightDetails.destinationId", rowName: "Destination", label: (o) => (this.findRelatedObject("aerodromes", "flightDetails.destinationId", o, "icaoCode")), sortable: true},
            {key: "flightDetails.phaseId", rowName: "Phase", label: (o) => (this.findRelatedObject("phases", "flightDetails.phaseId", o, "phase")), sortable: true},
            {key: "flightDetails.operatorId", rowName: "Operator", label: (o) => (this.findRelatedObject("operators", "flightDetails.operatorId", o, "operator")), sortable: true},
            {key: "flightDetails.operationTypeId", rowName: "Operation Type", label: (o) => (this.findRelatedObject("operationTypes", "flightDetails.operationTypeId", o, "operationType")), sortable: true},
            {key: "flightDetails.callsign", rowName: "Callsign", sortable: true},
            {key: "flightDetails.flightNumber", rowName: "Flight Number", sortable: true},
            {key: "flightDetails.numberPersons", rowName: "Number Persons", sortable: true},
            {key: "operationalInformation.aircraftAltitude", rowName: "Aircraft Altitude", sortable: true},
            {key: "operationalInformation.aircraftFlightLevel", rowName: "Aircraft Flight Level", sortable: true},
            {key: "operationalInformation.speed", rowName: "Speed", sortable: true},
            {key: "operationalInformation.typeOfAirspeedId", rowName: "Type Of Airspeed", label: (o) => (this.findRelatedObject("typeOfAirspeeds", "operationalInformation.typeOfAirspeedId", o, "typeOfAirspeed")), sortable: true},
            {key: "operationalInformation.flightRuleId", rowName: "Flight Rule", label: (o) => (this.findRelatedObject("flightRules", "operationalInformation.flightRuleId", o, "flightRule")), sortable: true},
            {key: "operationalInformation.trafficTypeId", rowName: "Traffic Type", label: (o) => (this.findRelatedObject("trafficTypes", "operationalInformation.trafficTypeId", o, "trafficType")), sortable: true},
            {key: "operationalInformation.instrumentApprTypeId", rowName: "Instrument Appr Type", label: (o) => (this.findRelatedObject("instrumentApprTypes", "operationalInformation.instrumentApprTypeId", o, "instrumentApprType")), sortable: true},
            {key: "classification.ocurrenceClassId", rowName: "Ocurrence Class", label: (o) => (this.findRelatedObject("ocurrenceClasss", "classification.ocurrenceClassId", o, "ocurrenceClass")), sortable: true},
            {key: "classification.detectionPhaseId", rowName: "Detection Phase", label: (o) => (this.findRelatedObject("detectionPhases", "classification.detectionPhaseId", o, "detectionPhase")), sortable: true},
            {key: "risk.riskClassification", rowName: "Risk Classification", sortable: true},
            {key: "risk.riskMethodology", rowName: "Risk Methodology", sortable: true},
            {key: "risk.riskAssessment", rowName: "Risk Assessment", sortable: true},
            {key: "assessment.analysisFollowUp", rowName: "Analysis Follow Up", sortable: true},
            {key: "assessment.correctiveActions", rowName: "Corrective Actions", sortable: true},
            {key: "assessment.conclusions", rowName: "Conclusions", sortable: true},
            {key: "reportManagement.reportStatusId", rowName: "Report Status", label: (o) => (this.findRelatedObject("neoReportStatuss", "reportManagement.reportStatusId", o, "reportStatus")), sortable: true},
            {key: "reportManagement.reportVersion", rowName: "Report Version", sortable: true},
            {key: "attachments", rowName: "Attachments", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <NeoFlightOperationFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      