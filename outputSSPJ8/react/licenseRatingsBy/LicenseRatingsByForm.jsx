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
import LicenseRatingsByForm from './components/licenseRatingsBy/LicenseRatingsByForm'
import LicenseRatingsByList from './components/licenseRatingsBy/LicenseRatingsByList'

<Route path="/licenseRatingsBy/" component={LicenseRatingsByList} />
<Route path="/licenseRatingsBy/new" component={LicenseRatingsByForm} />
<Route path="/licenseRatingsBy/:id" component={LicenseRatingsByForm} />
<Route path="/licenseRatingsBy/:page/:pageLength" component={LicenseRatingsByList} />
*/
export default class LicenseRatingsByForm extends GForm{
    showUrl =  '/licenseRatingsBy/'
    listUrl =  '/licenseRatingsBy/'
    apiGetUrl =  '/licenseRatingsBy/show/'
    apiCreateUrl = '/licenseRatingsBy/save'
    apiUpdateUrl = '/licenseRatingsBy/update/'
    apiDeleteUrl = '/licenseRatingsBy/delete/'
    

    objStr = 'License Ratings By'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
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


      