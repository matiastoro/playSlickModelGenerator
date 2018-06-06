import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import HelicopterLandingAreaSurfaceTypeFilter from './HelicopterLandingAreaSurfaceTypeFilter'

//inputs de nested


/*
import HelicopterLandingAreaSurfaceTypeForm from './components/helicopterLandingAreaSurfaceType/HelicopterLandingAreaSurfaceTypeForm'
import HelicopterLandingAreaSurfaceTypeList from './components/helicopterLandingAreaSurfaceType/HelicopterLandingAreaSurfaceTypeList'
import HelicopterLandingAreaSurfaceTypeTable from './components/helicopterLandingAreaSurfaceType/HelicopterLandingAreaSurfaceTypeTable'

<Route path="/helicopterLandingAreaSurfaceType/" component={HelicopterLandingAreaSurfaceTypeList} />
<Route path="/helicopterLandingAreaSurfaceType/" component={HelicopterLandingAreaSurfaceTypeTable} />
<Route path="/helicopterLandingAreaSurfaceType/new" component={HelicopterLandingAreaSurfaceTypeForm} />
<Route path="/helicopterLandingAreaSurfaceType/:id" component={HelicopterLandingAreaSurfaceTypeForm} />
<Route path="/helicopterLandingAreaSurfaceType/:page/:pageLength" component={HelicopterLandingAreaSurfaceTypeList} />
<Route path="/helicopterLandingAreaSurfaceType/:page/:pageLength/:query" component={HelicopterLandingAreaSurfaceTypeList} />
<Route path="/helicopterLandingAreaSurfaceType/:page/:pageLength" component={HelicopterLandingAreaSurfaceTypeTable} />
<Route path="/helicopterLandingAreaSurfaceType/:page/:pageLength/:query" component={HelicopterLandingAreaSurfaceTypeTable} />
<Route path="/helicopterLandingAreaSurfaceType/:page/:pageLength/:sortColumn/:sortOrder" component={HelicopterLandingAreaSurfaceTypeTable} />
<Route path="/helicopterLandingAreaSurfaceType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={HelicopterLandingAreaSurfaceTypeTable} />
*/
export default class HelicopterLandingAreaSurfaceTypeTable extends GTable{
    showUrl =  '/helicopterLandingAreaSurfaceType/'
    apiGetUrl =  '/helicopterLandingAreaSurfaceType/'
    apiCreateUrl = '/helicopterLandingAreaSurfaceType/save'
    apiDeleteUrl = '/helicopterLandingAreaSurfaceType/delete/'
    
    listUrl = '/helicopterLandingAreaSurfaceType/'

    objsStr = 'Helicopter Landing Area Surface Types'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "surfaceType", rowName: "Surface Type", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <HelicopterLandingAreaSurfaceTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      