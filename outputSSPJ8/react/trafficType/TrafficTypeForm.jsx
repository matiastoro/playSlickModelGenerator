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
import TrafficTypeForm from './components/trafficType/TrafficTypeForm'
import TrafficTypeList from './components/trafficType/TrafficTypeList'

<Route path="/trafficType/" component={TrafficTypeList} />
<Route path="/trafficType/new" component={TrafficTypeForm} />
<Route path="/trafficType/:id" component={TrafficTypeForm} />
<Route path="/trafficType/:page/:pageLength" component={TrafficTypeList} />
*/
export default class TrafficTypeForm extends GForm{
    showUrl =  '/trafficType/'
    listUrl =  '/trafficType/'
    apiGetUrl =  '/trafficType/show/'
    apiCreateUrl = '/trafficType/save'
    apiUpdateUrl = '/trafficType/update/'
    apiDeleteUrl = '/trafficType/delete/'
    

    objStr = 'Traffic Type'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["trafficType"] = input}  name="trafficType" fullWidth defaultValue={this.getAttr(obj, "trafficType", "")} floatingLabelText="Traffic Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "trafficType")}/>
          </div>
        </div>
    }

}


      