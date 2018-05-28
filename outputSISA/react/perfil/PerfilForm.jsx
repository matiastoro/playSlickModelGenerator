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
<Route path="/perfil/" component={PerfilList} />
<Route path="/perfil/new" component={PerfilForm} />
<Route path="/perfil/:id" component={PerfilForm} />
<Route path="/perfil/:page/:pageLength" component={PerfilList} />
*/
export default class PerfilForm extends GForm{
    showUrl =  '/perfil/'
    listUrl =  '/perfil/'
    apiGetUrl =  '/perfil/show/'
    apiCreateUrl = '/perfil/save'
    apiUpdateUrl = '/perfil/update/'
    apiDeleteUrl = '/perfil/delete/'
    

    objStr = 'Perfil'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["perfil"] = input}  name="perfil" defaultValue={obj.perfil || ""} floatingLabelText="Perfil" readOnly={readOnly} required={true} errors={errors && errors.perfil}/>
          </div>
        </div>
    }

}


      