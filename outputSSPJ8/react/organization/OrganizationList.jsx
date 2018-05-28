import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import OrganizationFilter from './OrganizationFilter'

//inputs de nested


/*
<Route path="/organization/" component={OrganizationList} />
<Route path="/organization/new" component={OrganizationForm} />
<Route path="/organization/:id" component={OrganizationForm} />
<Route path="/organization/:page/:pageLength" component={OrganizationList} />
*/
export default class OrganizationList extends GList{
    showUrl =  '/organization/'
    apiGetUrl =  '/organization/'
    apiCreateUrl = '/organization/save'
    apiDeleteUrl = '/organization/delete/'
    
    listUrl = '/organization/'

    objsStr = 'Organizations'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.organization
    }
    

    renderFilter = () => {
        return <div>
            <OrganizationFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      