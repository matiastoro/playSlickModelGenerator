import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';

import {GNestedForms} from '../gforms/GForm';
import {ProgramaTipoInspectorFormInline} from '../programaTipoInspector/ProgramaTipoInspectorForm'
      
//inputs de nested


/*
<Route path="/programa/" component={ProgramaList} />
<Route path="/programa/new" component={ProgramaForm} />
<Route path="/programa/:id" component={ProgramaForm} />
<Route path="/programa/:page/:pageLength" component={ProgramaList} />
*/
export default class ProgramaForm extends GForm{
    showUrl =  '/programa/'
    listUrl =  '/programa/'
    apiGetUrl =  '/programa/show/'
    apiCreateUrl = '/programa/save'
    apiUpdateUrl = '/programa/update/'
    apiDeleteUrl = '/programa/delete/'
    apiOptionsUrl = "/programa/options"

    objStr = 'Programa'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["programa"] = input}  name="programa" defaultValue={obj.programa || ""} floatingLabelText="Programa" readOnly={readOnly} required={true} errors={errors && errors.programa}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["acronimo"] = input}  name="acronimo" defaultValue={obj.acronimo || ""} floatingLabelText="Acronimo" readOnly={readOnly} required={true} errors={errors && errors.acronimo}/>
          </div>
          <div>
            {hide.includes("departamentoId")?<HiddenField ref={(input) => this._inputs["departamentoId"] = input} name="departamentoId" defaultValue={obj.departamentoId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["departamentoId"] = input}  name="departamentoId" defaultValue={obj.departamentoId || ""} options={this.state.options.departamentos.map(o => {return {"value": o.id, "label": o.departamento}})} floatingLabelText="DepartamentoId" readOnly={readOnly} required={false} errors={errors && errors[this.props.prefix+"["+i+"].departamentoId"]} />}
          </div>
          <div>
            {hide.includes("categoriaInspeccionId")?<HiddenField ref={(input) => this._inputs["categoriaInspeccionId"] = input} name="categoriaInspeccionId" defaultValue={obj.categoriaInspeccionId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["categoriaInspeccionId"] = input}  name="categoriaInspeccionId" defaultValue={obj.categoriaInspeccionId || ""} options={this.state.options.categoria_inspeccions.map(o => {return {"value": o.id, "label": o.categoriaInspeccion}})} floatingLabelText="CategoriaInspeccionId" readOnly={readOnly} required={false} errors={errors && errors[this.props.prefix+"["+i+"].categoriaInspeccionId"]} />}
          </div>
          <div>
            {hide.includes("objetoInspeccionTipoId")?<HiddenField ref={(input) => this._inputs["objetoInspeccionTipoId"] = input} name="objetoInspeccionTipoId" defaultValue={obj.objetoInspeccionTipoId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["objetoInspeccionTipoId"] = input}  name="objetoInspeccionTipoId" defaultValue={obj.objetoInspeccionTipoId || ""} options={this.state.options.objeto_inspeccion_tipos.map(o => {return {"value": o.id, "label": o.objetoInspeccionTipo}})} floatingLabelText="ObjetoInspeccionTipoId" readOnly={readOnly} required={false} errors={errors && errors[this.props.prefix+"["+i+"].objetoInspeccionTipoId"]} />}
          </div>
            <GNestedForms ref={(i) => this._inputs["programaTipoInspectors"] = i} description="ProgramaTipoInspectors" prefix="programaTipoInspectors" readOnly={readOnly} objs={obj.programaTipoInspectors} renderNested={(nobj, k, refFunc) => <ProgramaTipoInspectorFormInline i={k} obj={Object.assign({programaId: obj.id},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["programaId"]} errors={errors} prefix="programaTipoInspectors"/>}/>
        </div>
    }

}


      