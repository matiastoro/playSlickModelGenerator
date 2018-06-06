import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class AerodromeStatusForm extends GForm{
    showUrl =  '/aerodromeStatus/'
    listUrl =  '/aerodromeStatus/'
    apiGetUrl =  '/aerodromeStatus/show/'
    apiCreateUrl = '/aerodromeStatus/save'
    apiUpdateUrl = '/aerodromeStatus/update/'
    apiDeleteUrl = '/aerodromeStatus/delete/'
    

    objStr = 'Aerodrome Status'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["status"] = input}  name="status" fullWidth defaultValue={this.getAttr(obj, "status", "")} floatingLabelText="Status" readOnly={readOnly} required={true} errors={this.getAttr(errors, "status")}/>
          </div>
        </div>
    }

}


      