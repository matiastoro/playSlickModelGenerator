import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AircraftMassGroupFilter from './AircraftMassGroupFilter'

//inputs de nested


/*
import AircraftMassGroupForm from './components/aircraftMassGroup/AircraftMassGroupForm'
import AircraftMassGroupList from './components/aircraftMassGroup/AircraftMassGroupList'
import AircraftMassGroupTable from './components/aircraftMassGroup/AircraftMassGroupTable'

<Route path="/aircraftMassGroup/" component={AircraftMassGroupList} />
<Route path="/aircraftMassGroup/" component={AircraftMassGroupTable} />
<Route path="/aircraftMassGroup/new" component={AircraftMassGroupForm} />
<Route path="/aircraftMassGroup/:id" component={AircraftMassGroupForm} />
<Route path="/aircraftMassGroup/:page/:pageLength" component={AircraftMassGroupList} />
<Route path="/aircraftMassGroup/:page/:pageLength/:query" component={AircraftMassGroupList} />
<Route path="/aircraftMassGroup/:page/:pageLength" component={AircraftMassGroupTable} />
<Route path="/aircraftMassGroup/:page/:pageLength/:query" component={AircraftMassGroupTable} />
<Route path="/aircraftMassGroup/:page/:pageLength/:sortColumn/:sortOrder" component={AircraftMassGroupTable} />
<Route path="/aircraftMassGroup/:page/:pageLength/:sortColumn/:sortOrder/:query" component={AircraftMassGroupTable} />
*/
export default class AircraftMassGroupTable extends GTable{
    showUrl =  '/aircraftMassGroup/'
    apiGetUrl =  '/aircraftMassGroup/'
    apiCreateUrl = '/aircraftMassGroup/save'
    apiDeleteUrl = '/aircraftMassGroup/delete/'
    
    listUrl = '/aircraftMassGroup/'

    objsStr = 'Aircraft Mass Groups'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "massGroup", rowName: "Mass Group", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <AircraftMassGroupFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      