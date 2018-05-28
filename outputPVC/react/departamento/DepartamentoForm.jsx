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
<Route path="/departamento/" component={DepartamentoList} />
<Route path="/departamento/new" component={DepartamentoForm} />
<Route path="/departamento/:id" component={DepartamentoForm} />
<Route path="/departamento/:page/:pageLength" component={DepartamentoList} />
*/
export default class DepartamentoForm extends GForm{
    showUrl =  '/departamento/'
    listUrl =  '/departamento/'
    apiGetUrl =  '/departamento/show/'
    apiCreateUrl = '/departamento/save'
    apiUpdateUrl = '/departamento/update/'
    apiDeleteUrl = '/departamento/delete/'
    apiOptionsUrl = "/departamento/options"

    objStr = 'Departamento'
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
            <TextField ref={(input) => this._inputs["jefeId"] = input}  name="jefeId" defaultValue={obj.jefeId || ""} floatingLabelText="JefeId" readOnly={readOnly} required={true} errors={errors && errors.jefeId}/>
          </div>
        </div>
    }

}


      