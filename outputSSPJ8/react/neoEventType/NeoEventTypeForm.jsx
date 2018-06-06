import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class NeoEventTypeForm extends GForm{
    showUrl =  '/neoEventType/'
    listUrl =  '/neoEventType/'
    apiGetUrl =  '/neoEventType/show/'
    apiCreateUrl = '/neoEventType/save'
    apiUpdateUrl = '/neoEventType/update/'
    apiDeleteUrl = '/neoEventType/delete/'
    

    objStr = 'Neo Event Type'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["eventType"] = input}  name="eventType" fullWidth defaultValue={this.getAttr(obj, "eventType", "")} floatingLabelText="Event Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "eventType")}/>
          </div>
        </div>
    }

}


      