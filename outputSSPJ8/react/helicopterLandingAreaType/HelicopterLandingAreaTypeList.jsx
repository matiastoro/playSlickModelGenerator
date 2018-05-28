import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import HelicopterLandingAreaTypeFilter from './HelicopterLandingAreaTypeFilter'

//inputs de nested


/*
<Route path="/helicopterLandingAreaType/" component={HelicopterLandingAreaTypeList} />
<Route path="/helicopterLandingAreaType/new" component={HelicopterLandingAreaTypeForm} />
<Route path="/helicopterLandingAreaType/:id" component={HelicopterLandingAreaTypeForm} />
<Route path="/helicopterLandingAreaType/:page/:pageLength" component={HelicopterLandingAreaTypeList} />
*/
export default class HelicopterLandingAreaTypeList extends GList{
    showUrl =  '/helicopterLandingAreaType/'
    apiGetUrl =  '/helicopterLandingAreaType/'
    apiCreateUrl = '/helicopterLandingAreaType/save'
    apiDeleteUrl = '/helicopterLandingAreaType/delete/'
    
    listUrl = '/helicopterLandingAreaType/'

    objsStr = 'Helicopter Landing Area Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.landingAreaType
    }
    

    renderFilter = () => {
        return <div>
            <HelicopterLandingAreaTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      