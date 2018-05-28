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
import PhaseForm from './components/phase/PhaseForm'
import PhaseList from './components/phase/PhaseList'

<Route path="/phase/" component={PhaseList} />
<Route path="/phase/new" component={PhaseForm} />
<Route path="/phase/:id" component={PhaseForm} />
<Route path="/phase/:page/:pageLength" component={PhaseList} />
*/
export default class PhaseForm extends GForm{
    showUrl =  '/phase/'
    listUrl =  '/phase/'
    apiGetUrl =  '/phase/show/'
    apiCreateUrl = '/phase/save'
    apiUpdateUrl = '/phase/update/'
    apiDeleteUrl = '/phase/delete/'
    apiOptionsUrl = "/phase/options"

    objStr = 'Phase'
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
            <TextField ref={(input) => this._inputs["phase"] = input}  name="phase" fullWidth defaultValue={this.getAttr(obj, "phase", "")} floatingLabelText="Phase" readOnly={readOnly} required={true} errors={this.getAttr(errors, "phase")}/>
          </div>
          <div>
            {hide.includes("phaseId")?<HiddenField ref={(input) => this._inputs["phaseId"] = input} name="phaseId" defaultValue={this.getAttr(obj, "phaseId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["phaseId"] = input}  name="phaseId" fullWidth defaultValue={this.getAttr(obj, "phaseId", "")} options={this.getOptions(options.phases.map(o => {return {"value": o.id, "label": o.phase, "parentId": o.phaseId}}))} floatingLabelText="Phase Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "phaseId")} />}
          </div>
        </div>
    }

}


      