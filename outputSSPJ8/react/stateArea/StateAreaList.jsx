import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import StateAreaFilter from './StateAreaFilter'

//inputs de nested


/*
<Route path="/stateArea/" component={StateAreaList} />
<Route path="/stateArea/new" component={StateAreaForm} />
<Route path="/stateArea/:id" component={StateAreaForm} />
<Route path="/stateArea/:page/:pageLength" component={StateAreaList} />
<Route path="/stateArea/:page/:pageLength/:query" component={StateAreaList} />
<Route path="/stateArea/:page/:pageLength/:sortColumn/:sortOrder" component={{StateAreaTable} />
<Route path="/stateArea/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{StateAreaTable} />
*/
export default class StateAreaList extends GList{
    showUrl =  '/stateArea/'
    apiGetUrl =  '/stateArea/'
    apiCreateUrl = '/stateArea/save'
    apiDeleteUrl = '/stateArea/delete/'
    apiOptionsUrl = '/stateArea/options'
    listUrl = '/stateArea/'

    objsStr = 'State Areas'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.stateArea
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.stateAreaId && this.state.indexedOptions?this.state.indexedOptions.stateAreas[obj.stateAreaId].stateArea:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <StateAreaFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      