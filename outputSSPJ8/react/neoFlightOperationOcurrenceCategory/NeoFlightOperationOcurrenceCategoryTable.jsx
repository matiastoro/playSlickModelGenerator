import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoFlightOperationOcurrenceCategoryFilter from './NeoFlightOperationOcurrenceCategoryFilter'

//inputs de nested


/*
import NeoFlightOperationOcurrenceCategoryForm from './components/neoFlightOperationOcurrenceCategory/NeoFlightOperationOcurrenceCategoryForm'
import NeoFlightOperationOcurrenceCategoryList from './components/neoFlightOperationOcurrenceCategory/NeoFlightOperationOcurrenceCategoryList'
import NeoFlightOperationOcurrenceCategoryTable from './components/neoFlightOperationOcurrenceCategory/NeoFlightOperationOcurrenceCategoryTable'

<Route path="/neoFlightOperationOcurrenceCategory/" component={NeoFlightOperationOcurrenceCategoryList} />
<Route path="/neoFlightOperationOcurrenceCategory/" component={NeoFlightOperationOcurrenceCategoryTable} />
<Route path="/neoFlightOperationOcurrenceCategory/new" component={NeoFlightOperationOcurrenceCategoryForm} />
<Route path="/neoFlightOperationOcurrenceCategory/:id" component={NeoFlightOperationOcurrenceCategoryForm} />
<Route path="/neoFlightOperationOcurrenceCategory/:page/:pageLength" component={NeoFlightOperationOcurrenceCategoryList} />
<Route path="/neoFlightOperationOcurrenceCategory/:page/:pageLength/:query" component={NeoFlightOperationOcurrenceCategoryList} />
<Route path="/neoFlightOperationOcurrenceCategory/:page/:pageLength" component={NeoFlightOperationOcurrenceCategoryTable} />
<Route path="/neoFlightOperationOcurrenceCategory/:page/:pageLength/:query" component={NeoFlightOperationOcurrenceCategoryTable} />
<Route path="/neoFlightOperationOcurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder" component={NeoFlightOperationOcurrenceCategoryTable} />
<Route path="/neoFlightOperationOcurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder/:query" component={NeoFlightOperationOcurrenceCategoryTable} />
*/
export default class NeoFlightOperationOcurrenceCategoryTable extends GTable{
    showUrl =  '/neoFlightOperationOcurrenceCategory/'
    apiGetUrl =  '/neoFlightOperationOcurrenceCategory/'
    apiCreateUrl = '/neoFlightOperationOcurrenceCategory/save'
    apiDeleteUrl = '/neoFlightOperationOcurrenceCategory/delete/'
    apiOptionsUrl = '/neoFlightOperationOcurrenceCategory/options'
    listUrl = '/neoFlightOperationOcurrenceCategory/'

    objsStr = 'Neo Flight Operation Ocurrence Categorys'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "neoFlightOperationId", rowName: "Neo Flight Operation", label: (o) => (this.findRelatedObject("neoFlightOperations", "neoFlightOperationId", o, "neoFlightOperation")), sortable: true},
            {key: "ocurrenceCategoryId", rowName: "Ocurrence Category", label: (o) => (this.findRelatedObject("ocurrenceCategorys", "ocurrenceCategoryId", o, "ocurrenceCategory")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <NeoFlightOperationOcurrenceCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      