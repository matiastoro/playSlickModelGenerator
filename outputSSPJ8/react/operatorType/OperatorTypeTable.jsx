import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import OperatorTypeFilter from './OperatorTypeFilter'

//inputs de nested


/*
import OperatorTypeForm from './components/operatorType/OperatorTypeForm'
import OperatorTypeList from './components/operatorType/OperatorTypeList'
import OperatorTypeTable from './components/operatorType/OperatorTypeTable'

<Route path="/operatorType/" component={OperatorTypeList} />
<Route path="/operatorType/" component={OperatorTypeTable} />
<Route path="/operatorType/new" component={OperatorTypeForm} />
<Route path="/operatorType/:id" component={OperatorTypeForm} />
<Route path="/operatorType/:page/:pageLength" component={OperatorTypeList} />
<Route path="/operatorType/:page/:pageLength/:query" component={OperatorTypeList} />
<Route path="/operatorType/:page/:pageLength" component={OperatorTypeTable} />
<Route path="/operatorType/:page/:pageLength/:query" component={OperatorTypeTable} />
<Route path="/operatorType/:page/:pageLength/:sortColumn/:sortOrder" component={OperatorTypeTable} />
<Route path="/operatorType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={OperatorTypeTable} />
*/
export default class OperatorTypeTable extends GTable{
    showUrl =  '/operatorType/'
    apiGetUrl =  '/operatorType/'
    apiCreateUrl = '/operatorType/save'
    apiDeleteUrl = '/operatorType/delete/'
    apiOptionsUrl = '/operatorType/options'
    listUrl = '/operatorType/'

    objsStr = 'Operator Types'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "operatorType", rowName: "Operator Type", sortable: true},
            {key: "operatorTypeId", rowName: "Operator Type", label: (o) => (this.findRelatedObject("operatorTypes", "operatorTypeId", o, "operatorType")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <OperatorTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      