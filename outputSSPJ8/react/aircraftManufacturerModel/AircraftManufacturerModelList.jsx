import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AircraftManufacturerModelFilter from './AircraftManufacturerModelFilter'

//inputs de nested


/*
<Route path="/aircraftManufacturerModel/" component={AircraftManufacturerModelList} />
<Route path="/aircraftManufacturerModel/new" component={AircraftManufacturerModelForm} />
<Route path="/aircraftManufacturerModel/:id" component={AircraftManufacturerModelForm} />
<Route path="/aircraftManufacturerModel/:page/:pageLength" component={AircraftManufacturerModelList} />
*/
export default class AircraftManufacturerModelList extends GList{
    showUrl =  '/aircraftManufacturerModel/'
    apiGetUrl =  '/aircraftManufacturerModel/'
    apiCreateUrl = '/aircraftManufacturerModel/save'
    apiDeleteUrl = '/aircraftManufacturerModel/delete/'
    apiOptionsUrl = '/aircraftManufacturerModel/options'
    listUrl = '/aircraftManufacturerModel/'

    objsStr = 'Aircraft Manufacturer Models'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.manufacturerModel
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.aircraftManufacturerModelId && this.state.indexedOptions?this.state.indexedOptions.aircraftManufacturerModels[obj.aircraftManufacturerModelId].manufacturer_model:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <AircraftManufacturerModelFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      