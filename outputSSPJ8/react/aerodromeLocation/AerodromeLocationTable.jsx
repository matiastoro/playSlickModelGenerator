import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AerodromeLocationFilter from './AerodromeLocationFilter'

//inputs de nested


/*
import AerodromeLocationForm from './components/aerodromeLocation/AerodromeLocationForm'
import AerodromeLocationList from './components/aerodromeLocation/AerodromeLocationList'
import AerodromeLocationTable from './components/aerodromeLocation/AerodromeLocationTable'

<Route path="/aerodromeLocation/" component={AerodromeLocationList} />
<Route path="/aerodromeLocation/" component={AerodromeLocationTable} />
<Route path="/aerodromeLocation/new" component={AerodromeLocationForm} />
<Route path="/aerodromeLocation/:id" component={AerodromeLocationForm} />
<Route path="/aerodromeLocation/:page/:pageLength" component={AerodromeLocationList} />
<Route path="/aerodromeLocation/:page/:pageLength/:query" component={AerodromeLocationList} />
<Route path="/aerodromeLocation/:page/:pageLength" component={AerodromeLocationTable} />
<Route path="/aerodromeLocation/:page/:pageLength/:query" component={AerodromeLocationTable} />
<Route path="/aerodromeLocation/:page/:pageLength/:sortColumn/:sortOrder" component={AerodromeLocationTable} />
<Route path="/aerodromeLocation/:page/:pageLength/:sortColumn/:sortOrder/:query" component={AerodromeLocationTable} />
*/
export default class AerodromeLocationTable extends GTable{
    showUrl =  '/aerodromeLocation/'
    apiGetUrl =  '/aerodromeLocation/'
    apiCreateUrl = '/aerodromeLocation/save'
    apiDeleteUrl = '/aerodromeLocation/delete/'
    
    listUrl = '/aerodromeLocation/'

    objsStr = 'Aerodrome Locations'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "location", rowName: "Location", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <AerodromeLocationFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      