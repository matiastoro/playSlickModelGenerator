import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class AerodromeTypeForm extends GForm{
    showUrl =  '/aerodromeType/'
    listUrl =  '/aerodromeType/'
    apiGetUrl =  '/aerodromeType/show/'
    apiCreateUrl = '/aerodromeType/save'
    apiUpdateUrl = '/aerodromeType/update/'
    apiDeleteUrl = '/aerodromeType/delete/'
    

    objStr = 'Aerodrome Type'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["tpe"] = input}  name="tpe" fullWidth defaultValue={this.getAttr(obj, "tpe", "")} floatingLabelText="Tpe" readOnly={readOnly} required={true} errors={this.getAttr(errors, "tpe")}/>
          </div>
        </div>
    }

}


      