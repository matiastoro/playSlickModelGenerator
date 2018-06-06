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



export default class NeoFlightOperationOcurrenceCategoryForm extends GForm{
    showUrl =  '/neoFlightOperationOcurrenceCategory/'
    listUrl =  '/neoFlightOperationOcurrenceCategory/'
    apiGetUrl =  '/neoFlightOperationOcurrenceCategory/show/'
    apiCreateUrl = '/neoFlightOperationOcurrenceCategory/save'
    apiUpdateUrl = '/neoFlightOperationOcurrenceCategory/update/'
    apiDeleteUrl = '/neoFlightOperationOcurrenceCategory/delete/'
    apiOptionsUrl = "/neoFlightOperationOcurrenceCategory/options"

    objStr = 'Neo Flight Operation Ocurrence Category'
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
            {hide.includes("neoFlightOperationId")?<HiddenField ref={(input) => this._inputs["neoFlightOperationId"] = input} name="neoFlightOperationId" defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["neoFlightOperationId"] = input}  name="neoFlightOperationId" fullWidth defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} options={this.getOptions(options.neoFlightOperations.map(o => {return {"value": o.id, "label": o.neoFlightOperation, "parentId": o.neoFlightOperationId}}))} floatingLabelText="Neo Flight Operation" readOnly={readOnly} required={true} errors={this.getAttr(errors, "neoFlightOperationId")} />}
          </div>
          <div>
            {hide.includes("ocurrenceCategoryId")?<HiddenField ref={(input) => this._inputs["ocurrenceCategoryId"] = input} name="ocurrenceCategoryId" defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["ocurrenceCategoryId"] = input}  name="ocurrenceCategoryId" fullWidth defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} options={this.getOptions(options.ocurrenceCategorys.map(o => {return {"value": o.id, "label": o.ocurrenceCategory, "parentId": o.ocurrenceCategoryId}}))} floatingLabelText="Ocurrence Category" readOnly={readOnly} required={true} errors={this.getAttr(errors, "ocurrenceCategoryId")} />}
          </div>
        </div>
    }

}


export class NeoFlightOperationOcurrenceCategoryFormInline extends GFormInline{
    apiOptionsUrl = "/neoFlightOperationOcurrenceCategory/options"
    renderForm(){
        const obj = this.props.obj;
        const readOnly = this.props.readOnly
        const errors = this.props.errors
        const hide = this.props.hide || []
        const i = this.props.i
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("neoFlightOperationId")?<HiddenField ref={(input) => this._inputs["neoFlightOperationId"] = input} name="neoFlightOperationId" defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["neoFlightOperationId"] = input}  name="neoFlightOperationId" fullWidth defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} options={this.getOptions(options.neoFlightOperations.map(o => {return {"value": o.id, "label": o.neoFlightOperation, "parentId": o.neoFlightOperationId}}))} floatingLabelText="Neo Flight Operation" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].neoFlightOperationId", "")} />}
          </div>
          <div>
            {hide.includes("ocurrenceCategoryId")?<HiddenField ref={(input) => this._inputs["ocurrenceCategoryId"] = input} name="ocurrenceCategoryId" defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["ocurrenceCategoryId"] = input}  name="ocurrenceCategoryId" fullWidth defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} options={this.getOptions(options.ocurrenceCategorys.map(o => {return {"value": o.id, "label": o.ocurrenceCategory, "parentId": o.ocurrenceCategoryId}}))} floatingLabelText="Ocurrence Category" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].ocurrenceCategoryId", "")} />}
          </div>
        </div>
    }

}
    
      