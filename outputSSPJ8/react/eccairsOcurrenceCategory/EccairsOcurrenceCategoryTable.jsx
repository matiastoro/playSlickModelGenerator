import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import EccairsOcurrenceCategoryFilter from './EccairsOcurrenceCategoryFilter'

//inputs de nested


/*
import EccairsOcurrenceCategoryForm from './components/eccairsOcurrenceCategory/EccairsOcurrenceCategoryForm'
import EccairsOcurrenceCategoryList from './components/eccairsOcurrenceCategory/EccairsOcurrenceCategoryList'
import EccairsOcurrenceCategoryTable from './components/eccairsOcurrenceCategory/EccairsOcurrenceCategoryTable'

<Route path="/eccairsOcurrenceCategory/" component={EccairsOcurrenceCategoryList} />
<Route path="/eccairsOcurrenceCategory/" component={EccairsOcurrenceCategoryTable} />
<Route path="/eccairsOcurrenceCategory/new" component={EccairsOcurrenceCategoryForm} />
<Route path="/eccairsOcurrenceCategory/:id" component={EccairsOcurrenceCategoryForm} />
<Route path="/eccairsOcurrenceCategory/:page/:pageLength" component={EccairsOcurrenceCategoryList} />
<Route path="/eccairsOcurrenceCategory/:page/:pageLength/:query" component={EccairsOcurrenceCategoryList} />
<Route path="/eccairsOcurrenceCategory/:page/:pageLength" component={EccairsOcurrenceCategoryTable} />
<Route path="/eccairsOcurrenceCategory/:page/:pageLength/:query" component={EccairsOcurrenceCategoryTable} />
<Route path="/eccairsOcurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder" component={EccairsOcurrenceCategoryTable} />
<Route path="/eccairsOcurrenceCategory/:page/:pageLength/:sortColumn/:sortOrder/:query" component={EccairsOcurrenceCategoryTable} />
*/
export default class EccairsOcurrenceCategoryTable extends GTable{
    showUrl =  '/eccairsOcurrenceCategory/'
    apiGetUrl =  '/eccairsOcurrenceCategory/'
    apiCreateUrl = '/eccairsOcurrenceCategory/save'
    apiDeleteUrl = '/eccairsOcurrenceCategory/delete/'
    apiOptionsUrl = '/eccairsOcurrenceCategory/options'
    listUrl = '/eccairsOcurrenceCategory/'

    objsStr = 'Eccairs Ocurrence Categorys'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "eccairsId", rowName: "Eccairs", label: (o) => (this.findRelatedObject("eccairss", "eccairsId", o, "eccairs")), sortable: true},
            {key: "ocurrenceCategoryId", rowName: "Ocurrence Category", label: (o) => (this.findRelatedObject("ocurrenceCategorys", "ocurrenceCategoryId", o, "ocurrenceCategory")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <EccairsOcurrenceCategoryFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      