import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import LanguageFilter from './LanguageFilter'

//inputs de nested


/*
<Route path="/language/" component={LanguageList} />
<Route path="/language/new" component={LanguageForm} />
<Route path="/language/:id" component={LanguageForm} />
<Route path="/language/:page/:pageLength" component={LanguageList} />
*/
export default class LanguageList extends GList{
    showUrl =  '/language/'
    apiGetUrl =  '/language/'
    apiCreateUrl = '/language/save'
    apiDeleteUrl = '/language/delete/'
    
    listUrl = '/language/'

    objsStr = 'Languages'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.language
    }
    

    renderFilter = () => {
        return <div>
            <LanguageFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      