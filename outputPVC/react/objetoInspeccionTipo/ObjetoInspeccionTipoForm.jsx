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
<Route path="/objetoInspeccionTipo/" component={ObjetoInspeccionTipoList} />
<Route path="/objetoInspeccionTipo/new" component={ObjetoInspeccionTipoForm} />
<Route path="/objetoInspeccionTipo/:id" component={ObjetoInspeccionTipoForm} />
<Route path="/objetoInspeccionTipo/:page/:pageLength" component={ObjetoInspeccionTipoList} />
*/
export default class ObjetoInspeccionTipoForm extends GForm{
    showUrl =  '/objetoInspeccionTipo/'
    listUrl =  '/objetoInspeccionTipo/'
    apiGetUrl =  '/objetoInspeccionTipo/show/'
    apiCreateUrl = '/objetoInspeccionTipo/save'
    apiUpdateUrl = '/objetoInspeccionTipo/update/'
    apiDeleteUrl = '/objetoInspeccionTipo/delete/'
    

    objStr = 'ObjetoInspeccionTipo'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["cantidadMes"] = input}  name="cantidadMes" defaultValue={obj.cantidadMes || ""} floatingLabelText="CantidadMes" readOnly={readOnly} required={true} errors={errors && errors.cantidadMes}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["abreviacion"] = input}  name="abreviacion" defaultValue={obj.abreviacion || ""} floatingLabelText="Abreviacion" readOnly={readOnly} required={true} errors={errors && errors.abreviacion}/>
          </div>
        </div>
    }

}


      