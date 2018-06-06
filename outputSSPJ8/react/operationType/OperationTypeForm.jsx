import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class OperationTypeForm extends GForm{
    showUrl =  '/operationType/'
    listUrl =  '/operationType/'
    apiGetUrl =  '/operationType/show/'
    apiCreateUrl = '/operationType/save'
    apiUpdateUrl = '/operationType/update/'
    apiDeleteUrl = '/operationType/delete/'
    apiOptionsUrl = "/operationType/options"

    objStr = 'Operation Type'
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
            <TextField ref={(input) => this._inputs["operationType"] = input}  name="operationType" fullWidth defaultValue={this.getAttr(obj, "operationType", "")} floatingLabelText="Operation Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "operationType")}/>
          </div>
          <div>
            {hide.includes("operationTypeId")?<HiddenField ref={(input) => this._inputs["operationTypeId"] = input} name="operationTypeId" defaultValue={this.getAttr(obj, "operationTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["operationTypeId"] = input}  name="operationTypeId" fullWidth defaultValue={this.getAttr(obj, "operationTypeId", "")} options={this.getOptions(options.operationTypes.map(o => {return {"value": o.id, "label": o.operationType, "parentId": o.operationTypeId}}))} floatingLabelText="Operation Type" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operationTypeId")} />}
          </div>
        </div>
    }

}


      