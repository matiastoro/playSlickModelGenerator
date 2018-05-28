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
import LicenseRatingsForm from './components/licenseRatings/LicenseRatingsForm'
import LicenseRatingsList from './components/licenseRatings/LicenseRatingsList'

<Route path="/licenseRatings/" component={LicenseRatingsList} />
<Route path="/licenseRatings/new" component={LicenseRatingsForm} />
<Route path="/licenseRatings/:id" component={LicenseRatingsForm} />
<Route path="/licenseRatings/:page/:pageLength" component={LicenseRatingsList} />
*/
export default class LicenseRatingsForm extends GForm{
    showUrl =  '/licenseRatings/'
    listUrl =  '/licenseRatings/'
    apiGetUrl =  '/licenseRatings/show/'
    apiCreateUrl = '/licenseRatings/save'
    apiUpdateUrl = '/licenseRatings/update/'
    apiDeleteUrl = '/licenseRatings/delete/'
    

    objStr = 'License Ratings'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["licenseRatings"] = input}  name="licenseRatings" fullWidth defaultValue={this.getAttr(obj, "licenseRatings", "")} floatingLabelText="License Ratings" readOnly={readOnly} required={true} errors={this.getAttr(errors, "licenseRatings")}/>
          </div>
        </div>
    }

}


      