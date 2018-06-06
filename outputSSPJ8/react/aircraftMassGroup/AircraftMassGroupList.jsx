import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import AircraftMassGroupFilter from './AircraftMassGroupFilter'

//inputs de nested


/*
<Route path="/aircraftMassGroup/" component={AircraftMassGroupList} />
<Route path="/aircraftMassGroup/new" component={AircraftMassGroupForm} />
<Route path="/aircraftMassGroup/:id" component={AircraftMassGroupForm} />
<Route path="/aircraftMassGroup/:page/:pageLength" component={AircraftMassGroupList} />
<Route path="/aircraftMassGroup/:page/:pageLength/:query" component={AircraftMassGroupList} />
<Route path="/aircraftMassGroup/:page/:pageLength/:sortColumn/:sortOrder" component={{AircraftMassGroupTable} />
<Route path="/aircraftMassGroup/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{AircraftMassGroupTable} />
*/
export default class AircraftMassGroupList extends GList{
    showUrl =  '/aircraftMassGroup/'
    apiGetUrl =  '/aircraftMassGroup/'
    apiCreateUrl = '/aircraftMassGroup/save'
    apiDeleteUrl = '/aircraftMassGroup/delete/'
    
    listUrl = '/aircraftMassGroup/'

    objsStr = 'Aircraft Mass Groups'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.massGroup
    }
    

    renderFilter = () => {
        return <div>
            <AircraftMassGroupFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      