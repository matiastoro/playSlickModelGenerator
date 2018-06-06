import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import RunwayFilter from './RunwayFilter'

//inputs de nested


/*
<Route path="/runway/" component={RunwayList} />
<Route path="/runway/new" component={RunwayForm} />
<Route path="/runway/:id" component={RunwayForm} />
<Route path="/runway/:page/:pageLength" component={RunwayList} />
<Route path="/runway/:page/:pageLength/:query" component={RunwayList} />
<Route path="/runway/:page/:pageLength/:sortColumn/:sortOrder" component={{RunwayTable} />
<Route path="/runway/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{RunwayTable} />
*/
export default class RunwayList extends GList{
    showUrl =  '/runway/'
    apiGetUrl =  '/runway/'
    apiCreateUrl = '/runway/save'
    apiDeleteUrl = '/runway/delete/'
    apiOptionsUrl = '/runway/options'
    listUrl = '/runway/'

    objsStr = 'Runways'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.runway
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.runwaySurfaceTypeId && this.state.indexedOptions?this.state.indexedOptions.runwaySurfaceTypes[obj.runwaySurfaceTypeId].surfaceType:null} -- 
            {obj.aerodromeId && this.state.indexedOptions?this.state.indexedOptions.aerodromes[obj.aerodromeId].aerodrome:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <RunwayFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      