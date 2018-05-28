import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/formInstanciaHistoria/" component={FormInstanciaHistoriaList} />
<Route path="/formInstanciaHistoria/new" component={FormInstanciaHistoriaForm} />
<Route path="/formInstanciaHistoria/:id" component={FormInstanciaHistoriaForm} />
<Route path="/formInstanciaHistoria/:page/:pageLength" component={FormInstanciaHistoriaList} />
*/
export default class FormInstanciaHistoriaForm extends GList{
    showUrl =  '/formInstanciaHistoria/'
    apiGetUrl =  '/formInstanciaHistoria/'
    apiCreateUrl = '/formInstanciaHistoria/save'
    apiDeleteUrl = '/formInstanciaHistoria/delete/'
    apiOptionsUrl = '/formInstanciaHistoria/options'

    objsStr = 'FormInstanciaHistorias'

    renderPrimaryText = (obj) => {
        return obj.data
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.formId && this.state.indexedOptions?this.state.indexedOptions.forms[obj.formId].form:null} -- 
            {obj.estado} -- 
            {obj.createdAt}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      