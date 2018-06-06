import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import StateAreaFilter from './StateAreaFilter'

//inputs de nested


/*
import StateAreaForm from './components/stateArea/StateAreaForm'
import StateAreaList from './components/stateArea/StateAreaList'
import StateAreaTable from './components/stateArea/StateAreaTable'

<Route path="/stateArea/" component={StateAreaList} />
<Route path="/stateArea/" component={StateAreaTable} />
<Route path="/stateArea/new" component={StateAreaForm} />
<Route path="/stateArea/:id" component={StateAreaForm} />
<Route path="/stateArea/:page/:pageLength" component={StateAreaList} />
<Route path="/stateArea/:page/:pageLength/:query" component={StateAreaList} />
<Route path="/stateArea/:page/:pageLength" component={StateAreaTable} />
<Route path="/stateArea/:page/:pageLength/:query" component={StateAreaTable} />
<Route path="/stateArea/:page/:pageLength/:sortColumn/:sortOrder" component={StateAreaTable} />
<Route path="/stateArea/:page/:pageLength/:sortColumn/:sortOrder/:query" component={StateAreaTable} />
*/
export default class StateAreaTable extends GTable{
    showUrl =  '/stateArea/'
    apiGetUrl =  '/stateArea/'
    apiCreateUrl = '/stateArea/save'
    apiDeleteUrl = '/stateArea/delete/'
    apiOptionsUrl = '/stateArea/options'
    listUrl = '/stateArea/'

    objsStr = 'State Areas'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "stateArea", rowName: "State Area", sortable: true},
            {key: "stateAreaId", rowName: "State Area", label: (o) => (this.findRelatedObject("stateAreas", "stateAreaId", o, "stateArea")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <StateAreaFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      