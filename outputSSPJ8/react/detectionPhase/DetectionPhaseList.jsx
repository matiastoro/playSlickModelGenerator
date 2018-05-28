import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import DetectionPhaseFilter from './DetectionPhaseFilter'

//inputs de nested


/*
<Route path="/detectionPhase/" component={DetectionPhaseList} />
<Route path="/detectionPhase/new" component={DetectionPhaseForm} />
<Route path="/detectionPhase/:id" component={DetectionPhaseForm} />
<Route path="/detectionPhase/:page/:pageLength" component={DetectionPhaseList} />
*/
export default class DetectionPhaseList extends GList{
    showUrl =  '/detectionPhase/'
    apiGetUrl =  '/detectionPhase/'
    apiCreateUrl = '/detectionPhase/save'
    apiDeleteUrl = '/detectionPhase/delete/'
    
    listUrl = '/detectionPhase/'

    objsStr = 'Detection Phases'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.detectionPhase
    }
    

    renderFilter = () => {
        return <div>
            <DetectionPhaseFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      