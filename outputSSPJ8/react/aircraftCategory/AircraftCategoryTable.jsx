import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AircraftCategoryFilter from './AircraftCategoryFilter'

//inputs de nested


/*
import AircraftCategoryForm from './components/aircraftCategory/AircraftCategoryForm'
import AircraftCategoryList from './components/aircraftCategory/AircraftCategoryList'
import AircraftCategoryTable from './components/aircraftCategory/AircraftCategoryTable'

<Route path="/aircraftCategory/" component={AircraftCategoryList} />
<Route path="/aircraftCategory/" component={AircraftCategoryTable} />
<Route path="/aircraftCategory/new" component={AircraftCategoryForm} />
<Route path="/aircraftCategory/:id" component={AircraftCategoryForm} />
<Route path="/aircraftCategory/:page/:pageLength" component={AircraftCategoryList} />
<Route path="/aircraftCategory/:page/:pageLength/:query" component={AircraftCategoryList} />
<Route path="/aircraftCategory/:page/:pageLength" component={AircraftCategoryTable} />
<Route path="/aircraftCategory/:page/:pageLength/:query" component={AircraftCategoryTable} />
<Route path="/aircraftCategory/:page/:pageLength/:sortColumn/:sortOrder" component={AircraftCategoryTable} />
<Route path="/aircraftCategory/:page/:pageLength/:sortColumn/:sortOrder/:query" component={AircraftCategoryTable} />
*/
export default class AircraftCategoryTable extends GTable{
    showUrl =  '/aircraftCategory/'
    apiGetUrl =  '/aircraftCategory/'
    apiCreateUrl = '/aircraftCategory/save'
    apiDeleteUrl = '/aircraftCategory/delete/'
    apiOptionsUrl = '/aircraftCategory/options'
    listUrl = '/aircraftCategory/'

    objsStr = 'Aircraft Categorys'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "category", rowName: "Category", sortable: true},
            {key: "aircraftCategoryId", rowName: "Aircraft Category", label: (o) => (this.findRelatedObject("aircraftCategorys", "aircraftCategoryId", o, "category")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <AircraftCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      