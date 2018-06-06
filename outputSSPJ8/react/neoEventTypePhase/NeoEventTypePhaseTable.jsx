import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoEventTypePhaseFilter from './NeoEventTypePhaseFilter'

//inputs de nested


/*
import NeoEventTypePhaseForm from './components/neoEventTypePhase/NeoEventTypePhaseForm'
import NeoEventTypePhaseList from './components/neoEventTypePhase/NeoEventTypePhaseList'
import NeoEventTypePhaseTable from './components/neoEventTypePhase/NeoEventTypePhaseTable'

<Route path="/neoEventTypePhase/" component={NeoEventTypePhaseList} />
<Route path="/neoEventTypePhase/" component={NeoEventTypePhaseTable} />
<Route path="/neoEventTypePhase/new" component={NeoEventTypePhaseForm} />
<Route path="/neoEventTypePhase/:id" component={NeoEventTypePhaseForm} />
<Route path="/neoEventTypePhase/:page/:pageLength" component={NeoEventTypePhaseList} />
<Route path="/neoEventTypePhase/:page/:pageLength/:query" component={NeoEventTypePhaseList} />
<Route path="/neoEventTypePhase/:page/:pageLength" component={NeoEventTypePhaseTable} />
<Route path="/neoEventTypePhase/:page/:pageLength/:query" component={NeoEventTypePhaseTable} />
<Route path="/neoEventTypePhase/:page/:pageLength/:sortColumn/:sortOrder" component={NeoEventTypePhaseTable} />
<Route path="/neoEventTypePhase/:page/:pageLength/:sortColumn/:sortOrder/:query" component={NeoEventTypePhaseTable} />
*/
export default class NeoEventTypePhaseTable extends GTable{
    showUrl =  '/neoEventTypePhase/'
    apiGetUrl =  '/neoEventTypePhase/'
    apiCreateUrl = '/neoEventTypePhase/save'
    apiDeleteUrl = '/neoEventTypePhase/delete/'
    apiOptionsUrl = '/neoEventTypePhase/options'
    listUrl = '/neoEventTypePhase/'

    objsStr = 'Neo Event Type Phases'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "neoAerodromeId", rowName: "Neo Aerodrome", label: (o) => (this.findRelatedObject("neoAerodromes", "neoAerodromeId", o, "neoAerodrome")), sortable: true},
            {key: "neoEventTypeId", rowName: "Neo Event Type", label: (o) => (this.findRelatedObject("neoEventTypes", "neoEventTypeId", o, "eventType")), sortable: true},
            {key: "phaseId", rowName: "Phase", label: (o) => (this.findRelatedObject("phases", "phaseId", o, "phase")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <NeoEventTypePhaseFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      