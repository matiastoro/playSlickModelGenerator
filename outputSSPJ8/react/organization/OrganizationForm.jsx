import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class OrganizationForm extends GForm{
    showUrl =  '/organization/'
    listUrl =  '/organization/'
    apiGetUrl =  '/organization/show/'
    apiCreateUrl = '/organization/save'
    apiUpdateUrl = '/organization/update/'
    apiDeleteUrl = '/organization/delete/'
    

    objStr = 'Organization'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["organization"] = input}  name="organization" fullWidth defaultValue={this.getAttr(obj, "organization", "")} floatingLabelText="Organization" readOnly={readOnly} required={true} errors={this.getAttr(errors, "organization")}/>
          </div>
        </div>
    }

}


      