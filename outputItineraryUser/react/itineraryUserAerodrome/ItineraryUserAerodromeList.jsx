import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/itineraryUserAerodrome/" component={ItineraryUserAerodromeList} />
<Route path="/itineraryUserAerodrome/new" component={ItineraryUserAerodromeForm} />
<Route path="/itineraryUserAerodrome/:id" component={ItineraryUserAerodromeForm} />
<Route path="/itineraryUserAerodrome/:page/:pageLength" component={ItineraryUserAerodromeList} />
*/
export default class ItineraryUserAerodromeForm extends GList{
    showUrl =  '/itineraryUserAerodrome/'
    apiGetUrl =  '/itineraryUserAerodrome/'
    apiCreateUrl = '/itineraryUserAerodrome/save'
    apiDeleteUrl = '/itineraryUserAerodrome/delete/'
    apiOptionsUrl = '/itineraryUserAerodrome/options'

    objsStr = 'ItineraryUserAerodromes'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.userId && this.state.indexedOptions?this.state.indexedOptions.users[obj.userId].user:null} -- 
            {obj.aerodromeId && this.state.indexedOptions?this.state.indexedOptions.aerodromes[obj.aerodromeId].aerodrome:null}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      