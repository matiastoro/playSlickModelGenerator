import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/organizacion/" component={OrganizacionList} />
<Route path="/organizacion/new" component={OrganizacionForm} />
<Route path="/organizacion/:id" component={OrganizacionForm} />
<Route path="/organizacion/:page/:pageLength" component={OrganizacionList} />
*/
export default class OrganizacionForm extends GList{
    showUrl =  '/organizacion/'
    apiGetUrl =  '/organizacion/'
    apiCreateUrl = '/organizacion/save'
    apiDeleteUrl = '/organizacion/delete/'
    

    objsStr = 'Organizacions'

    renderPrimaryText = (obj) => {
        return obj.organizacion
    }
    
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      