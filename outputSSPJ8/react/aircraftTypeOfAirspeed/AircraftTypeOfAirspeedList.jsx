import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/aircraftTypeOfAirspeed/" component={AircraftTypeOfAirspeedList} />
<Route path="/aircraftTypeOfAirspeed/new" component={AircraftTypeOfAirspeedForm} />
<Route path="/aircraftTypeOfAirspeed/:id" component={AircraftTypeOfAirspeedForm} />
<Route path="/aircraftTypeOfAirspeed/:page/:pageLength" component={AircraftTypeOfAirspeedList} />
*/
export default class AircraftTypeOfAirspeedForm extends GList{
    showUrl =  '/aircraftTypeOfAirspeed/'
    apiGetUrl =  '/aircraftTypeOfAirspeed/'
    apiCreateUrl = '/aircraftTypeOfAirspeed/save'
    apiDeleteUrl = '/aircraftTypeOfAirspeed/delete/'
    

    objsStr = 'Aircraft Type Of Airspeeds'

    renderPrimaryText = (obj) => {
        return obj.typeOfAirspeed
    }
    
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      