import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class StateAreaForm extends GForm{
    showUrl =  '/stateArea/'
    listUrl =  '/stateArea/'
    apiGetUrl =  '/stateArea/show/'
    apiCreateUrl = '/stateArea/save'
    apiUpdateUrl = '/stateArea/update/'
    apiDeleteUrl = '/stateArea/delete/'
    apiOptionsUrl = "/stateArea/options"

    objStr = 'State Area'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["stateArea"] = input}  name="stateArea" fullWidth defaultValue={this.getAttr(obj, "stateArea", "")} floatingLabelText="State Area" readOnly={readOnly} required={true} errors={this.getAttr(errors, "stateArea")}/>
          </div>
          <div>
            {hide.includes("stateAreaId")?<HiddenField ref={(input) => this._inputs["stateAreaId"] = input} name="stateAreaId" defaultValue={this.getAttr(obj, "stateAreaId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["stateAreaId"] = input}  name="stateAreaId" fullWidth defaultValue={this.getAttr(obj, "stateAreaId", "")} options={this.getOptions(options.stateAreas.map(o => {return {"value": o.id, "label": o.stateArea, "parentId": o.stateAreaId}}))} floatingLabelText="State Area" readOnly={readOnly} required={false} errors={this.getAttr(errors, "stateAreaId")} />}
          </div>
        </div>
    }

}


      