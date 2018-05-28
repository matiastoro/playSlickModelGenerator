import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/departamentoUsuario/" component={DepartamentoUsuarioList} />
<Route path="/departamentoUsuario/new" component={DepartamentoUsuarioForm} />
<Route path="/departamentoUsuario/:id" component={DepartamentoUsuarioForm} />
<Route path="/departamentoUsuario/:page/:pageLength" component={DepartamentoUsuarioList} />
*/
export default class DepartamentoUsuarioForm extends GList{
    showUrl =  '/departamentoUsuario/'
    apiGetUrl =  '/departamentoUsuario/'
    apiCreateUrl = '/departamentoUsuario/save'
    apiDeleteUrl = '/departamentoUsuario/delete/'
    apiOptionsUrl = '/departamentoUsuario/options'

    objsStr = 'DepartamentoUsuarios'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.departamentoId && this.state.indexedOptions?this.state.indexedOptions.departamentos[obj.departamentoId].departamento:null} -- 
            {obj.usuarioId && this.state.indexedOptions?this.state.indexedOptions.usuarios[obj.usuarioId].usuario:null}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      