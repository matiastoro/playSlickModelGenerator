import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';

import {GNestedForms} from '../gforms/GForm';
import {NeoAerodromeOcurrenceCategoryFormInline} from '../neoAerodromeOcurrenceCategory/NeoAerodromeOcurrenceCategoryForm'
import {NeoEventTypePhaseFormInline} from '../neoEventTypePhase/NeoEventTypePhaseForm'
      
//inputs de nested


/*
import NeoAerodromeForm from './components/neoAerodrome/NeoAerodromeForm'
import NeoAerodromeList from './components/neoAerodrome/NeoAerodromeList'

<Route path="/neoAerodrome/" component={NeoAerodromeList} />
<Route path="/neoAerodrome/new" component={NeoAerodromeForm} />
<Route path="/neoAerodrome/:id" component={NeoAerodromeForm} />
<Route path="/neoAerodrome/:page/:pageLength" component={NeoAerodromeList} />
*/
export default class NeoAerodromeForm extends GForm{
    showUrl =  '/neoAerodrome/'
    listUrl =  '/neoAerodrome/'
    apiGetUrl =  '/neoAerodrome/show/'
    apiCreateUrl = '/neoAerodrome/save'
    apiUpdateUrl = '/neoAerodrome/update/'
    apiDeleteUrl = '/neoAerodrome/delete/'
    apiOptionsUrl = "/neoAerodrome/options"

    objStr = 'Neo Aerodrome'
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
            {hide.includes("reportingEntityId")?<HiddenField ref={(input) => this._inputs["reportingEntityId"] = input} name="reportingEntityId" defaultValue={this.getAttr(obj, "reportingEntityId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["reportingEntityId"] = input}  name="reportingEntityId" fullWidth defaultValue={this.getAttr(obj, "reportingEntityId", "")} options={this.getOptions(options.reportingEntitys.map(o => {return {"value": o.id, "label": o.reportingEntity, "parentId": o.reportingEntityId}}))} floatingLabelText="Reporting Entity Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "reportingEntityId")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["reportingEntityName"] = input}  name="reportingEntityName" fullWidth defaultValue={this.getAttr(obj, "reportingEntityName", "")} floatingLabelText="Reporting Entity Name" readOnly={readOnly} required={true} errors={this.getAttr(errors, "reportingEntityName")}/>
          </div>
          <div>
            <DateTime ref={(input) => this._inputs["generalInformation.when"] = input}  name="generalInformation.when" fullWidth defaultValue={this.getAttr(obj, "generalInformation.when")} floatingLabelText="When" readOnly={readOnly} required={true} errors={this.getAttr(errors, "generalInformation.when")} />
          </div>
          <div>
            <DateTime ref={(input) => this._inputs["generalInformation.whenLocal"] = input}  name="generalInformation.whenLocal" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whenLocal")} floatingLabelText="When Local" readOnly={readOnly} required={false} errors={this.getAttr(errors, "generalInformation.whenLocal")} />
          </div>
          <div>
            {hide.includes("generalInformation.whereStateAreaId")?<HiddenField ref={(input) => this._inputs["generalInformation.whereStateAreaId"] = input} name="generalInformation.whereStateAreaId" defaultValue={this.getAttr(obj, "generalInformation.whereStateAreaId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["generalInformation.whereStateAreaId"] = input}  name="generalInformation.whereStateAreaId" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whereStateAreaId", "")} options={this.getOptions(options.stateAreas.map(o => {return {"value": o.id, "label": o.stateArea, "parentId": o.stateAreaId}}))} floatingLabelText="Where State Area Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "generalInformation.whereStateAreaId")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["generalInformation.whereLocationName"] = input}  name="generalInformation.whereLocationName" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whereLocationName", "")} floatingLabelText="Where Location Name" readOnly={readOnly} required={true} errors={this.getAttr(errors, "generalInformation.whereLocationName")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["generalInformation.whereLatitudeOcc"] = input}  name="generalInformation.whereLatitudeOcc" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whereLatitudeOcc", "")} floatingLabelText="Where Latitude Occ" readOnly={readOnly} required={false} errors={this.getAttr(errors, "generalInformation.whereLatitudeOcc")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["generalInformation.whereLongitudeOcc"] = input}  name="generalInformation.whereLongitudeOcc" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whereLongitudeOcc", "")} floatingLabelText="Where Longitude Occ" readOnly={readOnly} required={false} errors={this.getAttr(errors, "generalInformation.whereLongitudeOcc")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["generalInformation.whatHeadline"] = input}  name="generalInformation.whatHeadline" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whatHeadline", "")} floatingLabelText="What Headline" readOnly={readOnly} required={true} errors={this.getAttr(errors, "generalInformation.whatHeadline")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["generalInformation.whatNarrativeLanguaje"] = input}  name="generalInformation.whatNarrativeLanguaje" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whatNarrativeLanguaje", "")} floatingLabelText="What Narrative Languaje" readOnly={readOnly} required={true} errors={this.getAttr(errors, "generalInformation.whatNarrativeLanguaje")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["generalInformation.whatNarrative"] = input} multiLine rows={3} name="generalInformation.whatNarrative" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whatNarrative", "")} floatingLabelText="What Narrative" readOnly={readOnly} required={true} errors={this.getAttr(errors, "generalInformation.whatNarrative")}/>
          </div>
          <div>
            {hide.includes("aerodromeDescription.aerodromeId")?<HiddenField ref={(input) => this._inputs["aerodromeDescription.aerodromeId"] = input} name="aerodromeDescription.aerodromeId" defaultValue={this.getAttr(obj, "aerodromeDescription.aerodromeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeDescription.aerodromeId"] = input}  name="aerodromeDescription.aerodromeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.aerodromeId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.icaoCode, "parentId": o.aerodromeId}}))} floatingLabelText="Aerodrome Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aerodromeDescription.aerodromeId")} />}
          </div>
          <div>
            {hide.includes("aerodromeDescription.aerodromeLocationId")?<HiddenField ref={(input) => this._inputs["aerodromeDescription.aerodromeLocationId"] = input} name="aerodromeDescription.aerodromeLocationId" defaultValue={this.getAttr(obj, "aerodromeDescription.aerodromeLocationId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeDescription.aerodromeLocationId"] = input}  name="aerodromeDescription.aerodromeLocationId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.aerodromeLocationId", "")} options={this.getOptions(options.aerodromeLocations.map(o => {return {"value": o.id, "label": o.location, "parentId": o.aerodromeLocationId}}))} floatingLabelText="Aerodrome Location Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aerodromeDescription.aerodromeLocationId")} />}
          </div>
          <div>
            {hide.includes("aerodromeDescription.aerodromeStatusId")?<HiddenField ref={(input) => this._inputs["aerodromeDescription.aerodromeStatusId"] = input} name="aerodromeDescription.aerodromeStatusId" defaultValue={this.getAttr(obj, "aerodromeDescription.aerodromeStatusId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeDescription.aerodromeStatusId"] = input}  name="aerodromeDescription.aerodromeStatusId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.aerodromeStatusId", "")} options={this.getOptions(options.aerodromeStatuss.map(o => {return {"value": o.id, "label": o.status, "parentId": o.aerodromeStatusId}}))} floatingLabelText="Aerodrome Status Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aerodromeDescription.aerodromeStatusId")} />}
          </div>
          <div>
            {hide.includes("aerodromeDescription.aerodromeTypeId")?<HiddenField ref={(input) => this._inputs["aerodromeDescription.aerodromeTypeId"] = input} name="aerodromeDescription.aerodromeTypeId" defaultValue={this.getAttr(obj, "aerodromeDescription.aerodromeTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeDescription.aerodromeTypeId"] = input}  name="aerodromeDescription.aerodromeTypeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.aerodromeTypeId", "")} options={this.getOptions(options.aerodromeTypes.map(o => {return {"value": o.id, "label": o.tpe, "parentId": o.aerodromeTypeId}}))} floatingLabelText="Aerodrome Type Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aerodromeDescription.aerodromeTypeId")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["aerodromeDescription.latitude"] = input}  name="aerodromeDescription.latitude" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.latitude", "")} floatingLabelText="Latitude" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aerodromeDescription.latitude")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["aerodromeDescription.longitude"] = input}  name="aerodromeDescription.longitude" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.longitude", "")} floatingLabelText="Longitude" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aerodromeDescription.longitude")}/>
          </div>
          <div>
            {hide.includes("aerodromeDescription.runwayId")?<HiddenField ref={(input) => this._inputs["aerodromeDescription.runwayId"] = input} name="aerodromeDescription.runwayId" defaultValue={this.getAttr(obj, "aerodromeDescription.runwayId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeDescription.runwayId"] = input}  name="aerodromeDescription.runwayId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.runwayId", "")} options={this.getOptions(options.runways.map(o => {return {"value": o.id, "label": o.runway, "parentId": o.runwayId}}))} floatingLabelText="Runway Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aerodromeDescription.runwayId")} />}
          </div>
          <div>
            {hide.includes("aerodromeDescription.runwaySurfaceTypeId")?<HiddenField ref={(input) => this._inputs["aerodromeDescription.runwaySurfaceTypeId"] = input} name="aerodromeDescription.runwaySurfaceTypeId" defaultValue={this.getAttr(obj, "aerodromeDescription.runwaySurfaceTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeDescription.runwaySurfaceTypeId"] = input}  name="aerodromeDescription.runwaySurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.runwaySurfaceTypeId", "")} options={this.getOptions(options.runwaySurfaceTypes.map(o => {return {"value": o.id, "label": o.surfaceType, "parentId": o.runwaySurfaceTypeId}}))} floatingLabelText="Runway Surface Type Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aerodromeDescription.runwaySurfaceTypeId")} />}
          </div>
          <div>
            {hide.includes("aerodromeDescription.helicopterLandingAreaTypeId")?<HiddenField ref={(input) => this._inputs["aerodromeDescription.helicopterLandingAreaTypeId"] = input} name="aerodromeDescription.helicopterLandingAreaTypeId" defaultValue={this.getAttr(obj, "aerodromeDescription.helicopterLandingAreaTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeDescription.helicopterLandingAreaTypeId"] = input}  name="aerodromeDescription.helicopterLandingAreaTypeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.helicopterLandingAreaTypeId", "")} options={this.getOptions(options.helicopterLandingAreaTypes.map(o => {return {"value": o.id, "label": o.landingAreaType, "parentId": o.helicopterLandingAreaTypeId}}))} floatingLabelText="Helicopter Landing Area Type Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aerodromeDescription.helicopterLandingAreaTypeId")} />}
          </div>
          <div>
            {hide.includes("aerodromeDescription.helicopterLandingAreaAreaConfigurationId")?<HiddenField ref={(input) => this._inputs["aerodromeDescription.helicopterLandingAreaAreaConfigurationId"] = input} name="aerodromeDescription.helicopterLandingAreaAreaConfigurationId" defaultValue={this.getAttr(obj, "aerodromeDescription.helicopterLandingAreaAreaConfigurationId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeDescription.helicopterLandingAreaAreaConfigurationId"] = input}  name="aerodromeDescription.helicopterLandingAreaAreaConfigurationId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.helicopterLandingAreaAreaConfigurationId", "")} options={this.getOptions(options.helicopterLandingAreaAreaConfigurations.map(o => {return {"value": o.id, "label": o.areaConfiguration, "parentId": o.helicopterLandingAreaAreaConfigurationId}}))} floatingLabelText="Helicopter Landing Area Area Configuration Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aerodromeDescription.helicopterLandingAreaAreaConfigurationId")} />}
          </div>
          <div>
            {hide.includes("aerodromeDescription.helicopterLandingAreaSurfaceTypeId")?<HiddenField ref={(input) => this._inputs["aerodromeDescription.helicopterLandingAreaSurfaceTypeId"] = input} name="aerodromeDescription.helicopterLandingAreaSurfaceTypeId" defaultValue={this.getAttr(obj, "aerodromeDescription.helicopterLandingAreaSurfaceTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aerodromeDescription.helicopterLandingAreaSurfaceTypeId"] = input}  name="aerodromeDescription.helicopterLandingAreaSurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.helicopterLandingAreaSurfaceTypeId", "")} options={this.getOptions(options.helicopterLandingAreaSurfaceTypes.map(o => {return {"value": o.id, "label": o.surfaceType, "parentId": o.helicopterLandingAreaSurfaceTypeId}}))} floatingLabelText="Helicopter Landing Area Surface Type Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aerodromeDescription.helicopterLandingAreaSurfaceTypeId")} />}
          </div>
          <div>
            {hide.includes("classification.ocurrenceClassId")?<HiddenField ref={(input) => this._inputs["classification.ocurrenceClassId"] = input} name="classification.ocurrenceClassId" defaultValue={this.getAttr(obj, "classification.ocurrenceClassId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["classification.ocurrenceClassId"] = input}  name="classification.ocurrenceClassId" fullWidth defaultValue={this.getAttr(obj, "classification.ocurrenceClassId", "")} options={this.getOptions(options.ocurrenceClasss.map(o => {return {"value": o.id, "label": o.ocurrenceClass, "parentId": o.ocurrenceClassId}}))} floatingLabelText="Ocurrence Class Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "classification.ocurrenceClassId")} />}
          </div>
          <div>
            {hide.includes("classification.detectionPhaseId")?<HiddenField ref={(input) => this._inputs["classification.detectionPhaseId"] = input} name="classification.detectionPhaseId" defaultValue={this.getAttr(obj, "classification.detectionPhaseId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["classification.detectionPhaseId"] = input}  name="classification.detectionPhaseId" fullWidth defaultValue={this.getAttr(obj, "classification.detectionPhaseId", "")} options={this.getOptions(options.detectionPhases.map(o => {return {"value": o.id, "label": o.detectionPhase, "parentId": o.detectionPhaseId}}))} floatingLabelText="Detection Phase Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "classification.detectionPhaseId")} />}
          </div>
            <GNestedForms ref={(i) => this._inputs["neoAerodromeOcurrenceCategorys"] = i} description="List of Neo Aerodrome Ocurrence Categorys" prefix="neoAerodromeOcurrenceCategorys" readOnly={readOnly} objs={obj.neoAerodromeOcurrenceCategorys} renderNested={(nobj, k, refFunc) => <NeoAerodromeOcurrenceCategoryFormInline i={k} obj={Object.assign({neoAerodromeId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["neoAerodromeId"]} errors={errors} prefix="neoAerodromeOcurrenceCategorys"/>}/>
            <GNestedForms ref={(i) => this._inputs["neoEventTypePhases"] = i} description="List of Neo Event Type Phases" prefix="neoEventTypePhases" readOnly={readOnly} objs={obj.neoEventTypePhases} renderNested={(nobj, k, refFunc) => <NeoEventTypePhaseFormInline i={k} obj={Object.assign({neoAerodromeId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["neoAerodromeId"]} errors={errors} prefix="neoEventTypePhases"/>}/>
          <div>
            <TextField ref={(input) => this._inputs["risk.riskClassification"] = input}  name="risk.riskClassification" fullWidth defaultValue={this.getAttr(obj, "risk.riskClassification", "")} floatingLabelText="Risk Classification" readOnly={readOnly} required={true} errors={this.getAttr(errors, "risk.riskClassification")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["risk.riskMethodology"] = input}  name="risk.riskMethodology" fullWidth defaultValue={this.getAttr(obj, "risk.riskMethodology", "")} floatingLabelText="Risk Methodology" readOnly={readOnly} required={false} errors={this.getAttr(errors, "risk.riskMethodology")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["risk.riskAssessment"] = input} multiLine rows={3} name="risk.riskAssessment" fullWidth defaultValue={this.getAttr(obj, "risk.riskAssessment", "")} floatingLabelText="Risk Assessment" readOnly={readOnly} required={false} errors={this.getAttr(errors, "risk.riskAssessment")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["assessment.analysisFollowUp"] = input} multiLine rows={3} name="assessment.analysisFollowUp" fullWidth defaultValue={this.getAttr(obj, "assessment.analysisFollowUp", "")} floatingLabelText="Analysis Follow Up" readOnly={readOnly} required={false} errors={this.getAttr(errors, "assessment.analysisFollowUp")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["assessment.correctiveActions"] = input} multiLine rows={3} name="assessment.correctiveActions" fullWidth defaultValue={this.getAttr(obj, "assessment.correctiveActions", "")} floatingLabelText="Corrective Actions" readOnly={readOnly} required={false} errors={this.getAttr(errors, "assessment.correctiveActions")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["assessment.conclusions"] = input} multiLine rows={3} name="assessment.conclusions" fullWidth defaultValue={this.getAttr(obj, "assessment.conclusions", "")} floatingLabelText="Conclusions" readOnly={readOnly} required={false} errors={this.getAttr(errors, "assessment.conclusions")}/>
          </div>
          <div>
            {hide.includes("reportManagement.reportStatusId")?<HiddenField ref={(input) => this._inputs["reportManagement.reportStatusId"] = input} name="reportManagement.reportStatusId" defaultValue={this.getAttr(obj, "reportManagement.reportStatusId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["reportManagement.reportStatusId"] = input}  name="reportManagement.reportStatusId" fullWidth defaultValue={this.getAttr(obj, "reportManagement.reportStatusId", "")} options={this.getOptions(options.neoReportStatuss.map(o => {return {"value": o.id, "label": o.reportStatus, "parentId": o.neoReportStatusId}}))} floatingLabelText="Report Status Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "reportManagement.reportStatusId")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["reportManagement.reportVersion"] = input} multiLine rows={3} name="reportManagement.reportVersion" fullWidth defaultValue={this.getAttr(obj, "reportManagement.reportVersion", "")} floatingLabelText="Report Version" readOnly={readOnly} required={false} errors={this.getAttr(errors, "reportManagement.reportVersion")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["attachments"] = input} multiLine rows={3} name="attachments" fullWidth defaultValue={this.getAttr(obj, "attachments", "")} floatingLabelText="Attachments" readOnly={readOnly} required={false} errors={this.getAttr(errors, "attachments")}/>
          </div>
        </div>
    }

}


      