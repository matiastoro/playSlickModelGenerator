import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoAerodromeOcurrenceCategoryFilter from './NeoAerodromeOcurrenceCategoryFilter'

//inputs de nested


/*
<Route path="/neoAerodromeOcurrenceCategory/" component={NeoAerodromeOcurrenceCategoryList} />
<Route path="/neoAerodromeOcurrenceCategory/new" component={NeoAerodromeOcurrenceCategoryForm} />
<Route path="/neoAerodromeOcurrenceCategory/:id" component={NeoAerodromeOcurrenceCategoryForm} />
<Route path="/neoAerodromeOcurrenceCategory/:page/:pageLength" component={NeoAerodromeOcurrenceCategoryList} />
*/
export default class NeoAerodromeOcurrenceCategoryList extends GList{
    showUrl =  '/neoAerodromeOcurrenceCategory/'
    apiGetUrl =  '/neoAerodromeOcurrenceCategory/'
    apiCreateUrl = '/neoAerodromeOcurrenceCategory/save'
    apiDeleteUrl = '/neoAerodromeOcurrenceCategory/delete/'
    apiOptionsUrl = '/neoAerodromeOcurrenceCategory/options'
    listUrl = '/neoAerodromeOcurrenceCategory/'

    objsStr = 'Neo Aerodrome Ocurrence Categorys'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.id
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.neoAerodromeId && this.state.indexedOptions?this.state.indexedOptions.neoAerodromes[obj.neoAerodromeId].neoAerodrome:null} -- 
            {obj.ocurrenceCategoryId && this.state.indexedOptions?this.state.indexedOptions.ocurrenceCategorys[obj.ocurrenceCategoryId].ocurrenceCategory:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <NeoAerodromeOcurrenceCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      