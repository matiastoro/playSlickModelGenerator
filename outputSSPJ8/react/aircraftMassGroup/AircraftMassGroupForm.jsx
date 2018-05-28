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
import AircraftMassGroupForm from './components/aircraftMassGroup/AircraftMassGroupForm'
import AircraftMassGroupList from './components/aircraftMassGroup/AircraftMassGroupList'

<Route path="/aircraftMassGroup/" component={AircraftMassGroupList} />
<Route path="/aircraftMassGroup/new" component={AircraftMassGroupForm} />
<Route path="/aircraftMassGroup/:id" component={AircraftMassGroupForm} />
<Route path="/aircraftMassGroup/:page/:pageLength" component={AircraftMassGroupList} />
*/
export default class AircraftMassGroupForm extends GForm{
    showUrl =  '/aircraftMassGroup/'
    listUrl =  '/aircraftMassGroup/'
    apiGetUrl =  '/aircraftMassGroup/show/'
    apiCreateUrl = '/aircraftMassGroup/save'
    apiUpdateUrl = '/aircraftMassGroup/update/'
    apiDeleteUrl = '/aircraftMassGroup/delete/'
    

    objStr = 'Aircraft Mass Group'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["massGroup"] = input}  name="massGroup" fullWidth defaultValue={this.getAttr(obj, "massGroup", "")} floatingLabelText="Mass Group" readOnly={readOnly} required={true} errors={this.getAttr(errors, "massGroup")}/>
          </div>
        </div>
    }

}


      