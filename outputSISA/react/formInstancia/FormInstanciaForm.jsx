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
<Route path="/formInstancia/" component={FormInstanciaList} />
<Route path="/formInstancia/new" component={FormInstanciaForm} />
<Route path="/formInstancia/:id" component={FormInstanciaForm} />
<Route path="/formInstancia/:page/:pageLength" component={FormInstanciaList} />
*/
export default class FormInstanciaForm extends GForm{
    showUrl =  '/formInstancia/'
    listUrl =  '/formInstancia/'
    apiGetUrl =  '/formInstancia/show/'
    apiCreateUrl = '/formInstancia/save'
    apiUpdateUrl = '/formInstancia/update/'
    apiDeleteUrl = '/formInstancia/delete/'
    apiOptionsUrl = "/formInstancia/options"

    objStr = 'FormInstancia'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("formId")?<HiddenField ref={(input) => this._inputs["formId"] = input} name="formId" defaultValue={obj.formId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["formId"] = input}  name="formId" defaultValue={obj.formId || ""} options={this.state.options.forms.map(o => {return {"value": o.id, "label": o.form}})} floatingLabelText="FormId" readOnly={readOnly} required={true} errors={errors && errors.formId} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["data"] = input}  name="data" defaultValue={obj.data || ""} floatingLabelText="Data" readOnly={readOnly} required={true} errors={errors && errors.data}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["estado"] = input}  name="estado" defaultValue={obj.estado || ""} floatingLabelText="Estado" readOnly={readOnly} required={true} errors={errors && errors.estado}/>
          </div>
        </div>
    }

}


      