import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import OcurrenceCategoryFilter from './OcurrenceCategoryFilter'

//inputs de nested


/*
import OcurrenceCategoryForm from './components/ocurrenceCategory/OcurrenceCategoryForm'
import OcurrenceCategoryList from './components/ocurrenceCategory/OcurrenceCategoryList'
import OcurrenceCategoryTable from './components/ocurrenceCategory/OcurrenceCategoryTable'

<Route path="/ocurrenceCategory/" component={OcurrenceCategoryList} />
<Route path="/ocurrenceCategory/" component={OcurrenceCategoryTable} />
<Route path="/ocurrenceCategory/new" component={OcurrenceCategoryForm} />
<Route path="/ocurrenceCategory/:id" component={OcurrenceCategoryForm} />
<Route path="/ocurrenceCategory/:page/:pageLength" component={OcurrenceCategoryList} />
<Route path="/ocurrenceCategory/:page/:pageLength/:query" component={OcurrenceCategoryList} />
<Route path="/ocurrenceCategory/:page/:pageLength" component={OcurrenceCategoryTable} />
<Route path="/ocurrenceCategory/:page/:pageLength/:query" component={OcurrenceCategoryTable} />
<Route path="/ocurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder" component={OcurrenceCategoryTable} />
<Route path="/ocurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder/:query" component={OcurrenceCategoryTable} />
*/
export default class OcurrenceCategoryTable extends GTable{
    showUrl =  '/ocurrenceCategory/'
    apiGetUrl =  '/ocurrenceCategory/'
    apiCreateUrl = '/ocurrenceCategory/save'
    apiDeleteUrl = '/ocurrenceCategory/delete/'
    
    listUrl = '/ocurrenceCategory/'

    objsStr = 'Ocurrence Categorys'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "code", rowName: "Code", sortable: true},
            {key: "ocurrenceCategory", rowName: "Ocurrence Category", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <OcurrenceCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      