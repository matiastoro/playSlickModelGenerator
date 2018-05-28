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
<Route path="/form/" component={FormList} />
<Route path="/form/new" component={FormForm} />
<Route path="/form/:id" component={FormForm} />
<Route path="/form/:page/:pageLength" component={FormList} />
*/
export default class FormForm extends GForm{
    showUrl =  '/form/'
    listUrl =  '/form/'
    apiGetUrl =  '/form/show/'
    apiCreateUrl = '/form/save'
    apiUpdateUrl = '/form/update/'
    apiDeleteUrl = '/form/delete/'
    apiOptionsUrl = "/form/options"

    objStr = 'Form'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["codigo"] = input}  name="codigo" defaultValue={obj.codigo || ""} floatingLabelText="Codigo" readOnly={readOnly} required={true} errors={errors && errors.codigo}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["schema"] = input}  name="schema" defaultValue={obj.schema || ""} floatingLabelText="Schema" readOnly={readOnly} required={true} errors={errors && errors.schema}/>
          </div>
          <div>
            {hide.includes("organizacionId")?<HiddenField ref={(input) => this._inputs["organizacionId"] = input} name="organizacionId" defaultValue={obj.organizacionId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["organizacionId"] = input}  name="organizacionId" defaultValue={obj.organizacionId || ""} options={this.state.options.organizacions.map(o => {return {"value": o.id, "label": o.organizacion}})} floatingLabelText="OrganizacionId" readOnly={readOnly} required={false} errors={errors && errors.organizacionId} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["estado"] = input}  name="estado" defaultValue={obj.estado || ""} floatingLabelText="Estado" readOnly={readOnly} required={true} errors={errors && errors.estado}/>
          </div>
        </div>
    }

}


      