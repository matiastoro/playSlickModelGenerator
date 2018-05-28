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
<Route path="/profileUser/" component={ProfileUserList} />
<Route path="/profileUser/new" component={ProfileUserForm} />
<Route path="/profileUser/:id" component={ProfileUserForm} />
<Route path="/profileUser/:page/:pageLength" component={ProfileUserList} />
*/
export default class ProfileUserForm extends GForm{
    showUrl =  '/profileUser/'
    listUrl =  '/profileUser/'
    apiGetUrl =  '/profileUser/show/'
    apiCreateUrl = '/profileUser/save'
    apiUpdateUrl = '/profileUser/update/'
    apiDeleteUrl = '/profileUser/delete/'
    apiOptionsUrl = "/profileUser/options"

    objStr = 'ProfileUser'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("profileId")?<HiddenField ref={(input) => this._inputs["profileId"] = input} name="profileId" defaultValue={obj.profileId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["profileId"] = input}  name="profileId" defaultValue={obj.profileId || ""} options={this.state.options.profiles.map(o => {return {"value": o.id, "label": o.profile}})} floatingLabelText="ProfileId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].profileId"]} />}
          </div>
          <div>
            {hide.includes("userId")?<HiddenField ref={(input) => this._inputs["userId"] = input} name="userId" defaultValue={obj.userId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["userId"] = input}  name="userId" defaultValue={obj.userId || ""} options={this.state.options.users.map(o => {return {"value": o.id, "label": o.user}})} floatingLabelText="UserId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].userId"]} />}
          </div>
        </div>
    }

}


export class ProfileUserFormInline extends GFormInline{
    apiOptionsUrl = "/profileUser/options"
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
            {hide.includes("profileId")?<HiddenField ref={(input) => this._inputs["profileId"] = input} name="profileId" defaultValue={obj.profileId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["profileId"] = input}  name="profileId" defaultValue={obj.profileId || ""} options={this.state.options.profiles.map(o => {return {"value": o.id, "label": o.profile}})} floatingLabelText="ProfileId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].profileId"]} />}
          </div>
          <div>
            {hide.includes("userId")?<HiddenField ref={(input) => this._inputs["userId"] = input} name="userId" defaultValue={obj.userId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["userId"] = input}  name="userId" defaultValue={obj.userId || ""} options={this.state.options.users.map(o => {return {"value": o.id, "label": o.user}})} floatingLabelText="UserId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].userId"]} />}
          </div>
        </div>
    }

}
    
      