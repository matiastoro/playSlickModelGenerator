import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AircraftLandingGearTypeFilter from './AircraftLandingGearTypeFilter'

//inputs de nested


/*
<Route path="/aircraftLandingGearType/" component={AircraftLandingGearTypeList} />
<Route path="/aircraftLandingGearType/new" component={AircraftLandingGearTypeForm} />
<Route path="/aircraftLandingGearType/:id" component={AircraftLandingGearTypeForm} />
<Route path="/aircraftLandingGearType/:page/:pageLength" component={AircraftLandingGearTypeList} />
*/
export default class AircraftLandingGearTypeList extends GList{
    showUrl =  '/aircraftLandingGearType/'
    apiGetUrl =  '/aircraftLandingGearType/'
    apiCreateUrl = '/aircraftLandingGearType/save'
    apiDeleteUrl = '/aircraftLandingGearType/delete/'
    apiOptionsUrl = '/aircraftLandingGearType/options'
    listUrl = '/aircraftLandingGearType/'

    objsStr = 'Aircraft Landing Gear Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.landingGearType
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.aircraftLandingGearTypeId && this.state.indexedOptions?this.state.indexedOptions.aircraftLandingGearTypes[obj.aircraftLandingGearTypeId].landing_gear_type:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <AircraftLandingGearTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      