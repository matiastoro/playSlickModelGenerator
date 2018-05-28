import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AerodromeFilter from './AerodromeFilter'

//inputs de nested


/*
<Route path="/aerodrome/" component={AerodromeList} />
<Route path="/aerodrome/new" component={AerodromeForm} />
<Route path="/aerodrome/:id" component={AerodromeForm} />
<Route path="/aerodrome/:page/:pageLength" component={AerodromeList} />
*/
export default class AerodromeList extends GList{
    showUrl =  '/aerodrome/'
    apiGetUrl =  '/aerodrome/'
    apiCreateUrl = '/aerodrome/save'
    apiDeleteUrl = '/aerodrome/delete/'
    apiOptionsUrl = '/aerodrome/options'
    listUrl = '/aerodrome/'

    objsStr = 'Aerodromes'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.icaoCode
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.aerodromeStatusId && this.state.indexedOptions?this.state.indexedOptions.aerodromeStatuss[obj.aerodromeStatusId].aerodromeStatus:null} -- 
            {obj.aerodromeTypeId && this.state.indexedOptions?this.state.indexedOptions.aerodromeTypes[obj.aerodromeTypeId].aerodromeType:null} -- 
            {obj.latitude} -- 
            {obj.longitude} -- 
            {obj.elevationAboveMsl}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <AerodromeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      