import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AerodromeLocationFilter from './AerodromeLocationFilter'

//inputs de nested


/*
<Route path="/aerodromeLocation/" component={AerodromeLocationList} />
<Route path="/aerodromeLocation/new" component={AerodromeLocationForm} />
<Route path="/aerodromeLocation/:id" component={AerodromeLocationForm} />
<Route path="/aerodromeLocation/:page/:pageLength" component={AerodromeLocationList} />
<Route path="/aerodromeLocation/:page/:pageLength/:query" component={AerodromeLocationList} />
<Route path="/aerodromeLocation/:page/:pageLength/:sortColumn/:sortOrder" component={{AerodromeLocationTable} />
<Route path="/aerodromeLocation/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{AerodromeLocationTable} />
*/
export default class AerodromeLocationList extends GList{
    showUrl =  '/aerodromeLocation/'
    apiGetUrl =  '/aerodromeLocation/'
    apiCreateUrl = '/aerodromeLocation/save'
    apiDeleteUrl = '/aerodromeLocation/delete/'
    
    listUrl = '/aerodromeLocation/'

    objsStr = 'Aerodrome Locations'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.location
    }
    

    renderFilter = () => {
        return <div>
            <AerodromeLocationFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      