import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/user/" component={UserList} />
<Route path="/user/new" component={UserForm} />
<Route path="/user/:id" component={UserForm} />
<Route path="/user/:page/:pageLength" component={UserList} />
*/
export default class UserForm extends GList{
    showUrl =  '/user/'
    apiGetUrl =  '/user/'
    apiCreateUrl = '/user/save'
    apiDeleteUrl = '/user/delete/'
    

    objsStr = 'Users'

    renderPrimaryText = (obj) => {
        return obj.name
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.username} -- 
            {obj.password} -- 
            {obj.email}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      