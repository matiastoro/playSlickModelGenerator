import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class FlightCrewCategoryForm extends GForm{
    showUrl =  '/flightCrewCategory/'
    listUrl =  '/flightCrewCategory/'
    apiGetUrl =  '/flightCrewCategory/show/'
    apiCreateUrl = '/flightCrewCategory/save'
    apiUpdateUrl = '/flightCrewCategory/update/'
    apiDeleteUrl = '/flightCrewCategory/delete/'
    

    objStr = 'Flight Crew Category'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["category"] = input}  name="category" fullWidth defaultValue={this.getAttr(obj, "category", "")} floatingLabelText="Category" readOnly={readOnly} required={true} errors={this.getAttr(errors, "category")}/>
          </div>
        </div>
    }

}


      