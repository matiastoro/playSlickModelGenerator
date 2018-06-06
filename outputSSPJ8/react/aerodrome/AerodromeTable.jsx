import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AerodromeFilter from './AerodromeFilter'

//inputs de nested


/*
import AerodromeForm from './components/aerodrome/AerodromeForm'
import AerodromeList from './components/aerodrome/AerodromeList'
import AerodromeTable from './components/aerodrome/AerodromeTable'

<Route path="/aerodrome/" component={AerodromeList} />
<Route path="/aerodrome/" component={AerodromeTable} />
<Route path="/aerodrome/new" component={AerodromeForm} />
<Route path="/aerodrome/:id" component={AerodromeForm} />
<Route path="/aerodrome/:page/:pageLength" component={AerodromeList} />
<Route path="/aerodrome/:page/:pageLength/:query" component={AerodromeList} />
<Route path="/aerodrome/:page/:pageLength" component={AerodromeTable} />
<Route path="/aerodrome/:page/:pageLength/:query" component={AerodromeTable} />
<Route path="/aerodrome/:page/:pageLength/:sortColumn/:sortOrder" component={AerodromeTable} />
<Route path="/aerodrome/:page/:pageLength/:sortColumn/:sortOrder/:query" component={AerodromeTable} />
*/
export default class AerodromeTable extends GTable{
    showUrl =  '/aerodrome/'
    apiGetUrl =  '/aerodrome/'
    apiCreateUrl = '/aerodrome/save'
    apiDeleteUrl = '/aerodrome/delete/'
    apiOptionsUrl = '/aerodrome/options'
    listUrl = '/aerodrome/'

    objsStr = 'Aerodromes'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "icaoCode", rowName: "IcaoCode", sortable: true},
            {key: "aerodromeStatusId", rowName: "Aerodrome Status", label: (o) => (this.findRelatedObject("aerodromeStatuss", "aerodromeStatusId", o, "status")), sortable: true},
            {key: "aerodromeTypeId", rowName: "Aerodrome Type", label: (o) => (this.findRelatedObject("aerodromeTypes", "aerodromeTypeId", o, "tpe")), sortable: true},
            {key: "latitude", rowName: "Latitude", sortable: true},
            {key: "longitude", rowName: "Longitude", sortable: true},
            {key: "elevationAboveMsl", rowName: "Elevation Above Msl", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <AerodromeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      