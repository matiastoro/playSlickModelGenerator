import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoFlightOperationEventTypePhaseFilter from './NeoFlightOperationEventTypePhaseFilter'

//inputs de nested


/*
<Route path="/neoFlightOperationEventTypePhase/" component={NeoFlightOperationEventTypePhaseList} />
<Route path="/neoFlightOperationEventTypePhase/new" component={NeoFlightOperationEventTypePhaseForm} />
<Route path="/neoFlightOperationEventTypePhase/:id" component={NeoFlightOperationEventTypePhaseForm} />
<Route path="/neoFlightOperationEventTypePhase/:page/:pageLength" component={NeoFlightOperationEventTypePhaseList} />
<Route path="/neoFlightOperationEventTypePhase/:page/:pageLength/:query" component={NeoFlightOperationEventTypePhaseList} />
<Route path="/neoFlightOperationEventTypePhase/:page/:pageLength/:sortColumn/:sortOrder" component={{NeoFlightOperationEventTypePhaseTable} />
<Route path="/neoFlightOperationEventTypePhase/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{NeoFlightOperationEventTypePhaseTable} />
*/
export default class NeoFlightOperationEventTypePhaseList extends GList{
    showUrl =  '/neoFlightOperationEventTypePhase/'
    apiGetUrl =  '/neoFlightOperationEventTypePhase/'
    apiCreateUrl = '/neoFlightOperationEventTypePhase/save'
    apiDeleteUrl = '/neoFlightOperationEventTypePhase/delete/'
    apiOptionsUrl = '/neoFlightOperationEventTypePhase/options'
    listUrl = '/neoFlightOperationEventTypePhase/'

    objsStr = 'Neo Flight Operation Event Type Phases'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.id
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.neoFlightOperationId && this.state.indexedOptions?this.state.indexedOptions.neoFlightOperations[obj.neoFlightOperationId].neoFlightOperation:null} -- 
            {obj.neoEventTypeId && this.state.indexedOptions?this.state.indexedOptions.neoEventTypes[obj.neoEventTypeId].eventType:null} -- 
            {obj.phaseId && this.state.indexedOptions?this.state.indexedOptions.phases[obj.phaseId].phase:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <NeoFlightOperationEventTypePhaseFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      