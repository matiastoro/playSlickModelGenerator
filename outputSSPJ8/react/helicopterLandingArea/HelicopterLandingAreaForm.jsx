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



export default class HelicopterLandingAreaForm extends GForm{
    showUrl =  '/helicopterLandingArea/'
    listUrl =  '/helicopterLandingArea/'
    apiGetUrl =  '/helicopterLandingArea/show/'
    apiCreateUrl = '/helicopterLandingArea/save'
    apiUpdateUrl = '/helicopterLandingArea/update/'
    apiDeleteUrl = '/helicopterLandingArea/delete/'
    apiOptionsUrl = "/helicopterLandingArea/options"

    objStr = 'Helicopter Landing Area'
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
            <TextField ref={(input) => this._inputs["helicopterLandingArea"] = input}  name="helicopterLandingArea" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingArea", "")} floatingLabelText="Helicopter Landing Area" readOnly={readOnly} required={true} errors={this.getAttr(errors, "helicopterLandingArea")}/>
          </div>
          <div>
            {hide.includes("aerodromeId")?<HiddenField ref={(input) => this._inputs["aerodromeId"] = input} name="aerodromeId" defaultValue={this.getAttr(obj, "aerodromeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeId"] = input}  name="aerodromeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.aerodrome, "parentId": o.aerodromeId}}))} floatingLabelText="Aerodrome" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aerodromeId")} />}
          </div>
          <div>
            {hide.includes("helicopterLandingAreaTypeId")?<HiddenField ref={(input) => this._inputs["helicopterLandingAreaTypeId"] = input} name="helicopterLandingAreaTypeId" defaultValue={this.getAttr(obj, "helicopterLandingAreaTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["helicopterLandingAreaTypeId"] = input}  name="helicopterLandingAreaTypeId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaTypeId", "")} options={this.getOptions(options.helicopterLandingAreaTypes.map(o => {return {"value": o.id, "label": o.tpe, "parentId": o.helicopterLandingAreaTypeId}}))} floatingLabelText="Helicopter Landing Area Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "helicopterLandingAreaTypeId")} />}
          </div>
          <div>
            {hide.includes("helicopterLandingAreaAreaConfigurationId")?<HiddenField ref={(input) => this._inputs["helicopterLandingAreaAreaConfigurationId"] = input} name="helicopterLandingAreaAreaConfigurationId" defaultValue={this.getAttr(obj, "helicopterLandingAreaAreaConfigurationId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["helicopterLandingAreaAreaConfigurationId"] = input}  name="helicopterLandingAreaAreaConfigurationId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaAreaConfigurationId", "")} options={this.getOptions(options.helicopterLandingAreaAreaConfigurations.map(o => {return {"value": o.id, "label": o.areaConfiguration, "parentId": o.helicopterLandingAreaAreaConfigurationId}}))} floatingLabelText="Helicopter Landing Area Area Configuration" readOnly={readOnly} required={true} errors={this.getAttr(errors, "helicopterLandingAreaAreaConfigurationId")} />}
          </div>
          <div>
            {hide.includes("helicopterLandingAreaSurfaceTypeId")?<HiddenField ref={(input) => this._inputs["helicopterLandingAreaSurfaceTypeId"] = input} name="helicopterLandingAreaSurfaceTypeId" defaultValue={this.getAttr(obj, "helicopterLandingAreaSurfaceTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["helicopterLandingAreaSurfaceTypeId"] = input}  name="helicopterLandingAreaSurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaSurfaceTypeId", "")} options={this.getOptions(options.helicopterLandingAreaSurfaceTypes.map(o => {return {"value": o.id, "label": o.surfaceType, "parentId": o.helicopterLandingAreaSurfaceTypeId}}))} floatingLabelText="Helicopter Landing Area Surface Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "helicopterLandingAreaSurfaceTypeId")} />}
          </div>
        </div>
    }

}


export class HelicopterLandingAreaFormInline extends GFormInline{
    apiOptionsUrl = "/helicopterLandingArea/options"
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
            <TextField ref={(input) => this._inputs["helicopterLandingArea"] = input}  name="helicopterLandingArea" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingArea", "")} floatingLabelText="Helicopter Landing Area" readOnly={readOnly} required={true} errors={this.getAttr(errors, "helicopterLandingArea")}/>
          </div>
          <div>
            {hide.includes("aerodromeId")?<HiddenField ref={(input) => this._inputs["aerodromeId"] = input} name="aerodromeId" defaultValue={this.getAttr(obj, "aerodromeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeId"] = input}  name="aerodromeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.aerodrome, "parentId": o.aerodromeId}}))} floatingLabelText="Aerodrome" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].aerodromeId", "")} />}
          </div>
          <div>
            {hide.includes("helicopterLandingAreaTypeId")?<HiddenField ref={(input) => this._inputs["helicopterLandingAreaTypeId"] = input} name="helicopterLandingAreaTypeId" defaultValue={this.getAttr(obj, "helicopterLandingAreaTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["helicopterLandingAreaTypeId"] = input}  name="helicopterLandingAreaTypeId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaTypeId", "")} options={this.getOptions(options.helicopterLandingAreaTypes.map(o => {return {"value": o.id, "label": o.tpe, "parentId": o.helicopterLandingAreaTypeId}}))} floatingLabelText="Helicopter Landing Area Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].helicopterLandingAreaTypeId", "")} />}
          </div>
          <div>
            {hide.includes("helicopterLandingAreaAreaConfigurationId")?<HiddenField ref={(input) => this._inputs["helicopterLandingAreaAreaConfigurationId"] = input} name="helicopterLandingAreaAreaConfigurationId" defaultValue={this.getAttr(obj, "helicopterLandingAreaAreaConfigurationId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["helicopterLandingAreaAreaConfigurationId"] = input}  name="helicopterLandingAreaAreaConfigurationId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaAreaConfigurationId", "")} options={this.getOptions(options.helicopterLandingAreaAreaConfigurations.map(o => {return {"value": o.id, "label": o.areaConfiguration, "parentId": o.helicopterLandingAreaAreaConfigurationId}}))} floatingLabelText="Helicopter Landing Area Area Configuration" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].helicopterLandingAreaAreaConfigurationId", "")} />}
          </div>
          <div>
            {hide.includes("helicopterLandingAreaSurfaceTypeId")?<HiddenField ref={(input) => this._inputs["helicopterLandingAreaSurfaceTypeId"] = input} name="helicopterLandingAreaSurfaceTypeId" defaultValue={this.getAttr(obj, "helicopterLandingAreaSurfaceTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["helicopterLandingAreaSurfaceTypeId"] = input}  name="helicopterLandingAreaSurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaSurfaceTypeId", "")} options={this.getOptions(options.helicopterLandingAreaSurfaceTypes.map(o => {return {"value": o.id, "label": o.surfaceType, "parentId": o.helicopterLandingAreaSurfaceTypeId}}))} floatingLabelText="Helicopter Landing Area Surface Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].helicopterLandingAreaSurfaceTypeId", "")} />}
          </div>
        </div>
    }

}
    
      