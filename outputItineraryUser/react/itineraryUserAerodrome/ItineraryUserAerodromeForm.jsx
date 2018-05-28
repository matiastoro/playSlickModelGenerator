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
<Route path="/itineraryUserAerodrome/" component={ItineraryUserAerodromeList} />
<Route path="/itineraryUserAerodrome/new" component={ItineraryUserAerodromeForm} />
<Route path="/itineraryUserAerodrome/:id" component={ItineraryUserAerodromeForm} />
<Route path="/itineraryUserAerodrome/:page/:pageLength" component={ItineraryUserAerodromeList} />
*/
export default class ItineraryUserAerodromeForm extends GForm{
    showUrl =  '/itineraryUserAerodrome/'
    listUrl =  '/itineraryUserAerodrome/'
    apiGetUrl =  '/itineraryUserAerodrome/show/'
    apiCreateUrl = '/itineraryUserAerodrome/save'
    apiUpdateUrl = '/itineraryUserAerodrome/update/'
    apiDeleteUrl = '/itineraryUserAerodrome/delete/'
    apiOptionsUrl = "/itineraryUserAerodrome/options"

    objStr = 'ItineraryUserAerodrome'
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


export class ItineraryUserAerodromeFormInline extends GFormInline{
    apiOptionsUrl = "/itineraryUserAerodrome/options"
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
    
      