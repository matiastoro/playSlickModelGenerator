import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import OcurrenceClassFilter from './OcurrenceClassFilter'

//inputs de nested


/*
import OcurrenceClassForm from './components/ocurrenceClass/OcurrenceClassForm'
import OcurrenceClassList from './components/ocurrenceClass/OcurrenceClassList'
import OcurrenceClassTable from './components/ocurrenceClass/OcurrenceClassTable'

<Route path="/ocurrenceClass/" component={OcurrenceClassList} />
<Route path="/ocurrenceClass/" component={OcurrenceClassTable} />
<Route path="/ocurrenceClass/new" component={OcurrenceClassForm} />
<Route path="/ocurrenceClass/:id" component={OcurrenceClassForm} />
<Route path="/ocurrenceClass/:page/:pageLength" component={OcurrenceClassList} />
<Route path="/ocurrenceClass/:page/:pageLength/:query" component={OcurrenceClassList} />
<Route path="/ocurrenceClass/:page/:pageLength" component={OcurrenceClassTable} />
<Route path="/ocurrenceClass/:page/:pageLength/:query" component={OcurrenceClassTable} />
<Route path="/ocurrenceClass/:page/:pageLength/:sortColumn/:sortOrder" component={OcurrenceClassTable} />
<Route path="/ocurrenceClass/:page/:pageLength/:sortColumn/:sortOrder/:query" component={OcurrenceClassTable} />
*/
export default class OcurrenceClassTable extends GTable{
    showUrl =  '/ocurrenceClass/'
    apiGetUrl =  '/ocurrenceClass/'
    apiCreateUrl = '/ocurrenceClass/save'
    apiDeleteUrl = '/ocurrenceClass/delete/'
    
    listUrl = '/ocurrenceClass/'

    objsStr = 'Ocurrence Classs'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "ocurrenceClass", rowName: "Ocurrence Class", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <OcurrenceClassFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      