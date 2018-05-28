import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';

import {GNestedForms} from '../gforms/GForm';
import {FlightCrewFormInline} from '../flightCrew/FlightCrewForm'
import {NeoFlightOperationOcurrenceCategoryFormInline} from '../neoFlightOperationOcurrenceCategory/NeoFlightOperationOcurrenceCategoryForm'
import {NeoFlightOperationEventTypePhaseFormInline} from '../neoFlightOperationEventTypePhase/NeoFlightOperationEventTypePhaseForm'
      
//inputs de nested


/*
import NeoFlightOperationForm from './components/neoFlightOperation/NeoFlightOperationForm'
import NeoFlightOperationList from './components/neoFlightOperation/NeoFlightOperationList'

<Route path="/neoFlightOperation/" component={NeoFlightOperationList} />
<Route path="/neoFlightOperation/new" component={NeoFlightOperationForm} />
<Route path="/neoFlightOperation/:id" component={NeoFlightOperationForm} />
<Route path="/neoFlightOperation/:page/:pageLength" component={NeoFlightOperationList} />
*/
export default class NeoFlightOperationForm extends GForm{
    showUrl =  '/neoFlightOperation/'
    listUrl =  '/neoFlightOperation/'
    apiGetUrl =  '/neoFlightOperation/show/'
    apiCreateUrl = '/neoFlightOperation/save'
    apiUpdateUrl = '/neoFlightOperation/update/'
    apiDeleteUrl = '/neoFlightOperation/delete/'
    apiOptionsUrl = "/neoFlightOperation/options"

    objStr = 'Neo Flight Operation'
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
            {hide.includes("aircraftIdentification.stateOfRegistryId")?<HiddenField ref={(input) => this._inputs["aircraftIdentification.stateOfRegistryId"] = input} name="aircraftIdentification.stateOfRegistryId" defaultValue={this.getAttr(obj, "aircraftIdentification.stateOfRegistryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aircraftIdentification.stateOfRegistryId"] = input}  name="aircraftIdentification.stateOfRegistryId" fullWidth defaultValue={this.getAttr(obj, "aircraftIdentification.stateOfRegistryId", "")} options={this.getOptions(options.stateAreas.map(o => {return {"value": o.id, "label": o.stateArea, "parentId": o.stateAreaId}}))} floatingLabelText="State Of Registry Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aircraftIdentification.stateOfRegistryId")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["aircraftIdentification.registration"] = input}  name="aircraftIdentification.registration" fullWidth defaultValue={this.getAttr(obj, "aircraftIdentification.registration", "")} floatingLabelText="Registration" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aircraftIdentification.registration")}/>
          </div>
          <div>
            {hide.includes("aircraftIdentification.manufacturerModelId")?<HiddenField ref={(input) => this._inputs["aircraftIdentification.manufacturerModelId"] = input} name="aircraftIdentification.manufacturerModelId" defaultValue={this.getAttr(obj, "aircraftIdentification.manufacturerModelId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aircraftIdentification.manufacturerModelId"] = input}  name="aircraftIdentification.manufacturerModelId" fullWidth defaultValue={this.getAttr(obj, "aircraftIdentification.manufacturerModelId", "")} options={this.getOptions(options.aircraftManufacturerModels.map(o => {return {"value": o.id, "label": o.manufacturerModel, "parentId": o.aircraftManufacturerModelId}}))} floatingLabelText="Manufacturer Model Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aircraftIdentification.manufacturerModelId")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["aircraftIdentification.serialNumber"] = input}  name="aircraftIdentification.serialNumber" fullWidth defaultValue={this.getAttr(obj, "aircraftIdentification.serialNumber", "")} floatingLabelText="Serial Number" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aircraftIdentification.serialNumber")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["aircraftIdentification.yearBuilt"] = input}  name="aircraftIdentification.yearBuilt" fullWidth defaultValue={this.getAttr(obj, "aircraftIdentification.yearBuilt", "")} floatingLabelText="Year Built" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aircraftIdentification.yearBuilt")}/>
          </div>
          <div>
            {hide.includes("aircraftDescription.categoryId")?<HiddenField ref={(input) => this._inputs["aircraftDescription.categoryId"] = input} name="aircraftDescription.categoryId" defaultValue={this.getAttr(obj, "aircraftDescription.categoryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aircraftDescription.categoryId"] = input}  name="aircraftDescription.categoryId" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.categoryId", "")} options={this.getOptions(options.aircraftCategorys.map(o => {return {"value": o.id, "label": o.category, "parentId": o.aircraftCategoryId}}))} floatingLabelText="Category Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aircraftDescription.categoryId")} />}
          </div>
          <div>
            {hide.includes("aircraftDescription.massGroupId")?<HiddenField ref={(input) => this._inputs["aircraftDescription.massGroupId"] = input} name="aircraftDescription.massGroupId" defaultValue={this.getAttr(obj, "aircraftDescription.massGroupId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aircraftDescription.massGroupId"] = input}  name="aircraftDescription.massGroupId" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.massGroupId", "")} options={this.getOptions(options.aircraftMassGroups.map(o => {return {"value": o.id, "label": o.massGroup, "parentId": o.aircraftMassGroupId}}))} floatingLabelText="Mass Group Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aircraftDescription.massGroupId")} />}
          </div>
          <div>
            {hide.includes("aircraftDescription.propulsionTypeId")?<HiddenField ref={(input) => this._inputs["aircraftDescription.propulsionTypeId"] = input} name="aircraftDescription.propulsionTypeId" defaultValue={this.getAttr(obj, "aircraftDescription.propulsionTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aircraftDescription.propulsionTypeId"] = input}  name="aircraftDescription.propulsionTypeId" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.propulsionTypeId", "")} options={this.getOptions(options.aircraftPropulsionTypes.map(o => {return {"value": o.id, "label": o.propulsionType, "parentId": o.aircraftPropulsionTypeId}}))} floatingLabelText="Propulsion Type Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "aircraftDescription.propulsionTypeId")} />}
          </div>
          <div>
            {hide.includes("aircraftDescription.wakeTurbCategoryId")?<HiddenField ref={(input) => this._inputs["aircraftDescription.wakeTurbCategoryId"] = input} name="aircraftDescription.wakeTurbCategoryId" defaultValue={this.getAttr(obj, "aircraftDescription.wakeTurbCategoryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aircraftDescription.wakeTurbCategoryId"] = input}  name="aircraftDescription.wakeTurbCategoryId" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.wakeTurbCategoryId", "")} options={this.getOptions(options.aircraftWakeTurbCategorys.map(o => {return {"value": o.id, "label": o.wakeTurbCategory, "parentId": o.aircraftWakeTurbCategoryId}}))} floatingLabelText="Wake Turb Category Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aircraftDescription.wakeTurbCategoryId")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["aircraftDescription.numberOfEngines"] = input}  name="aircraftDescription.numberOfEngines" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.numberOfEngines", "")} floatingLabelText="Number Of Engines" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aircraftDescription.numberOfEngines")}/>
          </div>
          <div>
            {hide.includes("aircraftDescription.landingGearTypeId")?<HiddenField ref={(input) => this._inputs["aircraftDescription.landingGearTypeId"] = input} name="aircraftDescription.landingGearTypeId" defaultValue={this.getAttr(obj, "aircraftDescription.landingGearTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["aircraftDescription.landingGearTypeId"] = input}  name="aircraftDescription.landingGearTypeId" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.landingGearTypeId", "")} options={this.getOptions(options.aircraftLandingGearTypes.map(o => {return {"value": o.id, "label": o.landingGearType, "parentId": o.aircraftLandingGearTypeId}}))} floatingLabelText="Landing Gear Type Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aircraftDescription.landingGearTypeId")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["aircraftDescription.maximumMass"] = input}  name="aircraftDescription.maximumMass" fullWidth defaultValue={this.getAttr(obj, "aircraftDescription.maximumMass", "")} floatingLabelText="Maximum Mass" readOnly={readOnly} required={false} errors={this.getAttr(errors, "aircraftDescription.maximumMass")}/>
          </div>
          <div>
            {hide.includes("flightDetails.departureId")?<HiddenField ref={(input) => this._inputs["flightDetails.departureId"] = input} name="flightDetails.departureId" defaultValue={this.getAttr(obj, "flightDetails.departureId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["flightDetails.departureId"] = input}  name="flightDetails.departureId" fullWidth defaultValue={this.getAttr(obj, "flightDetails.departureId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.icaoCode, "parentId": o.aerodromeId}}))} floatingLabelText="Departure Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "flightDetails.departureId")} />}
          </div>
          <div>
            {hide.includes("flightDetails.destinationId")?<HiddenField ref={(input) => this._inputs["flightDetails.destinationId"] = input} name="flightDetails.destinationId" defaultValue={this.getAttr(obj, "flightDetails.destinationId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["flightDetails.destinationId"] = input}  name="flightDetails.destinationId" fullWidth defaultValue={this.getAttr(obj, "flightDetails.destinationId", "")} options={this.getOptions(options.aerodromes.map(o => {return {"value": o.id, "label": o.icaoCode, "parentId": o.aerodromeId}}))} floatingLabelText="Destination Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "flightDetails.destinationId")} />}
          </div>
          <div>
            {hide.includes("flightDetails.phaseId")?<HiddenField ref={(input) => this._inputs["flightDetails.phaseId"] = input} name="flightDetails.phaseId" defaultValue={this.getAttr(obj, "flightDetails.phaseId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["flightDetails.phaseId"] = input}  name="flightDetails.phaseId" fullWidth defaultValue={this.getAttr(obj, "flightDetails.phaseId", "")} options={this.getOptions(options.phases.map(o => {return {"value": o.id, "label": o.phase, "parentId": o.phaseId}}))} floatingLabelText="Phase Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "flightDetails.phaseId")} />}
          </div>
          <div>
            {hide.includes("flightDetails.operatorId")?<HiddenField ref={(input) => this._inputs["flightDetails.operatorId"] = input} name="flightDetails.operatorId" defaultValue={this.getAttr(obj, "flightDetails.operatorId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["flightDetails.operatorId"] = input}  name="flightDetails.operatorId" fullWidth defaultValue={this.getAttr(obj, "flightDetails.operatorId", "")} options={this.getOptions(options.operators.map(o => {return {"value": o.id, "label": o.operator, "parentId": o.operatorId}}))} floatingLabelText="Operator Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "flightDetails.operatorId")} />}
          </div>
          <div>
            {hide.includes("flightDetails.operationTypeId")?<HiddenField ref={(input) => this._inputs["flightDetails.operationTypeId"] = input} name="flightDetails.operationTypeId" defaultValue={this.getAttr(obj, "flightDetails.operationTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["flightDetails.operationTypeId"] = input}  name="flightDetails.operationTypeId" fullWidth defaultValue={this.getAttr(obj, "flightDetails.operationTypeId", "")} options={this.getOptions(options.operationTypes.map(o => {return {"value": o.id, "label": o.operationType, "parentId": o.operationTypeId}}))} floatingLabelText="Operation Type Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "flightDetails.operationTypeId")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["flightDetails.callsign"] = input}  name="flightDetails.callsign" fullWidth defaultValue={this.getAttr(obj, "flightDetails.callsign", "")} floatingLabelText="Callsign" readOnly={readOnly} required={true} errors={this.getAttr(errors, "flightDetails.callsign")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["flightDetails.flightNumber"] = input}  name="flightDetails.flightNumber" fullWidth defaultValue={this.getAttr(obj, "flightDetails.flightNumber", "")} floatingLabelText="Flight Number" readOnly={readOnly} required={false} errors={this.getAttr(errors, "flightDetails.flightNumber")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["flightDetails.numberPersons"] = input}  name="flightDetails.numberPersons" fullWidth defaultValue={this.getAttr(obj, "flightDetails.numberPersons", "")} floatingLabelText="Number Persons" readOnly={readOnly} required={false} errors={this.getAttr(errors, "flightDetails.numberPersons")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["operationalInformation.aircraftAltitude"] = input}  name="operationalInformation.aircraftAltitude" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.aircraftAltitude", "")} floatingLabelText="Aircraft Altitude" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operationalInformation.aircraftAltitude")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["operationalInformation.aircraftFlightLevel"] = input}  name="operationalInformation.aircraftFlightLevel" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.aircraftFlightLevel", "")} floatingLabelText="Aircraft Flight Level" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operationalInformation.aircraftFlightLevel")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["operationalInformation.speed"] = input}  name="operationalInformation.speed" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.speed", "")} floatingLabelText="Speed" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operationalInformation.speed")}/>
          </div>
          <div>
            {hide.includes("operationalInformation.typeOfAirspeedId")?<HiddenField ref={(input) => this._inputs["operationalInformation.typeOfAirspeedId"] = input} name="operationalInformation.typeOfAirspeedId" defaultValue={this.getAttr(obj, "operationalInformation.typeOfAirspeedId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["operationalInformation.typeOfAirspeedId"] = input}  name="operationalInformation.typeOfAirspeedId" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.typeOfAirspeedId", "")} options={this.getOptions(options.typeOfAirspeeds.map(o => {return {"value": o.id, "label": o.typeOfAirspeed, "parentId": o.typeOfAirspeedId}}))} floatingLabelText="Type Of Airspeed Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operationalInformation.typeOfAirspeedId")} />}
          </div>
          <div>
            {hide.includes("operationalInformation.flightRuleId")?<HiddenField ref={(input) => this._inputs["operationalInformation.flightRuleId"] = input} name="operationalInformation.flightRuleId" defaultValue={this.getAttr(obj, "operationalInformation.flightRuleId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["operationalInformation.flightRuleId"] = input}  name="operationalInformation.flightRuleId" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.flightRuleId", "")} options={this.getOptions(options.flightRules.map(o => {return {"value": o.id, "label": o.flightRule, "parentId": o.flightRuleId}}))} floatingLabelText="Flight Rule Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operationalInformation.flightRuleId")} />}
          </div>
          <div>
            {hide.includes("operationalInformation.trafficTypeId")?<HiddenField ref={(input) => this._inputs["operationalInformation.trafficTypeId"] = input} name="operationalInformation.trafficTypeId" defaultValue={this.getAttr(obj, "operationalInformation.trafficTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["operationalInformation.trafficTypeId"] = input}  name="operationalInformation.trafficTypeId" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.trafficTypeId", "")} options={this.getOptions(options.trafficTypes.map(o => {return {"value": o.id, "label": o.trafficType, "parentId": o.trafficTypeId}}))} floatingLabelText="Traffic Type Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operationalInformation.trafficTypeId")} />}
          </div>
          <div>
            {hide.includes("operationalInformation.instrumentApprTypeId")?<HiddenField ref={(input) => this._inputs["operationalInformation.instrumentApprTypeId"] = input} name="operationalInformation.instrumentApprTypeId" defaultValue={this.getAttr(obj, "operationalInformation.instrumentApprTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["operationalInformation.instrumentApprTypeId"] = input}  name="operationalInformation.instrumentApprTypeId" fullWidth defaultValue={this.getAttr(obj, "operationalInformation.instrumentApprTypeId", "")} options={this.getOptions(options.instrumentApprTypes.map(o => {return {"value": o.id, "label": o.instrumentApprType, "parentId": o.instrumentApprTypeId}}))} floatingLabelText="Instrument Appr Type Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operationalInformation.instrumentApprTypeId")} />}
          </div>
            <GNestedForms ref={(i) => this._inputs["flightCrews"] = i} description="List of Flight Crews" prefix="flightCrews" readOnly={readOnly} objs={obj.flightCrews} renderNested={(nobj, k, refFunc) => <FlightCrewFormInline i={k} obj={Object.assign({neoFlightOperationId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["neoFlightOperationId"]} errors={errors} prefix="flightCrews"/>}/>
          <div>
            {hide.includes("classification.ocurrenceClassId")?<HiddenField ref={(input) => this._inputs["classification.ocurrenceClassId"] = input} name="classification.ocurrenceClassId" defaultValue={this.getAttr(obj, "classification.ocurrenceClassId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["classification.ocurrenceClassId"] = input}  name="classification.ocurrenceClassId" fullWidth defaultValue={this.getAttr(obj, "classification.ocurrenceClassId", "")} options={this.getOptions(options.ocurrenceClasss.map(o => {return {"value": o.id, "label": o.ocurrenceClass, "parentId": o.ocurrenceClassId}}))} floatingLabelText="Ocurrence Class Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "classification.ocurrenceClassId")} />}
          </div>
          <div>
            {hide.includes("classification.detectionPhaseId")?<HiddenField ref={(input) => this._inputs["classification.detectionPhaseId"] = input} name="classification.detectionPhaseId" defaultValue={this.getAttr(obj, "classification.detectionPhaseId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["classification.detectionPhaseId"] = input}  name="classification.detectionPhaseId" fullWidth defaultValue={this.getAttr(obj, "classification.detectionPhaseId", "")} options={this.getOptions(options.detectionPhases.map(o => {return {"value": o.id, "label": o.detectionPhase, "parentId": o.detectionPhaseId}}))} floatingLabelText="Detection Phase Id" readOnly={readOnly} required={true} errors={this.getAttr(errors, "classification.detectionPhaseId")} />}
          </div>
            <GNestedForms ref={(i) => this._inputs["neoFlightOperationOcurrenceCategorys"] = i} description="List of Neo Flight Operation Ocurrence Categorys" prefix="neoFlightOperationOcurrenceCategorys" readOnly={readOnly} objs={obj.neoFlightOperationOcurrenceCategorys} renderNested={(nobj, k, refFunc) => <NeoFlightOperationOcurrenceCategoryFormInline i={k} obj={Object.assign({neoFlightOperationId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["neoFlightOperationId"]} errors={errors} prefix="neoFlightOperationOcurrenceCategorys"/>}/>
            <GNestedForms ref={(i) => this._inputs["neoFlightOperationEventTypePhases"] = i} description="List of Neo Flight Operation Event Type Phases" prefix="neoFlightOperationEventTypePhases" readOnly={readOnly} objs={obj.neoFlightOperationEventTypePhases} renderNested={(nobj, k, refFunc) => <NeoFlightOperationEventTypePhaseFormInline i={k} obj={Object.assign({neoFlightOperationId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["neoFlightOperationId"]} errors={errors} prefix="neoFlightOperationEventTypePhases"/>}/>
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


      