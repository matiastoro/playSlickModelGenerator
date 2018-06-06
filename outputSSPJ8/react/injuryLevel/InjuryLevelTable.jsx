import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import InjuryLevelFilter from './InjuryLevelFilter'

//inputs de nested


/*
import InjuryLevelForm from './components/injuryLevel/InjuryLevelForm'
import InjuryLevelList from './components/injuryLevel/InjuryLevelList'
import InjuryLevelTable from './components/injuryLevel/InjuryLevelTable'

<Route path="/injuryLevel/" component={InjuryLevelList} />
<Route path="/injuryLevel/" component={InjuryLevelTable} />
<Route path="/injuryLevel/new" component={InjuryLevelForm} />
<Route path="/injuryLevel/:id" component={InjuryLevelForm} />
<Route path="/injuryLevel/:page/:pageLength" component={InjuryLevelList} />
<Route path="/injuryLevel/:page/:pageLength/:query" component={InjuryLevelList} />
<Route path="/injuryLevel/:page/:pageLength" component={InjuryLevelTable} />
<Route path="/injuryLevel/:page/:pageLength/:query" component={InjuryLevelTable} />
<Route path="/injuryLevel/:page/:pageLength/:sortColumn/:sortOrder" component={InjuryLevelTable} />
<Route path="/injuryLevel/:page/:pageLength/:sortColumn/:sortOrder/:query" component={InjuryLevelTable} />
*/
export default class InjuryLevelTable extends GTable{
    showUrl =  '/injuryLevel/'
    apiGetUrl =  '/injuryLevel/'
    apiCreateUrl = '/injuryLevel/save'
    apiDeleteUrl = '/injuryLevel/delete/'
    
    listUrl = '/injuryLevel/'

    objsStr = 'Injury Levels'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "injuryLevel", rowName: "Injury Level", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <InjuryLevelFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      