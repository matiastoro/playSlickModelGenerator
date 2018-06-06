import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoAerodromeOcurrenceCategoryFilter from './NeoAerodromeOcurrenceCategoryFilter'

//inputs de nested


/*
import NeoAerodromeOcurrenceCategoryForm from './components/neoAerodromeOcurrenceCategory/NeoAerodromeOcurrenceCategoryForm'
import NeoAerodromeOcurrenceCategoryList from './components/neoAerodromeOcurrenceCategory/NeoAerodromeOcurrenceCategoryList'
import NeoAerodromeOcurrenceCategoryTable from './components/neoAerodromeOcurrenceCategory/NeoAerodromeOcurrenceCategoryTable'

<Route path="/neoAerodromeOcurrenceCategory/" component={NeoAerodromeOcurrenceCategoryList} />
<Route path="/neoAerodromeOcurrenceCategory/" component={NeoAerodromeOcurrenceCategoryTable} />
<Route path="/neoAerodromeOcurrenceCategory/new" component={NeoAerodromeOcurrenceCategoryForm} />
<Route path="/neoAerodromeOcurrenceCategory/:id" component={NeoAerodromeOcurrenceCategoryForm} />
<Route path="/neoAerodromeOcurrenceCategory/:page/:pageLength" component={NeoAerodromeOcurrenceCategoryList} />
<Route path="/neoAerodromeOcurrenceCategory/:page/:pageLength/:query" component={NeoAerodromeOcurrenceCategoryList} />
<Route path="/neoAerodromeOcurrenceCategory/:page/:pageLength" component={NeoAerodromeOcurrenceCategoryTable} />
<Route path="/neoAerodromeOcurrenceCategory/:page/:pageLength/:query" component={NeoAerodromeOcurrenceCategoryTable} />
<Route path="/neoAerodromeOcurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder" component={NeoAerodromeOcurrenceCategoryTable} />
<Route path="/neoAerodromeOcurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder/:query" component={NeoAerodromeOcurrenceCategoryTable} />
*/
export default class NeoAerodromeOcurrenceCategoryTable extends GTable{
    showUrl =  '/neoAerodromeOcurrenceCategory/'
    apiGetUrl =  '/neoAerodromeOcurrenceCategory/'
    apiCreateUrl = '/neoAerodromeOcurrenceCategory/save'
    apiDeleteUrl = '/neoAerodromeOcurrenceCategory/delete/'
    apiOptionsUrl = '/neoAerodromeOcurrenceCategory/options'
    listUrl = '/neoAerodromeOcurrenceCategory/'

    objsStr = 'Neo Aerodrome Ocurrence Categorys'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "neoAerodromeId", rowName: "Neo Aerodrome", label: (o) => (this.findRelatedObject("neoAerodromes", "neoAerodromeId", o, "neoAerodrome")), sortable: true},
            {key: "ocurrenceCategoryId", rowName: "Ocurrence Category", label: (o) => (this.findRelatedObject("ocurrenceCategorys", "ocurrenceCategoryId", o, "ocurrenceCategory")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <NeoAerodromeOcurrenceCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      