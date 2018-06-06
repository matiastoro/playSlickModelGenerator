import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import HelicopterLandingAreaAreaTypeFilter from './HelicopterLandingAreaAreaTypeFilter'

//inputs de nested


/*
<Route path="/helicopterLandingAreaAreaType/" component={HelicopterLandingAreaAreaTypeList} />
<Route path="/helicopterLandingAreaAreaType/new" component={HelicopterLandingAreaAreaTypeForm} />
<Route path="/helicopterLandingAreaAreaType/:id" component={HelicopterLandingAreaAreaTypeForm} />
<Route path="/helicopterLandingAreaAreaType/:page/:pageLength" component={HelicopterLandingAreaAreaTypeList} />
<Route path="/helicopterLandingAreaAreaType/:page/:pageLength/:query" component={HelicopterLandingAreaAreaTypeList} />
*/
export default class HelicopterLandingAreaAreaTypeList extends GList{
    showUrl =  '/helicopterLandingAreaAreaType/'
    apiGetUrl =  '/helicopterLandingAreaAreaType/'
    apiCreateUrl = '/helicopterLandingAreaAreaType/save'
    apiDeleteUrl = '/helicopterLandingAreaAreaType/delete/'
    apiOptionsUrl = '/helicopterLandingAreaAreaType/options'
    listUrl = '/helicopterLandingAreaAreaType/'

    objsStr = 'Helicopter Landing Area Area Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.helicopterLandingArea
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.helicopterLandingAreaTypeId && this.state.indexedOptions?this.state.indexedOptions.helicopterLandingAreaTypes[obj.helicopterLandingAreaTypeId].helicopterLandingAreaType:null} -- 
            {obj.helicopterLandingAreaAreaConfigurationId && this.state.indexedOptions?this.state.indexedOptions.helicopterLandingAreaAreaConfigurations[obj.helicopterLandingAreaAreaConfigurationId].helicopterLandingAreaAreaConfiguration:null} -- 
            {obj.helicopterLandingAreaSurfaceTypeId && this.state.indexedOptions?this.state.indexedOptions.helicopterLandingAreaSurfaceTypes[obj.helicopterLandingAreaSurfaceTypeId].helicopterLandingAreaSurfaceType:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <HelicopterLandingAreaAreaTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      