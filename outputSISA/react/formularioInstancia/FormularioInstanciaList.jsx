import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/formularioInstancia/" component={FormularioInstanciaList} />
<Route path="/formularioInstancia/new" component={FormularioInstanciaForm} />
<Route path="/formularioInstancia/:id" component={FormularioInstanciaForm} />
<Route path="/formularioInstancia/:page/:pageLength" component={FormularioInstanciaList} />
*/
export default class FormularioInstanciaForm extends GList{
    showUrl =  '/formularioInstancia/'
    apiGetUrl =  '/formularioInstancia/'
    apiCreateUrl = '/formularioInstancia/save'
    apiDeleteUrl = '/formularioInstancia/delete/'
    apiOptionsUrl = '/formularioInstancia/options'

    objsStr = 'FormularioInstancias'

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
            {obj.designadorOaci} -- 
            {obj.createdAt} -- 
            {obj.updatedAt}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      