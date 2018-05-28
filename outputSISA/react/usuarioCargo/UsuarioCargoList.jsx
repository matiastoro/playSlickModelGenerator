import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/usuarioCargo/" component={UsuarioCargoList} />
<Route path="/usuarioCargo/new" component={UsuarioCargoForm} />
<Route path="/usuarioCargo/:id" component={UsuarioCargoForm} />
<Route path="/usuarioCargo/:page/:pageLength" component={UsuarioCargoList} />
*/
export default class UsuarioCargoForm extends GList{
    showUrl =  '/usuarioCargo/'
    apiGetUrl =  '/usuarioCargo/'
    apiCreateUrl = '/usuarioCargo/save'
    apiDeleteUrl = '/usuarioCargo/delete/'
    apiOptionsUrl = '/usuarioCargo/options'

    objsStr = 'UsuarioCargos'

    renderPrimaryText = (obj) => {
        return obj.aerodromo
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.cargoId && this.state.indexedOptions?this.state.indexedOptions.cargos[obj.cargoId].cargo:null} -- 
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
      