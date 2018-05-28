import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/form/" component={FormList} />
<Route path="/form/new" component={FormForm} />
<Route path="/form/:id" component={FormForm} />
<Route path="/form/:page/:pageLength" component={FormList} />
*/
export default class FormForm extends GList{
    showUrl =  '/form/'
    apiGetUrl =  '/form/'
    apiCreateUrl = '/form/save'
    apiDeleteUrl = '/form/delete/'
    apiOptionsUrl = '/form/options'

    objsStr = 'Forms'

    renderPrimaryText = (obj) => {
        return obj.codigo
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.schema} -- 
            {obj.organizacionId && this.state.indexedOptions?this.state.indexedOptions.organizacions[obj.organizacionId].organizacion:null} -- 
            {obj.estado}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      