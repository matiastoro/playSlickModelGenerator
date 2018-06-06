import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class LicenseIssuedByForm extends GForm{
    showUrl =  '/licenseIssuedBy/'
    listUrl =  '/licenseIssuedBy/'
    apiGetUrl =  '/licenseIssuedBy/show/'
    apiCreateUrl = '/licenseIssuedBy/save'
    apiUpdateUrl = '/licenseIssuedBy/update/'
    apiDeleteUrl = '/licenseIssuedBy/delete/'
    apiOptionsUrl = "/licenseIssuedBy/options"

    objStr = 'License Issued By'
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
            <TextField ref={(input) => this._inputs["licenseIssuedBy"] = input}  name="licenseIssuedBy" fullWidth defaultValue={this.getAttr(obj, "licenseIssuedBy", "")} floatingLabelText="License Issued By" readOnly={readOnly} required={true} errors={this.getAttr(errors, "licenseIssuedBy")}/>
          </div>
          <div>
            {hide.includes("licenseIssuedById")?<HiddenField ref={(input) => this._inputs["licenseIssuedById"] = input} name="licenseIssuedById" defaultValue={this.getAttr(obj, "licenseIssuedById", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["licenseIssuedById"] = input}  name="licenseIssuedById" fullWidth defaultValue={this.getAttr(obj, "licenseIssuedById", "")} options={this.getOptions(options.licenseIssuedBys.map(o => {return {"value": o.id, "label": o.licenseIssuedBy, "parentId": o.licenseIssuedById}}))} floatingLabelText="License Issued By" readOnly={readOnly} required={false} errors={this.getAttr(errors, "licenseIssuedById")} />}
          </div>
        </div>
    }

}


      