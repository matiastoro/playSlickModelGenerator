import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoReportStatusFilter from './NeoReportStatusFilter'

//inputs de nested


/*
<Route path="/neoReportStatus/" component={NeoReportStatusList} />
<Route path="/neoReportStatus/new" component={NeoReportStatusForm} />
<Route path="/neoReportStatus/:id" component={NeoReportStatusForm} />
<Route path="/neoReportStatus/:page/:pageLength" component={NeoReportStatusList} />
*/
export default class NeoReportStatusList extends GList{
    showUrl =  '/neoReportStatus/'
    apiGetUrl =  '/neoReportStatus/'
    apiCreateUrl = '/neoReportStatus/save'
    apiDeleteUrl = '/neoReportStatus/delete/'
    
    listUrl = '/neoReportStatus/'

    objsStr = 'Neo Report Statuss'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.reportStatus
    }
    

    renderFilter = () => {
        return <div>
            <NeoReportStatusFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      