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



export default class NeoFlightOperationEventTypePhaseForm extends GForm{
    showUrl =  '/neoFlightOperationEventTypePhase/'
    listUrl =  '/neoFlightOperationEventTypePhase/'
    apiGetUrl =  '/neoFlightOperationEventTypePhase/show/'
    apiCreateUrl = '/neoFlightOperationEventTypePhase/save'
    apiUpdateUrl = '/neoFlightOperationEventTypePhase/update/'
    apiDeleteUrl = '/neoFlightOperationEventTypePhase/delete/'
    apiOptionsUrl = "/neoFlightOperationEventTypePhase/options"

    objStr = 'Neo Flight Operation Event Type Phase'
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
            {hide.includes("neoEventTypeId")?<HiddenField ref={(input) => this._inputs["neoEventTypeId"] = input} name="neoEventTypeId" defaultValue={this.getAttr(obj, "neoEventTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["neoEventTypeId"] = input}  name="neoEventTypeId" fullWidth defaultValue={this.getAttr(obj, "neoEventTypeId", "")} options={this.getOptions(options.neoEventTypes.map(o => {return {"value": o.id, "label": o.eventType, "parentId": o.neoEventTypeId}}))} floatingLabelText="Neo Event Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "neoEventTypeId")} />}
          </div>
          <div>
            {hide.includes("phaseId")?<HiddenField ref={(input) => this._inputs["phaseId"] = input} name="phaseId" defaultValue={this.getAttr(obj, "phaseId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["phaseId"] = input}  name="phaseId" fullWidth defaultValue={this.getAttr(obj, "phaseId", "")} options={this.getOptions(options.phases.map(o => {return {"value": o.id, "label": o.phase, "parentId": o.phaseId}}))} floatingLabelText="Phase" readOnly={readOnly} required={true} errors={this.getAttr(errors, "phaseId")} />}
          </div>
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
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("neoFlightOperationId")?<HiddenField ref={(input) => this._inputs["neoFlightOperationId"] = input} name="neoFlightOperationId" defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["neoFlightOperationId"] = input}  name="neoFlightOperationId" fullWidth defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} options={this.getOptions(options.neoFlightOperations.map(o => {return {"value": o.id, "label": o.neoFlightOperation, "parentId": o.neoFlightOperationId}}))} floatingLabelText="Neo Flight Operation" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].neoFlightOperationId", "")} />}
          </div>
          <div>
            {hide.includes("neoEventTypeId")?<HiddenField ref={(input) => this._inputs["neoEventTypeId"] = input} name="neoEventTypeId" defaultValue={this.getAttr(obj, "neoEventTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["neoEventTypeId"] = input}  name="neoEventTypeId" fullWidth defaultValue={this.getAttr(obj, "neoEventTypeId", "")} options={this.getOptions(options.neoEventTypes.map(o => {return {"value": o.id, "label": o.eventType, "parentId": o.neoEventTypeId}}))} floatingLabelText="Neo Event Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].neoEventTypeId", "")} />}
          </div>
          <div>
            {hide.includes("phaseId")?<HiddenField ref={(input) => this._inputs["phaseId"] = input} name="phaseId" defaultValue={this.getAttr(obj, "phaseId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["phaseId"] = input}  name="phaseId" fullWidth defaultValue={this.getAttr(obj, "phaseId", "")} options={this.getOptions(options.phases.map(o => {return {"value": o.id, "label": o.phase, "parentId": o.phaseId}}))} floatingLabelText="Phase" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].phaseId", "")} />}
          </div>
        </div>
    }

}
    
      