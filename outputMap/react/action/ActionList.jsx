import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/action/" component={ActionList} />
<Route path="/action/new" component={ActionForm} />
<Route path="/action/:id" component={ActionForm} />
<Route path="/action/:page/:pageLength" component={ActionList} />
*/
export default class ActionForm extends GList{
    showUrl =  '/action/'
    apiGetUrl =  '/action/'
    apiCreateUrl = '/action/save'
    apiDeleteUrl = '/action/delete/'
    apiOptionsUrl = '/action/options'

    objsStr = 'Actions'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.userId && this.state.indexedOptions?this.state.indexedOptions.users[obj.userId].user:null} -- 
            {obj.turnId && this.state.indexedOptions?this.state.indexedOptions.turns[obj.turnId].turn:null} -- 
            {obj.characterId && this.state.indexedOptions?this.state.indexedOptions.characters[obj.characterId].character:null} -- 
            {obj.fromQ} -- 
            {obj.fromR} -- 
            {obj.fromS} -- 
            {obj.toQ} -- 
            {obj.toR} -- 
            {obj.toS}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      