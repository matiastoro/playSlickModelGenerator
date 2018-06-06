import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GFilter from '../gforms/GFilter';
import {GFormInline} from '../gforms/GForm';

import { Row, Col } from 'react-flexbox-grid';

//inputs de nested




export default class FlightCrewLicenseFilter extends GFilter{

    renderFilter(){
        const readOnly = false
        const obj = this.props.query?JSON.parse(this.props.query):{}
        const errors = {}
        const options = this.props.options
        const selectDefault = "-1"
        return <div>
            <Row>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["id"] = input}  name="id" fullWidth defaultValue={this.getAttr(obj, "id", "")} floatingLabelText="Id" readOnly={readOnly}  errors={this.getAttr(errors, "id")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["flightCrewId"] = input}  name="flightCrewId" fullWidth defaultValue={this.getAttr(obj, "flightCrewId", "")} options={this.getOptions(options.flightCrews.map(o => {return {"value": o.id, "label": o.flightCrew, "parentId": o.flightCrewId}}))} floatingLabelText="Flight Crew" readOnly={readOnly}  errors={this.getAttr(errors, "flightCrewId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["licenseTypeId"] = input}  name="licenseTypeId" fullWidth defaultValue={this.getAttr(obj, "licenseTypeId", "")} options={this.getOptions(options.licenseTypes.map(o => {return {"value": o.id, "label": o.licenseType, "parentId": o.licenseTypeId}}))} floatingLabelText="License Type" readOnly={readOnly}  errors={this.getAttr(errors, "licenseTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["licenseIssuedById"] = input}  name="licenseIssuedById" fullWidth defaultValue={this.getAttr(obj, "licenseIssuedById", "")} options={this.getOptions(options.licenseIssuedBys.map(o => {return {"value": o.id, "label": o.licenseIssuedBy, "parentId": o.licenseIssuedById}}))} floatingLabelText="License Issued By" readOnly={readOnly}  errors={this.getAttr(errors, "licenseIssuedById")} />
                </Col>
                <Col xs={6} sm={2}>
                    <DateTime ref={(input) => this._inputs["dateOfLicenseFrom"] = input}  name="dateOfLicenseFrom" fullWidth defaultValue={this.getAttr(obj, "dateOfLicenseFrom")} floatingLabelText="Date Of License desde" readOnly={readOnly}  errors={this.getAttr(errors, "dateOfLicenseFrom")} />
<DateTime ref={(input) => this._inputs["dateOfLicenseTo"] = input}  name="dateOfLicenseTo" fullWidth defaultValue={this.getAttr(obj, "dateOfLicenseTo")} floatingLabelText="Date Of License hasta" readOnly={readOnly}  errors={this.getAttr(errors, "dateOfLicenseTo")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["licenseValidityId"] = input}  name="licenseValidityId" fullWidth defaultValue={this.getAttr(obj, "licenseValidityId", "")} options={this.getOptions(options.licenseValiditys.map(o => {return {"value": o.id, "label": o.licenseValidity, "parentId": o.licenseValidityId}}))} floatingLabelText="License Validity" readOnly={readOnly}  errors={this.getAttr(errors, "licenseValidityId")} />
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["licenseRatingsId"] = input}  name="licenseRatingsId" fullWidth defaultValue={this.getAttr(obj, "licenseRatingsId", "")} options={this.getOptions(options.licenseRatingss.map(o => {return {"value": o.id, "label": o.licenseRatings, "parentId": o.licenseRatingsId}}))} floatingLabelText="License Ratings" readOnly={readOnly}  errors={this.getAttr(errors, "licenseRatingsId")} />
                </Col>
            </Row>
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
        return <div>
<TextField ref={(input) => this._inputs["id"] = input}  name="id" fullWidth defaultValue={this.getAttr(obj, "id", "")} floatingLabelText="Id" readOnly={readOnly}  errors={this.getAttr(errors, "id")}/>
<SelectField ref={(input) => this._inputs["flightCrewId"] = input}  name="flightCrewId" fullWidth defaultValue={this.getAttr(obj, "flightCrewId", "")} options={this.getOptions(options.flightCrews.map(o => {return {"value": o.id, "label": o.flightCrew, "parentId": o.flightCrewId}}))} floatingLabelText="Flight Crew" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].flightCrewId", "")} />
<SelectField ref={(input) => this._inputs["licenseTypeId"] = input}  name="licenseTypeId" fullWidth defaultValue={this.getAttr(obj, "licenseTypeId", "")} options={this.getOptions(options.licenseTypes.map(o => {return {"value": o.id, "label": o.licenseType, "parentId": o.licenseTypeId}}))} floatingLabelText="License Type" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].licenseTypeId", "")} />
<SelectField ref={(input) => this._inputs["licenseIssuedById"] = input}  name="licenseIssuedById" fullWidth defaultValue={this.getAttr(obj, "licenseIssuedById", "")} options={this.getOptions(options.licenseIssuedBys.map(o => {return {"value": o.id, "label": o.licenseIssuedBy, "parentId": o.licenseIssuedById}}))} floatingLabelText="License Issued By" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].licenseIssuedById", "")} />
<DateTime ref={(input) => this._inputs["dateOfLicenseFrom"] = input}  name="dateOfLicenseFrom" fullWidth defaultValue={this.getAttr(obj, "dateOfLicenseFrom")} floatingLabelText="Date Of License desde" readOnly={readOnly}  errors={this.getAttr(errors, "dateOfLicenseFrom")} />
<DateTime ref={(input) => this._inputs["dateOfLicenseTo"] = input}  name="dateOfLicenseTo" fullWidth defaultValue={this.getAttr(obj, "dateOfLicenseTo")} floatingLabelText="Date Of License hasta" readOnly={readOnly}  errors={this.getAttr(errors, "dateOfLicenseTo")} />
<SelectField ref={(input) => this._inputs["licenseValidityId"] = input}  name="licenseValidityId" fullWidth defaultValue={this.getAttr(obj, "licenseValidityId", "")} options={this.getOptions(options.licenseValiditys.map(o => {return {"value": o.id, "label": o.licenseValidity, "parentId": o.licenseValidityId}}))} floatingLabelText="License Validity" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].licenseValidityId", "")} />
<SelectField ref={(input) => this._inputs["licenseRatingsId"] = input}  name="licenseRatingsId" fullWidth defaultValue={this.getAttr(obj, "licenseRatingsId", "")} options={this.getOptions(options.licenseRatingss.map(o => {return {"value": o.id, "label": o.licenseRatings, "parentId": o.licenseRatingsId}}))} floatingLabelText="License Ratings" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].licenseRatingsId", "")} />
        </div>
    }

}
    
      