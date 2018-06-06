import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AircraftManufacturerModelFilter from './AircraftManufacturerModelFilter'

//inputs de nested


/*
import AircraftManufacturerModelForm from './components/aircraftManufacturerModel/AircraftManufacturerModelForm'
import AircraftManufacturerModelList from './components/aircraftManufacturerModel/AircraftManufacturerModelList'
import AircraftManufacturerModelTable from './components/aircraftManufacturerModel/AircraftManufacturerModelTable'

<Route path="/aircraftManufacturerModel/" component={AircraftManufacturerModelList} />
<Route path="/aircraftManufacturerModel/" component={AircraftManufacturerModelTable} />
<Route path="/aircraftManufacturerModel/new" component={AircraftManufacturerModelForm} />
<Route path="/aircraftManufacturerModel/:id" component={AircraftManufacturerModelForm} />
<Route path="/aircraftManufacturerModel/:page/:pageLength" component={AircraftManufacturerModelList} />
<Route path="/aircraftManufacturerModel/:page/:pageLength/:query" component={AircraftManufacturerModelList} />
<Route path="/aircraftManufacturerModel/:page/:pageLength" component={AircraftManufacturerModelTable} />
<Route path="/aircraftManufacturerModel/:page/:pageLength/:query" component={AircraftManufacturerModelTable} />
<Route path="/aircraftManufacturerModel/:page/:pageLength/:sortColumn/:sortOrder" component={AircraftManufacturerModelTable} />
<Route path="/aircraftManufacturerModel/:page/:pageLength/:sortColumn/:sortOrder/:query" component={AircraftManufacturerModelTable} />
*/
export default class AircraftManufacturerModelTable extends GTable{
    showUrl =  '/aircraftManufacturerModel/'
    apiGetUrl =  '/aircraftManufacturerModel/'
    apiCreateUrl = '/aircraftManufacturerModel/save'
    apiDeleteUrl = '/aircraftManufacturerModel/delete/'
    apiOptionsUrl = '/aircraftManufacturerModel/options'
    listUrl = '/aircraftManufacturerModel/'

    objsStr = 'Aircraft Manufacturer Models'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "manufacturerModel", rowName: "Manufacturer Model", sortable: true},
            {key: "aircraftManufacturerModelId", rowName: "Aircraft Manufacturer Model", label: (o) => (this.findRelatedObject("aircraftManufacturerModels", "aircraftManufacturerModelId", o, "manufacturerModel")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <AircraftManufacturerModelFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      