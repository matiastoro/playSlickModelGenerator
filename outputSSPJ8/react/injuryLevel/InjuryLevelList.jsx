import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import InjuryLevelFilter from './InjuryLevelFilter'

//inputs de nested


/*
<Route path="/injuryLevel/" component={InjuryLevelList} />
<Route path="/injuryLevel/new" component={InjuryLevelForm} />
<Route path="/injuryLevel/:id" component={InjuryLevelForm} />
<Route path="/injuryLevel/:page/:pageLength" component={InjuryLevelList} />
*/
export default class InjuryLevelList extends GList{
    showUrl =  '/injuryLevel/'
    apiGetUrl =  '/injuryLevel/'
    apiCreateUrl = '/injuryLevel/save'
    apiDeleteUrl = '/injuryLevel/delete/'
    
    listUrl = '/injuryLevel/'

    objsStr = 'Injury Levels'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.injuryLevel
    }
    

    renderFilter = () => {
        return <div>
            <InjuryLevelFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      