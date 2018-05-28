import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/tipoInspector/" component={TipoInspectorList} />
<Route path="/tipoInspector/new" component={TipoInspectorForm} />
<Route path="/tipoInspector/:id" component={TipoInspectorForm} />
<Route path="/tipoInspector/:page/:pageLength" component={TipoInspectorList} />
*/
export default class TipoInspectorForm extends GList{
    showUrl =  '/tipoInspector/'
    apiGetUrl =  '/tipoInspector/'
    apiCreateUrl = '/tipoInspector/save'
    apiDeleteUrl = '/tipoInspector/delete/'
    

    objsStr = 'TipoInspectors'

    renderPrimaryText = (obj) => {
        return obj.tipo
    }
    
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      