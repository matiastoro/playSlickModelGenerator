import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import HelicopterLandingAreaAreaConfigurationFilter from './HelicopterLandingAreaAreaConfigurationFilter'

//inputs de nested


/*
import HelicopterLandingAreaAreaConfigurationForm from './components/helicopterLandingAreaAreaConfiguration/HelicopterLandingAreaAreaConfigurationForm'
import HelicopterLandingAreaAreaConfigurationList from './components/helicopterLandingAreaAreaConfiguration/HelicopterLandingAreaAreaConfigurationList'
import HelicopterLandingAreaAreaConfigurationTable from './components/helicopterLandingAreaAreaConfiguration/HelicopterLandingAreaAreaConfigurationTable'

<Route path="/helicopterLandingAreaAreaConfiguration/" component={HelicopterLandingAreaAreaConfigurationList} />
<Route path="/helicopterLandingAreaAreaConfiguration/" component={HelicopterLandingAreaAreaConfigurationTable} />
<Route path="/helicopterLandingAreaAreaConfiguration/new" component={HelicopterLandingAreaAreaConfigurationForm} />
<Route path="/helicopterLandingAreaAreaConfiguration/:id" component={HelicopterLandingAreaAreaConfigurationForm} />
<Route path="/helicopterLandingAreaAreaConfiguration/:page/:pageLength" component={HelicopterLandingAreaAreaConfigurationList} />
<Route path="/helicopterLandingAreaAreaConfiguration/:page/:pageLength/:query" component={HelicopterLandingAreaAreaConfigurationList} />
<Route path="/helicopterLandingAreaAreaConfiguration/:page/:pageLength" component={HelicopterLandingAreaAreaConfigurationTable} />
<Route path="/helicopterLandingAreaAreaConfiguration/:page/:pageLength/:query" component={HelicopterLandingAreaAreaConfigurationTable} />
<Route path="/helicopterLandingAreaAreaConfiguration/:page/:pageLength/:sortColumn/:sortOrder" component={HelicopterLandingAreaAreaConfigurationTable} />
<Route path="/helicopterLandingAreaAreaConfiguration/:page/:pageLength/:sortColumn/:sortOrder/:query" component={HelicopterLandingAreaAreaConfigurationTable} />
*/
export default class HelicopterLandingAreaAreaConfigurationTable extends GTable{
    showUrl =  '/helicopterLandingAreaAreaConfiguration/'
    apiGetUrl =  '/helicopterLandingAreaAreaConfiguration/'
    apiCreateUrl = '/helicopterLandingAreaAreaConfiguration/save'
    apiDeleteUrl = '/helicopterLandingAreaAreaConfiguration/delete/'
    
    listUrl = '/helicopterLandingAreaAreaConfiguration/'

    objsStr = 'Helicopter Landing Area Area Configurations'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "areaConfiguration", rowName: "Area Configuration", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <HelicopterLandingAreaAreaConfigurationFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      