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
<Route path="/usuarioTipoInspector/" component={UsuarioTipoInspectorList} />
<Route path="/usuarioTipoInspector/new" component={UsuarioTipoInspectorForm} />
<Route path="/usuarioTipoInspector/:id" component={UsuarioTipoInspectorForm} />
<Route path="/usuarioTipoInspector/:page/:pageLength" component={UsuarioTipoInspectorList} />
*/
export default class UsuarioTipoInspectorForm extends GForm{
    showUrl =  '/usuarioTipoInspector/'
    listUrl =  '/usuarioTipoInspector/'
    apiGetUrl =  '/usuarioTipoInspector/show/'
    apiCreateUrl = '/usuarioTipoInspector/save'
    apiUpdateUrl = '/usuarioTipoInspector/update/'
    apiDeleteUrl = '/usuarioTipoInspector/delete/'
    apiOptionsUrl = "/usuarioTipoInspector/options"

    objStr = 'UsuarioTipoInspector'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("usuarioId")?<HiddenField ref={(input) => this._inputs["usuarioId"] = input} name="usuarioId" defaultValue={obj.usuarioId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["usuarioId"] = input}  name="usuarioId" defaultValue={obj.usuarioId || ""} options={this.state.options.usuarios.map(o => {return {"value": o.id, "label": o.usuario}})} floatingLabelText="UsuarioId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].usuarioId"]} />}
          </div>
          <div>
            {hide.includes("tipoInspectorId")?<HiddenField ref={(input) => this._inputs["tipoInspectorId"] = input} name="tipoInspectorId" defaultValue={obj.tipoInspectorId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["tipoInspectorId"] = input}  name="tipoInspectorId" defaultValue={obj.tipoInspectorId || ""} options={this.state.options.tipo_inspectors.map(o => {return {"value": o.id, "label": o.tipoInspector}})} floatingLabelText="TipoInspectorId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].tipoInspectorId"]} />}
          </div>
        </div>
    }

}


export class UsuarioTipoInspectorFormInline extends GFormInline{
    apiOptionsUrl = "/usuarioTipoInspector/options"
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
            {hide.includes("usuarioId")?<HiddenField ref={(input) => this._inputs["usuarioId"] = input} name="usuarioId" defaultValue={obj.usuarioId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["usuarioId"] = input}  name="usuarioId" defaultValue={obj.usuarioId || ""} options={this.state.options.usuarios.map(o => {return {"value": o.id, "label": o.usuario}})} floatingLabelText="UsuarioId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].usuarioId"]} />}
          </div>
          <div>
            {hide.includes("tipoInspectorId")?<HiddenField ref={(input) => this._inputs["tipoInspectorId"] = input} name="tipoInspectorId" defaultValue={obj.tipoInspectorId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["tipoInspectorId"] = input}  name="tipoInspectorId" defaultValue={obj.tipoInspectorId || ""} options={this.state.options.tipo_inspectors.map(o => {return {"value": o.id, "label": o.tipoInspector}})} floatingLabelText="TipoInspectorId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].tipoInspectorId"]} />}
          </div>
        </div>
    }

}
    
      