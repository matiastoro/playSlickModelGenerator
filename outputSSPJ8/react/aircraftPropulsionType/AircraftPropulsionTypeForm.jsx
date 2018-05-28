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
import AircraftPropulsionTypeForm from './components/aircraftPropulsionType/AircraftPropulsionTypeForm'
import AircraftPropulsionTypeList from './components/aircraftPropulsionType/AircraftPropulsionTypeList'

<Route path="/aircraftPropulsionType/" component={AircraftPropulsionTypeList} />
<Route path="/aircraftPropulsionType/new" component={AircraftPropulsionTypeForm} />
<Route path="/aircraftPropulsionType/:id" component={AircraftPropulsionTypeForm} />
<Route path="/aircraftPropulsionType/:page/:pageLength" component={AircraftPropulsionTypeList} />
*/
export default class AircraftPropulsionTypeForm extends GForm{
    showUrl =  '/aircraftPropulsionType/'
    listUrl =  '/aircraftPropulsionType/'
    apiGetUrl =  '/aircraftPropulsionType/show/'
    apiCreateUrl = '/aircraftPropulsionType/save'
    apiUpdateUrl = '/aircraftPropulsionType/update/'
    apiDeleteUrl = '/aircraftPropulsionType/delete/'
    

    objStr = 'Aircraft Propulsion Type'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["propulsionType"] = input}  name="propulsionType" fullWidth defaultValue={this.getAttr(obj, "propulsionType", "")} floatingLabelText="Propulsion Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "propulsionType")}/>
          </div>
        </div>
    }

}


      