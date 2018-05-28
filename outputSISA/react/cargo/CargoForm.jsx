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
<Route path="/cargo/" component={CargoList} />
<Route path="/cargo/new" component={CargoForm} />
<Route path="/cargo/:id" component={CargoForm} />
<Route path="/cargo/:page/:pageLength" component={CargoList} />
*/
export default class CargoForm extends GForm{
    showUrl =  '/cargo/'
    listUrl =  '/cargo/'
    apiGetUrl =  '/cargo/show/'
    apiCreateUrl = '/cargo/save'
    apiUpdateUrl = '/cargo/update/'
    apiDeleteUrl = '/cargo/delete/'
    apiOptionsUrl = "/cargo/options"

    objStr = 'Cargo'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["cargo"] = input}  name="cargo" defaultValue={obj.cargo || ""} floatingLabelText="Cargo" readOnly={readOnly} required={true} errors={errors && errors.cargo}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["codigo"] = input}  name="codigo" defaultValue={obj.codigo || ""} floatingLabelText="Codigo" readOnly={readOnly} required={true} errors={errors && errors.codigo}/>
          </div>
          <div>
            {hide.includes("organizacionId")?<HiddenField ref={(input) => this._inputs["organizacionId"] = input} name="organizacionId" defaultValue={obj.organizacionId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["organizacionId"] = input}  name="organizacionId" defaultValue={obj.organizacionId || ""} options={this.state.options.organizacions.map(o => {return {"value": o.id, "label": o.organizacion}})} floatingLabelText="OrganizacionId" readOnly={readOnly} required={false} errors={errors && errors[this.props.prefix+"["+i+"].organizacionId"]} />}
          </div>
        </div>
    }

}


      