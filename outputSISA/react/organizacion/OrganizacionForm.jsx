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
<Route path="/organizacion/" component={OrganizacionList} />
<Route path="/organizacion/new" component={OrganizacionForm} />
<Route path="/organizacion/:id" component={OrganizacionForm} />
<Route path="/organizacion/:page/:pageLength" component={OrganizacionList} />
*/
export default class OrganizacionForm extends GForm{
    showUrl =  '/organizacion/'
    listUrl =  '/organizacion/'
    apiGetUrl =  '/organizacion/show/'
    apiCreateUrl = '/organizacion/save'
    apiUpdateUrl = '/organizacion/update/'
    apiDeleteUrl = '/organizacion/delete/'
    

    objStr = 'Organizacion'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["organizacion"] = input}  name="organizacion" defaultValue={obj.organizacion || ""} floatingLabelText="Organizacion" readOnly={readOnly} required={true} errors={errors && errors.organizacion}/>
          </div>
        </div>
    }

}


      