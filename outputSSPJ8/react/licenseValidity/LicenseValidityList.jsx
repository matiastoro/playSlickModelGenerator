import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import LicenseValidityFilter from './LicenseValidityFilter'

//inputs de nested


/*
<Route path="/licenseValidity/" component={LicenseValidityList} />
<Route path="/licenseValidity/new" component={LicenseValidityForm} />
<Route path="/licenseValidity/:id" component={LicenseValidityForm} />
<Route path="/licenseValidity/:page/:pageLength" component={LicenseValidityList} />
*/
export default class LicenseValidityList extends GList{
    showUrl =  '/licenseValidity/'
    apiGetUrl =  '/licenseValidity/'
    apiCreateUrl = '/licenseValidity/save'
    apiDeleteUrl = '/licenseValidity/delete/'
    
    listUrl = '/licenseValidity/'

    objsStr = 'License Validitys'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.licenseValidity
    }
    

    renderFilter = () => {
        return <div>
            <LicenseValidityFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      