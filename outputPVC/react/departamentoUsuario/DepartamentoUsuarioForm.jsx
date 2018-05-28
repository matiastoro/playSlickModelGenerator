import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';
import {GFormInline} from '../gforms/GForm';

//inputs de nested


/*
<Route path="/departamentoUsuario/" component={DepartamentoUsuarioList} />
<Route path="/departamentoUsuario/new" component={DepartamentoUsuarioForm} />
<Route path="/departamentoUsuario/:id" component={DepartamentoUsuarioForm} />
<Route path="/departamentoUsuario/:page/:pageLength" component={DepartamentoUsuarioList} />
*/
export default class DepartamentoUsuarioForm extends GForm{
    showUrl =  '/departamentoUsuario/'
    listUrl =  '/departamentoUsuario/'
    apiGetUrl =  '/departamentoUsuario/show/'
    apiCreateUrl = '/departamentoUsuario/save'
    apiUpdateUrl = '/departamentoUsuario/update/'
    apiDeleteUrl = '/departamentoUsuario/delete/'
    apiOptionsUrl = "/departamentoUsuario/options"

    objStr = 'DepartamentoUsuario'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("departamentoId")?<HiddenField ref={(input) => this._inputs["departamentoId"] = input} name="departamentoId" defaultValue={obj.departamentoId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["departamentoId"] = input}  name="departamentoId" defaultValue={obj.departamentoId || ""} options={this.state.options.departamentos.map(o => {return {"value": o.id, "label": o.departamento}})} floatingLabelText="DepartamentoId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].departamentoId"]} />}
          </div>
          <div>
            {hide.includes("usuarioId")?<HiddenField ref={(input) => this._inputs["usuarioId"] = input} name="usuarioId" defaultValue={obj.usuarioId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["usuarioId"] = input}  name="usuarioId" defaultValue={obj.usuarioId || ""} options={this.state.options.usuarios.map(o => {return {"value": o.id, "label": o.usuario}})} floatingLabelText="UsuarioId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].usuarioId"]} />}
          </div>
        </div>
    }

}


export class DepartamentoUsuarioFormInline extends GFormInline{
    apiOptionsUrl = "/departamentoUsuario/options"
    renderForm(){
        const obj = this.props.obj;
        const readOnly = this.props.readOnly
        const errors = this.props.errors
        const hide = this.props.hide || []
        const i = this.props.i
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("departamentoId")?<HiddenField ref={(input) => this._inputs["departamentoId"] = input} name="departamentoId" defaultValue={obj.departamentoId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["departamentoId"] = input}  name="departamentoId" defaultValue={obj.departamentoId || ""} options={this.state.options.departamentos.map(o => {return {"value": o.id, "label": o.departamento}})} floatingLabelText="DepartamentoId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].departamentoId"]} />}
          </div>
          <div>
            {hide.includes("usuarioId")?<HiddenField ref={(input) => this._inputs["usuarioId"] = input} name="usuarioId" defaultValue={obj.usuarioId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["usuarioId"] = input}  name="usuarioId" defaultValue={obj.usuarioId || ""} options={this.state.options.usuarios.map(o => {return {"value": o.id, "label": o.usuario}})} floatingLabelText="UsuarioId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].usuarioId"]} />}
          </div>
        </div>
    }

}
    
      