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
import HelicopterLandingAreaTypeForm from './components/helicopterLandingAreaType/HelicopterLandingAreaTypeForm'
import HelicopterLandingAreaTypeList from './components/helicopterLandingAreaType/HelicopterLandingAreaTypeList'

<Route path="/helicopterLandingAreaType/" component={HelicopterLandingAreaTypeList} />
<Route path="/helicopterLandingAreaType/new" component={HelicopterLandingAreaTypeForm} />
<Route path="/helicopterLandingAreaType/:id" component={HelicopterLandingAreaTypeForm} />
<Route path="/helicopterLandingAreaType/:page/:pageLength" component={HelicopterLandingAreaTypeList} />
*/
export default class HelicopterLandingAreaTypeForm extends GForm{
    showUrl =  '/helicopterLandingAreaType/'
    listUrl =  '/helicopterLandingAreaType/'
    apiGetUrl =  '/helicopterLandingAreaType/show/'
    apiCreateUrl = '/helicopterLandingAreaType/save'
    apiUpdateUrl = '/helicopterLandingAreaType/update/'
    apiDeleteUrl = '/helicopterLandingAreaType/delete/'
    

    objStr = 'Helicopter Landing Area Type'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["landingAreaType"] = input}  name="landingAreaType" fullWidth defaultValue={this.getAttr(obj, "landingAreaType", "")} floatingLabelText="Landing Area Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "landingAreaType")}/>
          </div>
        </div>
    }

}


      