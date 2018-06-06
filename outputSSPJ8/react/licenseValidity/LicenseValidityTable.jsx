import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import LicenseValidityFilter from './LicenseValidityFilter'

//inputs de nested


/*
import LicenseValidityForm from './components/licenseValidity/LicenseValidityForm'
import LicenseValidityList from './components/licenseValidity/LicenseValidityList'
import LicenseValidityTable from './components/licenseValidity/LicenseValidityTable'

<Route path="/licenseValidity/" component={LicenseValidityList} />
<Route path="/licenseValidity/" component={LicenseValidityTable} />
<Route path="/licenseValidity/new" component={LicenseValidityForm} />
<Route path="/licenseValidity/:id" component={LicenseValidityForm} />
<Route path="/licenseValidity/:page/:pageLength" component={LicenseValidityList} />
<Route path="/licenseValidity/:page/:pageLength/:query" component={LicenseValidityList} />
<Route path="/licenseValidity/:page/:pageLength" component={LicenseValidityTable} />
<Route path="/licenseValidity/:page/:pageLength/:query" component={LicenseValidityTable} />
<Route path="/licenseValidity/:page/:pageLength/:sortColumn/:sortOrder" component={LicenseValidityTable} />
<Route path="/licenseValidity/:page/:pageLength/:sortColumn/:sortOrder/:query" component={LicenseValidityTable} />
*/
export default class LicenseValidityTable extends GTable{
    showUrl =  '/licenseValidity/'
    apiGetUrl =  '/licenseValidity/'
    apiCreateUrl = '/licenseValidity/save'
    apiDeleteUrl = '/licenseValidity/delete/'
    
    listUrl = '/licenseValidity/'

    objsStr = 'License Validitys'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "licenseValidity", rowName: "License Validity", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <LicenseValidityFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      