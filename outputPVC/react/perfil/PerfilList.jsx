import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/perfil/" component={PerfilList} />
<Route path="/perfil/new" component={PerfilForm} />
<Route path="/perfil/:id" component={PerfilForm} />
<Route path="/perfil/:page/:pageLength" component={PerfilList} />
*/
export default class PerfilForm extends GList{
    showUrl =  '/perfil/'
    apiGetUrl =  '/perfil/'
    apiCreateUrl = '/perfil/save'
    apiDeleteUrl = '/perfil/delete/'
    

    objsStr = 'Perfils'

    renderPrimaryText = (obj) => {
        return obj.perfil
    }
    
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      