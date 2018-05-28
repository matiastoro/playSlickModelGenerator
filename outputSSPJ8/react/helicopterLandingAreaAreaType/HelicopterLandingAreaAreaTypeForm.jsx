import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested


/*
import HelicopterLandingAreaAreaTypeForm from './components/helicopterLandingAreaAreaType/HelicopterLandingAreaAreaTypeForm'
import HelicopterLandingAreaAreaTypeList from './components/helicopterLandingAreaAreaType/HelicopterLandingAreaAreaTypeList'

<Route path="/helicopterLandingAreaAreaType/" component={HelicopterLandingAreaAreaTypeList} />
<Route path="/helicopterLandingAreaAreaType/new" component={HelicopterLandingAreaAreaTypeForm} />
<Route path="/helicopterLandingAreaAreaType/:id" component={HelicopterLandingAreaAreaTypeForm} />
<Route path="/helicopterLandingAreaAreaType/:page/:pageLength" component={HelicopterLandingAreaAreaTypeList} />
*/
export default class HelicopterLandingAreaAreaTypeForm extends GForm{
    showUrl =  '/helicopterLandingAreaAreaType/'
    listUrl =  '/helicopterLandingAreaAreaType/'
    apiGetUrl =  '/helicopterLandingAreaAreaType/show/'
    apiCreateUrl = '/helicopterLandingAreaAreaType/save'
    apiUpdateUrl = '/helicopterLandingAreaAreaType/update/'
    apiDeleteUrl = '/helicopterLandingAreaAreaType/delete/'
    apiOptionsUrl = "/helicopterLandingAreaAreaType/options"

    objStr = 'Helicopter Landing Area Area Type'
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
            {hide.includes("helicopterLandingAreaTypeId")?<HiddenField ref={(input) => this._inputs["helicopterLandingAreaTypeId"] = input} name="helicopterLandingAreaTypeId" defaultValue={this.getAttr(obj, "helicopterLandingAreaTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["helicopterLandingAreaTypeId"] = input}  name="helicopterLandingAreaTypeId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaTypeId", "")} options={this.getOptions(options.helicopterLandingAreaTypes.map(o => {return {"value": o.id, "label": o.helicopterLandingAreaType, "parentId": o.helicopterLandingAreaTypeId}}))} floatingLabelText="Helicopter Landing Area Type Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "helicopterLandingAreaTypeId")} />}
          </div>
          <div>
            {hide.includes("helicopterLandingAreaAreaConfigurationId")?<HiddenField ref={(input) => this._inputs["helicopterLandingAreaAreaConfigurationId"] = input} name="helicopterLandingAreaAreaConfigurationId" defaultValue={this.getAttr(obj, "helicopterLandingAreaAreaConfigurationId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["helicopterLandingAreaAreaConfigurationId"] = input}  name="helicopterLandingAreaAreaConfigurationId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaAreaConfigurationId", "")} options={this.getOptions(options.helicopterLandingAreaAreaConfigurations.map(o => {return {"value": o.id, "label": o.helicopterLandingAreaAreaConfiguration, "parentId": o.helicopterLandingAreaAreaConfigurationId}}))} floatingLabelText="Helicopter Landing Area Area Configuration Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "helicopterLandingAreaAreaConfigurationId")} />}
          </div>
          <div>
            {hide.includes("helicopterLandingAreaSurfaceTypeId")?<HiddenField ref={(input) => this._inputs["helicopterLandingAreaSurfaceTypeId"] = input} name="helicopterLandingAreaSurfaceTypeId" defaultValue={this.getAttr(obj, "helicopterLandingAreaSurfaceTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["helicopterLandingAreaSurfaceTypeId"] = input}  name="helicopterLandingAreaSurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaSurfaceTypeId", "")} options={this.getOptions(options.helicopterLandingAreaSurfaceTypes.map(o => {return {"value": o.id, "label": o.helicopterLandingAreaSurfaceType, "parentId": o.helicopterLandingAreaSurfaceTypeId}}))} floatingLabelText="Helicopter Landing Area Surface Type Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "helicopterLandingAreaSurfaceTypeId")} />}
          </div>
        </div>
    }

}


      