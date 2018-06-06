import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import OperationTypeFilter from './OperationTypeFilter'

//inputs de nested


/*
import OperationTypeForm from './components/operationType/OperationTypeForm'
import OperationTypeList from './components/operationType/OperationTypeList'
import OperationTypeTable from './components/operationType/OperationTypeTable'

<Route path="/operationType/" component={OperationTypeList} />
<Route path="/operationType/" component={OperationTypeTable} />
<Route path="/operationType/new" component={OperationTypeForm} />
<Route path="/operationType/:id" component={OperationTypeForm} />
<Route path="/operationType/:page/:pageLength" component={OperationTypeList} />
<Route path="/operationType/:page/:pageLength/:query" component={OperationTypeList} />
<Route path="/operationType/:page/:pageLength" component={OperationTypeTable} />
<Route path="/operationType/:page/:pageLength/:query" component={OperationTypeTable} />
<Route path="/operationType/:page/:pageLength/:sortColumn/:sortOrder" component={OperationTypeTable} />
<Route path="/operationType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={OperationTypeTable} />
*/
export default class OperationTypeTable extends GTable{
    showUrl =  '/operationType/'
    apiGetUrl =  '/operationType/'
    apiCreateUrl = '/operationType/save'
    apiDeleteUrl = '/operationType/delete/'
    apiOptionsUrl = '/operationType/options'
    listUrl = '/operationType/'

    objsStr = 'Operation Types'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "operationType", rowName: "Operation Type", sortable: true},
            {key: "operationTypeId", rowName: "Operation Type", label: (o) => (this.findRelatedObject("operationTypes", "operationTypeId", o, "operationType")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <OperationTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      