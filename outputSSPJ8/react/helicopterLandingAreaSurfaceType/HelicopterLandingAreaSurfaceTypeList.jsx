import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import HelicopterLandingAreaSurfaceTypeFilter from './HelicopterLandingAreaSurfaceTypeFilter'

//inputs de nested


/*
<Route path="/helicopterLandingAreaSurfaceType/" component={HelicopterLandingAreaSurfaceTypeList} />
<Route path="/helicopterLandingAreaSurfaceType/new" component={HelicopterLandingAreaSurfaceTypeForm} />
<Route path="/helicopterLandingAreaSurfaceType/:id" component={HelicopterLandingAreaSurfaceTypeForm} />
<Route path="/helicopterLandingAreaSurfaceType/:page/:pageLength" component={HelicopterLandingAreaSurfaceTypeList} />
<Route path="/helicopterLandingAreaSurfaceType/:page/:pageLength/:query" component={HelicopterLandingAreaSurfaceTypeList} />
<Route path="/helicopterLandingAreaSurfaceType/:page/:pageLength/:sortColumn/:sortOrder" component={{HelicopterLandingAreaSurfaceTypeTable} />
<Route path="/helicopterLandingAreaSurfaceType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{HelicopterLandingAreaSurfaceTypeTable} />
*/
export default class HelicopterLandingAreaSurfaceTypeList extends GList{
    showUrl =  '/helicopterLandingAreaSurfaceType/'
    apiGetUrl =  '/helicopterLandingAreaSurfaceType/'
    apiCreateUrl = '/helicopterLandingAreaSurfaceType/save'
    apiDeleteUrl = '/helicopterLandingAreaSurfaceType/delete/'
    
    listUrl = '/helicopterLandingAreaSurfaceType/'

    objsStr = 'Helicopter Landing Area Surface Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.surfaceType
    }
    

    renderFilter = () => {
        return <div>
            <HelicopterLandingAreaSurfaceTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      