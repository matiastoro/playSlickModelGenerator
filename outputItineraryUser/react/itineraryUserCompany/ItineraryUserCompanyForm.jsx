import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';
import {GFormInline} from '../gforms/GForm';

//inputs de nested


/*
<Route path="/itineraryUserCompany/" component={ItineraryUserCompanyList} />
<Route path="/itineraryUserCompany/new" component={ItineraryUserCompanyForm} />
<Route path="/itineraryUserCompany/:id" component={ItineraryUserCompanyForm} />
<Route path="/itineraryUserCompany/:page/:pageLength" component={ItineraryUserCompanyList} />
*/
export default class ItineraryUserCompanyForm extends GForm{
    showUrl =  '/itineraryUserCompany/'
    listUrl =  '/itineraryUserCompany/'
    apiGetUrl =  '/itineraryUserCompany/show/'
    apiCreateUrl = '/itineraryUserCompany/save'
    apiUpdateUrl = '/itineraryUserCompany/update/'
    apiDeleteUrl = '/itineraryUserCompany/delete/'
    apiOptionsUrl = "/itineraryUserCompany/options"

    objStr = 'ItineraryUserCompany'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("userId")?<HiddenField ref={(input) => this._inputs["userId"] = input} name="userId" defaultValue={obj.userId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["userId"] = input}  name="userId" defaultValue={obj.userId || ""} options={this.state.options.users.map(o => {return {"value": o.id, "label": o.user}})} floatingLabelText="UserId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].userId"]} />}
          </div>
          <div>
            {hide.includes("companyId")?<HiddenField ref={(input) => this._inputs["companyId"] = input} name="companyId" defaultValue={obj.companyId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["companyId"] = input}  name="companyId" defaultValue={obj.companyId || ""} options={this.state.options.companys.map(o => {return {"value": o.id, "label": o.company}})} floatingLabelText="CompanyId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].companyId"]} />}
          </div>
        </div>
    }

}


export class ItineraryUserCompanyFormInline extends GFormInline{
    apiOptionsUrl = "/itineraryUserCompany/options"
    renderForm(){
        const obj = this.props.obj;
        const readOnly = this.props.readOnly
        const errors = this.props.errors
        const hide = this.props.hide || []
        const i = this.props.i
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("userId")?<HiddenField ref={(input) => this._inputs["userId"] = input} name="userId" defaultValue={obj.userId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["userId"] = input}  name="userId" defaultValue={obj.userId || ""} options={this.state.options.users.map(o => {return {"value": o.id, "label": o.user}})} floatingLabelText="UserId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].userId"]} />}
          </div>
          <div>
            {hide.includes("companyId")?<HiddenField ref={(input) => this._inputs["companyId"] = input} name="companyId" defaultValue={obj.companyId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["companyId"] = input}  name="companyId" defaultValue={obj.companyId || ""} options={this.state.options.companys.map(o => {return {"value": o.id, "label": o.company}})} floatingLabelText="CompanyId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].companyId"]} />}
          </div>
        </div>
    }

}
    
      