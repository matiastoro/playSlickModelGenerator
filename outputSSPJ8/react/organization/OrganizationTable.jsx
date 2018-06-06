import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import OrganizationFilter from './OrganizationFilter'

//inputs de nested


/*
import OrganizationForm from './components/organization/OrganizationForm'
import OrganizationList from './components/organization/OrganizationList'
import OrganizationTable from './components/organization/OrganizationTable'

<Route path="/organization/" component={OrganizationList} />
<Route path="/organization/" component={OrganizationTable} />
<Route path="/organization/new" component={OrganizationForm} />
<Route path="/organization/:id" component={OrganizationForm} />
<Route path="/organization/:page/:pageLength" component={OrganizationList} />
<Route path="/organization/:page/:pageLength/:query" component={OrganizationList} />
<Route path="/organization/:page/:pageLength" component={OrganizationTable} />
<Route path="/organization/:page/:pageLength/:query" component={OrganizationTable} />
<Route path="/organization/:page/:pageLength/:sortColumn/:sortOrder" component={OrganizationTable} />
<Route path="/organization/:page/:pageLength/:sortColumn/:sortOrder/:query" component={OrganizationTable} />
*/
export default class OrganizationTable extends GTable{
    showUrl =  '/organization/'
    apiGetUrl =  '/organization/'
    apiCreateUrl = '/organization/save'
    apiDeleteUrl = '/organization/delete/'
    
    listUrl = '/organization/'

    objsStr = 'Organizations'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "organization", rowName: "Organization", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <OrganizationFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      