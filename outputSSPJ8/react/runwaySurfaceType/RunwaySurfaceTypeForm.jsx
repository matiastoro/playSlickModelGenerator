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
import RunwaySurfaceTypeForm from './components/runwaySurfaceType/RunwaySurfaceTypeForm'
import RunwaySurfaceTypeList from './components/runwaySurfaceType/RunwaySurfaceTypeList'

<Route path="/runwaySurfaceType/" component={RunwaySurfaceTypeList} />
<Route path="/runwaySurfaceType/new" component={RunwaySurfaceTypeForm} />
<Route path="/runwaySurfaceType/:id" component={RunwaySurfaceTypeForm} />
<Route path="/runwaySurfaceType/:page/:pageLength" component={RunwaySurfaceTypeList} />
*/
export default class RunwaySurfaceTypeForm extends GForm{
    showUrl =  '/runwaySurfaceType/'
    listUrl =  '/runwaySurfaceType/'
    apiGetUrl =  '/runwaySurfaceType/show/'
    apiCreateUrl = '/runwaySurfaceType/save'
    apiUpdateUrl = '/runwaySurfaceType/update/'
    apiDeleteUrl = '/runwaySurfaceType/delete/'
    

    objStr = 'Runway Surface Type'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["surfaceType"] = input}  name="surfaceType" fullWidth defaultValue={this.getAttr(obj, "surfaceType", "")} floatingLabelText="Surface Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "surfaceType")}/>
          </div>
        </div>
    }

}


      