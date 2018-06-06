import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AircraftLandingGearTypeFilter from './AircraftLandingGearTypeFilter'

//inputs de nested


/*
import AircraftLandingGearTypeForm from './components/aircraftLandingGearType/AircraftLandingGearTypeForm'
import AircraftLandingGearTypeList from './components/aircraftLandingGearType/AircraftLandingGearTypeList'
import AircraftLandingGearTypeTable from './components/aircraftLandingGearType/AircraftLandingGearTypeTable'

<Route path="/aircraftLandingGearType/" component={AircraftLandingGearTypeList} />
<Route path="/aircraftLandingGearType/" component={AircraftLandingGearTypeTable} />
<Route path="/aircraftLandingGearType/new" component={AircraftLandingGearTypeForm} />
<Route path="/aircraftLandingGearType/:id" component={AircraftLandingGearTypeForm} />
<Route path="/aircraftLandingGearType/:page/:pageLength" component={AircraftLandingGearTypeList} />
<Route path="/aircraftLandingGearType/:page/:pageLength/:query" component={AircraftLandingGearTypeList} />
<Route path="/aircraftLandingGearType/:page/:pageLength" component={AircraftLandingGearTypeTable} />
<Route path="/aircraftLandingGearType/:page/:pageLength/:query" component={AircraftLandingGearTypeTable} />
<Route path="/aircraftLandingGearType/:page/:pageLength/:sortColumn/:sortOrder" component={AircraftLandingGearTypeTable} />
<Route path="/aircraftLandingGearType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={AircraftLandingGearTypeTable} />
*/
export default class AircraftLandingGearTypeTable extends GTable{
    showUrl =  '/aircraftLandingGearType/'
    apiGetUrl =  '/aircraftLandingGearType/'
    apiCreateUrl = '/aircraftLandingGearType/save'
    apiDeleteUrl = '/aircraftLandingGearType/delete/'
    apiOptionsUrl = '/aircraftLandingGearType/options'
    listUrl = '/aircraftLandingGearType/'

    objsStr = 'Aircraft Landing Gear Types'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "landingGearType", rowName: "Landing Gear Type", sortable: true},
            {key: "aircraftLandingGearTypeId", rowName: "Aircraft Landing Gear Type", label: (o) => (this.findRelatedObject("aircraftLandingGearTypes", "aircraftLandingGearTypeId", o, "landingGearType")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <AircraftLandingGearTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      