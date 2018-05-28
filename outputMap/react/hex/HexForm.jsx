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
<Route path="/hex/" component={HexList} />
<Route path="/hex/new" component={HexForm} />
<Route path="/hex/:id" component={HexForm} />
<Route path="/hex/:page/:pageLength" component={HexList} />
*/
export default class HexForm extends GForm{
    showUrl =  '/hex/'
    listUrl =  '/hex/'
    apiGetUrl =  '/hex/show/'
    apiCreateUrl = '/hex/save'
    apiUpdateUrl = '/hex/update/'
    apiDeleteUrl = '/hex/delete/'
    apiOptionsUrl = "/hex/options"

    objStr = 'Hex'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["q"] = input}  name="q" defaultValue={obj.q || ""} floatingLabelText="Q" readOnly={readOnly} required={true} errors={errors && errors.q}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["r"] = input}  name="r" defaultValue={obj.r || ""} floatingLabelText="R" readOnly={readOnly} required={true} errors={errors && errors.r}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["s"] = input}  name="s" defaultValue={obj.s || ""} floatingLabelText="S" readOnly={readOnly} required={true} errors={errors && errors.s}/>
          </div>
          <div>
            {hide.includes("userId")?<HiddenField ref={(input) => this._inputs["userId"] = input} name="userId" defaultValue={obj.userId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["userId"] = input}  name="userId" defaultValue={obj.userId || ""} options={this.state.options.users.map(o => {return {"value": o.id, "label": o.user}})} floatingLabelText="UserId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].userId"]} />}
          </div>
        </div>
    }

}


      