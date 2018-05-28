import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/profile/" component={ProfileList} />
<Route path="/profile/new" component={ProfileForm} />
<Route path="/profile/:id" component={ProfileForm} />
<Route path="/profile/:page/:pageLength" component={ProfileList} />
*/
export default class ProfileForm extends GList{
    showUrl =  '/profile/'
    apiGetUrl =  '/profile/'
    apiCreateUrl = '/profile/save'
    apiDeleteUrl = '/profile/delete/'
    

    objsStr = 'Profiles'

    renderPrimaryText = (obj) => {
        return obj.profile
    }
    
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      