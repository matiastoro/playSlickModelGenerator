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
import AerodromeLocationForm from './components/aerodromeLocation/AerodromeLocationForm'
import AerodromeLocationList from './components/aerodromeLocation/AerodromeLocationList'

<Route path="/aerodromeLocation/" component={AerodromeLocationList} />
<Route path="/aerodromeLocation/new" component={AerodromeLocationForm} />
<Route path="/aerodromeLocation/:id" component={AerodromeLocationForm} />
<Route path="/aerodromeLocation/:page/:pageLength" component={AerodromeLocationList} />
*/
export default class AerodromeLocationForm extends GForm{
    showUrl =  '/aerodromeLocation/'
    listUrl =  '/aerodromeLocation/'
    apiGetUrl =  '/aerodromeLocation/show/'
    apiCreateUrl = '/aerodromeLocation/save'
    apiUpdateUrl = '/aerodromeLocation/update/'
    apiDeleteUrl = '/aerodromeLocation/delete/'
    

    objStr = 'Aerodrome Location'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["location"] = input}  name="location" fullWidth defaultValue={this.getAttr(obj, "location", "")} floatingLabelText="Location" readOnly={readOnly} required={true} errors={this.getAttr(errors, "location")}/>
          </div>
        </div>
    }

}


      