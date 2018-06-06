import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class OperatorForm extends GForm{
    showUrl =  '/operator/'
    listUrl =  '/operator/'
    apiGetUrl =  '/operator/show/'
    apiCreateUrl = '/operator/save'
    apiUpdateUrl = '/operator/update/'
    apiDeleteUrl = '/operator/delete/'
    apiOptionsUrl = "/operator/options"

    objStr = 'Operator'
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
            <TextField ref={(input) => this._inputs["operator"] = input}  name="operator" fullWidth defaultValue={this.getAttr(obj, "operator", "")} floatingLabelText="Operator" readOnly={readOnly} required={true} errors={this.getAttr(errors, "operator")}/>
          </div>
          <div>
            {hide.includes("operatorTypeId")?<HiddenField ref={(input) => this._inputs["operatorTypeId"] = input} name="operatorTypeId" defaultValue={this.getAttr(obj, "operatorTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["operatorTypeId"] = input}  name="operatorTypeId" fullWidth defaultValue={this.getAttr(obj, "operatorTypeId", "")} options={this.getOptions(options.operatorTypes.map(o => {return {"value": o.id, "label": o.operatorType, "parentId": o.operatorTypeId}}))} floatingLabelText="Operator Type" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operatorTypeId")} />}
          </div>
        </div>
    }

}


      