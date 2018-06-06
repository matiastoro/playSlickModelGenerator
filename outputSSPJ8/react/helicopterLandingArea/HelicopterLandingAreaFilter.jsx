import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GFilter from '../gforms/GFilter';
import {GFormInline} from '../gforms/GForm';

import { Row, Col } from 'react-flexbox-grid';

//inputs de nested




export default class HelicopterLandingAreaFilter extends GFilter{

    renderFilter(){
        const readOnly = false
        const obj = this.props.query?JSON.parse(this.props.query):{}
        const errors = {}
        const options = this.props.options
        const selectDefault = "-1"
        return <div>
            <Row>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["id"] = input}  name="id" fullWidth defaultValue={this.getAttr(obj, "id", "")} floatingLabelText="Id" readOnly={readOnly}  errors={this.getAttr(errors, "id")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["helicopterLandingArea"] = input}  name="helicopterLandingArea" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingArea", "")} floatingLabelText="Helicopter Landing Area" readOnly={readOnly}  errors={this.getAttr(errors, "helicopterLandingArea")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeId"] = input}  name="aerodromeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.aerodrome, "parentId": o.aerodromeId}}))} floatingLabelText="Aerodrome" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["helicopterLandingAreaTypeId"] = input}  name="helicopterLandingAreaTypeId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaTypeId", "")} options={this.getOptions(options.helicopterLandingAreaTypes.map(o => {return {"value": o.id, "label": o.tpe, "parentId": o.helicopterLandingAreaTypeId}}))} floatingLabelText="Helicopter Landing Area Type" readOnly={readOnly}  errors={this.getAttr(errors, "helicopterLandingAreaTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["helicopterLandingAreaAreaConfigurationId"] = input}  name="helicopterLandingAreaAreaConfigurationId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaAreaConfigurationId", "")} options={this.getOptions(options.helicopterLandingAreaAreaConfigurations.map(o => {return {"value": o.id, "label": o.areaConfiguration, "parentId": o.helicopterLandingAreaAreaConfigurationId}}))} floatingLabelText="Helicopter Landing Area Area Configuration" readOnly={readOnly}  errors={this.getAttr(errors, "helicopterLandingAreaAreaConfigurationId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["helicopterLandingAreaSurfaceTypeId"] = input}  name="helicopterLandingAreaSurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaSurfaceTypeId", "")} options={this.getOptions(options.helicopterLandingAreaSurfaceTypes.map(o => {return {"value": o.id, "label": o.surfaceType, "parentId": o.helicopterLandingAreaSurfaceTypeId}}))} floatingLabelText="Helicopter Landing Area Surface Type" readOnly={readOnly}  errors={this.getAttr(errors, "helicopterLandingAreaSurfaceTypeId")} />
                </Col>
            </Row>
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
        return <div>
<TextField ref={(input) => this._inputs["id"] = input}  name="id" fullWidth defaultValue={this.getAttr(obj, "id", "")} floatingLabelText="Id" readOnly={readOnly}  errors={this.getAttr(errors, "id")}/>
<TextField ref={(input) => this._inputs["helicopterLandingArea"] = input}  name="helicopterLandingArea" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingArea", "")} floatingLabelText="Helicopter Landing Area" readOnly={readOnly}  errors={this.getAttr(errors, "helicopterLandingArea")}/>
<SelectField ref={(input) => this._inputs["aerodromeId"] = input}  name="aerodromeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.aerodrome, "parentId": o.aerodromeId}}))} floatingLabelText="Aerodrome" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].aerodromeId", "")} />
<SelectField ref={(input) => this._inputs["helicopterLandingAreaTypeId"] = input}  name="helicopterLandingAreaTypeId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaTypeId", "")} options={this.getOptions(options.helicopterLandingAreaTypes.map(o => {return {"value": o.id, "label": o.tpe, "parentId": o.helicopterLandingAreaTypeId}}))} floatingLabelText="Helicopter Landing Area Type" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].helicopterLandingAreaTypeId", "")} />
<SelectField ref={(input) => this._inputs["helicopterLandingAreaAreaConfigurationId"] = input}  name="helicopterLandingAreaAreaConfigurationId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaAreaConfigurationId", "")} options={this.getOptions(options.helicopterLandingAreaAreaConfigurations.map(o => {return {"value": o.id, "label": o.areaConfiguration, "parentId": o.helicopterLandingAreaAreaConfigurationId}}))} floatingLabelText="Helicopter Landing Area Area Configuration" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].helicopterLandingAreaAreaConfigurationId", "")} />
<SelectField ref={(input) => this._inputs["helicopterLandingAreaSurfaceTypeId"] = input}  name="helicopterLandingAreaSurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaSurfaceTypeId", "")} options={this.getOptions(options.helicopterLandingAreaSurfaceTypes.map(o => {return {"value": o.id, "label": o.surfaceType, "parentId": o.helicopterLandingAreaSurfaceTypeId}}))} floatingLabelText="Helicopter Landing Area Surface Type" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].helicopterLandingAreaSurfaceTypeId", "")} />
        </div>
    }

}
    
      