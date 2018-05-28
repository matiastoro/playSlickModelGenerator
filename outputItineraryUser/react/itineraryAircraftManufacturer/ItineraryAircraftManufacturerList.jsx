import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/itineraryAircraftManufacturer/" component={ItineraryAircraftManufacturerList} />
<Route path="/itineraryAircraftManufacturer/new" component={ItineraryAircraftManufacturerForm} />
<Route path="/itineraryAircraftManufacturer/:id" component={ItineraryAircraftManufacturerForm} />
<Route path="/itineraryAircraftManufacturer/:page/:pageLength" component={ItineraryAircraftManufacturerList} />
*/
export default class ItineraryAircraftManufacturerForm extends GList{
    showUrl =  '/itineraryAircraftManufacturer/'
    apiGetUrl =  '/itineraryAircraftManufacturer/'
    apiCreateUrl = '/itineraryAircraftManufacturer/save'
    apiDeleteUrl = '/itineraryAircraftManufacturer/delete/'
    

    objsStr = 'ItineraryAircraftManufacturers'

    renderPrimaryText = (obj) => {
        return obj.name
    }
    
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      