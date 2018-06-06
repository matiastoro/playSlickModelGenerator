import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import OperationTypeFilter from './OperationTypeFilter'

//inputs de nested


/*
<Route path="/operationType/" component={OperationTypeList} />
<Route path="/operationType/new" component={OperationTypeForm} />
<Route path="/operationType/:id" component={OperationTypeForm} />
<Route path="/operationType/:page/:pageLength" component={OperationTypeList} />
<Route path="/operationType/:page/:pageLength/:query" component={OperationTypeList} />
<Route path="/operationType/:page/:pageLength/:sortColumn/:sortOrder" component={{OperationTypeTable} />
<Route path="/operationType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{OperationTypeTable} />
*/
export default class OperationTypeList extends GList{
    showUrl =  '/operationType/'
    apiGetUrl =  '/operationType/'
    apiCreateUrl = '/operationType/save'
    apiDeleteUrl = '/operationType/delete/'
    apiOptionsUrl = '/operationType/options'
    listUrl = '/operationType/'

    objsStr = 'Operation Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.operationType
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.operationTypeId && this.state.indexedOptions?this.state.indexedOptions.operationTypes[obj.operationTypeId].operationType:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <OperationTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      