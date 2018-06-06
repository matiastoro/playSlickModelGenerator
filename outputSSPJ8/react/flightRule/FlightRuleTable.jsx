import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import FlightRuleFilter from './FlightRuleFilter'

//inputs de nested


/*
import FlightRuleForm from './components/flightRule/FlightRuleForm'
import FlightRuleList from './components/flightRule/FlightRuleList'
import FlightRuleTable from './components/flightRule/FlightRuleTable'

<Route path="/flightRule/" component={FlightRuleList} />
<Route path="/flightRule/" component={FlightRuleTable} />
<Route path="/flightRule/new" component={FlightRuleForm} />
<Route path="/flightRule/:id" component={FlightRuleForm} />
<Route path="/flightRule/:page/:pageLength" component={FlightRuleList} />
<Route path="/flightRule/:page/:pageLength/:query" component={FlightRuleList} />
<Route path="/flightRule/:page/:pageLength" component={FlightRuleTable} />
<Route path="/flightRule/:page/:pageLength/:query" component={FlightRuleTable} />
<Route path="/flightRule/:page/:pageLength/:sortColumn/:sortOrder" component={FlightRuleTable} />
<Route path="/flightRule/:page/:pageLength/:sortColumn/:sortOrder/:query" component={FlightRuleTable} />
*/
export default class FlightRuleTable extends GTable{
    showUrl =  '/flightRule/'
    apiGetUrl =  '/flightRule/'
    apiCreateUrl = '/flightRule/save'
    apiDeleteUrl = '/flightRule/delete/'
    
    listUrl = '/flightRule/'

    objsStr = 'Flight Rules'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "flightRule", rowName: "Flight Rule", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <FlightRuleFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      