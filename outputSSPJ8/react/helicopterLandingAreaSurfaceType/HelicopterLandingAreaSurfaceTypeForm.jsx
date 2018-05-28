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
import HelicopterLandingAreaSurfaceTypeForm from './components/helicopterLandingAreaSurfaceType/HelicopterLandingAreaSurfaceTypeForm'
import HelicopterLandingAreaSurfaceTypeList from './components/helicopterLandingAreaSurfaceType/HelicopterLandingAreaSurfaceTypeList'

<Route path="/helicopterLandingAreaSurfaceType/" component={HelicopterLandingAreaSurfaceTypeList} />
<Route path="/helicopterLandingAreaSurfaceType/new" component={HelicopterLandingAreaSurfaceTypeForm} />
<Route path="/helicopterLandingAreaSurfaceType/:id" component={HelicopterLandingAreaSurfaceTypeForm} />
<Route path="/helicopterLandingAreaSurfaceType/:page/:pageLength" component={HelicopterLandingAreaSurfaceTypeList} />
*/
export default class HelicopterLandingAreaSurfaceTypeForm extends GForm{
    showUrl =  '/helicopterLandingAreaSurfaceType/'
    listUrl =  '/helicopterLandingAreaSurfaceType/'
    apiGetUrl =  '/helicopterLandingAreaSurfaceType/show/'
    apiCreateUrl = '/helicopterLandingAreaSurfaceType/save'
    apiUpdateUrl = '/helicopterLandingAreaSurfaceType/update/'
    apiDeleteUrl = '/helicopterLandingAreaSurfaceType/delete/'
    

    objStr = 'Helicopter Landing Area Surface Type'
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


      