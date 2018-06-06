import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import LicenseTypeFilter from './LicenseTypeFilter'

//inputs de nested


/*
import LicenseTypeForm from './components/licenseType/LicenseTypeForm'
import LicenseTypeList from './components/licenseType/LicenseTypeList'
import LicenseTypeTable from './components/licenseType/LicenseTypeTable'

<Route path="/licenseType/" component={LicenseTypeList} />
<Route path="/licenseType/" component={LicenseTypeTable} />
<Route path="/licenseType/new" component={LicenseTypeForm} />
<Route path="/licenseType/:id" component={LicenseTypeForm} />
<Route path="/licenseType/:page/:pageLength" component={LicenseTypeList} />
<Route path="/licenseType/:page/:pageLength/:query" component={LicenseTypeList} />
<Route path="/licenseType/:page/:pageLength" component={LicenseTypeTable} />
<Route path="/licenseType/:page/:pageLength/:query" component={LicenseTypeTable} />
<Route path="/licenseType/:page/:pageLength/:sortColumn/:sortOrder" component={LicenseTypeTable} />
<Route path="/licenseType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={LicenseTypeTable} />
*/
export default class LicenseTypeTable extends GTable{
    showUrl =  '/licenseType/'
    apiGetUrl =  '/licenseType/'
    apiCreateUrl = '/licenseType/save'
    apiDeleteUrl = '/licenseType/delete/'
    apiOptionsUrl = '/licenseType/options'
    listUrl = '/licenseType/'

    objsStr = 'License Types'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "licenseType", rowName: "License Type", sortable: true},
            {key: "licenseTypeId", rowName: "License Type", label: (o) => (this.findRelatedObject("licenseTypes", "licenseTypeId", o, "licenseType")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <LicenseTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      