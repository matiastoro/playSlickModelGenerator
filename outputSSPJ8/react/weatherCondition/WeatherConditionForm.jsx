import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class WeatherConditionForm extends GForm{
    showUrl =  '/weatherCondition/'
    listUrl =  '/weatherCondition/'
    apiGetUrl =  '/weatherCondition/show/'
    apiCreateUrl = '/weatherCondition/save'
    apiUpdateUrl = '/weatherCondition/update/'
    apiDeleteUrl = '/weatherCondition/delete/'
    

    objStr = 'Weather Condition'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["weatherCondition"] = input}  name="weatherCondition" fullWidth defaultValue={this.getAttr(obj, "weatherCondition", "")} floatingLabelText="Weather Condition" readOnly={readOnly} required={true} errors={this.getAttr(errors, "weatherCondition")}/>
          </div>
        </div>
    }

}


      