import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GFilter from '../gforms/GFilter';

import {GNestedForms} from '../gforms/GForm';
import {FlightCrewFormInline} from '../flightCrew/FlightCrewForm'
import {NeoFlightOperationOcurrenceCategoryFormInline} from '../neoFlightOperationOcurrenceCategory/NeoFlightOperationOcurrenceCategoryForm'
import {NeoFlightOperationEventTypePhaseFormInline} from '../neoFlightOperationEventTypePhase/NeoFlightOperationEventTypePhaseForm'
      
import { Row, Col } from 'react-flexbox-grid';

//inputs de nested




export default class NeoFlightOperationFilter extends GFilter{

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
                    <SelectField ref={(input) => this._inputs["reportingEntityId"] = input}  name="reportingEntityId" fullWidth defaultValue={this.getAttr(obj, "reportingEntityId", "")} options={this.getOptions(options.reportingEntitys.map(o => {return {"value": o.id, "label": o.reportingEntity, "parentId": o.reportingEntityId}}))} floatingLabelText="Reporting Entity" readOnly={readOnly}  errors={this.getAttr(errors, "reportingEntityId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["reportingEntityName"] = input}  name="reportingEntityName" fullWidth defaultValue={this.getAttr(obj, "reportingEntityName", "")} floatingLabelText="Reporting Entity Name" readOnly={readOnly}  errors={this.getAttr(errors, "reportingEntityName")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <DateTime ref={(input) => this._inputs["generalInformation.whenFrom"] = input}  name="generalInformation.whenFrom" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whenFrom")} floatingLabelText="When desde" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whenFrom")} />
<DateTime ref={(input) => this._inputs["generalInformation.whenTo"] = input}  name="generalInformation.whenTo" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whenTo")} floatingLabelText="When hasta" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whenTo")} />
                </Col>
                <Col xs={6} sm={2}>
                    <DateTime ref={(input) => this._inputs["generalInformation.whenLocalFrom"] = input}  name="generalInformation.whenLocalFrom" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whenLocalFrom")} floatingLabelText="When Local desde" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whenLocalFrom")} />
<DateTime ref={(input) => this._inputs["generalInformation.whenLocalTo"] = input}  name="generalInformation.whenLocalTo" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whenLocalTo")} floatingLabelText="When Local hasta" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whenLocalTo")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["generalInformation.whereStateAreaId"] = input}  name="generalInformation.whereStateAreaId" fullWidth defaultValue={this.getAttr(obj, "generalInformation.whereStateAreaId", "")} options={this.getOptions(options.stateAreas.map(o => {return {"value": o.id, "label": o.stateArea, "parentId": o.stateAreaId}}))} floatingLabelText="Where State Area" readOnly={readOnly}  errors={this.getAttr(errors, "generalInformation.whereStateAreaId")} />
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
                    <SelectField ref={(input) => this._inputs["aircraftIdentification.stateOfRegistryId"] = input}  name="aircraftIdentification.stateOfRegistryId" fullWidth defaultValue={this.getAttr(obj, "aircraftIdentification.stateOfRegistryId", "")} options={this.getOptions(options.stateAreas.map(o => {return {"value": o.id, "label": o.stateArea, "parentId": o.stateAreaId}}))} floatingLabelText="State Of Registry" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftIdentification.stateOfRegistryId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["aircraftIdentification.registration"] = input}  name="aircraftIdentification.registration" fullWidth defaultValue={this.getAttr(obj, "aircraftIdentification.registration", "")} floatingLabelText="Registration" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftIdentification.registration")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aircraftIdentification.manufacturerModelId"] = input}  name="aircraftIdentification.manufacturerModelId" fullWidth defaultValue={this.getAttr(obj, "aircraftIdentification.manufacturerModelId", "")} options={this.getOptions(options.aircraftManufacturerModels.map(o => {return {"value": o.id, "label": o.manufacturerModel, "parentId": o.aircraftManufacturerModelId}}))} floatingLabelText="Manufacturer Model" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftIdentification.manufacturerModelId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["aircraftIdentification.serialNumber"] = input}  name="aircraftIdentification.serialNumber" fullWidth defaultValue={this.getAttr(obj, "aircraftIdentification.serialNumber", "")} floatingLabelText="Serial Number" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftIdentification.serialNumber")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["aircraftIdentification.yearBuilt"] = input}  name="aircraftIdentification.yearBuilt" fullWidth defaultValue={this.getAttr(obj, "aircraftIdentification.yearBuilt", "")} floatingLabelText="Year Built" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftIdentification.yearBuilt")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aircraftDescription.categoryId"] = input}  name="aircraftDescription.categoryId" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.categoryId", "")} options={this.getOptions(options.aircraftCategorys.map(o => {return {"value": o.id, "label": o.category, "parentId": o.aircraftCategoryId}}))} floatingLabelText="Category" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftDescription.categoryId")} />
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aircraftDescription.massGroupId"] = input}  name="aircraftDescription.massGroupId" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.massGroupId", "")} options={this.getOptions(options.aircraftMassGroups.map(o => {return {"value": o.id, "label": o.massGroup, "parentId": o.aircraftMassGroupId}}))} floatingLabelText="Mass Group" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftDescription.massGroupId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aircraftDescription.propulsionTypeId"] = input}  name="aircraftDescription.propulsionTypeId" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.propulsionTypeId", "")} options={this.getOptions(options.aircraftPropulsionTypes.map(o => {return {"value": o.id, "label": o.propulsionType, "parentId": o.aircraftPropulsionTypeId}}))} floatingLabelText="Propulsion Type" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftDescription.propulsionTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aircraftDescription.wakeTurbCategoryId"] = input}  name="aircraftDescription.wakeTurbCategoryId" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.wakeTurbCategoryId", "")} options={this.getOptions(options.aircraftWakeTurbCategorys.map(o => {return {"value": o.id, "label": o.wakeTurbCategory, "parentId": o.aircraftWakeTurbCategoryId}}))} floatingLabelText="Wake Turb Category" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftDescription.wakeTurbCategoryId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["aircraftDescription.numberOfEngines"] = input}  name="aircraftDescription.numberOfEngines" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.numberOfEngines", "")} floatingLabelText="Number Of Engines" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftDescription.numberOfEngines")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aircraftDescription.landingGearTypeId"] = input}  name="aircraftDescription.landingGearTypeId" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.landingGearTypeId", "")} options={this.getOptions(options.aircraftLandingGearTypes.map(o => {return {"value": o.id, "label": o.landingGearType, "parentId": o.aircraftLandingGearTypeId}}))} floatingLabelText="Landing Gear Type" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftDescription.landingGearTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["aircraftDescription.maximumMass"] = input}  name="aircraftDescription.maximumMass" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.maximumMass", "")} floatingLabelText="Maximum Mass" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftDescription.maximumMass")}/>
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["flightDetails.departureId"] = input}  name="flightDetails.departureId" fullWidth defaultValue={this.getAttr(obj, "flightDetails.departureId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.icaoCode, "parentId": o.aerodromeId}}))} floatingLabelText="Departure" readOnly={readOnly}  errors={this.getAttr(errors, "flightDetails.departureId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["flightDetails.destinationId"] = input}  name="flightDetails.destinationId" fullWidth defaultValue={this.getAttr(obj, "flightDetails.destinationId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.icaoCode, "parentId": o.aerodromeId}}))} floatingLabelText="Destination" readOnly={readOnly}  errors={this.getAttr(errors, "flightDetails.destinationId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["flightDetails.phaseId"] = input}  name="flightDetails.phaseId" fullWidth defaultValue={this.getAttr(obj, "flightDetails.phaseId", "")} options={this.getOptions(options.phases.map(o => {return {"value": o.id, "label": o.phase, "parentId": o.phaseId}}))} floatingLabelText="Phase" readOnly={readOnly}  errors={this.getAttr(errors, "flightDetails.phaseId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["flightDetails.operatorId"] = input}  name="flightDetails.operatorId" fullWidth defaultValue={this.getAttr(obj, "flightDetails.operatorId", "")} options={this.getOptions(options.operators.map(o => {return {"value": o.id, "label": o.operator, "parentId": o.operatorId}}))} floatingLabelText="Operator" readOnly={readOnly}  errors={this.getAttr(errors, "flightDetails.operatorId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["flightDetails.operationTypeId"] = input}  name="flightDetails.operationTypeId" fullWidth defaultValue={this.getAttr(obj, "flightDetails.operationTypeId", "")} options={this.getOptions(options.operationTypes.map(o => {return {"value": o.id, "label": o.operationType, "parentId": o.operationTypeId}}))} floatingLabelText="Operation Type" readOnly={readOnly}  errors={this.getAttr(errors, "flightDetails.operationTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["flightDetails.callsign"] = input}  name="flightDetails.callsign" fullWidth defaultValue={this.getAttr(obj, "flightDetails.callsign", "")} floatingLabelText="Callsign" readOnly={readOnly}  errors={this.getAttr(errors, "flightDetails.callsign")}/>
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["flightDetails.flightNumber"] = input}  name="flightDetails.flightNumber" fullWidth defaultValue={this.getAttr(obj, "flightDetails.flightNumber", "")} floatingLabelText="Flight Number" readOnly={readOnly}  errors={this.getAttr(errors, "flightDetails.flightNumber")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["flightDetails.numberPersons"] = input}  name="flightDetails.numberPersons" fullWidth defaultValue={this.getAttr(obj, "flightDetails.numberPersons", "")} floatingLabelText="Number Persons" readOnly={readOnly}  errors={this.getAttr(errors, "flightDetails.numberPersons")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["operationalInformation.aircraftAltitude"] = input}  name="operationalInformation.aircraftAltitude" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.aircraftAltitude", "")} floatingLabelText="Aircraft Altitude" readOnly={readOnly}  errors={this.getAttr(errors, "operationalInformation.aircraftAltitude")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["operationalInformation.aircraftFlightLevel"] = input}  name="operationalInformation.aircraftFlightLevel" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.aircraftFlightLevel", "")} floatingLabelText="Aircraft Flight Level" readOnly={readOnly}  errors={this.getAttr(errors, "operationalInformation.aircraftFlightLevel")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["operationalInformation.speed"] = input}  name="operationalInformation.speed" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.speed", "")} floatingLabelText="Speed" readOnly={readOnly}  errors={this.getAttr(errors, "operationalInformation.speed")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["operationalInformation.typeOfAirspeedId"] = input}  name="operationalInformation.typeOfAirspeedId" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.typeOfAirspeedId", "")} options={this.getOptions(options.typeOfAirspeeds.map(o => {return {"value": o.id, "label": o.typeOfAirspeed, "parentId": o.typeOfAirspeedId}}))} floatingLabelText="Type Of Airspeed" readOnly={readOnly}  errors={this.getAttr(errors, "operationalInformation.typeOfAirspeedId")} />
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["operationalInformation.flightRuleId"] = input}  name="operationalInformation.flightRuleId" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.flightRuleId", "")} options={this.getOptions(options.flightRules.map(o => {return {"value": o.id, "label": o.flightRule, "parentId": o.flightRuleId}}))} floatingLabelText="Flight Rule" readOnly={readOnly}  errors={this.getAttr(errors, "operationalInformation.flightRuleId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["operationalInformation.trafficTypeId"] = input}  name="operationalInformation.trafficTypeId" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.trafficTypeId", "")} options={this.getOptions(options.trafficTypes.map(o => {return {"value": o.id, "label": o.trafficType, "parentId": o.trafficTypeId}}))} floatingLabelText="Traffic Type" readOnly={readOnly}  errors={this.getAttr(errors, "operationalInformation.trafficTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["operationalInformation.instrumentApprTypeId"] = input}  name="operationalInformation.instrumentApprTypeId" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.instrumentApprTypeId", "")} options={this.getOptions(options.instrumentApprTypes.map(o => {return {"value": o.id, "label": o.instrumentApprType, "parentId": o.instrumentApprTypeId}}))} floatingLabelText="Instrument Appr Type" readOnly={readOnly}  errors={this.getAttr(errors, "operationalInformation.instrumentApprTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                                <GNestedForms ref={(i) => this._inputs["flightCrews"] = i} description="List of Flight Crews" prefix="flightCrews" readOnly={readOnly} objs={obj.flightCrews} renderNested={(nobj, k, refFunc) => <FlightCrewFormInline i={k} obj={Object.assign({neoFlightOperationId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["neoFlightOperationId"]} errors={errors} prefix="flightCrews"/>}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["classification.ocurrenceClassId"] = input}  name="classification.ocurrenceClassId" fullWidth defaultValue={this.getAttr(obj, "classification.ocurrenceClassId", "")} options={this.getOptions(options.ocurrenceClasss.map(o => {return {"value": o.id, "label": o.ocurrenceClass, "parentId": o.ocurrenceClassId}}))} floatingLabelText="Ocurrence Class" readOnly={readOnly}  errors={this.getAttr(errors, "classification.ocurrenceClassId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["classification.detectionPhaseId"] = input}  name="classification.detectionPhaseId" fullWidth defaultValue={this.getAttr(obj, "classification.detectionPhaseId", "")} options={this.getOptions(options.detectionPhases.map(o => {return {"value": o.id, "label": o.detectionPhase, "parentId": o.detectionPhaseId}}))} floatingLabelText="Detection Phase" readOnly={readOnly}  errors={this.getAttr(errors, "classification.detectionPhaseId")} />
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                                <GNestedForms ref={(i) => this._inputs["neoFlightOperationOcurrenceCategorys"] = i} description="List of Neo Flight Operation Ocurrence Categorys" prefix="neoFlightOperationOcurrenceCategorys" readOnly={readOnly} objs={obj.neoFlightOperationOcurrenceCategorys} renderNested={(nobj, k, refFunc) => <NeoFlightOperationOcurrenceCategoryFormInline i={k} obj={Object.assign({neoFlightOperationId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["neoFlightOperationId"]} errors={errors} prefix="neoFlightOperationOcurrenceCategorys"/>}/>
                </Col>
                <Col xs={6} sm={2}>
                                <GNestedForms ref={(i) => this._inputs["neoFlightOperationEventTypePhases"] = i} description="List of Neo Flight Operation Event Type Phases" prefix="neoFlightOperationEventTypePhases" readOnly={readOnly} objs={obj.neoFlightOperationEventTypePhases} renderNested={(nobj, k, refFunc) => <NeoFlightOperationEventTypePhaseFormInline i={k} obj={Object.assign({neoFlightOperationId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["neoFlightOperationId"]} errors={errors} prefix="neoFlightOperationEventTypePhases"/>}/>
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
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["assessment.analysisFollowUp"] = input} multiLine rows={3} name="assessment.analysisFollowUp" fullWidth defaultValue={this.getAttr(obj, "assessment.analysisFollowUp", "")} floatingLabelText="Analysis Follow Up" readOnly={readOnly}  errors={this.getAttr(errors, "assessment.analysisFollowUp")}/>
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["assessment.correctiveActions"] = input} multiLine rows={3} name="assessment.correctiveActions" fullWidth defaultValue={this.getAttr(obj, "assessment.correctiveActions", "")} floatingLabelText="Corrective Actions" readOnly={readOnly}  errors={this.getAttr(errors, "assessment.correctiveActions")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["assessment.conclusions"] = input} multiLine rows={3} name="assessment.conclusions" fullWidth defaultValue={this.getAttr(obj, "assessment.conclusions", "")} floatingLabelText="Conclusions" readOnly={readOnly}  errors={this.getAttr(errors, "assessment.conclusions")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["reportManagement.reportStatusId"] = input}  name="reportManagement.reportStatusId" fullWidth defaultValue={this.getAttr(obj, "reportManagement.reportStatusId", "")} options={this.getOptions(options.neoReportStatuss.map(o => {return {"value": o.id, "label": o.reportStatus, "parentId": o.neoReportStatusId}}))} floatingLabelText="Report Status" readOnly={readOnly}  errors={this.getAttr(errors, "reportManagement.reportStatusId")} />
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


      