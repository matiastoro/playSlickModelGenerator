import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AerodromeTypeFilter from './AerodromeTypeFilter'

//inputs de nested


/*
<Route path="/aerodromeType/" component={AerodromeTypeList} />
<Route path="/aerodromeType/new" component={AerodromeTypeForm} />
<Route path="/aerodromeType/:id" component={AerodromeTypeForm} />
<Route path="/aerodromeType/:page/:pageLength" component={AerodromeTypeList} />
<Route path="/aerodromeType/:page/:pageLength/:query" component={AerodromeTypeList} />
<Route path="/aerodromeType/:page/:pageLength/:sortColumn/:sortOrder" component={{AerodromeTypeTable} />
<Route path="/aerodromeType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{AerodromeTypeTable} />
*/
export default class AerodromeTypeList extends GList{
    showUrl =  '/aerodromeType/'
    apiGetUrl =  '/aerodromeType/'
    apiCreateUrl = '/aerodromeType/save'
    apiDeleteUrl = '/aerodromeType/delete/'
    
    listUrl = '/aerodromeType/'

    objsStr = 'Aerodrome Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.tpe
    }
    

    renderFilter = () => {
        return <div>
            <AerodromeTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      