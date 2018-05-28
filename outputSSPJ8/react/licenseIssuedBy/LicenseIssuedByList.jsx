import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import LicenseIssuedByFilter from './LicenseIssuedByFilter'

//inputs de nested


/*
<Route path="/licenseIssuedBy/" component={LicenseIssuedByList} />
<Route path="/licenseIssuedBy/new" component={LicenseIssuedByForm} />
<Route path="/licenseIssuedBy/:id" component={LicenseIssuedByForm} />
<Route path="/licenseIssuedBy/:page/:pageLength" component={LicenseIssuedByList} />
*/
export default class LicenseIssuedByList extends GList{
    showUrl =  '/licenseIssuedBy/'
    apiGetUrl =  '/licenseIssuedBy/'
    apiCreateUrl = '/licenseIssuedBy/save'
    apiDeleteUrl = '/licenseIssuedBy/delete/'
    apiOptionsUrl = '/licenseIssuedBy/options'
    listUrl = '/licenseIssuedBy/'

    objsStr = 'License Issued Bys'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.licenseIssuedBy
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.licenseIssuedById && this.state.indexedOptions?this.state.indexedOptions.licenseIssuedBys[obj.licenseIssuedById].licenseIssuedBy:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <LicenseIssuedByFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      