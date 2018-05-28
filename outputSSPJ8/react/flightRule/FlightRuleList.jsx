import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import FlightRuleFilter from './FlightRuleFilter'

//inputs de nested


/*
<Route path="/flightRule/" component={FlightRuleList} />
<Route path="/flightRule/new" component={FlightRuleForm} />
<Route path="/flightRule/:id" component={FlightRuleForm} />
<Route path="/flightRule/:page/:pageLength" component={FlightRuleList} />
*/
export default class FlightRuleList extends GList{
    showUrl =  '/flightRule/'
    apiGetUrl =  '/flightRule/'
    apiCreateUrl = '/flightRule/save'
    apiDeleteUrl = '/flightRule/delete/'
    
    listUrl = '/flightRule/'

    objsStr = 'Flight Rules'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.flightRule
    }
    

    renderFilter = () => {
        return <div>
            <FlightRuleFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      