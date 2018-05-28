import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/character/" component={CharacterList} />
<Route path="/character/new" component={CharacterForm} />
<Route path="/character/:id" component={CharacterForm} />
<Route path="/character/:page/:pageLength" component={CharacterList} />
*/
export default class CharacterForm extends GList{
    showUrl =  '/character/'
    apiGetUrl =  '/character/'
    apiCreateUrl = '/character/save'
    apiDeleteUrl = '/character/delete/'
    apiOptionsUrl = '/character/options'

    objsStr = 'Characters'

    renderPrimaryText = (obj) => {
        return obj.character
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.background} -- 
            {obj.observations} -- 
            {obj.leader} -- 
            {obj.userId && this.state.indexedOptions?this.state.indexedOptions.users[obj.userId].user:null} -- 
            {obj.number} -- 
            {obj.q} -- 
            {obj.r} -- 
            {obj.s}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      