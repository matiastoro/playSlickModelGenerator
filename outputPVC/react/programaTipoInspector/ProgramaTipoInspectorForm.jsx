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
<Route path="/programaTipoInspector/" component={ProgramaTipoInspectorList} />
<Route path="/programaTipoInspector/new" component={ProgramaTipoInspectorForm} />
<Route path="/programaTipoInspector/:id" component={ProgramaTipoInspectorForm} />
<Route path="/programaTipoInspector/:page/:pageLength" component={ProgramaTipoInspectorList} />
*/
export default class ProgramaTipoInspectorForm extends GForm{
    showUrl =  '/programaTipoInspector/'
    listUrl =  '/programaTipoInspector/'
    apiGetUrl =  '/programaTipoInspector/show/'
    apiCreateUrl = '/programaTipoInspector/save'
    apiUpdateUrl = '/programaTipoInspector/update/'
    apiDeleteUrl = '/programaTipoInspector/delete/'
    apiOptionsUrl = "/programaTipoInspector/options"

    objStr = 'ProgramaTipoInspector'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("programaId")?<HiddenField ref={(input) => this._inputs["programaId"] = input} name="programaId" defaultValue={obj.programaId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["programaId"] = input}  name="programaId" defaultValue={obj.programaId || ""} options={this.state.options.programas.map(o => {return {"value": o.id, "label": o.programa}})} floatingLabelText="ProgramaId" readOnly={readOnly} required={false} errors={errors && errors[this.props.prefix+"["+i+"].programaId"]} />}
          </div>
          <div>
            {hide.includes("tipoInspectorId")?<HiddenField ref={(input) => this._inputs["tipoInspectorId"] = input} name="tipoInspectorId" defaultValue={obj.tipoInspectorId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["tipoInspectorId"] = input}  name="tipoInspectorId" defaultValue={obj.tipoInspectorId || ""} options={this.state.options.tipo_inspectors.map(o => {return {"value": o.id, "label": o.tipoInspector}})} floatingLabelText="TipoInspectorId" readOnly={readOnly} required={false} errors={errors && errors[this.props.prefix+"["+i+"].tipoInspectorId"]} />}
          </div>
        </div>
    }

}


export class ProgramaTipoInspectorFormInline extends GFormInline{
    apiOptionsUrl = "/programaTipoInspector/options"
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
            {hide.includes("programaId")?<HiddenField ref={(input) => this._inputs["programaId"] = input} name="programaId" defaultValue={obj.programaId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["programaId"] = input}  name="programaId" defaultValue={obj.programaId || ""} options={this.state.options.programas.map(o => {return {"value": o.id, "label": o.programa}})} floatingLabelText="ProgramaId" readOnly={readOnly} required={false} errors={errors && errors[this.props.prefix+"["+i+"].programaId"]} />}
          </div>
          <div>
            {hide.includes("tipoInspectorId")?<HiddenField ref={(input) => this._inputs["tipoInspectorId"] = input} name="tipoInspectorId" defaultValue={obj.tipoInspectorId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["tipoInspectorId"] = input}  name="tipoInspectorId" defaultValue={obj.tipoInspectorId || ""} options={this.state.options.tipo_inspectors.map(o => {return {"value": o.id, "label": o.tipoInspector}})} floatingLabelText="TipoInspectorId" readOnly={readOnly} required={false} errors={errors && errors[this.props.prefix+"["+i+"].tipoInspectorId"]} />}
          </div>
        </div>
    }

}
    
      