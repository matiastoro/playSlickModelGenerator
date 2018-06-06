import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GFilter from '../gforms/GFilter';
import {GFormInline} from '../gforms/GForm';
import {GNestedForms} from '../gforms/GForm';
import {FlightCrewLicenseFormInline} from '../flightCrewLicense/FlightCrewLicenseForm'
      
import { Row, Col } from 'react-flexbox-grid';

//inputs de nested




export default class FlightCrewFilter extends GFilter{

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
                    <SelectField ref={(input) => this._inputs["neoFlightOperationId"] = input}  name="neoFlightOperationId" fullWidth defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} options={this.getOptions(options.neoFlightOperations.map(o => {return {"value": o.id, "label": o.neoFlightOperation, "parentId": o.neoFlightOperationId}}))} floatingLabelText="Neo Flight Operation" readOnly={readOnly}  errors={this.getAttr(errors, "neoFlightOperationId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["categoryId"] = input}  name="categoryId" fullWidth defaultValue={this.getAttr(obj, "categoryId", "")} options={this.getOptions(options.flightCrewCategorys.map(o => {return {"value": o.id, "label": o.category, "parentId": o.flightCrewCategoryId}}))} floatingLabelText="Category" readOnly={readOnly}  errors={this.getAttr(errors, "categoryId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["experienceThis"] = input}  name="experienceThis" fullWidth defaultValue={this.getAttr(obj, "experienceThis", "")} floatingLabelText="Experience This" readOnly={readOnly}  errors={this.getAttr(errors, "experienceThis")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["experienceAll"] = input}  name="experienceAll" fullWidth defaultValue={this.getAttr(obj, "experienceAll", "")} floatingLabelText="Experience All" readOnly={readOnly}  errors={this.getAttr(errors, "experienceAll")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["dutyLast24Hours"] = input}  name="dutyLast24Hours" fullWidth defaultValue={this.getAttr(obj, "dutyLast24Hours", "")} floatingLabelText="Duty Last 24 Hours" readOnly={readOnly}  errors={this.getAttr(errors, "dutyLast24Hours")}/>
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["restBeforeDuty"] = input}  name="restBeforeDuty" fullWidth defaultValue={this.getAttr(obj, "restBeforeDuty", "")} floatingLabelText="Rest Before Duty" readOnly={readOnly}  errors={this.getAttr(errors, "restBeforeDuty")}/>
                </Col>
                <Col xs={6} sm={2}>
                                <GNestedForms ref={(i) => this._inputs["flightCrewLicenses"] = i} description="List of Flight Crew Licenses" prefix="flightCrewLicenses" readOnly={readOnly} objs={obj.flightCrewLicenses} renderNested={(nobj, k, refFunc) => <FlightCrewLicenseFormInline i={k} obj={Object.assign({flightCrewId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["flightCrewId"]} errors={errors} prefix="flightCrewLicenses"/>}/>
                </Col>
            </Row>
        </div>
    }

}


export class FlightCrewFormInline extends GFormInline{
    apiOptionsUrl = "/flightCrew/options"
    renderForm(){
        const obj = this.props.obj;
        const readOnly = this.props.readOnly
        const errors = this.props.errors
        const hide = this.props.hide || []
        const i = this.props.i
        return <div>
<TextField ref={(input) => this._inputs["id"] = input}  name="id" fullWidth defaultValue={this.getAttr(obj, "id", "")} floatingLabelText="Id" readOnly={readOnly}  errors={this.getAttr(errors, "id")}/>
<SelectField ref={(input) => this._inputs["neoFlightOperationId"] = input}  name="neoFlightOperationId" fullWidth defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} options={this.getOptions(options.neoFlightOperations.map(o => {return {"value": o.id, "label": o.neoFlightOperation, "parentId": o.neoFlightOperationId}}))} floatingLabelText="Neo Flight Operation" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].neoFlightOperationId", "")} />
<SelectField ref={(input) => this._inputs["categoryId"] = input}  name="categoryId" fullWidth defaultValue={this.getAttr(obj, "categoryId", "")} options={this.getOptions(options.flightCrewCategorys.map(o => {return {"value": o.id, "label": o.category, "parentId": o.flightCrewCategoryId}}))} floatingLabelText="Category" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].categoryId", "")} />
<TextField ref={(input) => this._inputs["experienceThis"] = input}  name="experienceThis" fullWidth defaultValue={this.getAttr(obj, "experienceThis", "")} floatingLabelText="Experience This" readOnly={readOnly}  errors={this.getAttr(errors, "experienceThis")}/>
<TextField ref={(input) => this._inputs["experienceAll"] = input}  name="experienceAll" fullWidth defaultValue={this.getAttr(obj, "experienceAll", "")} floatingLabelText="Experience All" readOnly={readOnly}  errors={this.getAttr(errors, "experienceAll")}/>
<TextField ref={(input) => this._inputs["dutyLast24Hours"] = input}  name="dutyLast24Hours" fullWidth defaultValue={this.getAttr(obj, "dutyLast24Hours", "")} floatingLabelText="Duty Last 24 Hours" readOnly={readOnly}  errors={this.getAttr(errors, "dutyLast24Hours")}/>
<TextField ref={(input) => this._inputs["restBeforeDuty"] = input}  name="restBeforeDuty" fullWidth defaultValue={this.getAttr(obj, "restBeforeDuty", "")} floatingLabelText="Rest Before Duty" readOnly={readOnly}  errors={this.getAttr(errors, "restBeforeDuty")}/>
            <GNestedForms ref={(i) => this._inputs["flightCrewLicenses"] = i} description="List of Flight Crew Licenses" prefix="flightCrewLicenses" readOnly={readOnly} objs={obj.flightCrewLicenses} renderNested={(nobj, k, refFunc) => <FlightCrewLicenseFormInline i={k} obj={Object.assign({flightCrewId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["flightCrewId"]} errors={errors} prefix="flightCrewLicenses"/>}/>
        </div>
    }

}
    
      