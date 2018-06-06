import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoFlightOperationFilter from './NeoFlightOperationFilter'

//inputs de nested


/*
<Route path="/neoFlightOperation/" component={NeoFlightOperationList} />
<Route path="/neoFlightOperation/new" component={NeoFlightOperationForm} />
<Route path="/neoFlightOperation/:id" component={NeoFlightOperationForm} />
<Route path="/neoFlightOperation/:page/:pageLength" component={NeoFlightOperationList} />
<Route path="/neoFlightOperation/:page/:pageLength/:query" component={NeoFlightOperationList} />
<Route path="/neoFlightOperation/:page/:pageLength/:sortColumn/:sortOrder" component={{NeoFlightOperationTable} />
<Route path="/neoFlightOperation/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{NeoFlightOperationTable} />
*/
export default class NeoFlightOperationList extends GList{
    showUrl =  '/neoFlightOperation/'
    apiGetUrl =  '/neoFlightOperation/'
    apiCreateUrl = '/neoFlightOperation/save'
    apiDeleteUrl = '/neoFlightOperation/delete/'
    apiOptionsUrl = '/neoFlightOperation/options'
    listUrl = '/neoFlightOperation/'

    objsStr = 'Neo Flight Operations'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.reportingEntityName
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.reportingEntityId && this.state.indexedOptions?this.state.indexedOptions.reportingEntitys[obj.reportingEntityId].reportingEntity:null} -- 
            {obj.attachments}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <NeoFlightOperationFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      