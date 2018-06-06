import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';
import {GFormInline} from '../gforms/GForm';
import {GNestedForms} from '../gforms/GForm';
import {FlightCrewLicenseFormInline} from '../flightCrewLicense/FlightCrewLicenseForm'
      
//inputs de nested



export default class FlightCrewForm extends GForm{
    showUrl =  '/flightCrew/'
    listUrl =  '/flightCrew/'
    apiGetUrl =  '/flightCrew/show/'
    apiCreateUrl = '/flightCrew/save'
    apiUpdateUrl = '/flightCrew/update/'
    apiDeleteUrl = '/flightCrew/delete/'
    apiOptionsUrl = "/flightCrew/options"

    objStr = 'Flight Crew'
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
            {hide.includes("neoFlightOperationId")?<HiddenField ref={(input) => this._inputs["neoFlightOperationId"] = input} name="neoFlightOperationId" defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["neoFlightOperationId"] = input}  name="neoFlightOperationId" fullWidth defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} options={this.getOptions(options.neoFlightOperations.map(o => {return {"value": o.id, "label": o.neoFlightOperation, "parentId": o.neoFlightOperationId}}))} floatingLabelText="Neo Flight Operation" readOnly={readOnly} required={true} errors={this.getAttr(errors, "neoFlightOperationId")} />}
          </div>
          <div>
            {hide.includes("categoryId")?<HiddenField ref={(input) => this._inputs["categoryId"] = input} name="categoryId" defaultValue={this.getAttr(obj, "categoryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["categoryId"] = input}  name="categoryId" fullWidth defaultValue={this.getAttr(obj, "categoryId", "")} options={this.getOptions(options.flightCrewCategorys.map(o => {return {"value": o.id, "label": o.category, "parentId": o.flightCrewCategoryId}}))} floatingLabelText="Category" readOnly={readOnly} required={true} errors={this.getAttr(errors, "categoryId")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["experienceThis"] = input}  name="experienceThis" fullWidth defaultValue={this.getAttr(obj, "experienceThis", "")} floatingLabelText="Experience This" readOnly={readOnly} required={false} errors={this.getAttr(errors, "experienceThis")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["experienceAll"] = input}  name="experienceAll" fullWidth defaultValue={this.getAttr(obj, "experienceAll", "")} floatingLabelText="Experience All" readOnly={readOnly} required={false} errors={this.getAttr(errors, "experienceAll")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["dutyLast24Hours"] = input}  name="dutyLast24Hours" fullWidth defaultValue={this.getAttr(obj, "dutyLast24Hours", "")} floatingLabelText="Duty Last 24 Hours" readOnly={readOnly} required={false} errors={this.getAttr(errors, "dutyLast24Hours")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["restBeforeDuty"] = input}  name="restBeforeDuty" fullWidth defaultValue={this.getAttr(obj, "restBeforeDuty", "")} floatingLabelText="Rest Before Duty" readOnly={readOnly} required={false} errors={this.getAttr(errors, "restBeforeDuty")}/>
          </div>
            <GNestedForms ref={(i) => this._inputs["flightCrewLicenses"] = i} description="List of Flight Crew Licenses" prefix="flightCrewLicenses" readOnly={readOnly} objs={obj.flightCrewLicenses} renderNested={(nobj, k, refFunc) => <FlightCrewLicenseFormInline i={k} obj={Object.assign({flightCrewId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["flightCrewId"]} errors={errors} prefix="flightCrewLicenses"/>}/>
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
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("neoFlightOperationId")?<HiddenField ref={(input) => this._inputs["neoFlightOperationId"] = input} name="neoFlightOperationId" defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["neoFlightOperationId"] = input}  name="neoFlightOperationId" fullWidth defaultValue={this.getAttr(obj, "neoFlightOperationId", "")} options={this.getOptions(options.neoFlightOperations.map(o => {return {"value": o.id, "label": o.neoFlightOperation, "parentId": o.neoFlightOperationId}}))} floatingLabelText="Neo Flight Operation" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].neoFlightOperationId", "")} />}
          </div>
          <div>
            {hide.includes("categoryId")?<HiddenField ref={(input) => this._inputs["categoryId"] = input} name="categoryId" defaultValue={this.getAttr(obj, "categoryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["categoryId"] = input}  name="categoryId" fullWidth defaultValue={this.getAttr(obj, "categoryId", "")} options={this.getOptions(options.flightCrewCategorys.map(o => {return {"value": o.id, "label": o.category, "parentId": o.flightCrewCategoryId}}))} floatingLabelText="Category" readOnly={readOnly} required={true} errors={this.getAttr(errors, this.props.prefix+"["+i+"].categoryId", "")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["experienceThis"] = input}  name="experienceThis" fullWidth defaultValue={this.getAttr(obj, "experienceThis", "")} floatingLabelText="Experience This" readOnly={readOnly} required={false} errors={this.getAttr(errors, "experienceThis")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["experienceAll"] = input}  name="experienceAll" fullWidth defaultValue={this.getAttr(obj, "experienceAll", "")} floatingLabelText="Experience All" readOnly={readOnly} required={false} errors={this.getAttr(errors, "experienceAll")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["dutyLast24Hours"] = input}  name="dutyLast24Hours" fullWidth defaultValue={this.getAttr(obj, "dutyLast24Hours", "")} floatingLabelText="Duty Last 24 Hours" readOnly={readOnly} required={false} errors={this.getAttr(errors, "dutyLast24Hours")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["restBeforeDuty"] = input}  name="restBeforeDuty" fullWidth defaultValue={this.getAttr(obj, "restBeforeDuty", "")} floatingLabelText="Rest Before Duty" readOnly={readOnly} required={false} errors={this.getAttr(errors, "restBeforeDuty")}/>
          </div>
            <GNestedForms ref={(i) => this._inputs["flightCrewLicenses"] = i} description="List of Flight Crew Licenses" prefix="flightCrewLicenses" readOnly={readOnly} objs={obj.flightCrewLicenses} renderNested={(nobj, k, refFunc) => <FlightCrewLicenseFormInline i={k} obj={Object.assign({flightCrewId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["flightCrewId"]} errors={errors} prefix="flightCrewLicenses"/>}/>
        </div>
    }

}
    
      