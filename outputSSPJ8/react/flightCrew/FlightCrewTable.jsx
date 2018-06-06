import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import FlightCrewFilter from './FlightCrewFilter'

//inputs de nested


/*
import FlightCrewForm from './components/flightCrew/FlightCrewForm'
import FlightCrewList from './components/flightCrew/FlightCrewList'
import FlightCrewTable from './components/flightCrew/FlightCrewTable'

<Route path="/flightCrew/" component={FlightCrewList} />
<Route path="/flightCrew/" component={FlightCrewTable} />
<Route path="/flightCrew/new" component={FlightCrewForm} />
<Route path="/flightCrew/:id" component={FlightCrewForm} />
<Route path="/flightCrew/:page/:pageLength" component={FlightCrewList} />
<Route path="/flightCrew/:page/:pageLength/:query" component={FlightCrewList} />
<Route path="/flightCrew/:page/:pageLength" component={FlightCrewTable} />
<Route path="/flightCrew/:page/:pageLength/:query" component={FlightCrewTable} />
<Route path="/flightCrew/:page/:pageLength/:sortColumn/:sortOrder" component={FlightCrewTable} />
<Route path="/flightCrew/:page/:pageLength/:sortColumn/:sortOrder/:query" component={FlightCrewTable} />
*/
export default class FlightCrewTable extends GTable{
    showUrl =  '/flightCrew/'
    apiGetUrl =  '/flightCrew/'
    apiCreateUrl = '/flightCrew/save'
    apiDeleteUrl = '/flightCrew/delete/'
    apiOptionsUrl = '/flightCrew/options'
    listUrl = '/flightCrew/'

    objsStr = 'Flight Crews'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "neoFlightOperationId", rowName: "Neo Flight Operation", label: (o) => (this.findRelatedObject("neoFlightOperations", "neoFlightOperationId", o, "neoFlightOperation")), sortable: true},
            {key: "categoryId", rowName: "Category", label: (o) => (this.findRelatedObject("flightCrewCategorys", "categoryId", o, "category")), sortable: true},
            {key: "experienceThis", rowName: "Experience This", sortable: true},
            {key: "experienceAll", rowName: "Experience All", sortable: true},
            {key: "dutyLast24Hours", rowName: "Duty Last 24 Hours", sortable: true},
            {key: "restBeforeDuty", rowName: "Rest Before Duty", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <FlightCrewFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      