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
import InjuryLevelForm from './components/injuryLevel/InjuryLevelForm'
import InjuryLevelList from './components/injuryLevel/InjuryLevelList'

<Route path="/injuryLevel/" component={InjuryLevelList} />
<Route path="/injuryLevel/new" component={InjuryLevelForm} />
<Route path="/injuryLevel/:id" component={InjuryLevelForm} />
<Route path="/injuryLevel/:page/:pageLength" component={InjuryLevelList} />
*/
export default class InjuryLevelForm extends GForm{
    showUrl =  '/injuryLevel/'
    listUrl =  '/injuryLevel/'
    apiGetUrl =  '/injuryLevel/show/'
    apiCreateUrl = '/injuryLevel/save'
    apiUpdateUrl = '/injuryLevel/update/'
    apiDeleteUrl = '/injuryLevel/delete/'
    

    objStr = 'Injury Level'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["injuryLevel"] = input}  name="injuryLevel" fullWidth defaultValue={this.getAttr(obj, "injuryLevel", "")} floatingLabelText="Injury Level" readOnly={readOnly} required={true} errors={this.getAttr(errors, "injuryLevel")}/>
          </div>
        </div>
    }

}


      