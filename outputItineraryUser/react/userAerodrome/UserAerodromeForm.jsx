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
<Route path="/userAerodrome/" component={UserAerodromeList} />
<Route path="/userAerodrome/new" component={UserAerodromeForm} />
<Route path="/userAerodrome/:id" component={UserAerodromeForm} />
<Route path="/userAerodrome/:page/:pageLength" component={UserAerodromeList} />
*/
export default class UserAerodromeForm extends GForm{
    showUrl =  '/userAerodrome/'
    listUrl =  '/userAerodrome/'
    apiGetUrl =  '/userAerodrome/show/'
    apiCreateUrl = '/userAerodrome/save'
    apiUpdateUrl = '/userAerodrome/update/'
    apiDeleteUrl = '/userAerodrome/delete/'
    apiOptionsUrl = "/userAerodrome/options"

    objStr = 'UserAerodrome'
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
            {hide.includes("aerodromeId")?<HiddenField ref={(input) => this._inputs["aerodromeId"] = input} name="aerodromeId" defaultValue={obj.aerodromeId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeId"] = input}  name="aerodromeId" defaultValue={obj.aerodromeId || ""} options={this.state.options.aerodromes.map(o => {return {"value": o.id, "label": o.aerodrome}})} floatingLabelText="AerodromeId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].aerodromeId"]} />}
          </div>
        </div>
    }

}


export class UserAerodromeFormInline extends GFormInline{
    apiOptionsUrl = "/userAerodrome/options"
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
            {hide.includes("aerodromeId")?<HiddenField ref={(input) => this._inputs["aerodromeId"] = input} name="aerodromeId" defaultValue={obj.aerodromeId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeId"] = input}  name="aerodromeId" defaultValue={obj.aerodromeId || ""} options={this.state.options.aerodromes.map(o => {return {"value": o.id, "label": o.aerodrome}})} floatingLabelText="AerodromeId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].aerodromeId"]} />}
          </div>
        </div>
    }

}
    
      