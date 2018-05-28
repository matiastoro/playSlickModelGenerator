import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/formulario/" component={FormularioList} />
<Route path="/formulario/new" component={FormularioForm} />
<Route path="/formulario/:id" component={FormularioForm} />
<Route path="/formulario/:page/:pageLength" component={FormularioList} />
*/
export default class FormularioForm extends GList{
    showUrl =  '/formulario/'
    apiGetUrl =  '/formulario/'
    apiCreateUrl = '/formulario/save'
    apiDeleteUrl = '/formulario/delete/'
    apiOptionsUrl = '/formulario/options'

    objsStr = 'Formularios'

    renderPrimaryText = (obj) => {
        return obj.nombre
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.codigo} -- 
            {obj.key} -- 
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
      