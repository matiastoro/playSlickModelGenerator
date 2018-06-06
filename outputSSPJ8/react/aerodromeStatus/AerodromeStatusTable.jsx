import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AerodromeStatusFilter from './AerodromeStatusFilter'

//inputs de nested


/*
import AerodromeStatusForm from './components/aerodromeStatus/AerodromeStatusForm'
import AerodromeStatusList from './components/aerodromeStatus/AerodromeStatusList'
import AerodromeStatusTable from './components/aerodromeStatus/AerodromeStatusTable'

<Route path="/aerodromeStatus/" component={AerodromeStatusList} />
<Route path="/aerodromeStatus/" component={AerodromeStatusTable} />
<Route path="/aerodromeStatus/new" component={AerodromeStatusForm} />
<Route path="/aerodromeStatus/:id" component={AerodromeStatusForm} />
<Route path="/aerodromeStatus/:page/:pageLength" component={AerodromeStatusList} />
<Route path="/aerodromeStatus/:page/:pageLength/:query" component={AerodromeStatusList} />
<Route path="/aerodromeStatus/:page/:pageLength" component={AerodromeStatusTable} />
<Route path="/aerodromeStatus/:page/:pageLength/:query" component={AerodromeStatusTable} />
<Route path="/aerodromeStatus/:page/:pageLength/:sortColumn/:sortOrder" component={AerodromeStatusTable} />
<Route path="/aerodromeStatus/:page/:pageLength/:sortColumn/:sortOrder/:query" component={AerodromeStatusTable} />
*/
export default class AerodromeStatusTable extends GTable{
    showUrl =  '/aerodromeStatus/'
    apiGetUrl =  '/aerodromeStatus/'
    apiCreateUrl = '/aerodromeStatus/save'
    apiDeleteUrl = '/aerodromeStatus/delete/'
    
    listUrl = '/aerodromeStatus/'

    objsStr = 'Aerodrome Statuss'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "status", rowName: "Status", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <AerodromeStatusFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      