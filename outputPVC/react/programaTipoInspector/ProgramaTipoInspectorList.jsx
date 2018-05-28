import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/programaTipoInspector/" component={ProgramaTipoInspectorList} />
<Route path="/programaTipoInspector/new" component={ProgramaTipoInspectorForm} />
<Route path="/programaTipoInspector/:id" component={ProgramaTipoInspectorForm} />
<Route path="/programaTipoInspector/:page/:pageLength" component={ProgramaTipoInspectorList} />
*/
export default class ProgramaTipoInspectorForm extends GList{
    showUrl =  '/programaTipoInspector/'
    apiGetUrl =  '/programaTipoInspector/'
    apiCreateUrl = '/programaTipoInspector/save'
    apiDeleteUrl = '/programaTipoInspector/delete/'
    apiOptionsUrl = '/programaTipoInspector/options'

    objsStr = 'ProgramaTipoInspectors'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.programaId && this.state.indexedOptions?this.state.indexedOptions.programas[obj.programaId].programa:null} -- 
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
      