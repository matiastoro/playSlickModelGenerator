import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/army/" component={ArmyList} />
<Route path="/army/new" component={ArmyForm} />
<Route path="/army/:id" component={ArmyForm} />
<Route path="/army/:page/:pageLength" component={ArmyList} />
*/
export default class ArmyForm extends GList{
    showUrl =  '/army/'
    apiGetUrl =  '/army/'
    apiCreateUrl = '/army/save'
    apiDeleteUrl = '/army/delete/'
    

    objsStr = 'Armys'

    renderPrimaryText = (obj) => {
        return obj.army
    }
    
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      