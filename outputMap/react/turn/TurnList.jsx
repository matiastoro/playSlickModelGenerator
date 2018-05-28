import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/turn/" component={TurnList} />
<Route path="/turn/new" component={TurnForm} />
<Route path="/turn/:id" component={TurnForm} />
<Route path="/turn/:page/:pageLength" component={TurnList} />
*/
export default class TurnForm extends GList{
    showUrl =  '/turn/'
    apiGetUrl =  '/turn/'
    apiCreateUrl = '/turn/save'
    apiDeleteUrl = '/turn/delete/'
    

    objsStr = 'Turns'

    renderPrimaryText = (obj) => {
        return obj.turn
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.observations}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      