import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/perfilUsuario/" component={PerfilUsuarioList} />
<Route path="/perfilUsuario/new" component={PerfilUsuarioForm} />
<Route path="/perfilUsuario/:id" component={PerfilUsuarioForm} />
<Route path="/perfilUsuario/:page/:pageLength" component={PerfilUsuarioList} />
*/
export default class PerfilUsuarioForm extends GList{
    showUrl =  '/perfilUsuario/'
    apiGetUrl =  '/perfilUsuario/'
    apiCreateUrl = '/perfilUsuario/save'
    apiDeleteUrl = '/perfilUsuario/delete/'
    apiOptionsUrl = '/perfilUsuario/options'

    objsStr = 'PerfilUsuarios'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.perfilId && this.state.indexedOptions?this.state.indexedOptions.perfils[obj.perfilId].perfil:null} -- 
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
      