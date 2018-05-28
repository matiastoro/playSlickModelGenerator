import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/formInstancia/" component={FormInstanciaList} />
<Route path="/formInstancia/new" component={FormInstanciaForm} />
<Route path="/formInstancia/:id" component={FormInstanciaForm} />
<Route path="/formInstancia/:page/:pageLength" component={FormInstanciaList} />
*/
export default class FormInstanciaForm extends GList{
    showUrl =  '/formInstancia/'
    apiGetUrl =  '/formInstancia/'
    apiCreateUrl = '/formInstancia/save'
    apiDeleteUrl = '/formInstancia/delete/'
    apiOptionsUrl = '/formInstancia/options'

    objsStr = 'FormInstancias'

    renderPrimaryText = (obj) => {
        return obj.data
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.formId && this.state.indexedOptions?this.state.indexedOptions.forms[obj.formId].form:null} -- 
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
      