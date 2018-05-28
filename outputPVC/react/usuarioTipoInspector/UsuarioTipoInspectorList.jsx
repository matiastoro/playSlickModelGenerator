import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/usuarioTipoInspector/" component={UsuarioTipoInspectorList} />
<Route path="/usuarioTipoInspector/new" component={UsuarioTipoInspectorForm} />
<Route path="/usuarioTipoInspector/:id" component={UsuarioTipoInspectorForm} />
<Route path="/usuarioTipoInspector/:page/:pageLength" component={UsuarioTipoInspectorList} />
*/
export default class UsuarioTipoInspectorForm extends GList{
    showUrl =  '/usuarioTipoInspector/'
    apiGetUrl =  '/usuarioTipoInspector/'
    apiCreateUrl = '/usuarioTipoInspector/save'
    apiDeleteUrl = '/usuarioTipoInspector/delete/'
    apiOptionsUrl = '/usuarioTipoInspector/options'

    objsStr = 'UsuarioTipoInspectors'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.usuarioId && this.state.indexedOptions?this.state.indexedOptions.usuarios[obj.usuarioId].usuario:null} -- 
            {obj.tipoInspectorId && this.state.indexedOptions?this.state.indexedOptions.tipo_inspectors[obj.tipoInspectorId].tipo_inspector:null}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      