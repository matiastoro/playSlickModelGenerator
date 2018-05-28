import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import InstrumentApprTypeFilter from './InstrumentApprTypeFilter'

//inputs de nested


/*
<Route path="/instrumentApprType/" component={InstrumentApprTypeList} />
<Route path="/instrumentApprType/new" component={InstrumentApprTypeForm} />
<Route path="/instrumentApprType/:id" component={InstrumentApprTypeForm} />
<Route path="/instrumentApprType/:page/:pageLength" component={InstrumentApprTypeList} />
*/
export default class InstrumentApprTypeList extends GList{
    showUrl =  '/instrumentApprType/'
    apiGetUrl =  '/instrumentApprType/'
    apiCreateUrl = '/instrumentApprType/save'
    apiDeleteUrl = '/instrumentApprType/delete/'
    
    listUrl = '/instrumentApprType/'

    objsStr = 'Instrument Appr Types'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.instrumentApprType
    }
    

    renderFilter = () => {
        return <div>
            <InstrumentApprTypeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      