import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/usuarioPerfil/" component={UsuarioPerfilList} />
<Route path="/usuarioPerfil/new" component={UsuarioPerfilForm} />
<Route path="/usuarioPerfil/:id" component={UsuarioPerfilForm} />
<Route path="/usuarioPerfil/:page/:pageLength" component={UsuarioPerfilList} />
*/
export default class UsuarioPerfilForm extends GList{
    showUrl =  '/usuarioPerfil/'
    apiGetUrl =  '/usuarioPerfil/'
    apiCreateUrl = '/usuarioPerfil/save'
    apiDeleteUrl = '/usuarioPerfil/delete/'
    apiOptionsUrl = '/usuarioPerfil/options'

    objsStr = 'UsuarioPerfils'

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
      