import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class OcurrenceCategoryForm extends GForm{
    showUrl =  '/ocurrenceCategory/'
    listUrl =  '/ocurrenceCategory/'
    apiGetUrl =  '/ocurrenceCategory/show/'
    apiCreateUrl = '/ocurrenceCategory/save'
    apiUpdateUrl = '/ocurrenceCategory/update/'
    apiDeleteUrl = '/ocurrenceCategory/delete/'
    

    objStr = 'Ocurrence Category'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["code"] = input}  name="code" fullWidth defaultValue={this.getAttr(obj, "code", "")} floatingLabelText="Code" readOnly={readOnly} required={true} errors={this.getAttr(errors, "code")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["ocurrenceCategory"] = input}  name="ocurrenceCategory" fullWidth defaultValue={this.getAttr(obj, "ocurrenceCategory", "")} floatingLabelText="Ocurrence Category" readOnly={readOnly} required={true} errors={this.getAttr(errors, "ocurrenceCategory")}/>
          </div>
        </div>
    }

}


      