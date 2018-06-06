import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AerodromeTypeFilter from './AerodromeTypeFilter'

//inputs de nested


/*
import AerodromeTypeForm from './components/aerodromeType/AerodromeTypeForm'
import AerodromeTypeList from './components/aerodromeType/AerodromeTypeList'
import AerodromeTypeTable from './components/aerodromeType/AerodromeTypeTable'

<Route path="/aerodromeType/" component={AerodromeTypeList} />
<Route path="/aerodromeType/" component={AerodromeTypeTable} />
<Route path="/aerodromeType/new" component={AerodromeTypeForm} />
<Route path="/aerodromeType/:id" component={AerodromeTypeForm} />
<Route path="/aerodromeType/:page/:pageLength" component={AerodromeTypeList} />
<Route path="/aerodromeType/:page/:pageLength/:query" component={AerodromeTypeList} />
<Route path="/aerodromeType/:page/:pageLength" component={AerodromeTypeTable} />
<Route path="/aerodromeType/:page/:pageLength/:query" component={AerodromeTypeTable} />
<Route path="/aerodromeType/:page/:pageLength/:sortColumn/:sortOrder" component={AerodromeTypeTable} />
<Route path="/aerodromeType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={AerodromeTypeTable} />
*/
export default class AerodromeTypeTable extends GTable{
    showUrl =  '/aerodromeType/'
    apiGetUrl =  '/aerodromeType/'
    apiCreateUrl = '/aerodromeType/save'
    apiDeleteUrl = '/aerodromeType/delete/'
    
    listUrl = '/aerodromeType/'

    objsStr = 'Aerodrome Types'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "tpe", rowName: "Tpe", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <AerodromeTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      