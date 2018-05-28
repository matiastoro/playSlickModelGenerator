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
import AircraftTypeOfAirspeedForm from './components/aircraftTypeOfAirspeed/AircraftTypeOfAirspeedForm'
import AircraftTypeOfAirspeedList from './components/aircraftTypeOfAirspeed/AircraftTypeOfAirspeedList'

<Route path="/aircraftTypeOfAirspeed/" component={AircraftTypeOfAirspeedList} />
<Route path="/aircraftTypeOfAirspeed/new" component={AircraftTypeOfAirspeedForm} />
<Route path="/aircraftTypeOfAirspeed/:id" component={AircraftTypeOfAirspeedForm} />
<Route path="/aircraftTypeOfAirspeed/:page/:pageLength" component={AircraftTypeOfAirspeedList} />
*/
export default class AircraftTypeOfAirspeedForm extends GForm{
    showUrl =  '/aircraftTypeOfAirspeed/'
    listUrl =  '/aircraftTypeOfAirspeed/'
    apiGetUrl =  '/aircraftTypeOfAirspeed/show/'
    apiCreateUrl = '/aircraftTypeOfAirspeed/save'
    apiUpdateUrl = '/aircraftTypeOfAirspeed/update/'
    apiDeleteUrl = '/aircraftTypeOfAirspeed/delete/'
    

    objStr = 'Aircraft Type Of Airspeed'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
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


      