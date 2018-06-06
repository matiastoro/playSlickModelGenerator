import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import ReportingEntityFilter from './ReportingEntityFilter'

//inputs de nested


/*
import ReportingEntityForm from './components/reportingEntity/ReportingEntityForm'
import ReportingEntityList from './components/reportingEntity/ReportingEntityList'
import ReportingEntityTable from './components/reportingEntity/ReportingEntityTable'

<Route path="/reportingEntity/" component={ReportingEntityList} />
<Route path="/reportingEntity/" component={ReportingEntityTable} />
<Route path="/reportingEntity/new" component={ReportingEntityForm} />
<Route path="/reportingEntity/:id" component={ReportingEntityForm} />
<Route path="/reportingEntity/:page/:pageLength" component={ReportingEntityList} />
<Route path="/reportingEntity/:page/:pageLength/:query" component={ReportingEntityList} />
<Route path="/reportingEntity/:page/:pageLength" component={ReportingEntityTable} />
<Route path="/reportingEntity/:page/:pageLength/:query" component={ReportingEntityTable} />
<Route path="/reportingEntity/:page/:pageLength/:sortColumn/:sortOrder" component={ReportingEntityTable} />
<Route path="/reportingEntity/:page/:pageLength/:sortColumn/:sortOrder/:query" component={ReportingEntityTable} />
*/
export default class ReportingEntityTable extends GTable{
    showUrl =  '/reportingEntity/'
    apiGetUrl =  '/reportingEntity/'
    apiCreateUrl = '/reportingEntity/save'
    apiDeleteUrl = '/reportingEntity/delete/'
    apiOptionsUrl = '/reportingEntity/options'
    listUrl = '/reportingEntity/'

    objsStr = 'Reporting Entitys'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "reportingEntity", rowName: "Reporting Entity", sortable: true},
            {key: "reportingEntityId", rowName: "Reporting Entity", label: (o) => (this.findRelatedObject("reportingEntitys", "reportingEntityId", o, "reportingEntity")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <ReportingEntityFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      