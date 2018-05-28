import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/userAerodrome/" component={UserAerodromeList} />
<Route path="/userAerodrome/new" component={UserAerodromeForm} />
<Route path="/userAerodrome/:id" component={UserAerodromeForm} />
<Route path="/userAerodrome/:page/:pageLength" component={UserAerodromeList} />
*/
export default class UserAerodromeForm extends GList{
    showUrl =  '/userAerodrome/'
    apiGetUrl =  '/userAerodrome/'
    apiCreateUrl = '/userAerodrome/save'
    apiDeleteUrl = '/userAerodrome/delete/'
    apiOptionsUrl = '/userAerodrome/options'

    objsStr = 'UserAerodromes'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.userId && this.state.indexedOptions?this.state.indexedOptions.users[obj.userId].user:null} -- 
            {obj.aerodromeId && this.state.indexedOptions?this.state.indexedOptions.aerodromes[obj.aerodromeId].aerodrome:null}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      