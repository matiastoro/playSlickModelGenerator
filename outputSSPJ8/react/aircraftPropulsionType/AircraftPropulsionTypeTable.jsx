import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AircraftPropulsionTypeFilter from './AircraftPropulsionTypeFilter'

//inputs de nested


/*
import AircraftPropulsionTypeForm from './components/aircraftPropulsionType/AircraftPropulsionTypeForm'
import AircraftPropulsionTypeList from './components/aircraftPropulsionType/AircraftPropulsionTypeList'
import AircraftPropulsionTypeTable from './components/aircraftPropulsionType/AircraftPropulsionTypeTable'

<Route path="/aircraftPropulsionType/" component={AircraftPropulsionTypeList} />
<Route path="/aircraftPropulsionType/" component={AircraftPropulsionTypeTable} />
<Route path="/aircraftPropulsionType/new" component={AircraftPropulsionTypeForm} />
<Route path="/aircraftPropulsionType/:id" component={AircraftPropulsionTypeForm} />
<Route path="/aircraftPropulsionType/:page/:pageLength" component={AircraftPropulsionTypeList} />
<Route path="/aircraftPropulsionType/:page/:pageLength/:query" component={AircraftPropulsionTypeList} />
<Route path="/aircraftPropulsionType/:page/:pageLength" component={AircraftPropulsionTypeTable} />
<Route path="/aircraftPropulsionType/:page/:pageLength/:query" component={AircraftPropulsionTypeTable} />
<Route path="/aircraftPropulsionType/:page/:pageLength/:sortColumn/:sortOrder" component={AircraftPropulsionTypeTable} />
<Route path="/aircraftPropulsionType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={AircraftPropulsionTypeTable} />
*/
export default class AircraftPropulsionTypeTable extends GTable{
    showUrl =  '/aircraftPropulsionType/'
    apiGetUrl =  '/aircraftPropulsionType/'
    apiCreateUrl = '/aircraftPropulsionType/save'
    apiDeleteUrl = '/aircraftPropulsionType/delete/'
    
    listUrl = '/aircraftPropulsionType/'

    objsStr = 'Aircraft Propulsion Types'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "propulsionType", rowName: "Propulsion Type", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <AircraftPropulsionTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      