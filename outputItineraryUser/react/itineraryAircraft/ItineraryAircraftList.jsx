import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/itineraryAircraft/" component={ItineraryAircraftList} />
<Route path="/itineraryAircraft/new" component={ItineraryAircraftForm} />
<Route path="/itineraryAircraft/:id" component={ItineraryAircraftForm} />
<Route path="/itineraryAircraft/:page/:pageLength" component={ItineraryAircraftList} />
*/
export default class ItineraryAircraftForm extends GList{
    showUrl =  '/itineraryAircraft/'
    apiGetUrl =  '/itineraryAircraft/'
    apiCreateUrl = '/itineraryAircraft/save'
    apiDeleteUrl = '/itineraryAircraft/delete/'
    apiOptionsUrl = '/itineraryAircraft/options'

    objsStr = 'ItineraryAircrafts'

    renderPrimaryText = (obj) => {
        return obj.model
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.manufacturerId && this.state.indexedOptions?this.state.indexedOptions.itinerary_aircraft_manufacturers[obj.manufacturerId].itinerary_aircraft_manufacturer:null} -- 
            {obj.tpe} -- 
            {obj.iataCode} -- 
            {obj.nPassengers} -- 
            {obj.length} -- 
            {obj.width} -- 
            {obj.wingspan} -- 
            {obj.OMGWS} -- 
            {obj.series} -- 
            {obj.ACN}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      