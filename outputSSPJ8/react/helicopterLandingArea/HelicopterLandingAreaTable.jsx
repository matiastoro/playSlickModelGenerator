import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import HelicopterLandingAreaFilter from './HelicopterLandingAreaFilter'

//inputs de nested


/*
import HelicopterLandingAreaForm from './components/helicopterLandingArea/HelicopterLandingAreaForm'
import HelicopterLandingAreaList from './components/helicopterLandingArea/HelicopterLandingAreaList'
import HelicopterLandingAreaTable from './components/helicopterLandingArea/HelicopterLandingAreaTable'

<Route path="/helicopterLandingArea/" component={HelicopterLandingAreaList} />
<Route path="/helicopterLandingArea/" component={HelicopterLandingAreaTable} />
<Route path="/helicopterLandingArea/new" component={HelicopterLandingAreaForm} />
<Route path="/helicopterLandingArea/:id" component={HelicopterLandingAreaForm} />
<Route path="/helicopterLandingArea/:page/:pageLength" component={HelicopterLandingAreaList} />
<Route path="/helicopterLandingArea/:page/:pageLength/:query" component={HelicopterLandingAreaList} />
<Route path="/helicopterLandingArea/:page/:pageLength" component={HelicopterLandingAreaTable} />
<Route path="/helicopterLandingArea/:page/:pageLength/:query" component={HelicopterLandingAreaTable} />
<Route path="/helicopterLandingArea/:page/:pageLength/:sortColumn/:sortOrder" component={HelicopterLandingAreaTable} />
<Route path="/helicopterLandingArea/:page/:pageLength/:sortColumn/:sortOrder/:query" component={HelicopterLandingAreaTable} />
*/
export default class HelicopterLandingAreaTable extends GTable{
    showUrl =  '/helicopterLandingArea/'
    apiGetUrl =  '/helicopterLandingArea/'
    apiCreateUrl = '/helicopterLandingArea/save'
    apiDeleteUrl = '/helicopterLandingArea/delete/'
    apiOptionsUrl = '/helicopterLandingArea/options'
    listUrl = '/helicopterLandingArea/'

    objsStr = 'Helicopter Landing Areas'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "helicopterLandingArea", rowName: "Helicopter Landing Area", sortable: true},
            {key: "aerodromeId", rowName: "Aerodrome", label: (o) => (this.findRelatedObject("aerodromes", "aerodromeId", o, "aerodrome")), sortable: true},
            {key: "helicopterLandingAreaTypeId", rowName: "Helicopter Landing Area Type", label: (o) => (this.findRelatedObject("helicopterLandingAreaTypes", "helicopterLandingAreaTypeId", o, "tpe")), sortable: true},
            {key: "helicopterLandingAreaAreaConfigurationId", rowName: "Helicopter Landing Area Area Configuration", label: (o) => (this.findRelatedObject("helicopterLandingAreaAreaConfigurations", "helicopterLandingAreaAreaConfigurationId", o, "areaConfiguration")), sortable: true},
            {key: "helicopterLandingAreaSurfaceTypeId", rowName: "Helicopter Landing Area Surface Type", label: (o) => (this.findRelatedObject("helicopterLandingAreaSurfaceTypes", "helicopterLandingAreaSurfaceTypeId", o, "surfaceType")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <HelicopterLandingAreaFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      