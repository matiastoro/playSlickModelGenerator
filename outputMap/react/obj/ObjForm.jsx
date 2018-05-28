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
<Route path="/obj/" component={ObjList} />
<Route path="/obj/new" component={ObjForm} />
<Route path="/obj/:id" component={ObjForm} />
<Route path="/obj/:page/:pageLength" component={ObjList} />
*/
export default class ObjForm extends GForm{
    showUrl =  '/obj/'
    listUrl =  '/obj/'
    apiGetUrl =  '/obj/show/'
    apiCreateUrl = '/obj/save'
    apiUpdateUrl = '/obj/update/'
    apiDeleteUrl = '/obj/delete/'
    

    objStr = 'Obj'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["tpe"] = input}  name="tpe" defaultValue={obj.tpe || ""} floatingLabelText="Tpe" readOnly={readOnly} required={true} errors={errors && errors.tpe}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["q"] = input}  name="q" defaultValue={obj.q || ""} floatingLabelText="Q" readOnly={readOnly} required={true} errors={errors && errors.q}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["r"] = input}  name="r" defaultValue={obj.r || ""} floatingLabelText="R" readOnly={readOnly} required={true} errors={errors && errors.r}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["s"] = input}  name="s" defaultValue={obj.s || ""} floatingLabelText="S" readOnly={readOnly} required={true} errors={errors && errors.s}/>
          </div>
        </div>
    }

}


      