import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/season/" component={SeasonList} />
<Route path="/season/new" component={SeasonForm} />
<Route path="/season/:id" component={SeasonForm} />
<Route path="/season/:page/:pageLength" component={SeasonList} />
*/
export default class SeasonForm extends GList{
    showUrl =  '/season/'
    apiGetUrl =  '/season/'
    apiCreateUrl = '/season/save'
    apiDeleteUrl = '/season/delete/'
    

    objsStr = 'Seasons'

    renderPrimaryText = (obj) => {
        return obj.season
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.from} -- 
            {obj.to}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      