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
<Route path="/profile/" component={ProfileList} />
<Route path="/profile/new" component={ProfileForm} />
<Route path="/profile/:id" component={ProfileForm} />
<Route path="/profile/:page/:pageLength" component={ProfileList} />
*/
export default class ProfileForm extends GForm{
    showUrl =  '/profile/'
    listUrl =  '/profile/'
    apiGetUrl =  '/profile/show/'
    apiCreateUrl = '/profile/save'
    apiUpdateUrl = '/profile/update/'
    apiDeleteUrl = '/profile/delete/'
    

    objStr = 'Profile'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["profile"] = input}  name="profile" defaultValue={obj.profile || ""} floatingLabelText="Profile" readOnly={readOnly} required={true} errors={errors && errors.profile}/>
          </div>
        </div>
    }

}


      