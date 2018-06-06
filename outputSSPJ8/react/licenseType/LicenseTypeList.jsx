import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import LicenseTypeFilter from './LicenseTypeFilter'

//inputs de nested


/*
<Route path="/licenseType/" component={LicenseTypeList} />
<Route path="/licenseType/new" component={LicenseTypeForm} />
<Route path="/licenseType/:id" component={LicenseTypeForm} />
<Route path="/licenseType/:page/:pageLength" component={LicenseTypeList} />
<Route path="/licenseType/:page/:pageLength/:query" component={LicenseTypeList} />
<Route path="/licenseType/:page/:pageLength/:sortColumn/:sortOrder" component={{LicenseTypeTable} />
<Route path="/licenseType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{LicenseTypeTable} />
*/
export default class LicenseTypeList extends GList{
    showUrl =  '/licenseType/'
    apiGetUrl =  '/licenseType/'
    apiCreateUrl = '/licenseType/save'
    apiDeleteUrl = '/licenseType/delete/'
    apiOptionsUrl = '/licenseType/options'
    listUrl = '/licenseType/'

    objsStr = 'License Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.licenseType
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.licenseTypeId && this.state.indexedOptions?this.state.indexedOptions.licenseTypes[obj.licenseTypeId].licenseType:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <LicenseTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      