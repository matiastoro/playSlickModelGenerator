import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/itineraryUserCompany/" component={ItineraryUserCompanyList} />
<Route path="/itineraryUserCompany/new" component={ItineraryUserCompanyForm} />
<Route path="/itineraryUserCompany/:id" component={ItineraryUserCompanyForm} />
<Route path="/itineraryUserCompany/:page/:pageLength" component={ItineraryUserCompanyList} />
*/
export default class ItineraryUserCompanyForm extends GList{
    showUrl =  '/itineraryUserCompany/'
    apiGetUrl =  '/itineraryUserCompany/'
    apiCreateUrl = '/itineraryUserCompany/save'
    apiDeleteUrl = '/itineraryUserCompany/delete/'
    apiOptionsUrl = '/itineraryUserCompany/options'

    objsStr = 'ItineraryUserCompanys'

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
      