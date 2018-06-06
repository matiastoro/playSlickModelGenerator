import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import ReportingEntityFilter from './ReportingEntityFilter'

//inputs de nested


/*
<Route path="/reportingEntity/" component={ReportingEntityList} />
<Route path="/reportingEntity/new" component={ReportingEntityForm} />
<Route path="/reportingEntity/:id" component={ReportingEntityForm} />
<Route path="/reportingEntity/:page/:pageLength" component={ReportingEntityList} />
<Route path="/reportingEntity/:page/:pageLength/:query" component={ReportingEntityList} />
<Route path="/reportingEntity/:page/:pageLength/:sortColumn/:sortOrder" component={{ReportingEntityTable} />
<Route path="/reportingEntity/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{ReportingEntityTable} />
*/
export default class ReportingEntityList extends GList{
    showUrl =  '/reportingEntity/'
    apiGetUrl =  '/reportingEntity/'
    apiCreateUrl = '/reportingEntity/save'
    apiDeleteUrl = '/reportingEntity/delete/'
    apiOptionsUrl = '/reportingEntity/options'
    listUrl = '/reportingEntity/'

    objsStr = 'Reporting Entitys'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.reportingEntity
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.reportingEntityId && this.state.indexedOptions?this.state.indexedOptions.reportingEntitys[obj.reportingEntityId].reportingEntity:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <ReportingEntityFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      