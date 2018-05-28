import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/departamento/" component={DepartamentoList} />
<Route path="/departamento/new" component={DepartamentoForm} />
<Route path="/departamento/:id" component={DepartamentoForm} />
<Route path="/departamento/:page/:pageLength" component={DepartamentoList} />
*/
export default class DepartamentoForm extends GList{
    showUrl =  '/departamento/'
    apiGetUrl =  '/departamento/'
    apiCreateUrl = '/departamento/save'
    apiDeleteUrl = '/departamento/delete/'
    apiOptionsUrl = '/departamento/options'

    objsStr = 'Departamentos'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.departamentoId && this.state.indexedOptions?this.state.indexedOptions.departamentos[obj.departamentoId].departamento:null} -- 
            {obj.jefeId}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      