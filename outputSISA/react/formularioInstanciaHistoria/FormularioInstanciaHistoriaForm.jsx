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
<Route path="/formularioInstanciaHistoria/" component={FormularioInstanciaHistoriaList} />
<Route path="/formularioInstanciaHistoria/new" component={FormularioInstanciaHistoriaForm} />
<Route path="/formularioInstanciaHistoria/:id" component={FormularioInstanciaHistoriaForm} />
<Route path="/formularioInstanciaHistoria/:page/:pageLength" component={FormularioInstanciaHistoriaList} />
*/
export default class FormularioInstanciaHistoriaForm extends GForm{
    showUrl =  '/formularioInstanciaHistoria/'
    listUrl =  '/formularioInstanciaHistoria/'
    apiGetUrl =  '/formularioInstanciaHistoria/show/'
    apiCreateUrl = '/formularioInstanciaHistoria/save'
    apiUpdateUrl = '/formularioInstanciaHistoria/update/'
    apiDeleteUrl = '/formularioInstanciaHistoria/delete/'
    apiOptionsUrl = "/formularioInstanciaHistoria/options"

    objStr = 'FormularioInstanciaHistoria'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("formularioId")?<HiddenField ref={(input) => this._inputs["formularioId"] = input} name="formularioId" defaultValue={obj.formularioId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["formularioId"] = input}  name="formularioId" defaultValue={obj.formularioId || ""} options={this.state.options.formularios.map(o => {return {"value": o.id, "label": o.formulario}})} floatingLabelText="FormularioId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].formularioId"]} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["data"] = input}  name="data" defaultValue={obj.data || ""} floatingLabelText="Data" readOnly={readOnly} required={true} errors={errors && errors.data}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["estado"] = input}  name="estado" defaultValue={obj.estado || ""} floatingLabelText="Estado" readOnly={readOnly} required={true} errors={errors && errors.estado}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["files"] = input}  name="files" defaultValue={obj.files || ""} floatingLabelText="Files" readOnly={readOnly} required={false} errors={errors && errors.files}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["adjuntos"] = input}  name="adjuntos" defaultValue={obj.adjuntos || ""} floatingLabelText="Adjuntos" readOnly={readOnly} required={false} errors={errors && errors.adjuntos}/>
          </div>
          <div>
            {hide.includes("usuarioId")?<HiddenField ref={(input) => this._inputs["usuarioId"] = input} name="usuarioId" defaultValue={obj.usuarioId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["usuarioId"] = input}  name="usuarioId" defaultValue={obj.usuarioId || ""} options={this.state.options.usuarios.map(o => {return {"value": o.id, "label": o.usuario}})} floatingLabelText="UsuarioId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].usuarioId"]} />}
          </div>
          <div>
            <DateTime ref={(input) => this._inputs["createdAt"] = input}  name="createdAt" defaultValue={obj.createdAt} floatingLabelText="CreatedAt" readOnly={readOnly} required={true} errors={errors && errors.createdAt} />
          </div>
        </div>
    }

}


      