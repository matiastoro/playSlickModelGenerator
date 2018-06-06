import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class OcurrenceClassForm extends GForm{
    showUrl =  '/ocurrenceClass/'
    listUrl =  '/ocurrenceClass/'
    apiGetUrl =  '/ocurrenceClass/show/'
    apiCreateUrl = '/ocurrenceClass/save'
    apiUpdateUrl = '/ocurrenceClass/update/'
    apiDeleteUrl = '/ocurrenceClass/delete/'
    

    objStr = 'Ocurrence Class'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["ocurrenceClass"] = input}  name="ocurrenceClass" fullWidth defaultValue={this.getAttr(obj, "ocurrenceClass", "")} floatingLabelText="Ocurrence Class" readOnly={readOnly} required={true} errors={this.getAttr(errors, "ocurrenceClass")}/>
          </div>
        </div>
    }

}


      