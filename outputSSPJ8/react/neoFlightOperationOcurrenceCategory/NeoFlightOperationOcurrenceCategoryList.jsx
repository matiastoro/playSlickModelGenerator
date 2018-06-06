import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoFlightOperationOcurrenceCategoryFilter from './NeoFlightOperationOcurrenceCategoryFilter'

//inputs de nested


/*
<Route path="/neoFlightOperationOcurrenceCategory/" component={NeoFlightOperationOcurrenceCategoryList} />
<Route path="/neoFlightOperationOcurrenceCategory/new" component={NeoFlightOperationOcurrenceCategoryForm} />
<Route path="/neoFlightOperationOcurrenceCategory/:id" component={NeoFlightOperationOcurrenceCategoryForm} />
<Route path="/neoFlightOperationOcurrenceCategory/:page/:pageLength" component={NeoFlightOperationOcurrenceCategoryList} />
<Route path="/neoFlightOperationOcurrenceCategory/:page/:pageLength/:query" component={NeoFlightOperationOcurrenceCategoryList} />
<Route path="/neoFlightOperationOcurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder" component={{NeoFlightOperationOcurrenceCategoryTable} />
<Route path="/neoFlightOperationOcurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{NeoFlightOperationOcurrenceCategoryTable} />
*/
export default class NeoFlightOperationOcurrenceCategoryList extends GList{
    showUrl =  '/neoFlightOperationOcurrenceCategory/'
    apiGetUrl =  '/neoFlightOperationOcurrenceCategory/'
    apiCreateUrl = '/neoFlightOperationOcurrenceCategory/save'
    apiDeleteUrl = '/neoFlightOperationOcurrenceCategory/delete/'
    apiOptionsUrl = '/neoFlightOperationOcurrenceCategory/options'
    listUrl = '/neoFlightOperationOcurrenceCategory/'

    objsStr = 'Neo Flight Operation Ocurrence Categorys'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.id
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.neoFlightOperationId && this.state.indexedOptions?this.state.indexedOptions.neoFlightOperations[obj.neoFlightOperationId].neoFlightOperation:null} -- 
            {obj.ocurrenceCategoryId && this.state.indexedOptions?this.state.indexedOptions.ocurrenceCategorys[obj.ocurrenceCategoryId].ocurrenceCategory:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <NeoFlightOperationOcurrenceCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      