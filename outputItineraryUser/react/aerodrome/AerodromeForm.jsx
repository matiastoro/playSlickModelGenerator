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
<Route path="/aerodrome/" component={AerodromeList} />
<Route path="/aerodrome/new" component={AerodromeForm} />
<Route path="/aerodrome/:id" component={AerodromeForm} />
<Route path="/aerodrome/:page/:pageLength" component={AerodromeList} />
*/
export default class AerodromeForm extends GForm{
    showUrl =  '/aerodrome/'
    listUrl =  '/aerodrome/'
    apiGetUrl =  '/aerodrome/show/'
    apiCreateUrl = '/aerodrome/save'
    apiUpdateUrl = '/aerodrome/update/'
    apiDeleteUrl = '/aerodrome/delete/'
    

    objStr = 'Aerodrome'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["icaoCode"] = input}  name="icaoCode" defaultValue={obj.icaoCode || ""} floatingLabelText="IcaoCode" readOnly={readOnly} required={true} errors={errors && errors.icaoCode}/>
          </div>
        </div>
    }

}


      