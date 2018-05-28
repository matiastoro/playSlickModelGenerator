import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/aerodrome/" component={AerodromeList} />
<Route path="/aerodrome/new" component={AerodromeForm} />
<Route path="/aerodrome/:id" component={AerodromeForm} />
<Route path="/aerodrome/:page/:pageLength" component={AerodromeList} />
*/
export default class AerodromeForm extends GList{
    showUrl =  '/aerodrome/'
    apiGetUrl =  '/aerodrome/'
    apiCreateUrl = '/aerodrome/save'
    apiDeleteUrl = '/aerodrome/delete/'
    

    objsStr = 'Aerodromes'

    renderPrimaryText = (obj) => {
        return obj.icaoCode
    }
    
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      