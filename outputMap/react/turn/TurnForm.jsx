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
<Route path="/turn/" component={TurnList} />
<Route path="/turn/new" component={TurnForm} />
<Route path="/turn/:id" component={TurnForm} />
<Route path="/turn/:page/:pageLength" component={TurnList} />
*/
export default class TurnForm extends GForm{
    showUrl =  '/turn/'
    listUrl =  '/turn/'
    apiGetUrl =  '/turn/show/'
    apiCreateUrl = '/turn/save'
    apiUpdateUrl = '/turn/update/'
    apiDeleteUrl = '/turn/delete/'
    

    objStr = 'Turn'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["turn"] = input}  name="turn" defaultValue={obj.turn || ""} floatingLabelText="Turn" readOnly={readOnly} required={true} errors={errors && errors.turn}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["observations"] = input}  name="observations" defaultValue={obj.observations || ""} floatingLabelText="Observations" readOnly={readOnly} required={true} errors={errors && errors.observations}/>
          </div>


        </div>
    }

}


      