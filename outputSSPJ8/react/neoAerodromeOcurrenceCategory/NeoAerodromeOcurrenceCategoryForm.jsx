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
import NeoAerodromeOcurrenceCategoryForm from './components/neoAerodromeOcurrenceCategory/NeoAerodromeOcurrenceCategoryForm'
import NeoAerodromeOcurrenceCategoryList from './components/neoAerodromeOcurrenceCategory/NeoAerodromeOcurrenceCategoryList'

<Route path="/neoAerodromeOcurrenceCategory/" component={NeoAerodromeOcurrenceCategoryList} />
<Route path="/neoAerodromeOcurrenceCategory/new" component={NeoAerodromeOcurrenceCategoryForm} />
<Route path="/neoAerodromeOcurrenceCategory/:id" component={NeoAerodromeOcurrenceCategoryForm} />
<Route path="/neoAerodromeOcurrenceCategory/:page/:pageLength" component={NeoAerodromeOcurrenceCategoryList} />
*/
export default class NeoAerodromeOcurrenceCategoryForm extends GForm{
    showUrl =  '/neoAerodromeOcurrenceCategory/'
    listUrl =  '/neoAerodromeOcurrenceCategory/'
    apiGetUrl =  '/neoAerodromeOcurrenceCategory/show/'
    apiCreateUrl = '/neoAerodromeOcurrenceCategory/save'
    apiUpdateUrl = '/neoAerodromeOcurrenceCategory/update/'
    apiDeleteUrl = '/neoAerodromeOcurrenceCategory/delete/'
    apiOptionsUrl = "/neoAerodromeOcurrenceCategory/options"

    objStr = 'Neo Aerodrome Ocurrence Category'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("neoAerodromeId")?<HiddenField ref={(input) => this._inputs["neoAerodromeId"] = input} name="neoAerodromeId" defaultValue={this.getAttr(obj, "neoAerodromeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["neoAerodromeId"] = input}  name="neoAerodromeId" fullWidth defaultValue={this.getAttr(obj, "neoAerodromeId", "")} options={this.getOptions(options.neoAerodromes.map(o => {return {"value": o.id, "label": o.neoAerodrome, "parentId": o.neoAerodromeId}}))} floatingLabelText="Neo Aerodrome Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "neoAerodromeId")} />}
          </div>
          <div>
            {hide.includes("ocurrenceCategoryId")?<HiddenField ref={(input) => this._inputs["ocurrenceCategoryId"] = input} name="ocurrenceCategoryId" defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["ocurrenceCategoryId"] = input}  name="ocurrenceCategoryId" fullWidth defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} options={this.getOptions(options.ocurrenceCategorys.map(o => {return {"value": o.id, "label": o.ocurrenceCategory, "parentId": o.ocurrenceCategoryId}}))} floatingLabelText="Ocurrence Category Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "ocurrenceCategoryId")} />}
          </div>
        </div>
    }

}


export class NeoAerodromeOcurrenceCategoryFormInline extends GFormInline{
    apiOptionsUrl = "/neoAerodromeOcurrenceCategory/options"
    renderForm(){
        const obj = this.props.obj;
        const readOnly = this.props.readOnly
        const errors = this.props.errors
        const hide = this.props.hide || []
        const i = this.props.i
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("neoAerodromeId")?<HiddenField ref={(input) => this._inputs["neoAerodromeId"] = input} name="neoAerodromeId" defaultValue={this.getAttr(obj, "neoAerodromeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["neoAerodromeId"] = input}  name="neoAerodromeId" fullWidth defaultValue={this.getAttr(obj, "neoAerodromeId", "")} options={this.getOptions(options.neoAerodromes.map(o => {return {"value": o.id, "label": o.neoAerodrome, "parentId": o.neoAerodromeId}}))} floatingLabelText="Neo Aerodrome Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].neoAerodromeId", "")} />}
          </div>
          <div>
            {hide.includes("ocurrenceCategoryId")?<HiddenField ref={(input) => this._inputs["ocurrenceCategoryId"] = input} name="ocurrenceCategoryId" defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["ocurrenceCategoryId"] = input}  name="ocurrenceCategoryId" fullWidth defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} options={this.getOptions(options.ocurrenceCategorys.map(o => {return {"value": o.id, "label": o.ocurrenceCategory, "parentId": o.ocurrenceCategoryId}}))} floatingLabelText="Ocurrence Category Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].ocurrenceCategoryId", "")} />}
          </div>
        </div>
    }

}
    
      