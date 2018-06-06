import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class NeoReportStatusForm extends GForm{
    showUrl =  '/neoReportStatus/'
    listUrl =  '/neoReportStatus/'
    apiGetUrl =  '/neoReportStatus/show/'
    apiCreateUrl = '/neoReportStatus/save'
    apiUpdateUrl = '/neoReportStatus/update/'
    apiDeleteUrl = '/neoReportStatus/delete/'
    

    objStr = 'Neo Report Status'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["reportStatus"] = input}  name="reportStatus" fullWidth defaultValue={this.getAttr(obj, "reportStatus", "")} floatingLabelText="Report Status" readOnly={readOnly} required={true} errors={this.getAttr(errors, "reportStatus")}/>
          </div>
        </div>
    }

}


      