import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import OperatorFilter from './OperatorFilter'

//inputs de nested


/*
import OperatorForm from './components/operator/OperatorForm'
import OperatorList from './components/operator/OperatorList'
import OperatorTable from './components/operator/OperatorTable'

<Route path="/operator/" component={OperatorList} />
<Route path="/operator/" component={OperatorTable} />
<Route path="/operator/new" component={OperatorForm} />
<Route path="/operator/:id" component={OperatorForm} />
<Route path="/operator/:page/:pageLength" component={OperatorList} />
<Route path="/operator/:page/:pageLength/:query" component={OperatorList} />
<Route path="/operator/:page/:pageLength" component={OperatorTable} />
<Route path="/operator/:page/:pageLength/:query" component={OperatorTable} />
<Route path="/operator/:page/:pageLength/:sortColumn/:sortOrder" component={OperatorTable} />
<Route path="/operator/:page/:pageLength/:sortColumn/:sortOrder/:query" component={OperatorTable} />
*/
export default class OperatorTable extends GTable{
    showUrl =  '/operator/'
    apiGetUrl =  '/operator/'
    apiCreateUrl = '/operator/save'
    apiDeleteUrl = '/operator/delete/'
    apiOptionsUrl = '/operator/options'
    listUrl = '/operator/'

    objsStr = 'Operators'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "operator", rowName: "Operator", sortable: true},
            {key: "operatorTypeId", rowName: "Operator Type", label: (o) => (this.findRelatedObject("operatorTypes", "operatorTypeId", o, "operatorType")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <OperatorFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      