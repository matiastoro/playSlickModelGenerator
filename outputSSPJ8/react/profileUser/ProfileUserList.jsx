import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/profileUser/" component={ProfileUserList} />
<Route path="/profileUser/new" component={ProfileUserForm} />
<Route path="/profileUser/:id" component={ProfileUserForm} />
<Route path="/profileUser/:page/:pageLength" component={ProfileUserList} />
*/
export default class ProfileUserForm extends GList{
    showUrl =  '/profileUser/'
    apiGetUrl =  '/profileUser/'
    apiCreateUrl = '/profileUser/save'
    apiDeleteUrl = '/profileUser/delete/'
    apiOptionsUrl = '/profileUser/options'

    objsStr = 'ProfileUsers'

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
      