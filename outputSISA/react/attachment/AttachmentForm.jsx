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
<Route path="/attachment/" component={AttachmentList} />
<Route path="/attachment/new" component={AttachmentForm} />
<Route path="/attachment/:id" component={AttachmentForm} />
<Route path="/attachment/:page/:pageLength" component={AttachmentList} />
*/
export default class AttachmentForm extends GForm{
    showUrl =  '/attachment/'
    listUrl =  '/attachment/'
    apiGetUrl =  '/attachment/show/'
    apiCreateUrl = '/attachment/save'
    apiUpdateUrl = '/attachment/update/'
    apiDeleteUrl = '/attachment/delete/'
    

    objStr = 'Attachment'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["attachment"] = input}  name="attachment" defaultValue={obj.attachment || ""} floatingLabelText="Attachment" readOnly={readOnly} required={false} errors={errors && errors.attachment}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["estado"] = input}  name="estado" defaultValue={obj.estado || ""} floatingLabelText="Estado" readOnly={readOnly} required={false} errors={errors && errors.estado}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["targetTable"] = input}  name="targetTable" defaultValue={obj.targetTable || ""} floatingLabelText="TargetTable" readOnly={readOnly} required={true} errors={errors && errors.targetTable}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["d"] = input}  name="d" defaultValue={obj.d || ""} floatingLabelText="D" readOnly={readOnly} required={true} errors={errors && errors.d}/>
          </div>
        </div>
    }

}


      