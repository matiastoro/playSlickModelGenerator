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



export default class RunwayForm extends GForm{
    showUrl =  '/runway/'
    listUrl =  '/runway/'
    apiGetUrl =  '/runway/show/'
    apiCreateUrl = '/runway/save'
    apiUpdateUrl = '/runway/update/'
    apiDeleteUrl = '/runway/delete/'
    apiOptionsUrl = "/runway/options"

    objStr = 'Runway'
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
            <TextField ref={(input) => this._inputs["runway"] = input}  name="runway" fullWidth defaultValue={this.getAttr(obj, "runway", "")} floatingLabelText="Runway" readOnly={readOnly} required={true} errors={this.getAttr(errors, "runway")}/>
          </div>
          <div>
            {hide.includes("runwaySurfaceTypeId")?<HiddenField ref={(input) => this._inputs["runwaySurfaceTypeId"] = input} name="runwaySurfaceTypeId" defaultValue={this.getAttr(obj, "runwaySurfaceTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["runwaySurfaceTypeId"] = input}  name="runwaySurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "runwaySurfaceTypeId", "")} options={this.getOptions(options.runwaySurfaceTypes.map(o => {return {"value": o.id, "label": o.surfaceType, "parentId": o.runwaySurfaceTypeId}}))} floatingLabelText="Runway Surface Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "runwaySurfaceTypeId")} />}
          </div>
          <div>
            {hide.includes("aerodromeId")?<HiddenField ref={(input) => this._inputs["aerodromeId"] = input} name="aerodromeId" defaultValue={this.getAttr(obj, "aerodromeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeId"] = input}  name="aerodromeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.aerodrome, "parentId": o.aerodromeId}}))} floatingLabelText="Aerodrome" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aerodromeId")} />}
          </div>
        </div>
    }

}


export class RunwayFormInline extends GFormInline{
    apiOptionsUrl = "/runway/options"
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
            <TextField ref={(input) => this._inputs["runway"] = input}  name="runway" fullWidth defaultValue={this.getAttr(obj, "runway", "")} floatingLabelText="Runway" readOnly={readOnly} required={true} errors={this.getAttr(errors, "runway")}/>
          </div>
          <div>
            {hide.includes("runwaySurfaceTypeId")?<HiddenField ref={(input) => this._inputs["runwaySurfaceTypeId"] = input} name="runwaySurfaceTypeId" defaultValue={this.getAttr(obj, "runwaySurfaceTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["runwaySurfaceTypeId"] = input}  name="runwaySurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "runwaySurfaceTypeId", "")} options={this.getOptions(options.runwaySurfaceTypes.map(o => {return {"value": o.id, "label": o.surfaceType, "parentId": o.runwaySurfaceTypeId}}))} floatingLabelText="Runway Surface Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].runwaySurfaceTypeId", "")} />}
          </div>
          <div>
            {hide.includes("aerodromeId")?<HiddenField ref={(input) => this._inputs["aerodromeId"] = input} name="aerodromeId" defaultValue={this.getAttr(obj, "aerodromeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeId"] = input}  name="aerodromeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.aerodrome, "parentId": o.aerodromeId}}))} floatingLabelText="Aerodrome" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].aerodromeId", "")} />}
          </div>
        </div>
    }

}
    
      