import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import TrafficTypeFilter from './TrafficTypeFilter'

//inputs de nested


/*
<Route path="/trafficType/" component={TrafficTypeList} />
<Route path="/trafficType/new" component={TrafficTypeForm} />
<Route path="/trafficType/:id" component={TrafficTypeForm} />
<Route path="/trafficType/:page/:pageLength" component={TrafficTypeList} />
<Route path="/trafficType/:page/:pageLength/:query" component={TrafficTypeList} />
<Route path="/trafficType/:page/:pageLength/:sortColumn/:sortOrder" component={{TrafficTypeTable} />
<Route path="/trafficType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{TrafficTypeTable} />
*/
export default class TrafficTypeList extends GList{
    showUrl =  '/trafficType/'
    apiGetUrl =  '/trafficType/'
    apiCreateUrl = '/trafficType/save'
    apiDeleteUrl = '/trafficType/delete/'
    
    listUrl = '/trafficType/'

    objsStr = 'Traffic Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.trafficType
    }
    

    renderFilter = () => {
        return <div>
            <TrafficTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      