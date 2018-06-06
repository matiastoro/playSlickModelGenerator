import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoFlightOperationEventTypePhaseFilter from './NeoFlightOperationEventTypePhaseFilter'

//inputs de nested


/*
import NeoFlightOperationEventTypePhaseForm from './components/neoFlightOperationEventTypePhase/NeoFlightOperationEventTypePhaseForm'
import NeoFlightOperationEventTypePhaseList from './components/neoFlightOperationEventTypePhase/NeoFlightOperationEventTypePhaseList'
import NeoFlightOperationEventTypePhaseTable from './components/neoFlightOperationEventTypePhase/NeoFlightOperationEventTypePhaseTable'

<Route path="/neoFlightOperationEventTypePhase/" component={NeoFlightOperationEventTypePhaseList} />
<Route path="/neoFlightOperationEventTypePhase/" component={NeoFlightOperationEventTypePhaseTable} />
<Route path="/neoFlightOperationEventTypePhase/new" component={NeoFlightOperationEventTypePhaseForm} />
<Route path="/neoFlightOperationEventTypePhase/:id" component={NeoFlightOperationEventTypePhaseForm} />
<Route path="/neoFlightOperationEventTypePhase/:page/:pageLength" component={NeoFlightOperationEventTypePhaseList} />
<Route path="/neoFlightOperationEventTypePhase/:page/:pageLength/:query" component={NeoFlightOperationEventTypePhaseList} />
<Route path="/neoFlightOperationEventTypePhase/:page/:pageLength" component={NeoFlightOperationEventTypePhaseTable} />
<Route path="/neoFlightOperationEventTypePhase/:page/:pageLength/:query" component={NeoFlightOperationEventTypePhaseTable} />
<Route path="/neoFlightOperationEventTypePhase/:page/:pageLength/:sortColumn/:sortOrder" component={NeoFlightOperationEventTypePhaseTable} />
<Route path="/neoFlightOperationEventTypePhase/:page/:pageLength/:sortColumn/:sortOrder/:query" component={NeoFlightOperationEventTypePhaseTable} />
*/
export default class NeoFlightOperationEventTypePhaseTable extends GTable{
    showUrl =  '/neoFlightOperationEventTypePhase/'
    apiGetUrl =  '/neoFlightOperationEventTypePhase/'
    apiCreateUrl = '/neoFlightOperationEventTypePhase/save'
    apiDeleteUrl = '/neoFlightOperationEventTypePhase/delete/'
    apiOptionsUrl = '/neoFlightOperationEventTypePhase/options'
    listUrl = '/neoFlightOperationEventTypePhase/'

    objsStr = 'Neo Flight Operation Event Type Phases'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "neoFlightOperationId", rowName: "Neo Flight Operation", label: (o) => (this.findRelatedObject("neoFlightOperations", "neoFlightOperationId", o, "neoFlightOperation")), sortable: true},
            {key: "neoEventTypeId", rowName: "Neo Event Type", label: (o) => (this.findRelatedObject("neoEventTypes", "neoEventTypeId", o, "eventType")), sortable: true},
            {key: "phaseId", rowName: "Phase", label: (o) => (this.findRelatedObject("phases", "phaseId", o, "phase")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <NeoFlightOperationEventTypePhaseFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      