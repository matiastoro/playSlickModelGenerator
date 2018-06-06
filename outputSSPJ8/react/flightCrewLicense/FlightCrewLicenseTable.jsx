import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import FlightCrewLicenseFilter from './FlightCrewLicenseFilter'

//inputs de nested


/*
import FlightCrewLicenseForm from './components/flightCrewLicense/FlightCrewLicenseForm'
import FlightCrewLicenseList from './components/flightCrewLicense/FlightCrewLicenseList'
import FlightCrewLicenseTable from './components/flightCrewLicense/FlightCrewLicenseTable'

<Route path="/flightCrewLicense/" component={FlightCrewLicenseList} />
<Route path="/flightCrewLicense/" component={FlightCrewLicenseTable} />
<Route path="/flightCrewLicense/new" component={FlightCrewLicenseForm} />
<Route path="/flightCrewLicense/:id" component={FlightCrewLicenseForm} />
<Route path="/flightCrewLicense/:page/:pageLength" component={FlightCrewLicenseList} />
<Route path="/flightCrewLicense/:page/:pageLength/:query" component={FlightCrewLicenseList} />
<Route path="/flightCrewLicense/:page/:pageLength" component={FlightCrewLicenseTable} />
<Route path="/flightCrewLicense/:page/:pageLength/:query" component={FlightCrewLicenseTable} />
<Route path="/flightCrewLicense/:page/:pageLength/:sortColumn/:sortOrder" component={FlightCrewLicenseTable} />
<Route path="/flightCrewLicense/:page/:pageLength/:sortColumn/:sortOrder/:query" component={FlightCrewLicenseTable} />
*/
export default class FlightCrewLicenseTable extends GTable{
    showUrl =  '/flightCrewLicense/'
    apiGetUrl =  '/flightCrewLicense/'
    apiCreateUrl = '/flightCrewLicense/save'
    apiDeleteUrl = '/flightCrewLicense/delete/'
    apiOptionsUrl = '/flightCrewLicense/options'
    listUrl = '/flightCrewLicense/'

    objsStr = 'Flight Crew Licenses'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "flightCrewId", rowName: "Flight Crew", label: (o) => (this.findRelatedObject("flightCrews", "flightCrewId", o, "flightCrew")), sortable: true},
            {key: "licenseTypeId", rowName: "License Type", label: (o) => (this.findRelatedObject("licenseTypes", "licenseTypeId", o, "licenseType")), sortable: true},
            {key: "licenseIssuedById", rowName: "License Issued By", label: (o) => (this.findRelatedObject("licenseIssuedBys", "licenseIssuedById", o, "licenseIssuedBy")), sortable: true},
            {key: "dateOfLicense", rowName: "Date Of License", label: (o, v) => this.displayDateTime(v), sortable: true},
            {key: "licenseValidityId", rowName: "License Validity", label: (o) => (this.findRelatedObject("licenseValiditys", "licenseValidityId", o, "licenseValidity")), sortable: true},
            {key: "licenseRatingsId", rowName: "License Ratings", label: (o) => (this.findRelatedObject("licenseRatingss", "licenseRatingsId", o, "licenseRatings")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <FlightCrewLicenseFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      