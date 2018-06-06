import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class AircraftLandingGearTypeForm extends GForm{
    showUrl =  '/aircraftLandingGearType/'
    listUrl =  '/aircraftLandingGearType/'
    apiGetUrl =  '/aircraftLandingGearType/show/'
    apiCreateUrl = '/aircraftLandingGearType/save'
    apiUpdateUrl = '/aircraftLandingGearType/update/'
    apiDeleteUrl = '/aircraftLandingGearType/delete/'
    apiOptionsUrl = "/aircraftLandingGearType/options"

    objStr = 'Aircraft Landing Gear Type'
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
            <TextField ref={(input) => this._inputs["landingGearType"] = input}  name="landingGearType" fullWidth defaultValue={this.getAttr(obj, "landingGearType", "")} floatingLabelText="Landing Gear Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "landingGearType")}/>
          </div>
          <div>
            {hide.includes("aircraftLandingGearTypeId")?<HiddenField ref={(input) => this._inputs["aircraftLandingGearTypeId"] = input} name="aircraftLandingGearTypeId" defaultValue={this.getAttr(obj, "aircraftLandingGearTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aircraftLandingGearTypeId"] = input}  name="aircraftLandingGearTypeId" fullWidth defaultValue={this.getAttr(obj, "aircraftLandingGearTypeId", "")} options={this.getOptions(options.aircraftLandingGearTypes.map(o => {return {"value": o.id, "label": o.landingGearType, "parentId": o.aircraftLandingGearTypeId}}))} floatingLabelText="Aircraft Landing Gear Type" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aircraftLandingGearTypeId")} />}
          </div>
        </div>
    }

}


      