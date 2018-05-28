import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/usuario/" component={UsuarioList} />
<Route path="/usuario/new" component={UsuarioForm} />
<Route path="/usuario/:id" component={UsuarioForm} />
<Route path="/usuario/:page/:pageLength" component={UsuarioList} />
*/
export default class UsuarioForm extends GList{
    showUrl =  '/usuario/'
    apiGetUrl =  '/usuario/'
    apiCreateUrl = '/usuario/save'
    apiDeleteUrl = '/usuario/delete/'
    

    objsStr = 'Usuarios'

    renderPrimaryText = (obj) => {
        return obj.nombre
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.email} -- 
            {obj.contrasena} -- 
            {obj.estado} -- 
            {obj.rut}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      