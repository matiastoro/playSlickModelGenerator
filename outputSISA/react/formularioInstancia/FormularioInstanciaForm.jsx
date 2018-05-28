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
<Route path="/formularioInstancia/" component={FormularioInstanciaList} />
<Route path="/formularioInstancia/new" component={FormularioInstanciaForm} />
<Route path="/formularioInstancia/:id" component={FormularioInstanciaForm} />
<Route path="/formularioInstancia/:page/:pageLength" component={FormularioInstanciaList} />
*/
export default class FormularioInstanciaForm extends GForm{
    showUrl =  '/formularioInstancia/'
    listUrl =  '/formularioInstancia/'
    apiGetUrl =  '/formularioInstancia/show/'
    apiCreateUrl = '/formularioInstancia/save'
    apiUpdateUrl = '/formularioInstancia/update/'
    apiDeleteUrl = '/formularioInstancia/delete/'
    apiOptionsUrl = "/formularioInstancia/options"

    objStr = 'FormularioInstancia'
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
            <TextField ref={(input) => this._inputs["designadorOaci"] = input}  name="designadorOaci" defaultValue={obj.designadorOaci || ""} floatingLabelText="DesignadorOaci" readOnly={readOnly} required={false} errors={errors && errors.designadorOaci}/>
          </div>
          <div>
            <DateTime ref={(input) => this._inputs["createdAt"] = input}  name="createdAt" defaultValue={obj.createdAt} floatingLabelText="CreatedAt" readOnly={readOnly} required={true} errors={errors && errors.createdAt} />
          </div>
          <div>
            <DateTime ref={(input) => this._inputs["updatedAt"] = input}  name="updatedAt" defaultValue={obj.updatedAt} floatingLabelText="UpdatedAt" readOnly={readOnly} required={false} errors={errors && errors.updatedAt} />
          </div>
        </div>
    }

}


      