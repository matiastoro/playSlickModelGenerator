import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import TypeOfAirspeedFilter from './TypeOfAirspeedFilter'

//inputs de nested


/*
<Route path="/typeOfAirspeed/" component={TypeOfAirspeedList} />
<Route path="/typeOfAirspeed/new" component={TypeOfAirspeedForm} />
<Route path="/typeOfAirspeed/:id" component={TypeOfAirspeedForm} />
<Route path="/typeOfAirspeed/:page/:pageLength" component={TypeOfAirspeedList} />
*/
export default class TypeOfAirspeedList extends GList{
    showUrl =  '/typeOfAirspeed/'
    apiGetUrl =  '/typeOfAirspeed/'
    apiCreateUrl = '/typeOfAirspeed/save'
    apiDeleteUrl = '/typeOfAirspeed/delete/'
    
    listUrl = '/typeOfAirspeed/'

    objsStr = 'Type Of Airspeeds'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.typeOfAirspeed
    }
    

    renderFilter = () => {
        return <div>
            <TypeOfAirspeedFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      