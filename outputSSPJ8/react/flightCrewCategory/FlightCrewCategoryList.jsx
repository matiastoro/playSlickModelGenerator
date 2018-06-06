import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import FlightCrewCategoryFilter from './FlightCrewCategoryFilter'

//inputs de nested


/*
<Route path="/flightCrewCategory/" component={FlightCrewCategoryList} />
<Route path="/flightCrewCategory/new" component={FlightCrewCategoryForm} />
<Route path="/flightCrewCategory/:id" component={FlightCrewCategoryForm} />
<Route path="/flightCrewCategory/:page/:pageLength" component={FlightCrewCategoryList} />
<Route path="/flightCrewCategory/:page/:pageLength/:query" component={FlightCrewCategoryList} />
<Route path="/flightCrewCategory/:page/:pageLength/:sortColumn/:sortOrder" component={{FlightCrewCategoryTable} />
<Route path="/flightCrewCategory/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{FlightCrewCategoryTable} />
*/
export default class FlightCrewCategoryList extends GList{
    showUrl =  '/flightCrewCategory/'
    apiGetUrl =  '/flightCrewCategory/'
    apiCreateUrl = '/flightCrewCategory/save'
    apiDeleteUrl = '/flightCrewCategory/delete/'
    
    listUrl = '/flightCrewCategory/'

    objsStr = 'Flight Crew Categorys'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.category
    }
    

    renderFilter = () => {
        return <div>
            <FlightCrewCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      