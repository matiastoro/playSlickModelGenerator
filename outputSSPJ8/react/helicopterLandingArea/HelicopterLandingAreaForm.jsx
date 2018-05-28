import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';
import {GFormInline} from '../gforms/GForm';

//inputs de nested


/*
import HelicopterLandingAreaForm from './components/helicopterLandingArea/HelicopterLandingAreaForm'
import HelicopterLandingAreaList from './components/helicopterLandingArea/HelicopterLandingAreaList'

<Route path="/helicopterLandingArea/" component={HelicopterLandingAreaList} />
<Route path="/helicopterLandingArea/new" component={HelicopterLandingAreaForm} />
<Route path="/helicopterLandingArea/:id" component={HelicopterLandingAreaForm} />
<Route path="/helicopterLandingArea/:page/:pageLength" component={HelicopterLandingAreaList} />
*/
export default class HelicopterLandingAreaForm extends GForm{
    showUrl =  '/helicopterLandingArea/'
    listUrl =  '/helicopterLandingArea/'
    apiGetUrl =  '/helicopterLandingArea/show/'
    apiCreateUrl = '/helicopterLandingArea/save'
    apiUpdateUrl = '/helicopterLandingArea/update/'
    apiDeleteUrl = '/helicopterLandingArea/delete/'
    apiOptionsUrl = "/helicopterLandingArea/options"

    objStr = 'Helicopter Landing Area'
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
            <TextField ref={(input) => this._inputs["helicopterLandingArea"] = input}  name="helicopterLandingArea" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingArea", "")} floatingLabelText="Helicopter Landing Area" readOnly={readOnly} required={true} errors={this.getAttr(errors, "helicopterLandingArea")}/>
          </div>
          <div>
            {hide.includes("aerodromeId")?<HiddenField ref={(input) => this._inputs["aerodromeId"] = input} name="aerodromeId" defaultValue={this.getAttr(obj, "aerodromeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeId"] = input}  name="aerodromeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.aerodrome, "parentId": o.aerodromeId}}))} floatingLabelText="Aerodrome Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aerodromeId")} />}
          </div>
        </div>
    }

}


export class HelicopterLandingAreaFormInline extends GFormInline{
    apiOptionsUrl = "/helicopterLandingArea/options"
    renderForm(){
        const obj = this.props.obj;
        const readOnly = this.props.readOnly
        const errors = this.props.errors
        const hide = this.props.hide || []
        const i = this.props.i
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["helicopterLandingArea"] = input}  name="helicopterLandingArea" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingArea", "")} floatingLabelText="Helicopter Landing Area" readOnly={readOnly} required={true} errors={this.getAttr(errors, "helicopterLandingArea")}/>
          </div>
          <div>
            {hide.includes("aerodromeId")?<HiddenField ref={(input) => this._inputs["aerodromeId"] = input} name="aerodromeId" defaultValue={this.getAttr(obj, "aerodromeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeId"] = input}  name="aerodromeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.aerodrome, "parentId": o.aerodromeId}}))} floatingLabelText="Aerodrome Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].aerodromeId", "")} />}
          </div>
        </div>
    }

}
    
      