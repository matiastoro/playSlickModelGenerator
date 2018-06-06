import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import LicenseIssuedByFilter from './LicenseIssuedByFilter'

//inputs de nested


/*
import LicenseIssuedByForm from './components/licenseIssuedBy/LicenseIssuedByForm'
import LicenseIssuedByList from './components/licenseIssuedBy/LicenseIssuedByList'
import LicenseIssuedByTable from './components/licenseIssuedBy/LicenseIssuedByTable'

<Route path="/licenseIssuedBy/" component={LicenseIssuedByList} />
<Route path="/licenseIssuedBy/" component={LicenseIssuedByTable} />
<Route path="/licenseIssuedBy/new" component={LicenseIssuedByForm} />
<Route path="/licenseIssuedBy/:id" component={LicenseIssuedByForm} />
<Route path="/licenseIssuedBy/:page/:pageLength" component={LicenseIssuedByList} />
<Route path="/licenseIssuedBy/:page/:pageLength/:query" component={LicenseIssuedByList} />
<Route path="/licenseIssuedBy/:page/:pageLength" component={LicenseIssuedByTable} />
<Route path="/licenseIssuedBy/:page/:pageLength/:query" component={LicenseIssuedByTable} />
<Route path="/licenseIssuedBy/:page/:pageLength/:sortColumn/:sortOrder" component={LicenseIssuedByTable} />
<Route path="/licenseIssuedBy/:page/:pageLength/:sortColumn/:sortOrder/:query" component={LicenseIssuedByTable} />
*/
export default class LicenseIssuedByTable extends GTable{
    showUrl =  '/licenseIssuedBy/'
    apiGetUrl =  '/licenseIssuedBy/'
    apiCreateUrl = '/licenseIssuedBy/save'
    apiDeleteUrl = '/licenseIssuedBy/delete/'
    apiOptionsUrl = '/licenseIssuedBy/options'
    listUrl = '/licenseIssuedBy/'

    objsStr = 'License Issued Bys'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "licenseIssuedBy", rowName: "License Issued By", sortable: true},
            {key: "licenseIssuedById", rowName: "License Issued By", label: (o) => (this.findRelatedObject("licenseIssuedBys", "licenseIssuedById", o, "licenseIssuedBy")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <LicenseIssuedByFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      