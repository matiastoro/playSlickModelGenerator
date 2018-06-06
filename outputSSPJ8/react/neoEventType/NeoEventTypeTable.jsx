import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoEventTypeFilter from './NeoEventTypeFilter'

//inputs de nested


/*
import NeoEventTypeForm from './components/neoEventType/NeoEventTypeForm'
import NeoEventTypeList from './components/neoEventType/NeoEventTypeList'
import NeoEventTypeTable from './components/neoEventType/NeoEventTypeTable'

<Route path="/neoEventType/" component={NeoEventTypeList} />
<Route path="/neoEventType/" component={NeoEventTypeTable} />
<Route path="/neoEventType/new" component={NeoEventTypeForm} />
<Route path="/neoEventType/:id" component={NeoEventTypeForm} />
<Route path="/neoEventType/:page/:pageLength" component={NeoEventTypeList} />
<Route path="/neoEventType/:page/:pageLength/:query" component={NeoEventTypeList} />
<Route path="/neoEventType/:page/:pageLength" component={NeoEventTypeTable} />
<Route path="/neoEventType/:page/:pageLength/:query" component={NeoEventTypeTable} />
<Route path="/neoEventType/:page/:pageLength/:sortColumn/:sortOrder" component={NeoEventTypeTable} />
<Route path="/neoEventType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={NeoEventTypeTable} />
*/
export default class NeoEventTypeTable extends GTable{
    showUrl =  '/neoEventType/'
    apiGetUrl =  '/neoEventType/'
    apiCreateUrl = '/neoEventType/save'
    apiDeleteUrl = '/neoEventType/delete/'
    
    listUrl = '/neoEventType/'

    objsStr = 'Neo Event Types'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "eventType", rowName: "Event Type", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <NeoEventTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      