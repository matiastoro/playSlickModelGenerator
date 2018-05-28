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
import NeoEventTypePhaseForm from './components/neoEventTypePhase/NeoEventTypePhaseForm'
import NeoEventTypePhaseList from './components/neoEventTypePhase/NeoEventTypePhaseList'

<Route path="/neoEventTypePhase/" component={NeoEventTypePhaseList} />
<Route path="/neoEventTypePhase/new" component={NeoEventTypePhaseForm} />
<Route path="/neoEventTypePhase/:id" component={NeoEventTypePhaseForm} />
<Route path="/neoEventTypePhase/:page/:pageLength" component={NeoEventTypePhaseList} />
*/
export default class NeoEventTypePhaseForm extends GForm{
    showUrl =  '/neoEventTypePhase/'
    listUrl =  '/neoEventTypePhase/'
    apiGetUrl =  '/neoEventTypePhase/show/'
    apiCreateUrl = '/neoEventTypePhase/save'
    apiUpdateUrl = '/neoEventTypePhase/update/'
    apiDeleteUrl = '/neoEventTypePhase/delete/'
    apiOptionsUrl = "/neoEventTypePhase/options"

    objStr = 'Neo Event Type Phase'
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
            {hide.includes("neoEventTypeId")?<HiddenField ref={(input) => this._inputs["neoEventTypeId"] = input} name="neoEventTypeId" defaultValue={this.getAttr(obj, "neoEventTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["neoEventTypeId"] = input}  name="neoEventTypeId" fullWidth defaultValue={this.getAttr(obj, "neoEventTypeId", "")} options={this.getOptions(options.neoEventTypes.map(o => {return {"value": o.id, "label": o.eventType, "parentId": o.neoEventTypeId}}))} floatingLabelText="Neo Event Type Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "neoEventTypeId")} />}
          </div>
          <div>
            {hide.includes("phaseId")?<HiddenField ref={(input) => this._inputs["phaseId"] = input} name="phaseId" defaultValue={this.getAttr(obj, "phaseId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["phaseId"] = input}  name="phaseId" fullWidth defaultValue={this.getAttr(obj, "phaseId", "")} options={this.getOptions(options.phases.map(o => {return {"value": o.id, "label": o.phase, "parentId": o.phaseId}}))} floatingLabelText="Phase Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "phaseId")} />}
          </div>
        </div>
    }

}


export class NeoEventTypePhaseFormInline extends GFormInline{
    apiOptionsUrl = "/neoEventTypePhase/options"
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
            {hide.includes("neoEventTypeId")?<HiddenField ref={(input) => this._inputs["neoEventTypeId"] = input} name="neoEventTypeId" defaultValue={this.getAttr(obj, "neoEventTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["neoEventTypeId"] = input}  name="neoEventTypeId" fullWidth defaultValue={this.getAttr(obj, "neoEventTypeId", "")} options={this.getOptions(options.neoEventTypes.map(o => {return {"value": o.id, "label": o.eventType, "parentId": o.neoEventTypeId}}))} floatingLabelText="Neo Event Type Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].neoEventTypeId", "")} />}
          </div>
          <div>
            {hide.includes("phaseId")?<HiddenField ref={(input) => this._inputs["phaseId"] = input} name="phaseId" defaultValue={this.getAttr(obj, "phaseId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["phaseId"] = input}  name="phaseId" fullWidth defaultValue={this.getAttr(obj, "phaseId", "")} options={this.getOptions(options.phases.map(o => {return {"value": o.id, "label": o.phase, "parentId": o.phaseId}}))} floatingLabelText="Phase Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].phaseId", "")} />}
          </div>
        </div>
    }

}
    
      