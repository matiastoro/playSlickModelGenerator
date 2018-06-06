import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import OperatorTypeFilter from './OperatorTypeFilter'

//inputs de nested


/*
<Route path="/operatorType/" component={OperatorTypeList} />
<Route path="/operatorType/new" component={OperatorTypeForm} />
<Route path="/operatorType/:id" component={OperatorTypeForm} />
<Route path="/operatorType/:page/:pageLength" component={OperatorTypeList} />
<Route path="/operatorType/:page/:pageLength/:query" component={OperatorTypeList} />
<Route path="/operatorType/:page/:pageLength/:sortColumn/:sortOrder" component={{OperatorTypeTable} />
<Route path="/operatorType/:page/:pageLength/:sortColumn/:sortOrder/:query" component={{OperatorTypeTable} />
*/
export default class OperatorTypeList extends GList{
    showUrl =  '/operatorType/'
    apiGetUrl =  '/operatorType/'
    apiCreateUrl = '/operatorType/save'
    apiDeleteUrl = '/operatorType/delete/'
    apiOptionsUrl = '/operatorType/options'
    listUrl = '/operatorType/'

    objsStr = 'Operator Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.operatorType
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.operatorTypeId && this.state.indexedOptions?this.state.indexedOptions.operatorTypes[obj.operatorTypeId].operatorType:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <OperatorTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      