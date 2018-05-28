import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import OperatorFilter from './OperatorFilter'

//inputs de nested


/*
<Route path="/operator/" component={OperatorList} />
<Route path="/operator/new" component={OperatorForm} />
<Route path="/operator/:id" component={OperatorForm} />
<Route path="/operator/:page/:pageLength" component={OperatorList} />
*/
export default class OperatorList extends GList{
    showUrl =  '/operator/'
    apiGetUrl =  '/operator/'
    apiCreateUrl = '/operator/save'
    apiDeleteUrl = '/operator/delete/'
    apiOptionsUrl = '/operator/options'
    listUrl = '/operator/'

    objsStr = 'Operators'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.operator
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.operatorTypeId && this.state.indexedOptions?this.state.indexedOptions.operatorTypes[obj.operatorTypeId].operatorType:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <OperatorFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      