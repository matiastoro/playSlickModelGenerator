import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoReportStatusFilter from './NeoReportStatusFilter'

//inputs de nested


/*
import NeoReportStatusForm from './components/neoReportStatus/NeoReportStatusForm'
import NeoReportStatusList from './components/neoReportStatus/NeoReportStatusList'
import NeoReportStatusTable from './components/neoReportStatus/NeoReportStatusTable'

<Route path="/neoReportStatus/" component={NeoReportStatusList} />
<Route path="/neoReportStatus/" component={NeoReportStatusTable} />
<Route path="/neoReportStatus/new" component={NeoReportStatusForm} />
<Route path="/neoReportStatus/:id" component={NeoReportStatusForm} />
<Route path="/neoReportStatus/:page/:pageLength" component={NeoReportStatusList} />
<Route path="/neoReportStatus/:page/:pageLength/:query" component={NeoReportStatusList} />
<Route path="/neoReportStatus/:page/:pageLength" component={NeoReportStatusTable} />
<Route path="/neoReportStatus/:page/:pageLength/:query" component={NeoReportStatusTable} />
<Route path="/neoReportStatus/:page/:pageLength/:sortColumn/:sortOrder" component={NeoReportStatusTable} />
<Route path="/neoReportStatus/:page/:pageLength/:sortColumn/:sortOrder/:query" component={NeoReportStatusTable} />
*/
export default class NeoReportStatusTable extends GTable{
    showUrl =  '/neoReportStatus/'
    apiGetUrl =  '/neoReportStatus/'
    apiCreateUrl = '/neoReportStatus/save'
    apiDeleteUrl = '/neoReportStatus/delete/'
    
    listUrl = '/neoReportStatus/'

    objsStr = 'Neo Report Statuss'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "reportStatus", rowName: "Report Status", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <NeoReportStatusFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      