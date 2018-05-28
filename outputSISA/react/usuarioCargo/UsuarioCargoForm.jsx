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
<Route path="/usuarioCargo/" component={UsuarioCargoList} />
<Route path="/usuarioCargo/new" component={UsuarioCargoForm} />
<Route path="/usuarioCargo/:id" component={UsuarioCargoForm} />
<Route path="/usuarioCargo/:page/:pageLength" component={UsuarioCargoList} />
*/
export default class UsuarioCargoForm extends GForm{
    showUrl =  '/usuarioCargo/'
    listUrl =  '/usuarioCargo/'
    apiGetUrl =  '/usuarioCargo/show/'
    apiCreateUrl = '/usuarioCargo/save'
    apiUpdateUrl = '/usuarioCargo/update/'
    apiDeleteUrl = '/usuarioCargo/delete/'
    apiOptionsUrl = "/usuarioCargo/options"

    objStr = 'UsuarioCargo'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("cargoId")?<HiddenField ref={(input) => this._inputs["cargoId"] = input} name="cargoId" defaultValue={obj.cargoId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["cargoId"] = input}  name="cargoId" defaultValue={obj.cargoId || ""} options={this.state.options.cargos.map(o => {return {"value": o.id, "label": o.cargo}})} floatingLabelText="CargoId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].cargoId"]} />}
          </div>
          <div>
            {hide.includes("usuarioId")?<HiddenField ref={(input) => this._inputs["usuarioId"] = input} name="usuarioId" defaultValue={obj.usuarioId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["usuarioId"] = input}  name="usuarioId" defaultValue={obj.usuarioId || ""} options={this.state.options.usuarios.map(o => {return {"value": o.id, "label": o.usuario}})} floatingLabelText="UsuarioId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].usuarioId"]} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["aerodromo"] = input}  name="aerodromo" defaultValue={obj.aerodromo || ""} floatingLabelText="Aerodromo" readOnly={readOnly} required={false} errors={errors && errors.aerodromo}/>
          </div>
        </div>
    }

}


export class UsuarioCargoFormInline extends GFormInline{
    apiOptionsUrl = "/usuarioCargo/options"
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
            {hide.includes("cargoId")?<HiddenField ref={(input) => this._inputs["cargoId"] = input} name="cargoId" defaultValue={obj.cargoId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["cargoId"] = input}  name="cargoId" defaultValue={obj.cargoId || ""} options={this.state.options.cargos.map(o => {return {"value": o.id, "label": o.cargo}})} floatingLabelText="CargoId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].cargoId"]} />}
          </div>
          <div>
            {hide.includes("usuarioId")?<HiddenField ref={(input) => this._inputs["usuarioId"] = input} name="usuarioId" defaultValue={obj.usuarioId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["usuarioId"] = input}  name="usuarioId" defaultValue={obj.usuarioId || ""} options={this.state.options.usuarios.map(o => {return {"value": o.id, "label": o.usuario}})} floatingLabelText="UsuarioId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].usuarioId"]} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["aerodromo"] = input}  name="aerodromo" defaultValue={obj.aerodromo || ""} floatingLabelText="Aerodromo" readOnly={readOnly} required={false} errors={errors && errors.aerodromo}/>
          </div>
        </div>
    }

}
    
      