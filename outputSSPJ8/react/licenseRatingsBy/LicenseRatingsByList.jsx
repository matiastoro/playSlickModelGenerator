import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/licenseRatingsBy/" component={LicenseRatingsByList} />
<Route path="/licenseRatingsBy/new" component={LicenseRatingsByForm} />
<Route path="/licenseRatingsBy/:id" component={LicenseRatingsByForm} />
<Route path="/licenseRatingsBy/:page/:pageLength" component={LicenseRatingsByList} />
*/
export default class LicenseRatingsByForm extends GList{
    showUrl =  '/licenseRatingsBy/'
    apiGetUrl =  '/licenseRatingsBy/'
    apiCreateUrl = '/licenseRatingsBy/save'
    apiDeleteUrl = '/licenseRatingsBy/delete/'
    

    objsStr = 'License Ratings Bys'

    renderPrimaryText = (obj) => {
        return obj.licenseRatings
    }
    
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      