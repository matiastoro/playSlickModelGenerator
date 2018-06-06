import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class AircraftCategoryForm extends GForm{
    showUrl =  '/aircraftCategory/'
    listUrl =  '/aircraftCategory/'
    apiGetUrl =  '/aircraftCategory/show/'
    apiCreateUrl = '/aircraftCategory/save'
    apiUpdateUrl = '/aircraftCategory/update/'
    apiDeleteUrl = '/aircraftCategory/delete/'
    apiOptionsUrl = "/aircraftCategory/options"

    objStr = 'Aircraft Category'
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
            <TextField ref={(input) => this._inputs["category"] = input}  name="category" fullWidth defaultValue={this.getAttr(obj, "category", "")} floatingLabelText="Category" readOnly={readOnly} required={true} errors={this.getAttr(errors, "category")}/>
          </div>
          <div>
            {hide.includes("aircraftCategoryId")?<HiddenField ref={(input) => this._inputs["aircraftCategoryId"] = input} name="aircraftCategoryId" defaultValue={this.getAttr(obj, "aircraftCategoryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aircraftCategoryId"] = input}  name="aircraftCategoryId" fullWidth defaultValue={this.getAttr(obj, "aircraftCategoryId", "")} options={this.getOptions(options.aircraftCategorys.map(o => {return {"value": o.id, "label": o.category, "parentId": o.aircraftCategoryId}}))} floatingLabelText="Aircraft Category" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aircraftCategoryId")} />}
          </div>
        </div>
    }

}


      