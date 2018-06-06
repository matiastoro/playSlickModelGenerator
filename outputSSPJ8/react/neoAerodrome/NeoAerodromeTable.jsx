import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoAerodromeFilter from './NeoAerodromeFilter'

//inputs de nested


/*
import NeoAerodromeForm from './components/neoAerodrome/NeoAerodromeForm'
import NeoAerodromeList from './components/neoAerodrome/NeoAerodromeList'
import NeoAerodromeTable from './components/neoAerodrome/NeoAerodromeTable'

<Route path="/neoAerodrome/" component={NeoAerodromeList} />
<Route path="/neoAerodrome/" component={NeoAerodromeTable} />
<Route path="/neoAerodrome/new" component={NeoAerodromeForm} />
<Route path="/neoAerodrome/:id" component={NeoAerodromeForm} />
<Route path="/neoAerodrome/:page/:pageLength" component={NeoAerodromeList} />
<Route path="/neoAerodrome/:page/:pageLength/:query" component={NeoAerodromeList} />
<Route path="/neoAerodrome/:page/:pageLength" component={NeoAerodromeTable} />
<Route path="/neoAerodrome/:page/:pageLength/:query" component={NeoAerodromeTable} />
<Route path="/neoAerodrome/:page/:pageLength/:sortColumn/:sortOrder" component={NeoAerodromeTable} />
<Route path="/neoAerodrome/:page/:pageLength/:sortColumn/:sortOrder/:query" component={NeoAerodromeTable} />
*/
export default class NeoAerodromeTable extends GTable{
    showUrl =  '/neoAerodrome/'
    apiGetUrl =  '/neoAerodrome/'
    apiCreateUrl = '/neoAerodrome/save'
    apiDeleteUrl = '/neoAerodrome/delete/'
    apiOptionsUrl = '/neoAerodrome/options'
    listUrl = '/neoAerodrome/'

    objsStr = 'Neo Aerodromes'


    
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
            {key: "aerodromeDescription.aerodromeId", rowName: "Aerodrome", label: (o) => (this.findRelatedObject("aerodromes", "aerodromeDescription.aerodromeId", o, "icaoCode")), sortable: true},
            {key: "aerodromeDescription.aerodromeLocationId", rowName: "Aerodrome Location", label: (o) => (this.findRelatedObject("aerodromeLocations", "aerodromeDescription.aerodromeLocationId", o, "location")), sortable: true},
            {key: "aerodromeDescription.aerodromeStatusId", rowName: "Aerodrome Status", label: (o) => (this.findRelatedObject("aerodromeStatuss", "aerodromeDescription.aerodromeStatusId", o, "status")), sortable: true},
            {key: "aerodromeDescription.aerodromeTypeId", rowName: "Aerodrome Type", label: (o) => (this.findRelatedObject("aerodromeTypes", "aerodromeDescription.aerodromeTypeId", o, "tpe")), sortable: true},
            {key: "aerodromeDescription.latitude", rowName: "Latitude", sortable: true},
            {key: "aerodromeDescription.longitude", rowName: "Longitude", sortable: true},
            {key: "aerodromeDescription.elevationAboveMsl", rowName: "Elevation Above Msl", sortable: true},
            {key: "aerodromeDescription.runwayId", rowName: "Runway", label: (o) => (this.findRelatedObject("runways", "aerodromeDescription.runwayId", o, "runway")), sortable: true},
            {key: "aerodromeDescription.runwaySurfaceTypeId", rowName: "Runway Surface Type", label: (o) => (this.findRelatedObject("runwaySurfaceTypes", "aerodromeDescription.runwaySurfaceTypeId", o, "surfaceType")), sortable: true},
            {key: "aerodromeDescription.helicopterLandingAreaTypeId", rowName: "Helicopter Landing Area Type", label: (o) => (this.findRelatedObject("helicopterLandingAreaTypes", "aerodromeDescription.helicopterLandingAreaTypeId", o, "tpe")), sortable: true},
            {key: "aerodromeDescription.helicopterLandingAreaAreaConfigurationId", rowName: "Helicopter Landing Area Area Configuration", label: (o) => (this.findRelatedObject("helicopterLandingAreaAreaConfigurations", "aerodromeDescription.helicopterLandingAreaAreaConfigurationId", o, "areaConfiguration")), sortable: true},
            {key: "aerodromeDescription.helicopterLandingAreaSurfaceTypeId", rowName: "Helicopter Landing Area Surface Type", label: (o) => (this.findRelatedObject("helicopterLandingAreaSurfaceTypes", "aerodromeDescription.helicopterLandingAreaSurfaceTypeId", o, "surfaceType")), sortable: true},
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
            <NeoAerodromeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      