import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import DetectionPhaseFilter from './DetectionPhaseFilter'

//inputs de nested


/*
import DetectionPhaseForm from './components/detectionPhase/DetectionPhaseForm'
import DetectionPhaseList from './components/detectionPhase/DetectionPhaseList'
import DetectionPhaseTable from './components/detectionPhase/DetectionPhaseTable'

<Route path="/detectionPhase/" component={DetectionPhaseList} />
<Route path="/detectionPhase/" component={DetectionPhaseTable} />
<Route path="/detectionPhase/new" component={DetectionPhaseForm} />
<Route path="/detectionPhase/:id" component={DetectionPhaseForm} />
<Route path="/detectionPhase/:page/:pageLength" component={DetectionPhaseList} />
<Route path="/detectionPhase/:page/:pageLength/:query" component={DetectionPhaseList} />
<Route path="/detectionPhase/:page/:pageLength" component={DetectionPhaseTable} />
<Route path="/detectionPhase/:page/:pageLength/:query" component={DetectionPhaseTable} />
<Route path="/detectionPhase/:page/:pageLength/:sortColumn/:sortOrder" component={DetectionPhaseTable} />
<Route path="/detectionPhase/:page/:pageLength/:sortColumn/:sortOrder/:query" component={DetectionPhaseTable} />
*/
export default class DetectionPhaseTable extends GTable{
    showUrl =  '/detectionPhase/'
    apiGetUrl =  '/detectionPhase/'
    apiCreateUrl = '/detectionPhase/save'
    apiDeleteUrl = '/detectionPhase/delete/'
    
    listUrl = '/detectionPhase/'

    objsStr = 'Detection Phases'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "detectionPhase", rowName: "Detection Phase", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <DetectionPhaseFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      