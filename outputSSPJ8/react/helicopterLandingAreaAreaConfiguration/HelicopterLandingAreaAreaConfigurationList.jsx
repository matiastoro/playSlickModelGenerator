import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import HelicopterLandingAreaAreaConfigurationFilter from './HelicopterLandingAreaAreaConfigurationFilter'

//inputs de nested


/*
<Route path="/helicopterLandingAreaAreaConfiguration/" component={HelicopterLandingAreaAreaConfigurationList} />
<Route path="/helicopterLandingAreaAreaConfiguration/new" component={HelicopterLandingAreaAreaConfigurationForm} />
<Route path="/helicopterLandingAreaAreaConfiguration/:id" component={HelicopterLandingAreaAreaConfigurationForm} />
<Route path="/helicopterLandingAreaAreaConfiguration/:page/:pageLength" component={HelicopterLandingAreaAreaConfigurationList} />
<Route path="/helicopterLandingAreaAreaConfiguration/:page/:pageLength/:query" component={HelicopterLandingAreaAreaConfigurationList} />
<Route path="/helicopterLandingAreaAreaConfiguration/:page/:pageLength/:sortColumn/:sortOrder" component={{HelicopterLandingAreaAreaConfigurationTable} />
<Route path="/helicopterLandingAreaAreaConfiguration/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{HelicopterLandingAreaAreaConfigurationTable} />
*/
export default class HelicopterLandingAreaAreaConfigurationList extends GList{
    showUrl =  '/helicopterLandingAreaAreaConfiguration/'
    apiGetUrl =  '/helicopterLandingAreaAreaConfiguration/'
    apiCreateUrl = '/helicopterLandingAreaAreaConfiguration/save'
    apiDeleteUrl = '/helicopterLandingAreaAreaConfiguration/delete/'
    
    listUrl = '/helicopterLandingAreaAreaConfiguration/'

    objsStr = 'Helicopter Landing Area Area Configurations'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.areaConfiguration
    }
    

    renderFilter = () => {
        return <div>
            <HelicopterLandingAreaAreaConfigurationFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      