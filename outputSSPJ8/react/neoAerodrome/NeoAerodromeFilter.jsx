import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GFilter from '../gforms/GFilter';

import {GNestedForms} from '../gforms/GForm';
import {NeoAerodromeOcurrenceCategoryFormInline} from '../neoAerodromeOcurrenceCategory/NeoAerodromeOcurrenceCategoryForm'
import {NeoEventTypePhaseFormInline} from '../neoEventTypePhase/NeoEventTypePhaseForm'
      
import { Row, Col } from 'react-flexbox-grid';

//inputs de nested



const styles= {
    contentFlex: {
        display: "flex",
        flexWrap: "wrap"
    },
    itemFlex: {
        flex: "1 1 120px",
        marginRight: 16
    }
}
export default class NeoAerodromeFilter extends GFilter{

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
                    <SelectField ref={(input) => this._inputs["reportingEntityId"] = input}  name="reportingEntityId" fullWidth defaultValue={this.getAttr(obj, "reportingEntityId", "")} options={this.getOptions(options.reportingEntitys.map(o => {return {"value": o.id, "label": o.reportingEntity, "parentId": o.reportingEntityId}}))} floatingLabelText="Reporting Entity Id" readOnly={readOnly}  errors={this.getAttr(errors, "reportingEntityId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["reportingEntityName"] = input}  name="reportingEntityName" fullWidth defaultValue={this.getAttr(obj, "reportingEntityName", "")} floatingLabelText="Reporting Entity Name" readOnly={readOnly}  errors={this.getAttr(errors, "reportingEntityName")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <DateTime ref={(input) => this._inputs["generalInformation.when"] = input}  name="generalInformation.when" fullWidth defaultValue={this.getAttr(obj, "generalInformation.when")} floatingLabelText="When" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.when")} />
                </Col>
                <Col xs={6} sm={2}>
                    <DateTime ref={(input) => this._inputs["generalInformation.whenLocal"] = input}  name="generalInformation.whenLocal" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whenLocal")} floatingLabelText="When Local" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whenLocal")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["generalInformation.whereStateAreaId"] = input}  name="generalInformation.whereStateAreaId" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whereStateAreaId", "")} options={this.getOptions(options.stateAreas.map(o => {return {"value": o.id, "label": o.stateArea, "parentId": o.stateAreaId}}))} floatingLabelText="Where State Area Id" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whereStateAreaId")} />
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["generalInformation.whereLocationName"] = input}  name="generalInformation.whereLocationName" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whereLocationName", "")} floatingLabelText="Where Location Name" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whereLocationName")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["generalInformation.whereLatitudeOcc"] = input}  name="generalInformation.whereLatitudeOcc" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whereLatitudeOcc", "")} floatingLabelText="Where Latitude Occ" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whereLatitudeOcc")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["generalInformation.whereLongitudeOcc"] = input}  name="generalInformation.whereLongitudeOcc" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whereLongitudeOcc", "")} floatingLabelText="Where Longitude Occ" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whereLongitudeOcc")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["generalInformation.whatHeadline"] = input}  name="generalInformation.whatHeadline" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whatHeadline", "")} floatingLabelText="What Headline" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whatHeadline")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["generalInformation.whatNarrativeLanguaje"] = input}  name="generalInformation.whatNarrativeLanguaje" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whatNarrativeLanguaje", "")} floatingLabelText="What Narrative Languaje" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whatNarrativeLanguaje")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["generalInformation.whatNarrative"] = input} multiLine rows={3} name="generalInformation.whatNarrative" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whatNarrative", "")} floatingLabelText="What Narrative" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whatNarrative")}/>
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeDescription.aerodromeId"] = input}  name="aerodromeDescription.aerodromeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.aerodromeId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.icaoCode, "parentId": o.aerodromeId}}))} floatingLabelText="Aerodrome Id" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeDescription.aerodromeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeDescription.aerodromeLocationId"] = input}  name="aerodromeDescription.aerodromeLocationId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.aerodromeLocationId", "")} options={this.getOptions(options.aerodromeLocations.map(o => {return {"value": o.id, "label": o.location, "parentId": o.aerodromeLocationId}}))} floatingLabelText="Aerodrome Location Id" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeDescription.aerodromeLocationId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeDescription.aerodromeStatusId"] = input}  name="aerodromeDescription.aerodromeStatusId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.aerodromeStatusId", "")} options={this.getOptions(options.aerodromeStatuss.map(o => {return {"value": o.id, "label": o.status, "parentId": o.aerodromeStatusId}}))} floatingLabelText="Aerodrome Status Id" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeDescription.aerodromeStatusId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeDescription.aerodromeTypeId"] = input}  name="aerodromeDescription.aerodromeTypeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.aerodromeTypeId", "")} options={this.getOptions(options.aerodromeTypes.map(o => {return {"value": o.id, "label": o.tpe, "parentId": o.aerodromeTypeId}}))} floatingLabelText="Aerodrome Type Id" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeDescription.aerodromeTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["aerodromeDescription.latitude"] = input}  name="aerodromeDescription.latitude" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.latitude", "")} floatingLabelText="Latitude" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeDescription.latitude")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["aerodromeDescription.longitude"] = input}  name="aerodromeDescription.longitude" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.longitude", "")} floatingLabelText="Longitude" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeDescription.longitude")}/>
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeDescription.runwayId"] = input}  name="aerodromeDescription.runwayId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.runwayId", "")} options={this.getOptions(options.runways.map(o => {return {"value": o.id, "label": o.runway, "parentId": o.runwayId}}))} floatingLabelText="Runway Id" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeDescription.runwayId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeDescription.runwaySurfaceTypeId"] = input}  name="aerodromeDescription.runwaySurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.runwaySurfaceTypeId", "")} options={this.getOptions(options.runwaySurfaceTypes.map(o => {return {"value": o.id, "label": o.surfaceType, "parentId": o.runwaySurfaceTypeId}}))} floatingLabelText="Runway Surface Type Id" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeDescription.runwaySurfaceTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeDescription.helicopterLandingAreaTypeId"] = input}  name="aerodromeDescription.helicopterLandingAreaTypeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.helicopterLandingAreaTypeId", "")} options={this.getOptions(options.helicopterLandingAreaTypes.map(o => {return {"value": o.id, "label": o.landingAreaType, "parentId": o.helicopterLandingAreaTypeId}}))} floatingLabelText="Helicopter Landing Area Type Id" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeDescription.helicopterLandingAreaTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeDescription.helicopterLandingAreaAreaConfigurationId"] = input}  name="aerodromeDescription.helicopterLandingAreaAreaConfigurationId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.helicopterLandingAreaAreaConfigurationId", "")} options={this.getOptions(options.helicopterLandingAreaAreaConfigurations.map(o => {return {"value": o.id, "label": o.areaConfiguration, "parentId": o.helicopterLandingAreaAreaConfigurationId}}))} floatingLabelText="Helicopter Landing Area Area Configuration Id" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeDescription.helicopterLandingAreaAreaConfigurationId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeDescription.helicopterLandingAreaSurfaceTypeId"] = input}  name="aerodromeDescription.helicopterLandingAreaSurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeDescription.helicopterLandingAreaSurfaceTypeId", "")} options={this.getOptions(options.helicopterLandingAreaSurfaceTypes.map(o => {return {"value": o.id, "label": o.surfaceType, "parentId": o.helicopterLandingAreaSurfaceTypeId}}))} floatingLabelText="Helicopter Landing Area Surface Type Id" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeDescription.helicopterLandingAreaSurfaceTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["classification.ocurrenceClassId"] = input}  name="classification.ocurrenceClassId" fullWidth defaultValue={this.getAttr(obj, "classification.ocurrenceClassId", "")} options={this.getOptions(options.ocurrenceClasss.map(o => {return {"value": o.id, "label": o.ocurrenceClass, "parentId": o.ocurrenceClassId}}))} floatingLabelText="Ocurrence Class Id" readOnly={readOnly}  errors={this.getAttr(errors, "classification.ocurrenceClassId")} />
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["classification.detectionPhaseId"] = input}  name="classification.detectionPhaseId" fullWidth defaultValue={this.getAttr(obj, "classification.detectionPhaseId", "")} options={this.getOptions(options.detectionPhases.map(o => {return {"value": o.id, "label": o.detectionPhase, "parentId": o.detectionPhaseId}}))} floatingLabelText="Detection Phase Id" readOnly={readOnly}  errors={this.getAttr(errors, "classification.detectionPhaseId")} />
                </Col>
                <Col xs={6} sm={2}>
                                <GNestedForms ref={(i) => this._inputs["neoAerodromeOcurrenceCategorys"] = i} description="List of Neo Aerodrome Ocurrence Categorys" prefix="neoAerodromeOcurrenceCategorys" readOnly={readOnly} objs={obj.neoAerodromeOcurrenceCategorys} renderNested={(nobj, k, refFunc) => <NeoAerodromeOcurrenceCategoryFormInline i={k} obj={Object.assign({neoAerodromeId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["neoAerodromeId"]} errors={errors} prefix="neoAerodromeOcurrenceCategorys"/>}/>
                </Col>
                <Col xs={6} sm={2}>
                                <GNestedForms ref={(i) => this._inputs["neoEventTypePhases"] = i} description="List of Neo Event Type Phases" prefix="neoEventTypePhases" readOnly={readOnly} objs={obj.neoEventTypePhases} renderNested={(nobj, k, refFunc) => <NeoEventTypePhaseFormInline i={k} obj={Object.assign({neoAerodromeId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["neoAerodromeId"]} errors={errors} prefix="neoEventTypePhases"/>}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["risk.riskClassification"] = input}  name="risk.riskClassification" fullWidth defaultValue={this.getAttr(obj, "risk.riskClassification", "")} floatingLabelText="Risk Classification" readOnly={readOnly}  errors={this.getAttr(errors, "risk.riskClassification")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["risk.riskMethodology"] = input}  name="risk.riskMethodology" fullWidth defaultValue={this.getAttr(obj, "risk.riskMethodology", "")} floatingLabelText="Risk Methodology" readOnly={readOnly}  errors={this.getAttr(errors, "risk.riskMethodology")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["risk.riskAssessment"] = input} multiLine rows={3} name="risk.riskAssessment" fullWidth defaultValue={this.getAttr(obj, "risk.riskAssessment", "")} floatingLabelText="Risk Assessment" readOnly={readOnly}  errors={this.getAttr(errors, "risk.riskAssessment")}/>
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["assessment.analysisFollowUp"] = input} multiLine rows={3} name="assessment.analysisFollowUp" fullWidth defaultValue={this.getAttr(obj, "assessment.analysisFollowUp", "")} floatingLabelText="Analysis Follow Up" readOnly={readOnly}  errors={this.getAttr(errors, "assessment.analysisFollowUp")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["assessment.correctiveActions"] = input} multiLine rows={3} name="assessment.correctiveActions" fullWidth defaultValue={this.getAttr(obj, "assessment.correctiveActions", "")} floatingLabelText="Corrective Actions" readOnly={readOnly}  errors={this.getAttr(errors, "assessment.correctiveActions")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["assessment.conclusions"] = input} multiLine rows={3} name="assessment.conclusions" fullWidth defaultValue={this.getAttr(obj, "assessment.conclusions", "")} floatingLabelText="Conclusions" readOnly={readOnly}  errors={this.getAttr(errors, "assessment.conclusions")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["reportManagement.reportStatusId"] = input}  name="reportManagement.reportStatusId" fullWidth defaultValue={this.getAttr(obj, "reportManagement.reportStatusId", "")} options={this.getOptions(options.neoReportStatuss.map(o => {return {"value": o.id, "label": o.reportStatus, "parentId": o.neoReportStatusId}}))} floatingLabelText="Report Status Id" readOnly={readOnly}  errors={this.getAttr(errors, "reportManagement.reportStatusId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["reportManagement.reportVersion"] = input} multiLine rows={3} name="reportManagement.reportVersion" fullWidth defaultValue={this.getAttr(obj, "reportManagement.reportVersion", "")} floatingLabelText="Report Version" readOnly={readOnly}  errors={this.getAttr(errors, "reportManagement.reportVersion")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["attachments"] = input} multiLine rows={3} name="attachments" fullWidth defaultValue={this.getAttr(obj, "attachments", "")} floatingLabelText="Attachments" readOnly={readOnly}  errors={this.getAttr(errors, "attachments")}/>
                </Col>
            </Row>
        </div>
    }

}


      