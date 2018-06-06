import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class AircraftWakeTurbCategoryForm extends GForm{
    showUrl =  '/aircraftWakeTurbCategory/'
    listUrl =  '/aircraftWakeTurbCategory/'
    apiGetUrl =  '/aircraftWakeTurbCategory/show/'
    apiCreateUrl = '/aircraftWakeTurbCategory/save'
    apiUpdateUrl = '/aircraftWakeTurbCategory/update/'
    apiDeleteUrl = '/aircraftWakeTurbCategory/delete/'
    

    objStr = 'Aircraft Wake Turb Category'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["wakeTurbCategory"] = input}  name="wakeTurbCategory" fullWidth defaultValue={this.getAttr(obj, "wakeTurbCategory", "")} floatingLabelText="Wake Turb Category" readOnly={readOnly} required={true} errors={this.getAttr(errors, "wakeTurbCategory")}/>
          </div>
        </div>
    }

}


      