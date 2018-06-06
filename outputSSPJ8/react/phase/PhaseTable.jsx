import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import PhaseFilter from './PhaseFilter'

//inputs de nested


/*
import PhaseForm from './components/phase/PhaseForm'
import PhaseList from './components/phase/PhaseList'
import PhaseTable from './components/phase/PhaseTable'

<Route path="/phase/" component={PhaseList} />
<Route path="/phase/" component={PhaseTable} />
<Route path="/phase/new" component={PhaseForm} />
<Route path="/phase/:id" component={PhaseForm} />
<Route path="/phase/:page/:pageLength" component={PhaseList} />
<Route path="/phase/:page/:pageLength/:query" component={PhaseList} />
<Route path="/phase/:page/:pageLength" component={PhaseTable} />
<Route path="/phase/:page/:pageLength/:query" component={PhaseTable} />
<Route path="/phase/:page/:pageLength/:sortColumn/:sortOrder" component={PhaseTable} />
<Route path="/phase/:page/:pageLength/:sortColumn/:sortOrder/:query" component={PhaseTable} />
*/
export default class PhaseTable extends GTable{
    showUrl =  '/phase/'
    apiGetUrl =  '/phase/'
    apiCreateUrl = '/phase/save'
    apiDeleteUrl = '/phase/delete/'
    apiOptionsUrl = '/phase/options'
    listUrl = '/phase/'

    objsStr = 'Phases'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "phase", rowName: "Phase", sortable: true},
            {key: "phaseId", rowName: "Phase", label: (o) => (this.findRelatedObject("phases", "phaseId", o, "phase")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <PhaseFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      