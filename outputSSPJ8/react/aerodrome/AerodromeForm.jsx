import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';

import {GNestedForms} from '../gforms/GForm';
import {RunwayFormInline} from '../runway/RunwayForm'
import {HelicopterLandingAreaFormInline} from '../helicopterLandingArea/HelicopterLandingAreaForm'
      
//inputs de nested


/*
import AerodromeForm from './components/aerodrome/AerodromeForm'
import AerodromeList from './components/aerodrome/AerodromeList'

<Route path="/aerodrome/" component={AerodromeList} />
<Route path="/aerodrome/new" component={AerodromeForm} />
<Route path="/aerodrome/:id" component={AerodromeForm} />
<Route path="/aerodrome/:page/:pageLength" component={AerodromeList} />
*/
export default class AerodromeForm extends GForm{
    showUrl =  '/aerodrome/'
    listUrl =  '/aerodrome/'
    apiGetUrl =  '/aerodrome/show/'
    apiCreateUrl = '/aerodrome/save'
    apiUpdateUrl = '/aerodrome/update/'
    apiDeleteUrl = '/aerodrome/delete/'
    apiOptionsUrl = "/aerodrome/options"

    objStr = 'Aerodrome'
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
            <TextField ref={(input) => this._inputs["icaoCode"] = input}  name="icaoCode" fullWidth defaultValue={this.getAttr(obj, "icaoCode", "")} floatingLabelText="IcaoCode" readOnly={readOnly} required={true} errors={this.getAttr(errors, "icaoCode")}/>
          </div>
          <div>
            {hide.includes("aerodromeStatusId")?<HiddenField ref={(input) => this._inputs["aerodromeStatusId"] = input} name="aerodromeStatusId" defaultValue={this.getAttr(obj, "aerodromeStatusId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeStatusId"] = input}  name="aerodromeStatusId" fullWidth defaultValue={this.getAttr(obj, "aerodromeStatusId", "")} options={this.getOptions(options.aerodromeStatuss.map(o => {return {"value": o.id, "label": o.aerodromeStatus, "parentId": o.aerodromeStatusId}}))} floatingLabelText="Aerodrome Status Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aerodromeStatusId")} />}
          </div>
          <div>
            {hide.includes("aerodromeTypeId")?<HiddenField ref={(input) => this._inputs["aerodromeTypeId"] = input} name="aerodromeTypeId" defaultValue={this.getAttr(obj, "aerodromeTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeTypeId"] = input}  name="aerodromeTypeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeTypeId", "")} options={this.getOptions(options.aerodromeTypes.map(o => {return {"value": o.id, "label": o.aerodromeType, "parentId": o.aerodromeTypeId}}))} floatingLabelText="Aerodrome Type Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aerodromeTypeId")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["latitude"] = input}  name="latitude" fullWidth defaultValue={this.getAttr(obj, "latitude", "")} floatingLabelText="Latitude" readOnly={readOnly} required={false} errors={this.getAttr(errors, "latitude")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["longitude"] = input}  name="longitude" fullWidth defaultValue={this.getAttr(obj, "longitude", "")} floatingLabelText="Longitude" readOnly={readOnly} required={false} errors={this.getAttr(errors, "longitude")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["elevationAboveMsl"] = input}  name="elevationAboveMsl" fullWidth defaultValue={this.getAttr(obj, "elevationAboveMsl", "")} floatingLabelText="Elevation Above Msl" readOnly={readOnly} required={false} errors={this.getAttr(errors, "elevationAboveMsl")}/>
          </div>
            <GNestedForms ref={(i) => this._inputs["runways"] = i} description="List of Runways" prefix="runways" readOnly={readOnly} objs={obj.runways} renderNested={(nobj, k, refFunc) => <RunwayFormInline i={k} obj={Object.assign({aerodromeId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["aerodromeId"]} errors={errors} prefix="runways"/>}/>
            <GNestedForms ref={(i) => this._inputs["helicopterLandingAreas"] = i} description="List of Helicopter Landing Areas" prefix="helicopterLandingAreas" readOnly={readOnly} objs={obj.helicopterLandingAreas} renderNested={(nobj, k, refFunc) => <HelicopterLandingAreaFormInline i={k} obj={Object.assign({aerodromeId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["aerodromeId"]} errors={errors} prefix="helicopterLandingAreas"/>}/>
        </div>
    }

}


      