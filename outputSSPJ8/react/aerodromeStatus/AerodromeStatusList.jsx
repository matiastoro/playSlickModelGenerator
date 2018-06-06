import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AerodromeStatusFilter from './AerodromeStatusFilter'

//inputs de nested


/*
<Route path="/aerodromeStatus/" component={AerodromeStatusList} />
<Route path="/aerodromeStatus/new" component={AerodromeStatusForm} />
<Route path="/aerodromeStatus/:id" component={AerodromeStatusForm} />
<Route path="/aerodromeStatus/:page/:pageLength" component={AerodromeStatusList} />
<Route path="/aerodromeStatus/:page/:pageLength/:query" component={AerodromeStatusList} />
<Route path="/aerodromeStatus/:page/:pageLength/:sortColumn/:sortOrder" component={{AerodromeStatusTable} />
<Route path="/aerodromeStatus/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{AerodromeStatusTable} />
*/
export default class AerodromeStatusList extends GList{
    showUrl =  '/aerodromeStatus/'
    apiGetUrl =  '/aerodromeStatus/'
    apiCreateUrl = '/aerodromeStatus/save'
    apiDeleteUrl = '/aerodromeStatus/delete/'
    
    listUrl = '/aerodromeStatus/'

    objsStr = 'Aerodrome Statuss'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.status
    }
    

    renderFilter = () => {
        return <div>
            <AerodromeStatusFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      