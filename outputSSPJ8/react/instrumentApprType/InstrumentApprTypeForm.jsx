import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class InstrumentApprTypeForm extends GForm{
    showUrl =  '/instrumentApprType/'
    listUrl =  '/instrumentApprType/'
    apiGetUrl =  '/instrumentApprType/show/'
    apiCreateUrl = '/instrumentApprType/save'
    apiUpdateUrl = '/instrumentApprType/update/'
    apiDeleteUrl = '/instrumentApprType/delete/'
    

    objStr = 'Instrument Appr Type'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["instrumentApprType"] = input}  name="instrumentApprType" fullWidth defaultValue={this.getAttr(obj, "instrumentApprType", "")} floatingLabelText="Instrument Appr Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "instrumentApprType")}/>
          </div>
        </div>
    }

}


      