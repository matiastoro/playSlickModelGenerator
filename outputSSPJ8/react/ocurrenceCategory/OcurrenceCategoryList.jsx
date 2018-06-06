import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import OcurrenceCategoryFilter from './OcurrenceCategoryFilter'

//inputs de nested


/*
<Route path="/ocurrenceCategory/" component={OcurrenceCategoryList} />
<Route path="/ocurrenceCategory/new" component={OcurrenceCategoryForm} />
<Route path="/ocurrenceCategory/:id" component={OcurrenceCategoryForm} />
<Route path="/ocurrenceCategory/:page/:pageLength" component={OcurrenceCategoryList} />
<Route path="/ocurrenceCategory/:page/:pageLength/:query" component={OcurrenceCategoryList} />
<Route path="/ocurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder" component={{OcurrenceCategoryTable} />
<Route path="/ocurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{OcurrenceCategoryTable} />
*/
export default class OcurrenceCategoryList extends GList{
    showUrl =  '/ocurrenceCategory/'
    apiGetUrl =  '/ocurrenceCategory/'
    apiCreateUrl = '/ocurrenceCategory/save'
    apiDeleteUrl = '/ocurrenceCategory/delete/'
    
    listUrl = '/ocurrenceCategory/'

    objsStr = 'Ocurrence Categorys'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.ocurrenceCategory
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.code}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <OcurrenceCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      