import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import TrafficTypeFilter from './TrafficTypeFilter'

//inputs de nested


/*
import TrafficTypeForm from './components/trafficType/TrafficTypeForm'
import TrafficTypeList from './components/trafficType/TrafficTypeList'
import TrafficTypeTable from './components/trafficType/TrafficTypeTable'

<Route path="/trafficType/" component={TrafficTypeList} />
<Route path="/trafficType/" component={TrafficTypeTable} />
<Route path="/trafficType/new" component={TrafficTypeForm} />
<Route path="/trafficType/:id" component={TrafficTypeForm} />
<Route path="/trafficType/:page/:pageLength" component={TrafficTypeList} />
<Route path="/trafficType/:page/:pageLength/:query" component={TrafficTypeList} />
<Route path="/trafficType/:page/:pageLength" component={TrafficTypeTable} />
<Route path="/trafficType/:page/:pageLength/:query" component={TrafficTypeTable} />
<Route path="/trafficType/:page/:pageLength/:sortColumn/:sortOrder" component={TrafficTypeTable} />
<Route path="/trafficType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={TrafficTypeTable} />
*/
export default class TrafficTypeTable extends GTable{
    showUrl =  '/trafficType/'
    apiGetUrl =  '/trafficType/'
    apiCreateUrl = '/trafficType/save'
    apiDeleteUrl = '/trafficType/delete/'
    
    listUrl = '/trafficType/'

    objsStr = 'Traffic Types'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "trafficType", rowName: "Traffic Type", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <TrafficTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      