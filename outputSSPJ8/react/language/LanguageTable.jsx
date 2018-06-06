import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import LanguageFilter from './LanguageFilter'

//inputs de nested


/*
import LanguageForm from './components/language/LanguageForm'
import LanguageList from './components/language/LanguageList'
import LanguageTable from './components/language/LanguageTable'

<Route path="/language/" component={LanguageList} />
<Route path="/language/" component={LanguageTable} />
<Route path="/language/new" component={LanguageForm} />
<Route path="/language/:id" component={LanguageForm} />
<Route path="/language/:page/:pageLength" component={LanguageList} />
<Route path="/language/:page/:pageLength/:query" component={LanguageList} />
<Route path="/language/:page/:pageLength" component={LanguageTable} />
<Route path="/language/:page/:pageLength/:query" component={LanguageTable} />
<Route path="/language/:page/:pageLength/:sortColumn/:sortOrder" component={LanguageTable} />
<Route path="/language/:page/:pageLength/:sortColumn/:sortOrder/:query" component={LanguageTable} />
*/
export default class LanguageTable extends GTable{
    showUrl =  '/language/'
    apiGetUrl =  '/language/'
    apiCreateUrl = '/language/save'
    apiDeleteUrl = '/language/delete/'
    
    listUrl = '/language/'

    objsStr = 'Languages'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "language", rowName: "Language", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <LanguageFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      