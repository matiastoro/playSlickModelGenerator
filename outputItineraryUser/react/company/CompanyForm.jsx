import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested


/*
<Route path="/company/" component={CompanyList} />
<Route path="/company/new" component={CompanyForm} />
<Route path="/company/:id" component={CompanyForm} />
<Route path="/company/:page/:pageLength" component={CompanyList} />
*/
export default class CompanyForm extends GForm{
    showUrl =  '/company/'
    listUrl =  '/company/'
    apiGetUrl =  '/company/show/'
    apiCreateUrl = '/company/save'
    apiUpdateUrl = '/company/update/'
    apiDeleteUrl = '/company/delete/'
    

    objStr = 'Company'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["company"] = input}  name="company" defaultValue={obj.company || ""} floatingLabelText="Company" readOnly={readOnly} required={true} errors={errors && errors.company}/>
          </div>
        </div>
    }

}


      