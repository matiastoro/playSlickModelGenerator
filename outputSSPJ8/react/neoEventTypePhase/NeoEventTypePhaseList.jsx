import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoEventTypePhaseFilter from './NeoEventTypePhaseFilter'

//inputs de nested


/*
<Route path="/neoEventTypePhase/" component={NeoEventTypePhaseList} />
<Route path="/neoEventTypePhase/new" component={NeoEventTypePhaseForm} />
<Route path="/neoEventTypePhase/:id" component={NeoEventTypePhaseForm} />
<Route path="/neoEventTypePhase/:page/:pageLength" component={NeoEventTypePhaseList} />
<Route path="/neoEventTypePhase/:page/:pageLength/:query" component={NeoEventTypePhaseList} />
<Route path="/neoEventTypePhase/:page/:pageLength/:sortColumn/:sortOrder" component={{NeoEventTypePhaseTable} />
<Route path="/neoEventTypePhase/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{NeoEventTypePhaseTable} />
*/
export default class NeoEventTypePhaseList extends GList{
    showUrl =  '/neoEventTypePhase/'
    apiGetUrl =  '/neoEventTypePhase/'
    apiCreateUrl = '/neoEventTypePhase/save'
    apiDeleteUrl = '/neoEventTypePhase/delete/'
    apiOptionsUrl = '/neoEventTypePhase/options'
    listUrl = '/neoEventTypePhase/'

    objsStr = 'Neo Event Type Phases'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.id
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.neoAerodromeId && this.state.indexedOptions?this.state.indexedOptions.neoAerodromes[obj.neoAerodromeId].neoAerodrome:null} -- 
            {obj.neoEventTypeId && this.state.indexedOptions?this.state.indexedOptions.neoEventTypes[obj.neoEventTypeId].eventType:null} -- 
            {obj.phaseId && this.state.indexedOptions?this.state.indexedOptions.phases[obj.phaseId].phase:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <NeoEventTypePhaseFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      