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
import HelicopterLandingAreaAreaConfigurationForm from './components/helicopterLandingAreaAreaConfiguration/HelicopterLandingAreaAreaConfigurationForm'
import HelicopterLandingAreaAreaConfigurationList from './components/helicopterLandingAreaAreaConfiguration/HelicopterLandingAreaAreaConfigurationList'

<Route path="/helicopterLandingAreaAreaConfiguration/" component={HelicopterLandingAreaAreaConfigurationList} />
<Route path="/helicopterLandingAreaAreaConfiguration/new" component={HelicopterLandingAreaAreaConfigurationForm} />
<Route path="/helicopterLandingAreaAreaConfiguration/:id" component={HelicopterLandingAreaAreaConfigurationForm} />
<Route path="/helicopterLandingAreaAreaConfiguration/:page/:pageLength" component={HelicopterLandingAreaAreaConfigurationList} />
*/
export default class HelicopterLandingAreaAreaConfigurationForm extends GForm{
    showUrl =  '/helicopterLandingAreaAreaConfiguration/'
    listUrl =  '/helicopterLandingAreaAreaConfiguration/'
    apiGetUrl =  '/helicopterLandingAreaAreaConfiguration/show/'
    apiCreateUrl = '/helicopterLandingAreaAreaConfiguration/save'
    apiUpdateUrl = '/helicopterLandingAreaAreaConfiguration/update/'
    apiDeleteUrl = '/helicopterLandingAreaAreaConfiguration/delete/'
    

    objStr = 'Helicopter Landing Area Area Configuration'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["areaConfiguration"] = input}  name="areaConfiguration" fullWidth defaultValue={this.getAttr(obj, "areaConfiguration", "")} floatingLabelText="Area Configuration" readOnly={readOnly} required={true} errors={this.getAttr(errors, "areaConfiguration")}/>
          </div>
        </div>
    }

}


      