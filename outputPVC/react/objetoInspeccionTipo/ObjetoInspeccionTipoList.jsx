import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/objetoInspeccionTipo/" component={ObjetoInspeccionTipoList} />
<Route path="/objetoInspeccionTipo/new" component={ObjetoInspeccionTipoForm} />
<Route path="/objetoInspeccionTipo/:id" component={ObjetoInspeccionTipoForm} />
<Route path="/objetoInspeccionTipo/:page/:pageLength" component={ObjetoInspeccionTipoList} />
*/
export default class ObjetoInspeccionTipoForm extends GList{
    showUrl =  '/objetoInspeccionTipo/'
    apiGetUrl =  '/objetoInspeccionTipo/'
    apiCreateUrl = '/objetoInspeccionTipo/save'
    apiDeleteUrl = '/objetoInspeccionTipo/delete/'
    

    objsStr = 'ObjetoInspeccionTipos'

    renderPrimaryText = (obj) => {
        return obj.abreviacion
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.cantidadMes}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      