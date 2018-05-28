import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/hex/" component={HexList} />
<Route path="/hex/new" component={HexForm} />
<Route path="/hex/:id" component={HexForm} />
<Route path="/hex/:page/:pageLength" component={HexList} />
*/
export default class HexForm extends GList{
    showUrl =  '/hex/'
    apiGetUrl =  '/hex/'
    apiCreateUrl = '/hex/save'
    apiDeleteUrl = '/hex/delete/'
    apiOptionsUrl = '/hex/options'

    objsStr = 'Hexs'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.q} -- 
            {obj.r} -- 
            {obj.s} -- 
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
      