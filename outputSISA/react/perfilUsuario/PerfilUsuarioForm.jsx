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
<Route path="/perfilUsuario/" component={PerfilUsuarioList} />
<Route path="/perfilUsuario/new" component={PerfilUsuarioForm} />
<Route path="/perfilUsuario/:id" component={PerfilUsuarioForm} />
<Route path="/perfilUsuario/:page/:pageLength" component={PerfilUsuarioList} />
*/
export default class PerfilUsuarioForm extends GForm{
    showUrl =  '/perfilUsuario/'
    listUrl =  '/perfilUsuario/'
    apiGetUrl =  '/perfilUsuario/show/'
    apiCreateUrl = '/perfilUsuario/save'
    apiUpdateUrl = '/perfilUsuario/update/'
    apiDeleteUrl = '/perfilUsuario/delete/'
    apiOptionsUrl = "/perfilUsuario/options"

    objStr = 'PerfilUsuario'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("perfilId")?<HiddenField ref={(input) => this._inputs["perfilId"] = input} name="perfilId" defaultValue={obj.perfilId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["perfilId"] = input}  name="perfilId" defaultValue={obj.perfilId || ""} options={this.state.options.perfils.map(o => {return {"value": o.id, "label": o.perfil}})} floatingLabelText="PerfilId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].perfilId"]} />}
          </div>
          <div>
            {hide.includes("usuarioId")?<HiddenField ref={(input) => this._inputs["usuarioId"] = input} name="usuarioId" defaultValue={obj.usuarioId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["usuarioId"] = input}  name="usuarioId" defaultValue={obj.usuarioId || ""} options={this.state.options.usuarios.map(o => {return {"value": o.id, "label": o.usuario}})} floatingLabelText="UsuarioId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].usuarioId"]} />}
          </div>
        </div>
    }

}


export class PerfilUsuarioFormInline extends GFormInline{
    apiOptionsUrl = "/perfilUsuario/options"
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
            {hide.includes("perfilId")?<HiddenField ref={(input) => this._inputs["perfilId"] = input} name="perfilId" defaultValue={obj.perfilId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["perfilId"] = input}  name="perfilId" defaultValue={obj.perfilId || ""} options={this.state.options.perfils.map(o => {return {"value": o.id, "label": o.perfil}})} floatingLabelText="PerfilId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].perfilId"]} />}
          </div>
          <div>
            {hide.includes("usuarioId")?<HiddenField ref={(input) => this._inputs["usuarioId"] = input} name="usuarioId" defaultValue={obj.usuarioId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["usuarioId"] = input}  name="usuarioId" defaultValue={obj.usuarioId || ""} options={this.state.options.usuarios.map(o => {return {"value": o.id, "label": o.usuario}})} floatingLabelText="UsuarioId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].usuarioId"]} />}
          </div>
        </div>
    }

}
    
      