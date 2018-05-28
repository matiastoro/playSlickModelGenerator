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
<Route path="/categoriaInspeccion/" component={CategoriaInspeccionList} />
<Route path="/categoriaInspeccion/new" component={CategoriaInspeccionForm} />
<Route path="/categoriaInspeccion/:id" component={CategoriaInspeccionForm} />
<Route path="/categoriaInspeccion/:page/:pageLength" component={CategoriaInspeccionList} />
*/
export default class CategoriaInspeccionForm extends GForm{
    showUrl =  '/categoriaInspeccion/'
    listUrl =  '/categoriaInspeccion/'
    apiGetUrl =  '/categoriaInspeccion/show/'
    apiCreateUrl = '/categoriaInspeccion/save'
    apiUpdateUrl = '/categoriaInspeccion/update/'
    apiDeleteUrl = '/categoriaInspeccion/delete/'
    

    objStr = 'CategoriaInspeccion'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
        </div>
    }

}


      