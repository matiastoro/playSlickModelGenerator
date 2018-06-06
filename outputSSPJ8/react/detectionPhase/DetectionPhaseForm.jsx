import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested



export default class DetectionPhaseForm extends GForm{
    showUrl =  '/detectionPhase/'
    listUrl =  '/detectionPhase/'
    apiGetUrl =  '/detectionPhase/show/'
    apiCreateUrl = '/detectionPhase/save'
    apiUpdateUrl = '/detectionPhase/update/'
    apiDeleteUrl = '/detectionPhase/delete/'
    

    objStr = 'Detection Phase'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["detectionPhase"] = input}  name="detectionPhase" fullWidth defaultValue={this.getAttr(obj, "detectionPhase", "")} floatingLabelText="Detection Phase" readOnly={readOnly} required={true} errors={this.getAttr(errors, "detectionPhase")}/>
          </div>
        </div>
    }

}


      