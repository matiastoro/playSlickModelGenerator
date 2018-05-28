import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/formularioInstanciaHistoria/" component={FormularioInstanciaHistoriaList} />
<Route path="/formularioInstanciaHistoria/new" component={FormularioInstanciaHistoriaForm} />
<Route path="/formularioInstanciaHistoria/:id" component={FormularioInstanciaHistoriaForm} />
<Route path="/formularioInstanciaHistoria/:page/:pageLength" component={FormularioInstanciaHistoriaList} />
*/
export default class FormularioInstanciaHistoriaForm extends GList{
    showUrl =  '/formularioInstanciaHistoria/'
    apiGetUrl =  '/formularioInstanciaHistoria/'
    apiCreateUrl = '/formularioInstanciaHistoria/save'
    apiDeleteUrl = '/formularioInstanciaHistoria/delete/'
    apiOptionsUrl = '/formularioInstanciaHistoria/options'

    objsStr = 'FormularioInstanciaHistorias'

    renderPrimaryText = (obj) => {
        return obj.data
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.formularioId && this.state.indexedOptions?this.state.indexedOptions.formularios[obj.formularioId].formulario:null} -- 
            {obj.estado} -- 
            {obj.files} -- 
            {obj.adjuntos} -- 
            {obj.usuarioId && this.state.indexedOptions?this.state.indexedOptions.usuarios[obj.usuarioId].usuario:null} -- 
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
      