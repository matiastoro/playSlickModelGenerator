import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import LicenseRatingsFilter from './LicenseRatingsFilter'

//inputs de nested


/*
<Route path="/licenseRatings/" component={LicenseRatingsList} />
<Route path="/licenseRatings/new" component={LicenseRatingsForm} />
<Route path="/licenseRatings/:id" component={LicenseRatingsForm} />
<Route path="/licenseRatings/:page/:pageLength" component={LicenseRatingsList} />
<Route path="/licenseRatings/:page/:pageLength/:query" component={LicenseRatingsList} />
<Route path="/licenseRatings/:page/:pageLength/:sortColumn/:sortOrder" component={{LicenseRatingsTable} />
<Route path="/licenseRatings/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{LicenseRatingsTable} />
*/
export default class LicenseRatingsList extends GList{
    showUrl =  '/licenseRatings/'
    apiGetUrl =  '/licenseRatings/'
    apiCreateUrl = '/licenseRatings/save'
    apiDeleteUrl = '/licenseRatings/delete/'
    
    listUrl = '/licenseRatings/'

    objsStr = 'License Ratingss'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.licenseRatings
    }
    

    renderFilter = () => {
        return <div>
            <LicenseRatingsFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      