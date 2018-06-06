import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AircraftWakeTurbCategoryFilter from './AircraftWakeTurbCategoryFilter'

//inputs de nested


/*
import AircraftWakeTurbCategoryForm from './components/aircraftWakeTurbCategory/AircraftWakeTurbCategoryForm'
import AircraftWakeTurbCategoryList from './components/aircraftWakeTurbCategory/AircraftWakeTurbCategoryList'
import AircraftWakeTurbCategoryTable from './components/aircraftWakeTurbCategory/AircraftWakeTurbCategoryTable'

<Route path="/aircraftWakeTurbCategory/" component={AircraftWakeTurbCategoryList} />
<Route path="/aircraftWakeTurbCategory/" component={AircraftWakeTurbCategoryTable} />
<Route path="/aircraftWakeTurbCategory/new" component={AircraftWakeTurbCategoryForm} />
<Route path="/aircraftWakeTurbCategory/:id" component={AircraftWakeTurbCategoryForm} />
<Route path="/aircraftWakeTurbCategory/:page/:pageLength" component={AircraftWakeTurbCategoryList} />
<Route path="/aircraftWakeTurbCategory/:page/:pageLength/:query" component={AircraftWakeTurbCategoryList} />
<Route path="/aircraftWakeTurbCategory/:page/:pageLength" component={AircraftWakeTurbCategoryTable} />
<Route path="/aircraftWakeTurbCategory/:page/:pageLength/:query" component={AircraftWakeTurbCategoryTable} />
<Route path="/aircraftWakeTurbCategory/:page/:pageLength/:sortColumn/:sortOrder" component={AircraftWakeTurbCategoryTable} />
<Route path="/aircraftWakeTurbCategory/:page/:pageLength/:sortColumn/:sortOrder/:query" component={AircraftWakeTurbCategoryTable} />
*/
export default class AircraftWakeTurbCategoryTable extends GTable{
    showUrl =  '/aircraftWakeTurbCategory/'
    apiGetUrl =  '/aircraftWakeTurbCategory/'
    apiCreateUrl = '/aircraftWakeTurbCategory/save'
    apiDeleteUrl = '/aircraftWakeTurbCategory/delete/'
    
    listUrl = '/aircraftWakeTurbCategory/'

    objsStr = 'Aircraft Wake Turb Categorys'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "wakeTurbCategory", rowName: "Wake Turb Category", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <AircraftWakeTurbCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      