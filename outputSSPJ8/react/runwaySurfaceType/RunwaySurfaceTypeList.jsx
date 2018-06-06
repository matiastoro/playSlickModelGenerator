import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import RunwaySurfaceTypeFilter from './RunwaySurfaceTypeFilter'

//inputs de nested


/*
<Route path="/runwaySurfaceType/" component={RunwaySurfaceTypeList} />
<Route path="/runwaySurfaceType/new" component={RunwaySurfaceTypeForm} />
<Route path="/runwaySurfaceType/:id" component={RunwaySurfaceTypeForm} />
<Route path="/runwaySurfaceType/:page/:pageLength" component={RunwaySurfaceTypeList} />
<Route path="/runwaySurfaceType/:page/:pageLength/:query" component={RunwaySurfaceTypeList} />
<Route path="/runwaySurfaceType/:page/:pageLength/:sortColumn/:sortOrder" component={{RunwaySurfaceTypeTable} />
<Route path="/runwaySurfaceType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{RunwaySurfaceTypeTable} />
*/
export default class RunwaySurfaceTypeList extends GList{
    showUrl =  '/runwaySurfaceType/'
    apiGetUrl =  '/runwaySurfaceType/'
    apiCreateUrl = '/runwaySurfaceType/save'
    apiDeleteUrl = '/runwaySurfaceType/delete/'
    
    listUrl = '/runwaySurfaceType/'

    objsStr = 'Runway Surface Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.surfaceType
    }
    

    renderFilter = () => {
        return <div>
            <RunwaySurfaceTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      