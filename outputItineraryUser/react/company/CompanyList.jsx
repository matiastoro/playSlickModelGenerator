import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/company/" component={CompanyList} />
<Route path="/company/new" component={CompanyForm} />
<Route path="/company/:id" component={CompanyForm} />
<Route path="/company/:page/:pageLength" component={CompanyList} />
*/
export default class CompanyForm extends GList{
    showUrl =  '/company/'
    apiGetUrl =  '/company/'
    apiCreateUrl = '/company/save'
    apiDeleteUrl = '/company/delete/'
    

    objsStr = 'Companys'

    renderPrimaryText = (obj) => {
        return obj.company
    }
    
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      