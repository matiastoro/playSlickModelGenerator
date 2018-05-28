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
<Route path="/formulario/" component={FormularioList} />
<Route path="/formulario/new" component={FormularioForm} />
<Route path="/formulario/:id" component={FormularioForm} />
<Route path="/formulario/:page/:pageLength" component={FormularioList} />
*/
export default class FormularioForm extends GForm{
    showUrl =  '/formulario/'
    listUrl =  '/formulario/'
    apiGetUrl =  '/formulario/show/'
    apiCreateUrl = '/formulario/save'
    apiUpdateUrl = '/formulario/update/'
    apiDeleteUrl = '/formulario/delete/'
    apiOptionsUrl = "/formulario/options"

    objStr = 'Formulario'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["nombre"] = input}  name="nombre" defaultValue={obj.nombre || ""} floatingLabelText="Nombre" readOnly={readOnly} required={true} errors={errors && errors.nombre}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["codigo"] = input}  name="codigo" defaultValue={obj.codigo || ""} floatingLabelText="Codigo" readOnly={readOnly} required={true} errors={errors && errors.codigo}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["key"] = input}  name="key" defaultValue={obj.key || ""} floatingLabelText="Key" readOnly={readOnly} required={true} errors={errors && errors.key}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["schema"] = input}  name="schema" defaultValue={obj.schema || ""} floatingLabelText="Schema" readOnly={readOnly} required={true} errors={errors && errors.schema}/>
          </div>
          <div>
            {hide.includes("organizacionId")?<HiddenField ref={(input) => this._inputs["organizacionId"] = input} name="organizacionId" defaultValue={obj.organizacionId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["organizacionId"] = input}  name="organizacionId" defaultValue={obj.organizacionId || ""} options={this.state.options.organizacions.map(o => {return {"value": o.id, "label": o.organizacion}})} floatingLabelText="OrganizacionId" readOnly={readOnly} required={false} errors={errors && errors[this.props.prefix+"["+i+"].organizacionId"]} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["estado"] = input}  name="estado" defaultValue={obj.estado || ""} floatingLabelText="Estado" readOnly={readOnly} required={false} errors={errors && errors.estado}/>
          </div>
        </div>
    }

}


      