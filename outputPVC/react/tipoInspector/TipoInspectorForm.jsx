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
<Route path="/tipoInspector/" component={TipoInspectorList} />
<Route path="/tipoInspector/new" component={TipoInspectorForm} />
<Route path="/tipoInspector/:id" component={TipoInspectorForm} />
<Route path="/tipoInspector/:page/:pageLength" component={TipoInspectorList} />
*/
export default class TipoInspectorForm extends GForm{
    showUrl =  '/tipoInspector/'
    listUrl =  '/tipoInspector/'
    apiGetUrl =  '/tipoInspector/show/'
    apiCreateUrl = '/tipoInspector/save'
    apiUpdateUrl = '/tipoInspector/update/'
    apiDeleteUrl = '/tipoInspector/delete/'
    

    objStr = 'TipoInspector'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["tipo"] = input}  name="tipo" defaultValue={obj.tipo || ""} floatingLabelText="Tipo" readOnly={readOnly} required={true} errors={errors && errors.tipo}/>
          </div>
        </div>
    }

}


      