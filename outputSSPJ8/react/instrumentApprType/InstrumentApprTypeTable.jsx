import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import InstrumentApprTypeFilter from './InstrumentApprTypeFilter'

//inputs de nested


/*
import InstrumentApprTypeForm from './components/instrumentApprType/InstrumentApprTypeForm'
import InstrumentApprTypeList from './components/instrumentApprType/InstrumentApprTypeList'
import InstrumentApprTypeTable from './components/instrumentApprType/InstrumentApprTypeTable'

<Route path="/instrumentApprType/" component={InstrumentApprTypeList} />
<Route path="/instrumentApprType/" component={InstrumentApprTypeTable} />
<Route path="/instrumentApprType/new" component={InstrumentApprTypeForm} />
<Route path="/instrumentApprType/:id" component={InstrumentApprTypeForm} />
<Route path="/instrumentApprType/:page/:pageLength" component={InstrumentApprTypeList} />
<Route path="/instrumentApprType/:page/:pageLength/:query" component={InstrumentApprTypeList} />
<Route path="/instrumentApprType/:page/:pageLength" component={InstrumentApprTypeTable} />
<Route path="/instrumentApprType/:page/:pageLength/:query" component={InstrumentApprTypeTable} />
<Route path="/instrumentApprType/:page/:pageLength/:sortColumn/:sortOrder" component={InstrumentApprTypeTable} />
<Route path="/instrumentApprType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={InstrumentApprTypeTable} />
*/
export default class InstrumentApprTypeTable extends GTable{
    showUrl =  '/instrumentApprType/'
    apiGetUrl =  '/instrumentApprType/'
    apiCreateUrl = '/instrumentApprType/save'
    apiDeleteUrl = '/instrumentApprType/delete/'
    
    listUrl = '/instrumentApprType/'

    objsStr = 'Instrument Appr Types'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "instrumentApprType", rowName: "Instrument Appr Type", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <InstrumentApprTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      