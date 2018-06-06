import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import FlightCrewCategoryFilter from './FlightCrewCategoryFilter'

//inputs de nested


/*
import FlightCrewCategoryForm from './components/flightCrewCategory/FlightCrewCategoryForm'
import FlightCrewCategoryList from './components/flightCrewCategory/FlightCrewCategoryList'
import FlightCrewCategoryTable from './components/flightCrewCategory/FlightCrewCategoryTable'

<Route path="/flightCrewCategory/" component={FlightCrewCategoryList} />
<Route path="/flightCrewCategory/" component={FlightCrewCategoryTable} />
<Route path="/flightCrewCategory/new" component={FlightCrewCategoryForm} />
<Route path="/flightCrewCategory/:id" component={FlightCrewCategoryForm} />
<Route path="/flightCrewCategory/:page/:pageLength" component={FlightCrewCategoryList} />
<Route path="/flightCrewCategory/:page/:pageLength/:query" component={FlightCrewCategoryList} />
<Route path="/flightCrewCategory/:page/:pageLength" component={FlightCrewCategoryTable} />
<Route path="/flightCrewCategory/:page/:pageLength/:query" component={FlightCrewCategoryTable} />
<Route path="/flightCrewCategory/:page/:pageLength/:sortColumn/:sortOrder" component={FlightCrewCategoryTable} />
<Route path="/flightCrewCategory/:page/:pageLength/:sortColumn/:sortOrder/:query" component={FlightCrewCategoryTable} />
*/
export default class FlightCrewCategoryTable extends GTable{
    showUrl =  '/flightCrewCategory/'
    apiGetUrl =  '/flightCrewCategory/'
    apiCreateUrl = '/flightCrewCategory/save'
    apiDeleteUrl = '/flightCrewCategory/delete/'
    
    listUrl = '/flightCrewCategory/'

    objsStr = 'Flight Crew Categorys'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "category", rowName: "Category", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <FlightCrewCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      