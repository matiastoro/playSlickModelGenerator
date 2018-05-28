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
<Route path="/army/" component={ArmyList} />
<Route path="/army/new" component={ArmyForm} />
<Route path="/army/:id" component={ArmyForm} />
<Route path="/army/:page/:pageLength" component={ArmyList} />
*/
export default class ArmyForm extends GForm{
    showUrl =  '/army/'
    listUrl =  '/army/'
    apiGetUrl =  '/army/show/'
    apiCreateUrl = '/army/save'
    apiUpdateUrl = '/army/update/'
    apiDeleteUrl = '/army/delete/'
    

    objStr = 'Army'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["army"] = input}  name="army" defaultValue={obj.army || ""} floatingLabelText="Army" readOnly={readOnly} required={true} errors={errors && errors.army}/>
          </div>
        </div>
    }

}


      