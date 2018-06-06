import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import LicenseRatingsFilter from './LicenseRatingsFilter'

//inputs de nested


/*
import LicenseRatingsForm from './components/licenseRatings/LicenseRatingsForm'
import LicenseRatingsList from './components/licenseRatings/LicenseRatingsList'
import LicenseRatingsTable from './components/licenseRatings/LicenseRatingsTable'

<Route path="/licenseRatings/" component={LicenseRatingsList} />
<Route path="/licenseRatings/" component={LicenseRatingsTable} />
<Route path="/licenseRatings/new" component={LicenseRatingsForm} />
<Route path="/licenseRatings/:id" component={LicenseRatingsForm} />
<Route path="/licenseRatings/:page/:pageLength" component={LicenseRatingsList} />
<Route path="/licenseRatings/:page/:pageLength/:query" component={LicenseRatingsList} />
<Route path="/licenseRatings/:page/:pageLength" component={LicenseRatingsTable} />
<Route path="/licenseRatings/:page/:pageLength/:query" component={LicenseRatingsTable} />
<Route path="/licenseRatings/:page/:pageLength/:sortColumn/:sortOrder" component={LicenseRatingsTable} />
<Route path="/licenseRatings/:page/:pageLength/:sortColumn/:sortOrder/:query" component={LicenseRatingsTable} />
*/
export default class LicenseRatingsTable extends GTable{
    showUrl =  '/licenseRatings/'
    apiGetUrl =  '/licenseRatings/'
    apiCreateUrl = '/licenseRatings/save'
    apiDeleteUrl = '/licenseRatings/delete/'
    
    listUrl = '/licenseRatings/'

    objsStr = 'License Ratingss'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "licenseRatings", rowName: "License Ratings", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <LicenseRatingsFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      