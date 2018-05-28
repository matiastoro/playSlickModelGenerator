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
<Route path="/userCompany/" component={UserCompanyList} />
<Route path="/userCompany/new" component={UserCompanyForm} />
<Route path="/userCompany/:id" component={UserCompanyForm} />
<Route path="/userCompany/:page/:pageLength" component={UserCompanyList} />
*/
export default class UserCompanyForm extends GForm{
    showUrl =  '/userCompany/'
    listUrl =  '/userCompany/'
    apiGetUrl =  '/userCompany/show/'
    apiCreateUrl = '/userCompany/save'
    apiUpdateUrl = '/userCompany/update/'
    apiDeleteUrl = '/userCompany/delete/'
    apiOptionsUrl = "/userCompany/options"

    objStr = 'UserCompany'
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


export class UserCompanyFormInline extends GFormInline{
    apiOptionsUrl = "/userCompany/options"
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
    
      