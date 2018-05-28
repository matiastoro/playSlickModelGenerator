import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import OcurrenceClassFilter from './OcurrenceClassFilter'

//inputs de nested


/*
<Route path="/ocurrenceClass/" component={OcurrenceClassList} />
<Route path="/ocurrenceClass/new" component={OcurrenceClassForm} />
<Route path="/ocurrenceClass/:id" component={OcurrenceClassForm} />
<Route path="/ocurrenceClass/:page/:pageLength" component={OcurrenceClassList} />
*/
export default class OcurrenceClassList extends GList{
    showUrl =  '/ocurrenceClass/'
    apiGetUrl =  '/ocurrenceClass/'
    apiCreateUrl = '/ocurrenceClass/save'
    apiDeleteUrl = '/ocurrenceClass/delete/'
    
    listUrl = '/ocurrenceClass/'

    objsStr = 'Ocurrence Classs'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.ocurrenceClass
    }
    

    renderFilter = () => {
        return <div>
            <OcurrenceClassFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      