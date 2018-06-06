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



export default class FlightCrewLicenseForm extends GForm{
    showUrl =  '/flightCrewLicense/'
    listUrl =  '/flightCrewLicense/'
    apiGetUrl =  '/flightCrewLicense/show/'
    apiCreateUrl = '/flightCrewLicense/save'
    apiUpdateUrl = '/flightCrewLicense/update/'
    apiDeleteUrl = '/flightCrewLicense/delete/'
    apiOptionsUrl = "/flightCrewLicense/options"

    objStr = 'Flight Crew License'
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
            {hide.includes("flightCrewId")?<HiddenField ref={(input) => this._inputs["flightCrewId"] = input} name="flightCrewId" defaultValue={this.getAttr(obj, "flightCrewId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["flightCrewId"] = input}  name="flightCrewId" fullWidth defaultValue={this.getAttr(obj, "flightCrewId", "")} options={this.getOptions(options.flightCrews.map(o => {return {"value": o.id, "label": o.flightCrew, "parentId": o.flightCrewId}}))} floatingLabelText="Flight Crew" readOnly={readOnly} required={true} errors={this.getAttr(errors, "flightCrewId")} />}
          </div>
          <div>
            {hide.includes("licenseTypeId")?<HiddenField ref={(input) => this._inputs["licenseTypeId"] = input} name="licenseTypeId" defaultValue={this.getAttr(obj, "licenseTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["licenseTypeId"] = input}  name="licenseTypeId" fullWidth defaultValue={this.getAttr(obj, "licenseTypeId", "")} options={this.getOptions(options.licenseTypes.map(o => {return {"value": o.id, "label": o.licenseType, "parentId": o.licenseTypeId}}))} floatingLabelText="License Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "licenseTypeId")} />}
          </div>
          <div>
            {hide.includes("licenseIssuedById")?<HiddenField ref={(input) => this._inputs["licenseIssuedById"] = input} name="licenseIssuedById" defaultValue={this.getAttr(obj, "licenseIssuedById", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["licenseIssuedById"] = input}  name="licenseIssuedById" fullWidth defaultValue={this.getAttr(obj, "licenseIssuedById", "")} options={this.getOptions(options.licenseIssuedBys.map(o => {return {"value": o.id, "label": o.licenseIssuedBy, "parentId": o.licenseIssuedById}}))} floatingLabelText="License Issued By" readOnly={readOnly} required={false} errors={this.getAttr(errors, "licenseIssuedById")} />}
          </div>
          <div>
            <DateTime ref={(input) => this._inputs["dateOfLicense"] = input}  name="dateOfLicense" fullWidth defaultValue={this.getAttr(obj, "dateOfLicense")} floatingLabelText="Date Of License" readOnly={readOnly} required={false} errors={this.getAttr(errors, "dateOfLicense")} />
          </div>
          <div>
            {hide.includes("licenseValidityId")?<HiddenField ref={(input) => this._inputs["licenseValidityId"] = input} name="licenseValidityId" defaultValue={this.getAttr(obj, "licenseValidityId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["licenseValidityId"] = input}  name="licenseValidityId" fullWidth defaultValue={this.getAttr(obj, "licenseValidityId", "")} options={this.getOptions(options.licenseValiditys.map(o => {return {"value": o.id, "label": o.licenseValidity, "parentId": o.licenseValidityId}}))} floatingLabelText="License Validity" readOnly={readOnly} required={false} errors={this.getAttr(errors, "licenseValidityId")} />}
          </div>
          <div>
            {hide.includes("licenseRatingsId")?<HiddenField ref={(input) => this._inputs["licenseRatingsId"] = input} name="licenseRatingsId" defaultValue={this.getAttr(obj, "licenseRatingsId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["licenseRatingsId"] = input}  name="licenseRatingsId" fullWidth defaultValue={this.getAttr(obj, "licenseRatingsId", "")} options={this.getOptions(options.licenseRatingss.map(o => {return {"value": o.id, "label": o.licenseRatings, "parentId": o.licenseRatingsId}}))} floatingLabelText="License Ratings" readOnly={readOnly} required={false} errors={this.getAttr(errors, "licenseRatingsId")} />}
          </div>
        </div>
    }

}


export class FlightCrewLicenseFormInline extends GFormInline{
    apiOptionsUrl = "/flightCrewLicense/options"
    renderForm(){
        const obj = this.props.obj;
        const readOnly = this.props.readOnly
        const errors = this.props.errors
        const hide = this.props.hide || []
        const i = this.props.i
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("flightCrewId")?<HiddenField ref={(input) => this._inputs["flightCrewId"] = input} name="flightCrewId" defaultValue={this.getAttr(obj, "flightCrewId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["flightCrewId"] = input}  name="flightCrewId" fullWidth defaultValue={this.getAttr(obj, "flightCrewId", "")} options={this.getOptions(options.flightCrews.map(o => {return {"value": o.id, "label": o.flightCrew, "parentId": o.flightCrewId}}))} floatingLabelText="Flight Crew" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].flightCrewId", "")} />}
          </div>
          <div>
            {hide.includes("licenseTypeId")?<HiddenField ref={(input) => this._inputs["licenseTypeId"] = input} name="licenseTypeId" defaultValue={this.getAttr(obj, "licenseTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["licenseTypeId"] = input}  name="licenseTypeId" fullWidth defaultValue={this.getAttr(obj, "licenseTypeId", "")} options={this.getOptions(options.licenseTypes.map(o => {return {"value": o.id, "label": o.licenseType, "parentId": o.licenseTypeId}}))} floatingLabelText="License Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].licenseTypeId", "")} />}
          </div>
          <div>
            {hide.includes("licenseIssuedById")?<HiddenField ref={(input) => this._inputs["licenseIssuedById"] = input} name="licenseIssuedById" defaultValue={this.getAttr(obj, "licenseIssuedById", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["licenseIssuedById"] = input}  name="licenseIssuedById" fullWidth defaultValue={this.getAttr(obj, "licenseIssuedById", "")} options={this.getOptions(options.licenseIssuedBys.map(o => {return {"value": o.id, "label": o.licenseIssuedBy, "parentId": o.licenseIssuedById}}))} floatingLabelText="License Issued By" readOnly={readOnly} required={false} errors={this.getAttr(errors, this.props.prefix+"["+i+"].licenseIssuedById", "")} />}
          </div>
          <div>
            <DateTime ref={(input) => this._inputs["dateOfLicense"] = input}  name="dateOfLicense" fullWidth defaultValue={this.getAttr(obj, "dateOfLicense")} floatingLabelText="Date Of License" readOnly={readOnly} required={false} errors={this.getAttr(errors, "dateOfLicense")} />
          </div>
          <div>
            {hide.includes("licenseValidityId")?<HiddenField ref={(input) => this._inputs["licenseValidityId"] = input} name="licenseValidityId" defaultValue={this.getAttr(obj, "licenseValidityId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["licenseValidityId"] = input}  name="licenseValidityId" fullWidth defaultValue={this.getAttr(obj, "licenseValidityId", "")} options={this.getOptions(options.licenseValiditys.map(o => {return {"value": o.id, "label": o.licenseValidity, "parentId": o.licenseValidityId}}))} floatingLabelText="License Validity" readOnly={readOnly} required={false} errors={this.getAttr(errors, this.props.prefix+"["+i+"].licenseValidityId", "")} />}
          </div>
          <div>
            {hide.includes("licenseRatingsId")?<HiddenField ref={(input) => this._inputs["licenseRatingsId"] = input} name="licenseRatingsId" defaultValue={this.getAttr(obj, "licenseRatingsId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["licenseRatingsId"] = input}  name="licenseRatingsId" fullWidth defaultValue={this.getAttr(obj, "licenseRatingsId", "")} options={this.getOptions(options.licenseRatingss.map(o => {return {"value": o.id, "label": o.licenseRatings, "parentId": o.licenseRatingsId}}))} floatingLabelText="License Ratings" readOnly={readOnly} required={false} errors={this.getAttr(errors, this.props.prefix+"["+i+"].licenseRatingsId", "")} />}
          </div>
        </div>
    }

}
    
      