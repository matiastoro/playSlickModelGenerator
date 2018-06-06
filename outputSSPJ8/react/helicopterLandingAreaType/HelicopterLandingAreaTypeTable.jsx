import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import HelicopterLandingAreaTypeFilter from './HelicopterLandingAreaTypeFilter'

//inputs de nested


/*
import HelicopterLandingAreaTypeForm from './components/helicopterLandingAreaType/HelicopterLandingAreaTypeForm'
import HelicopterLandingAreaTypeList from './components/helicopterLandingAreaType/HelicopterLandingAreaTypeList'
import HelicopterLandingAreaTypeTable from './components/helicopterLandingAreaType/HelicopterLandingAreaTypeTable'

<Route path="/helicopterLandingAreaType/" component={HelicopterLandingAreaTypeList} />
<Route path="/helicopterLandingAreaType/" component={HelicopterLandingAreaTypeTable} />
<Route path="/helicopterLandingAreaType/new" component={HelicopterLandingAreaTypeForm} />
<Route path="/helicopterLandingAreaType/:id" component={HelicopterLandingAreaTypeForm} />
<Route path="/helicopterLandingAreaType/:page/:pageLength" component={HelicopterLandingAreaTypeList} />
<Route path="/helicopterLandingAreaType/:page/:pageLength/:query" component={HelicopterLandingAreaTypeList} />
<Route path="/helicopterLandingAreaType/:page/:pageLength" component={HelicopterLandingAreaTypeTable} />
<Route path="/helicopterLandingAreaType/:page/:pageLength/:query" component={HelicopterLandingAreaTypeTable} />
<Route path="/helicopterLandingAreaType/:page/:pageLength/:sortColumn/:sortOrder" component={HelicopterLandingAreaTypeTable} />
<Route path="/helicopterLandingAreaType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={HelicopterLandingAreaTypeTable} />
*/
export default class HelicopterLandingAreaTypeTable extends GTable{
    showUrl =  '/helicopterLandingAreaType/'
    apiGetUrl =  '/helicopterLandingAreaType/'
    apiCreateUrl = '/helicopterLandingAreaType/save'
    apiDeleteUrl = '/helicopterLandingAreaType/delete/'
    
    listUrl = '/helicopterLandingAreaType/'

    objsStr = 'Helicopter Landing Area Types'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "tpe", rowName: "Tpe", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <HelicopterLandingAreaTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      