import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/programa/" component={ProgramaList} />
<Route path="/programa/new" component={ProgramaForm} />
<Route path="/programa/:id" component={ProgramaForm} />
<Route path="/programa/:page/:pageLength" component={ProgramaList} />
*/
export default class ProgramaForm extends GList{
    showUrl =  '/programa/'
    apiGetUrl =  '/programa/'
    apiCreateUrl = '/programa/save'
    apiDeleteUrl = '/programa/delete/'
    apiOptionsUrl = '/programa/options'

    objsStr = 'Programas'

    renderPrimaryText = (obj) => {
        return obj.programa
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.acronimo} -- 
            {obj.departamentoId && this.state.indexedOptions?this.state.indexedOptions.departamentos[obj.departamentoId].departamento:null} -- 
            {obj.categoriaInspeccionId && this.state.indexedOptions?this.state.indexedOptions.categoria_inspeccions[obj.categoriaInspeccionId].categoria_inspeccion:null} -- 
            {obj.objetoInspeccionTipoId && this.state.indexedOptions?this.state.indexedOptions.objeto_inspeccion_tipos[obj.objetoInspeccionTipoId].objeto_inspeccion_tipo:null}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      