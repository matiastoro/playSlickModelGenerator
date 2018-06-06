import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AircraftPropulsionTypeFilter from './AircraftPropulsionTypeFilter'

//inputs de nested


/*
<Route path="/aircraftPropulsionType/" component={AircraftPropulsionTypeList} />
<Route path="/aircraftPropulsionType/new" component={AircraftPropulsionTypeForm} />
<Route path="/aircraftPropulsionType/:id" component={AircraftPropulsionTypeForm} />
<Route path="/aircraftPropulsionType/:page/:pageLength" component={AircraftPropulsionTypeList} />
<Route path="/aircraftPropulsionType/:page/:pageLength/:query" component={AircraftPropulsionTypeList} />
<Route path="/aircraftPropulsionType/:page/:pageLength/:sortColumn/:sortOrder" component={{AircraftPropulsionTypeTable} />
<Route path="/aircraftPropulsionType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{AircraftPropulsionTypeTable} />
*/
export default class AircraftPropulsionTypeList extends GList{
    showUrl =  '/aircraftPropulsionType/'
    apiGetUrl =  '/aircraftPropulsionType/'
    apiCreateUrl = '/aircraftPropulsionType/save'
    apiDeleteUrl = '/aircraftPropulsionType/delete/'
    
    listUrl = '/aircraftPropulsionType/'

    objsStr = 'Aircraft Propulsion Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.propulsionType
    }
    

    renderFilter = () => {
        return <div>
            <AircraftPropulsionTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      