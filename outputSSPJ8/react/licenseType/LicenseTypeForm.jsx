import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class LicenseTypeForm extends GForm{
    showUrl =  '/licenseType/'
    listUrl =  '/licenseType/'
    apiGetUrl =  '/licenseType/show/'
    apiCreateUrl = '/licenseType/save'
    apiUpdateUrl = '/licenseType/update/'
    apiDeleteUrl = '/licenseType/delete/'
    apiOptionsUrl = "/licenseType/options"

    objStr = 'License Type'
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
            <TextField ref={(input) => this._inputs["licenseType"] = input}  name="licenseType" fullWidth defaultValue={this.getAttr(obj, "licenseType", "")} floatingLabelText="License Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "licenseType")}/>
          </div>
          <div>
            {hide.includes("licenseTypeId")?<HiddenField ref={(input) => this._inputs["licenseTypeId"] = input} name="licenseTypeId" defaultValue={this.getAttr(obj, "licenseTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["licenseTypeId"] = input}  name="licenseTypeId" fullWidth defaultValue={this.getAttr(obj, "licenseTypeId", "")} options={this.getOptions(options.licenseTypes.map(o => {return {"value": o.id, "label": o.licenseType, "parentId": o.licenseTypeId}}))} floatingLabelText="License Type" readOnly={readOnly} required={false} errors={this.getAttr(errors, "licenseTypeId")} />}
          </div>
        </div>
    }

}


      