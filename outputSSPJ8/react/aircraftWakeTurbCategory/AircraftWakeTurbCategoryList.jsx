import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AircraftWakeTurbCategoryFilter from './AircraftWakeTurbCategoryFilter'

//inputs de nested


/*
<Route path="/aircraftWakeTurbCategory/" component={AircraftWakeTurbCategoryList} />
<Route path="/aircraftWakeTurbCategory/new" component={AircraftWakeTurbCategoryForm} />
<Route path="/aircraftWakeTurbCategory/:id" component={AircraftWakeTurbCategoryForm} />
<Route path="/aircraftWakeTurbCategory/:page/:pageLength" component={AircraftWakeTurbCategoryList} />
*/
export default class AircraftWakeTurbCategoryList extends GList{
    showUrl =  '/aircraftWakeTurbCategory/'
    apiGetUrl =  '/aircraftWakeTurbCategory/'
    apiCreateUrl = '/aircraftWakeTurbCategory/save'
    apiDeleteUrl = '/aircraftWakeTurbCategory/delete/'
    
    listUrl = '/aircraftWakeTurbCategory/'

    objsStr = 'Aircraft Wake Turb Categorys'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.wakeTurbCategory
    }
    

    renderFilter = () => {
        return <div>
            <AircraftWakeTurbCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      