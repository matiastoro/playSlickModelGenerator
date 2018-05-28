import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AircraftCategoryFilter from './AircraftCategoryFilter'

//inputs de nested


/*
<Route path="/aircraftCategory/" component={AircraftCategoryList} />
<Route path="/aircraftCategory/new" component={AircraftCategoryForm} />
<Route path="/aircraftCategory/:id" component={AircraftCategoryForm} />
<Route path="/aircraftCategory/:page/:pageLength" component={AircraftCategoryList} />
*/
export default class AircraftCategoryList extends GList{
    showUrl =  '/aircraftCategory/'
    apiGetUrl =  '/aircraftCategory/'
    apiCreateUrl = '/aircraftCategory/save'
    apiDeleteUrl = '/aircraftCategory/delete/'
    apiOptionsUrl = '/aircraftCategory/options'
    listUrl = '/aircraftCategory/'

    objsStr = 'Aircraft Categorys'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.category
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.aircraftCategoryId && this.state.indexedOptions?this.state.indexedOptions.aircraftCategorys[obj.aircraftCategoryId].category:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <AircraftCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      