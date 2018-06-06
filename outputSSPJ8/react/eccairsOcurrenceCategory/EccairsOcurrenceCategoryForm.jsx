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



export default class EccairsOcurrenceCategoryForm extends GForm{
    showUrl =  '/eccairsOcurrenceCategory/'
    listUrl =  '/eccairsOcurrenceCategory/'
    apiGetUrl =  '/eccairsOcurrenceCategory/show/'
    apiCreateUrl = '/eccairsOcurrenceCategory/save'
    apiUpdateUrl = '/eccairsOcurrenceCategory/update/'
    apiDeleteUrl = '/eccairsOcurrenceCategory/delete/'
    apiOptionsUrl = "/eccairsOcurrenceCategory/options"

    objStr = 'Eccairs Ocurrence Category'
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
            {hide.includes("eccairsId")?<HiddenField ref={(input) => this._inputs["eccairsId"] = input} name="eccairsId" defaultValue={this.getAttr(obj, "eccairsId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["eccairsId"] = input}  name="eccairsId" fullWidth defaultValue={this.getAttr(obj, "eccairsId", "")} options={this.getOptions(options.eccairss.map(o => {return {"value": o.id, "label": o.eccairs, "parentId": o.eccairsId}}))} floatingLabelText="Eccairs" readOnly={readOnly} required={true} errors={this.getAttr(errors, "eccairsId")} />}
          </div>
          <div>
            {hide.includes("ocurrenceCategoryId")?<HiddenField ref={(input) => this._inputs["ocurrenceCategoryId"] = input} name="ocurrenceCategoryId" defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["ocurrenceCategoryId"] = input}  name="ocurrenceCategoryId" fullWidth defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} options={this.getOptions(options.ocurrenceCategorys.map(o => {return {"value": o.id, "label": o.ocurrenceCategory, "parentId": o.ocurrenceCategoryId}}))} floatingLabelText="Ocurrence Category" readOnly={readOnly} required={true} errors={this.getAttr(errors, "ocurrenceCategoryId")} />}
          </div>
        </div>
    }

}


export class EccairsOcurrenceCategoryFormInline extends GFormInline{
    apiOptionsUrl = "/eccairsOcurrenceCategory/options"
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
            {hide.includes("eccairsId")?<HiddenField ref={(input) => this._inputs["eccairsId"] = input} name="eccairsId" defaultValue={this.getAttr(obj, "eccairsId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["eccairsId"] = input}  name="eccairsId" fullWidth defaultValue={this.getAttr(obj, "eccairsId", "")} options={this.getOptions(options.eccairss.map(o => {return {"value": o.id, "label": o.eccairs, "parentId": o.eccairsId}}))} floatingLabelText="Eccairs" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].eccairsId", "")} />}
          </div>
          <div>
            {hide.includes("ocurrenceCategoryId")?<HiddenField ref={(input) => this._inputs["ocurrenceCategoryId"] = input} name="ocurrenceCategoryId" defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["ocurrenceCategoryId"] = input}  name="ocurrenceCategoryId" fullWidth defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} options={this.getOptions(options.ocurrenceCategorys.map(o => {return {"value": o.id, "label": o.ocurrenceCategory, "parentId": o.ocurrenceCategoryId}}))} floatingLabelText="Ocurrence Category" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].ocurrenceCategoryId", "")} />}
          </div>
        </div>
    }

}
    
      