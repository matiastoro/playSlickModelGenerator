import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import RunwayFilter from './RunwayFilter'

//inputs de nested


/*
import RunwayForm from './components/runway/RunwayForm'
import RunwayList from './components/runway/RunwayList'
import RunwayTable from './components/runway/RunwayTable'

<Route path="/runway/" component={RunwayList} />
<Route path="/runway/" component={RunwayTable} />
<Route path="/runway/new" component={RunwayForm} />
<Route path="/runway/:id" component={RunwayForm} />
<Route path="/runway/:page/:pageLength" component={RunwayList} />
<Route path="/runway/:page/:pageLength/:query" component={RunwayList} />
<Route path="/runway/:page/:pageLength" component={RunwayTable} />
<Route path="/runway/:page/:pageLength/:query" component={RunwayTable} />
<Route path="/runway/:page/:pageLength/:sortColumn/:sortOrder" component={RunwayTable} />
<Route path="/runway/:page/:pageLength/:sortColumn/:sortOrder/:query" component={RunwayTable} />
*/
export default class RunwayTable extends GTable{
    showUrl =  '/runway/'
    apiGetUrl =  '/runway/'
    apiCreateUrl = '/runway/save'
    apiDeleteUrl = '/runway/delete/'
    apiOptionsUrl = '/runway/options'
    listUrl = '/runway/'

    objsStr = 'Runways'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "runway", rowName: "Runway", sortable: true},
            {key: "runwaySurfaceTypeId", rowName: "Runway Surface Type", label: (o) => (this.findRelatedObject("runwaySurfaceTypes", "runwaySurfaceTypeId", o, "surfaceType")), sortable: true},
            {key: "aerodromeId", rowName: "Aerodrome", label: (o) => (this.findRelatedObject("aerodromes", "aerodromeId", o, "aerodrome")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <RunwayFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      