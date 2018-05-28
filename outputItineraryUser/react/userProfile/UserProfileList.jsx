import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/userProfile/" component={UserProfileList} />
<Route path="/userProfile/new" component={UserProfileForm} />
<Route path="/userProfile/:id" component={UserProfileForm} />
<Route path="/userProfile/:page/:pageLength" component={UserProfileList} />
*/
export default class UserProfileForm extends GList{
    showUrl =  '/userProfile/'
    apiGetUrl =  '/userProfile/'
    apiCreateUrl = '/userProfile/save'
    apiDeleteUrl = '/userProfile/delete/'
    apiOptionsUrl = '/userProfile/options'

    objsStr = 'UserProfiles'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.profileId && this.state.indexedOptions?this.state.indexedOptions.profiles[obj.profileId].profile:null} -- 
            {obj.userId && this.state.indexedOptions?this.state.indexedOptions.users[obj.userId].user:null}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      