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




export default class NeoFlightOperationEventTypePhaseFilter extends GFilter{

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
                    <SelectField ref={(input) => this._inputs["neoFlightOperationId"] = input}  name="neoFlightOperationId" fullWidth defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} options={this.getOptions(options.neoFlightOperations.map(o => {return {"value": o.id, "label": o.neoFlightOperation, "parentId": o.neoFlightOperationId}}))} floatingLabelText="Neo Flight Operation" readOnly={readOnly}  errors={this.getAttr(errors, "neoFlightOperationId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["neoEventTypeId"] = input}  name="neoEventTypeId" fullWidth defaultValue={this.getAttr(obj, "neoEventTypeId", "")} options={this.getOptions(options.neoEventTypes.map(o => {return {"value": o.id, "label": o.eventType, "parentId": o.neoEventTypeId}}))} floatingLabelText="Neo Event Type" readOnly={readOnly}  errors={this.getAttr(errors, "neoEventTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["phaseId"] = input}  name="phaseId" fullWidth defaultValue={this.getAttr(obj, "phaseId", "")} options={this.getOptions(options.phases.map(o => {return {"value": o.id, "label": o.phase, "parentId": o.phaseId}}))} floatingLabelText="Phase" readOnly={readOnly}  errors={this.getAttr(errors, "phaseId")} />
                </Col>
            </Row>
        </div>
    }

}


export class NeoFlightOperationEventTypePhaseFormInline extends GFormInline{
    apiOptionsUrl = "/neoFlightOperationEventTypePhase/options"
    renderForm(){
        const obj = this.props.obj;
        const readOnly = this.props.readOnly
        const errors = this.props.errors
        const hide = this.props.hide || []
        const i = this.props.i
        return <div>
<TextField ref={(input) => this._inputs["id"] = input}  name="id" fullWidth defaultValue={this.getAttr(obj, "id", "")} floatingLabelText="Id" readOnly={readOnly}  errors={this.getAttr(errors, "id")}/>
<SelectField ref={(input) => this._inputs["neoFlightOperationId"] = input}  name="neoFlightOperationId" fullWidth defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} options={this.getOptions(options.neoFlightOperations.map(o => {return {"value": o.id, "label": o.neoFlightOperation, "parentId": o.neoFlightOperationId}}))} floatingLabelText="Neo Flight Operation" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].neoFlightOperationId", "")} />
<SelectField ref={(input) => this._inputs["neoEventTypeId"] = input}  name="neoEventTypeId" fullWidth defaultValue={this.getAttr(obj, "neoEventTypeId", "")} options={this.getOptions(options.neoEventTypes.map(o => {return {"value": o.id, "label": o.eventType, "parentId": o.neoEventTypeId}}))} floatingLabelText="Neo Event Type" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].neoEventTypeId", "")} />
<SelectField ref={(input) => this._inputs["phaseId"] = input}  name="phaseId" fullWidth defaultValue={this.getAttr(obj, "phaseId", "")} options={this.getOptions(options.phases.map(o => {return {"value": o.id, "label": o.phase, "parentId": o.phaseId}}))} floatingLabelText="Phase" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].phaseId", "")} />
        </div>
    }

}
    
      