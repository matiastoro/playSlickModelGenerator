import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/categoriaInspeccion/" component={CategoriaInspeccionList} />
<Route path="/categoriaInspeccion/new" component={CategoriaInspeccionForm} />
<Route path="/categoriaInspeccion/:id" component={CategoriaInspeccionForm} />
<Route path="/categoriaInspeccion/:page/:pageLength" component={CategoriaInspeccionList} />
*/
export default class CategoriaInspeccionForm extends GList{
    showUrl =  '/categoriaInspeccion/'
    apiGetUrl =  '/categoriaInspeccion/'
    apiCreateUrl = '/categoriaInspeccion/save'
    apiDeleteUrl = '/categoriaInspeccion/delete/'
    

    objsStr = 'CategoriaInspeccions'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      