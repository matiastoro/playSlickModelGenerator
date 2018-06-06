import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class AircraftManufacturerModelForm extends GForm{
    showUrl =  '/aircraftManufacturerModel/'
    listUrl =  '/aircraftManufacturerModel/'
    apiGetUrl =  '/aircraftManufacturerModel/show/'
    apiCreateUrl = '/aircraftManufacturerModel/save'
    apiUpdateUrl = '/aircraftManufacturerModel/update/'
    apiDeleteUrl = '/aircraftManufacturerModel/delete/'
    apiOptionsUrl = "/aircraftManufacturerModel/options"

    objStr = 'Aircraft Manufacturer Model'
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
            <TextField ref={(input) => this._inputs["manufacturerModel"] = input}  name="manufacturerModel" fullWidth defaultValue={this.getAttr(obj, "manufacturerModel", "")} floatingLabelText="Manufacturer Model" readOnly={readOnly} required={true} errors={this.getAttr(errors, "manufacturerModel")}/>
          </div>
          <div>
            {hide.includes("aircraftManufacturerModelId")?<HiddenField ref={(input) => this._inputs["aircraftManufacturerModelId"] = input} name="aircraftManufacturerModelId" defaultValue={this.getAttr(obj, "aircraftManufacturerModelId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aircraftManufacturerModelId"] = input}  name="aircraftManufacturerModelId" fullWidth defaultValue={this.getAttr(obj, "aircraftManufacturerModelId", "")} options={this.getOptions(options.aircraftManufacturerModels.map(o => {return {"value": o.id, "label": o.manufacturerModel, "parentId": o.aircraftManufacturerModelId}}))} floatingLabelText="Aircraft Manufacturer Model" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aircraftManufacturerModelId")} />}
          </div>
        </div>
    }

}


      