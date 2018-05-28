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
import LicenseValidityForm from './components/licenseValidity/LicenseValidityForm'
import LicenseValidityList from './components/licenseValidity/LicenseValidityList'

<Route path="/licenseValidity/" component={LicenseValidityList} />
<Route path="/licenseValidity/new" component={LicenseValidityForm} />
<Route path="/licenseValidity/:id" component={LicenseValidityForm} />
<Route path="/licenseValidity/:page/:pageLength" component={LicenseValidityList} />
*/
export default class LicenseValidityForm extends GForm{
    showUrl =  '/licenseValidity/'
    listUrl =  '/licenseValidity/'
    apiGetUrl =  '/licenseValidity/show/'
    apiCreateUrl = '/licenseValidity/save'
    apiUpdateUrl = '/licenseValidity/update/'
    apiDeleteUrl = '/licenseValidity/delete/'
    

    objStr = 'License Validity'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["licenseValidity"] = input}  name="licenseValidity" fullWidth defaultValue={this.getAttr(obj, "licenseValidity", "")} floatingLabelText="License Validity" readOnly={readOnly} required={true} errors={this.getAttr(errors, "licenseValidity")}/>
          </div>
        </div>
    }

}


      