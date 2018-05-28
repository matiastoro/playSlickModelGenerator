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
<Route path="/season/" component={SeasonList} />
<Route path="/season/new" component={SeasonForm} />
<Route path="/season/:id" component={SeasonForm} />
<Route path="/season/:page/:pageLength" component={SeasonList} />
*/
export default class SeasonForm extends GForm{
    showUrl =  '/season/'
    listUrl =  '/season/'
    apiGetUrl =  '/season/show/'
    apiCreateUrl = '/season/save'
    apiUpdateUrl = '/season/update/'
    apiDeleteUrl = '/season/delete/'
    

    objStr = 'Season'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <SelectField ref={(input) => this._inputs["season"] = input}  name="season" defaultValue={obj.season || ""} options={[{"value": "1", "label": "Invierno"}, {"value": "2", "label": "Verano"}]} floatingLabelText="Season" readOnly={readOnly} required={true} errors={errors && errors.season} />
          </div>
          <div>
            <DateTime ref={(input) => this._inputs["from"] = input}  name="from" defaultValue={obj.from} floatingLabelText="From" readOnly={readOnly} required={true} errors={errors && errors.from} />
          </div>
          <div>
            <DateTime ref={(input) => this._inputs["to"] = input}  name="to" defaultValue={obj.to} floatingLabelText="To" readOnly={readOnly} required={true} errors={errors && errors.to} />
          </div>
        </div>
    }

}


      