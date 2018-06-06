import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GFilter from '../gforms/GFilter';

import {GNestedForms} from '../gforms/GForm';
import {EccairsOcurrenceCategoryFormInline} from '../eccairsOcurrenceCategory/EccairsOcurrenceCategoryForm'
      
import { Row, Col } from 'react-flexbox-grid';

//inputs de nested




export default class EccairsFilter extends GFilter{

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
                    <TextField ref={(input) => this._inputs["number"] = input}  name="number" fullWidth defaultValue={this.getAttr(obj, "number", "")} floatingLabelText="Number" readOnly={readOnly}  errors={this.getAttr(errors, "number")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <DateTime ref={(input) => this._inputs["dateFrom"] = input}  name="dateFrom" fullWidth defaultValue={this.getAttr(obj, "dateFrom")} floatingLabelText="Date desde" readOnly={readOnly}  errors={this.getAttr(errors, "dateFrom")} />
<DateTime ref={(input) => this._inputs["dateTo"] = input}  name="dateTo" fullWidth defaultValue={this.getAttr(obj, "dateTo")} floatingLabelText="Date hasta" readOnly={readOnly}  errors={this.getAttr(errors, "dateTo")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["stateAreaId"] = input}  name="stateAreaId" fullWidth defaultValue={this.getAttr(obj, "stateAreaId", "")} options={this.getOptions(options.stateAreas.map(o => {return {"value": o.id, "label": o.stateArea, "parentId": o.stateAreaId}}))} floatingLabelText="State Area" readOnly={readOnly}  errors={this.getAttr(errors, "stateAreaId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["ocurrenceClassId"] = input}  name="ocurrenceClassId" fullWidth defaultValue={this.getAttr(obj, "ocurrenceClassId", "")} options={this.getOptions(options.ocurrenceClasss.map(o => {return {"value": o.id, "label": o.ocurrenceClass, "parentId": o.ocurrenceClassId}}))} floatingLabelText="Ocurrence Class" readOnly={readOnly}  errors={this.getAttr(errors, "ocurrenceClassId")} />
                </Col>
                <Col xs={6} sm={2}>
                                <GNestedForms ref={(i) => this._inputs["eccairsOcurrenceCategorys"] = i} description="List of Eccairs Ocurrence Categorys" prefix="eccairsOcurrenceCategorys" readOnly={readOnly} objs={obj.eccairsOcurrenceCategorys} renderNested={(nobj, k, refFunc) => <EccairsOcurrenceCategoryFormInline i={k} obj={Object.assign({eccairsId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["eccairsId"]} errors={errors} prefix="eccairsOcurrenceCategorys"/>}/>
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["injuryLevelId"] = input}  name="injuryLevelId" fullWidth defaultValue={this.getAttr(obj, "injuryLevelId", "")} options={this.getOptions(options.injuryLevels.map(o => {return {"value": o.id, "label": o.injuryLevel, "parentId": o.injuryLevelId}}))} floatingLabelText="Injury Level" readOnly={readOnly}  errors={this.getAttr(errors, "injuryLevelId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["massGroupId"] = input}  name="massGroupId" fullWidth defaultValue={this.getAttr(obj, "massGroupId", "")} options={this.getOptions(options.aircraftMassGroups.map(o => {return {"value": o.id, "label": o.massGroup, "parentId": o.aircraftMassGroupId}}))} floatingLabelText="Mass Group" readOnly={readOnly}  errors={this.getAttr(errors, "massGroupId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["categoryId"] = input}  name="categoryId" fullWidth defaultValue={this.getAttr(obj, "categoryId", "")} options={this.getOptions(options.aircraftCategorys.map(o => {return {"value": o.id, "label": o.category, "parentId": o.aircraftCategoryId}}))} floatingLabelText="Category" readOnly={readOnly}  errors={this.getAttr(errors, "categoryId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["registry"] = input}  name="registry" fullWidth defaultValue={this.getAttr(obj, "registry", "")} floatingLabelText="Registry" readOnly={readOnly}  errors={this.getAttr(errors, "registry")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["operationTypeId"] = input}  name="operationTypeId" fullWidth defaultValue={this.getAttr(obj, "operationTypeId", "")} options={this.getOptions(options.operationTypes.map(o => {return {"value": o.id, "label": o.operationType, "parentId": o.operationTypeId}}))} floatingLabelText="Operation Type" readOnly={readOnly}  errors={this.getAttr(errors, "operationTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["operatorTypeId"] = input}  name="operatorTypeId" fullWidth defaultValue={this.getAttr(obj, "operatorTypeId", "")} options={this.getOptions(options.operatorTypes.map(o => {return {"value": o.id, "label": o.operatorType, "parentId": o.operatorTypeId}}))} floatingLabelText="Operator Type" readOnly={readOnly}  errors={this.getAttr(errors, "operatorTypeId")} />
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["operatorId"] = input}  name="operatorId" fullWidth defaultValue={this.getAttr(obj, "operatorId", "")} options={this.getOptions(options.operators.map(o => {return {"value": o.id, "label": o.operator, "parentId": o.operatorId}}))} floatingLabelText="Operator" readOnly={readOnly}  errors={this.getAttr(errors, "operatorId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["weatherConditionId"] = input}  name="weatherConditionId" fullWidth defaultValue={this.getAttr(obj, "weatherConditionId", "")} options={this.getOptions(options.weatherConditions.map(o => {return {"value": o.id, "label": o.weatherCondition, "parentId": o.weatherConditionId}}))} floatingLabelText="Weather Condition" readOnly={readOnly}  errors={this.getAttr(errors, "weatherConditionId")} />
                </Col>
            </Row>
        </div>
    }

}


      