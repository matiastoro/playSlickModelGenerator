import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import NeoAerodromeFilter from './NeoAerodromeFilter'

//inputs de nested


/*
<Route path="/neoAerodrome/" component={NeoAerodromeList} />
<Route path="/neoAerodrome/new" component={NeoAerodromeForm} />
<Route path="/neoAerodrome/:id" component={NeoAerodromeForm} />
<Route path="/neoAerodrome/:page/:pageLength" component={NeoAerodromeList} />
*/
export default class NeoAerodromeList extends GList{
    showUrl =  '/neoAerodrome/'
    apiGetUrl =  '/neoAerodrome/'
    apiCreateUrl = '/neoAerodrome/save'
    apiDeleteUrl = '/neoAerodrome/delete/'
    apiOptionsUrl = '/neoAerodrome/options'
    listUrl = '/neoAerodrome/'

    objsStr = 'Neo Aerodromes'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.reportingEntityName
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.reportingEntityId && this.state.indexedOptions?this.state.indexedOptions.reportingEntitys[obj.reportingEntityId].reportingEntity:null} -- 
            {obj.attachments}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <NeoAerodromeFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      