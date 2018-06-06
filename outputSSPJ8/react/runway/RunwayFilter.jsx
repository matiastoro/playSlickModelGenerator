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




export default class RunwayFilter extends GFilter{

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
                    <TextField ref={(input) => this._inputs["runway"] = input}  name="runway" fullWidth defaultValue={this.getAttr(obj, "runway", "")} floatingLabelText="Runway" readOnly={readOnly}  errors={this.getAttr(errors, "runway")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["runwaySurfaceTypeId"] = input}  name="runwaySurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "runwaySurfaceTypeId", "")} options={this.getOptions(options.runwaySurfaceTypes.map(o => {return {"value": o.id, "label": o.surfaceType, "parentId": o.runwaySurfaceTypeId}}))} floatingLabelText="Runway Surface Type" readOnly={readOnly}  errors={this.getAttr(errors, "runwaySurfaceTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeId"] = input}  name="aerodromeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.aerodrome, "parentId": o.aerodromeId}}))} floatingLabelText="Aerodrome" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeId")} />
                </Col>
            </Row>
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
        return <div>
<TextField ref={(input) => this._inputs["id"] = input}  name="id" fullWidth defaultValue={this.getAttr(obj, "id", "")} floatingLabelText="Id" readOnly={readOnly}  errors={this.getAttr(errors, "id")}/>
<TextField ref={(input) => this._inputs["runway"] = input}  name="runway" fullWidth defaultValue={this.getAttr(obj, "runway", "")} floatingLabelText="Runway" readOnly={readOnly}  errors={this.getAttr(errors, "runway")}/>
<SelectField ref={(input) => this._inputs["runwaySurfaceTypeId"] = input}  name="runwaySurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "runwaySurfaceTypeId", "")} options={this.getOptions(options.runwaySurfaceTypes.map(o => {return {"value": o.id, "label": o.surfaceType, "parentId": o.runwaySurfaceTypeId}}))} floatingLabelText="Runway Surface Type" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].runwaySurfaceTypeId", "")} />
<SelectField ref={(input) => this._inputs["aerodromeId"] = input}  name="aerodromeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.aerodrome, "parentId": o.aerodromeId}}))} floatingLabelText="Aerodrome" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].aerodromeId", "")} />
        </div>
    }

}
    
      