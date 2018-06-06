import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class FlightRuleForm extends GForm{
    showUrl =  '/flightRule/'
    listUrl =  '/flightRule/'
    apiGetUrl =  '/flightRule/show/'
    apiCreateUrl = '/flightRule/save'
    apiUpdateUrl = '/flightRule/update/'
    apiDeleteUrl = '/flightRule/delete/'
    

    objStr = 'Flight Rule'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["flightRule"] = input}  name="flightRule" fullWidth defaultValue={this.getAttr(obj, "flightRule", "")} floatingLabelText="Flight Rule" readOnly={readOnly} required={true} errors={this.getAttr(errors, "flightRule")}/>
          </div>
        </div>
    }

}


      