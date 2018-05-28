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
import ReportingEntityForm from './components/reportingEntity/ReportingEntityForm'
import ReportingEntityList from './components/reportingEntity/ReportingEntityList'

<Route path="/reportingEntity/" component={ReportingEntityList} />
<Route path="/reportingEntity/new" component={ReportingEntityForm} />
<Route path="/reportingEntity/:id" component={ReportingEntityForm} />
<Route path="/reportingEntity/:page/:pageLength" component={ReportingEntityList} />
*/
export default class ReportingEntityForm extends GForm{
    showUrl =  '/reportingEntity/'
    listUrl =  '/reportingEntity/'
    apiGetUrl =  '/reportingEntity/show/'
    apiCreateUrl = '/reportingEntity/save'
    apiUpdateUrl = '/reportingEntity/update/'
    apiDeleteUrl = '/reportingEntity/delete/'
    apiOptionsUrl = "/reportingEntity/options"

    objStr = 'Reporting Entity'
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
            <TextField ref={(input) => this._inputs["reportingEntity"] = input}  name="reportingEntity" fullWidth defaultValue={this.getAttr(obj, "reportingEntity", "")} floatingLabelText="Reporting Entity" readOnly={readOnly} required={true} errors={this.getAttr(errors, "reportingEntity")}/>
          </div>
          <div>
            {hide.includes("reportingEntityId")?<HiddenField ref={(input) => this._inputs["reportingEntityId"] = input} name="reportingEntityId" defaultValue={this.getAttr(obj, "reportingEntityId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["reportingEntityId"] = input}  name="reportingEntityId" fullWidth defaultValue={this.getAttr(obj, "reportingEntityId", "")} options={this.getOptions(options.reportingEntitys.map(o => {return {"value": o.id, "label": o.reportingEntity, "parentId": o.reportingEntityId}}))} floatingLabelText="Reporting Entity Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "reportingEntityId")} />}
          </div>
        </div>
    }

}


      