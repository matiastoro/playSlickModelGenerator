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
import TypeOfAirspeedForm from './components/typeOfAirspeed/TypeOfAirspeedForm'
import TypeOfAirspeedList from './components/typeOfAirspeed/TypeOfAirspeedList'

<Route path="/typeOfAirspeed/" component={TypeOfAirspeedList} />
<Route path="/typeOfAirspeed/new" component={TypeOfAirspeedForm} />
<Route path="/typeOfAirspeed/:id" component={TypeOfAirspeedForm} />
<Route path="/typeOfAirspeed/:page/:pageLength" component={TypeOfAirspeedList} />
*/
export default class TypeOfAirspeedForm extends GForm{
    showUrl =  '/typeOfAirspeed/'
    listUrl =  '/typeOfAirspeed/'
    apiGetUrl =  '/typeOfAirspeed/show/'
    apiCreateUrl = '/typeOfAirspeed/save'
    apiUpdateUrl = '/typeOfAirspeed/update/'
    apiDeleteUrl = '/typeOfAirspeed/delete/'
    

    objStr = 'Type Of Airspeed'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["typeOfAirspeed"] = input}  name="typeOfAirspeed" fullWidth defaultValue={this.getAttr(obj, "typeOfAirspeed", "")} floatingLabelText="Type Of Airspeed" readOnly={readOnly} required={true} errors={this.getAttr(errors, "typeOfAirspeed")}/>
          </div>
        </div>
    }

}


      