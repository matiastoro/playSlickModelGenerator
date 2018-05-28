import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import PhaseFilter from './PhaseFilter'

//inputs de nested


/*
<Route path="/phase/" component={PhaseList} />
<Route path="/phase/new" component={PhaseForm} />
<Route path="/phase/:id" component={PhaseForm} />
<Route path="/phase/:page/:pageLength" component={PhaseList} />
*/
export default class PhaseList extends GList{
    showUrl =  '/phase/'
    apiGetUrl =  '/phase/'
    apiCreateUrl = '/phase/save'
    apiDeleteUrl = '/phase/delete/'
    apiOptionsUrl = '/phase/options'
    listUrl = '/phase/'

    objsStr = 'Phases'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.phase
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.phaseId && this.state.indexedOptions?this.state.indexedOptions.phases[obj.phaseId].phase:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <PhaseFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      