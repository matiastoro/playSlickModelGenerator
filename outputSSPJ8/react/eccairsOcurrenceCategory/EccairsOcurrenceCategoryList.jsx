import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import EccairsOcurrenceCategoryFilter from './EccairsOcurrenceCategoryFilter'

//inputs de nested


/*
<Route path="/eccairsOcurrenceCategory/" component={EccairsOcurrenceCategoryList} />
<Route path="/eccairsOcurrenceCategory/new" component={EccairsOcurrenceCategoryForm} />
<Route path="/eccairsOcurrenceCategory/:id" component={EccairsOcurrenceCategoryForm} />
<Route path="/eccairsOcurrenceCategory/:page/:pageLength" component={EccairsOcurrenceCategoryList} />
<Route path="/eccairsOcurrenceCategory/:page/:pageLength/:query" component={EccairsOcurrenceCategoryList} />
<Route path="/eccairsOcurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder" component={{EccairsOcurrenceCategoryTable} />
<Route path="/eccairsOcurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{EccairsOcurrenceCategoryTable} />
*/
export default class EccairsOcurrenceCategoryList extends GList{
    showUrl =  '/eccairsOcurrenceCategory/'
    apiGetUrl =  '/eccairsOcurrenceCategory/'
    apiCreateUrl = '/eccairsOcurrenceCategory/save'
    apiDeleteUrl = '/eccairsOcurrenceCategory/delete/'
    apiOptionsUrl = '/eccairsOcurrenceCategory/options'
    listUrl = '/eccairsOcurrenceCategory/'

    objsStr = 'Eccairs Ocurrence Categorys'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.id
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.eccairsId && this.state.indexedOptions?this.state.indexedOptions.eccairss[obj.eccairsId].eccairs:null} -- 
            {obj.ocurrenceCategoryId && this.state.indexedOptions?this.state.indexedOptions.ocurrenceCategorys[obj.ocurrenceCategoryId].ocurrenceCategory:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <EccairsOcurrenceCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      