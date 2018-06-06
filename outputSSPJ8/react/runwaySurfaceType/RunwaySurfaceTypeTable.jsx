import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import RunwaySurfaceTypeFilter from './RunwaySurfaceTypeFilter'

//inputs de nested


/*
import RunwaySurfaceTypeForm from './components/runwaySurfaceType/RunwaySurfaceTypeForm'
import RunwaySurfaceTypeList from './components/runwaySurfaceType/RunwaySurfaceTypeList'
import RunwaySurfaceTypeTable from './components/runwaySurfaceType/RunwaySurfaceTypeTable'

<Route path="/runwaySurfaceType/" component={RunwaySurfaceTypeList} />
<Route path="/runwaySurfaceType/" component={RunwaySurfaceTypeTable} />
<Route path="/runwaySurfaceType/new" component={RunwaySurfaceTypeForm} />
<Route path="/runwaySurfaceType/:id" component={RunwaySurfaceTypeForm} />
<Route path="/runwaySurfaceType/:page/:pageLength" component={RunwaySurfaceTypeList} />
<Route path="/runwaySurfaceType/:page/:pageLength/:query" component={RunwaySurfaceTypeList} />
<Route path="/runwaySurfaceType/:page/:pageLength" component={RunwaySurfaceTypeTable} />
<Route path="/runwaySurfaceType/:page/:pageLength/:query" component={RunwaySurfaceTypeTable} />
<Route path="/runwaySurfaceType/:page/:pageLength/:sortColumn/:sortOrder" component={RunwaySurfaceTypeTable} />
<Route path="/runwaySurfaceType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={RunwaySurfaceTypeTable} />
*/
export default class RunwaySurfaceTypeTable extends GTable{
    showUrl =  '/runwaySurfaceType/'
    apiGetUrl =  '/runwaySurfaceType/'
    apiCreateUrl = '/runwaySurfaceType/save'
    apiDeleteUrl = '/runwaySurfaceType/delete/'
    
    listUrl = '/runwaySurfaceType/'

    objsStr = 'Runway Surface Types'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "surfaceType", rowName: "Surface Type", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <RunwaySurfaceTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      