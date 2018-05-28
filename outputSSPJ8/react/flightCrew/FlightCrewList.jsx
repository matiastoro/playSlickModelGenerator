import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import FlightCrewFilter from './FlightCrewFilter'

//inputs de nested


/*
<Route path="/flightCrew/" component={FlightCrewList} />
<Route path="/flightCrew/new" component={FlightCrewForm} />
<Route path="/flightCrew/:id" component={FlightCrewForm} />
<Route path="/flightCrew/:page/:pageLength" component={FlightCrewList} />
*/
export default class FlightCrewList extends GList{
    showUrl =  '/flightCrew/'
    apiGetUrl =  '/flightCrew/'
    apiCreateUrl = '/flightCrew/save'
    apiDeleteUrl = '/flightCrew/delete/'
    apiOptionsUrl = '/flightCrew/options'
    listUrl = '/flightCrew/'

    objsStr = 'Flight Crews'

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
            {obj.categoryId && this.state.indexedOptions?this.state.indexedOptions.flightCrewCategorys[obj.categoryId].category:null} -- 
            {obj.experienceThis} -- 
            {obj.experienceAll} -- 
            {obj.dutyLast24Hours} -- 
            {obj.restBeforeDuty}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <FlightCrewFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      