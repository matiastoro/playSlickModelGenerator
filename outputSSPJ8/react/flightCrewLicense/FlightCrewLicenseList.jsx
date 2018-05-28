import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import FlightCrewLicenseFilter from './FlightCrewLicenseFilter'

//inputs de nested


/*
<Route path="/flightCrewLicense/" component={FlightCrewLicenseList} />
<Route path="/flightCrewLicense/new" component={FlightCrewLicenseForm} />
<Route path="/flightCrewLicense/:id" component={FlightCrewLicenseForm} />
<Route path="/flightCrewLicense/:page/:pageLength" component={FlightCrewLicenseList} />
*/
export default class FlightCrewLicenseList extends GList{
    showUrl =  '/flightCrewLicense/'
    apiGetUrl =  '/flightCrewLicense/'
    apiCreateUrl = '/flightCrewLicense/save'
    apiDeleteUrl = '/flightCrewLicense/delete/'
    apiOptionsUrl = '/flightCrewLicense/options'
    listUrl = '/flightCrewLicense/'

    objsStr = 'Flight Crew Licenses'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.id
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.flightCrewId && this.state.indexedOptions?this.state.indexedOptions.flightCrews[obj.flightCrewId].flightCrew:null} -- 
            {obj.licenseTypeId && this.state.indexedOptions?this.state.indexedOptions.licenseTypes[obj.licenseTypeId].licenseType:null} -- 
            {obj.licenseIssuedById && this.state.indexedOptions?this.state.indexedOptions.licenseIssuedBys[obj.licenseIssuedById].licenseIssuedBy:null} -- 
            {obj.dateOfLicense} -- 
            {obj.licenseValidityId && this.state.indexedOptions?this.state.indexedOptions.licenseValiditys[obj.licenseValidityId].licenseValidity:null} -- 
            {obj.licenseRatingsId && this.state.indexedOptions?this.state.indexedOptions.licenseRatingss[obj.licenseRatingsId].licenseRatings:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <FlightCrewLicenseFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      