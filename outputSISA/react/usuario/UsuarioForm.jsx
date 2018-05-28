import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';

import {GNestedForms} from '../gforms/GForm';
import {UsuarioCargoFormInline} from '../usuarioCargo/UsuarioCargoForm'
import {PerfilUsuarioFormInline} from '../perfilUsuario/PerfilUsuarioForm'
      
//inputs de nested


/*
<Route path="/usuario/" component={UsuarioList} />
<Route path="/usuario/new" component={UsuarioForm} />
<Route path="/usuario/:id" component={UsuarioForm} />
<Route path="/usuario/:page/:pageLength" component={UsuarioList} />
*/
export default class UsuarioForm extends GForm{
    showUrl =  '/usuario/'
    listUrl =  '/usuario/'
    apiGetUrl =  '/usuario/show/'
    apiCreateUrl = '/usuario/save'
    apiUpdateUrl = '/usuario/update/'
    apiDeleteUrl = '/usuario/delete/'
    

    objStr = 'Usuario'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["nombre"] = input}  name="nombre" defaultValue={obj.nombre || ""} floatingLabelText="Nombre" readOnly={readOnly} required={true} errors={errors && errors.nombre}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["email"] = input}  name="email" defaultValue={obj.email || ""} floatingLabelText="Email" readOnly={readOnly} required={true} errors={errors && errors.email}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["contrasena"] = input}  name="contrasena" defaultValue={obj.contrasena || ""} floatingLabelText="Contrasena" readOnly={readOnly} required={true} errors={errors && errors.contrasena}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["estado"] = input}  name="estado" defaultValue={obj.estado || ""} floatingLabelText="Estado" readOnly={readOnly} required={true} errors={errors && errors.estado}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["rut"] = input}  name="rut" defaultValue={obj.rut || ""} floatingLabelText="Rut" readOnly={readOnly} required={false} errors={errors && errors.rut}/>
          </div>
            <GNestedForms ref={(i) => this._inputs["usuarioCargos"] = i} description="UsuarioCargos" prefix="usuarioCargos" readOnly={readOnly} objs={obj.usuarioCargos} renderNested={(nobj, k, refFunc) => <UsuarioCargoFormInline i={k} obj={Object.assign({usuarioId: obj.id},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["usuarioId"]} errors={errors} prefix="usuarioCargos"/>}/>
            <GNestedForms ref={(i) => this._inputs["perfilUsuarios"] = i} description="PerfilUsuarios" prefix="perfilUsuarios" readOnly={readOnly} objs={obj.perfilUsuarios} renderNested={(nobj, k, refFunc) => <PerfilUsuarioFormInline i={k} obj={Object.assign({usuarioId: obj.id},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["usuarioId"]} errors={errors} prefix="perfilUsuarios"/>}/>
        </div>
    }

}


      