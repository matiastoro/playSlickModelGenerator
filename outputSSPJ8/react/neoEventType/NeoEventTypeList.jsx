import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoEventTypeFilter from './NeoEventTypeFilter'

//inputs de nested


/*
<Route path="/neoEventType/" component={NeoEventTypeList} />
<Route path="/neoEventType/new" component={NeoEventTypeForm} />
<Route path="/neoEventType/:id" component={NeoEventTypeForm} />
<Route path="/neoEventType/:page/:pageLength" component={NeoEventTypeList} />
<Route path="/neoEventType/:page/:pageLength/:query" component={NeoEventTypeList} />
<Route path="/neoEventType/:page/:pageLength/:sortColumn/:sortOrder" component={{NeoEventTypeTable} />
<Route path="/neoEventType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{NeoEventTypeTable} />
*/
export default class NeoEventTypeList extends GList{
    showUrl =  '/neoEventType/'
    apiGetUrl =  '/neoEventType/'
    apiCreateUrl = '/neoEventType/save'
    apiDeleteUrl = '/neoEventType/delete/'
    
    listUrl = '/neoEventType/'

    objsStr = 'Neo Event Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.eventType
    }
    

    renderFilter = () => {
        return <div>
            <NeoEventTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      