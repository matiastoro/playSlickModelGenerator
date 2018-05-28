import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/userCompany/" component={UserCompanyList} />
<Route path="/userCompany/new" component={UserCompanyForm} />
<Route path="/userCompany/:id" component={UserCompanyForm} />
<Route path="/userCompany/:page/:pageLength" component={UserCompanyList} />
*/
export default class UserCompanyForm extends GList{
    showUrl =  '/userCompany/'
    apiGetUrl =  '/userCompany/'
    apiCreateUrl = '/userCompany/save'
    apiDeleteUrl = '/userCompany/delete/'
    apiOptionsUrl = '/userCompany/options'

    objsStr = 'UserCompanys'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.userId && this.state.indexedOptions?this.state.indexedOptions.users[obj.userId].user:null} -- 
            {obj.companyId && this.state.indexedOptions?this.state.indexedOptions.companys[obj.companyId].company:null}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      