import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import TypeOfAirspeedFilter from './TypeOfAirspeedFilter'

//inputs de nested


/*
import TypeOfAirspeedForm from './components/typeOfAirspeed/TypeOfAirspeedForm'
import TypeOfAirspeedList from './components/typeOfAirspeed/TypeOfAirspeedList'
import TypeOfAirspeedTable from './components/typeOfAirspeed/TypeOfAirspeedTable'

<Route path="/typeOfAirspeed/" component={TypeOfAirspeedList} />
<Route path="/typeOfAirspeed/" component={TypeOfAirspeedTable} />
<Route path="/typeOfAirspeed/new" component={TypeOfAirspeedForm} />
<Route path="/typeOfAirspeed/:id" component={TypeOfAirspeedForm} />
<Route path="/typeOfAirspeed/:page/:pageLength" component={TypeOfAirspeedList} />
<Route path="/typeOfAirspeed/:page/:pageLength/:query" component={TypeOfAirspeedList} />
<Route path="/typeOfAirspeed/:page/:pageLength" component={TypeOfAirspeedTable} />
<Route path="/typeOfAirspeed/:page/:pageLength/:query" component={TypeOfAirspeedTable} />
<Route path="/typeOfAirspeed/:page/:pageLength/:sortColumn/:sortOrder" component={TypeOfAirspeedTable} />
<Route path="/typeOfAirspeed/:page/:pageLength/:sortColumn/:sortOrder/:query" component={TypeOfAirspeedTable} />
*/
export default class TypeOfAirspeedTable extends GTable{
    showUrl =  '/typeOfAirspeed/'
    apiGetUrl =  '/typeOfAirspeed/'
    apiCreateUrl = '/typeOfAirspeed/save'
    apiDeleteUrl = '/typeOfAirspeed/delete/'
    
    listUrl = '/typeOfAirspeed/'

    objsStr = 'Type Of Airspeeds'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "typeOfAirspeed", rowName: "Type Of Airspeed", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <TypeOfAirspeedFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      