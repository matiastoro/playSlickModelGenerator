import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';

import {GNestedForms} from '../gforms/GForm';
import {EccairsOcurrenceCategoryFormInline} from '../eccairsOcurrenceCategory/EccairsOcurrenceCategoryForm'
      
//inputs de nested



export default class EccairsForm extends GForm{
    showUrl =  '/eccairs/'
    listUrl =  '/eccairs/'
    apiGetUrl =  '/eccairs/show/'
    apiCreateUrl = '/eccairs/save'
    apiUpdateUrl = '/eccairs/update/'
    apiDeleteUrl = '/eccairs/delete/'
    apiOptionsUrl = "/eccairs/options"

    objStr = 'Eccairs'
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
            <TextField ref={(input) => this._inputs["number"] = input}  name="number" fullWidth defaultValue={this.getAttr(obj, "number", "")} floatingLabelText="Number" readOnly={readOnly} required={true} errors={this.getAttr(errors, "number")}/>
          </div>
          <div>
            <DateTime ref={(input) => this._inputs["date"] = input}  name="date" fullWidth defaultValue={this.getAttr(obj, "date")} floatingLabelText="Date" readOnly={readOnly} required={true} errors={this.getAttr(errors, "date")} />
          </div>
          <div>
            {hide.includes("stateAreaId")?<HiddenField ref={(input) => this._inputs["stateAreaId"] = input} name="stateAreaId" defaultValue={this.getAttr(obj, "stateAreaId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["stateAreaId"] = input}  name="stateAreaId" fullWidth defaultValue={this.getAttr(obj, "stateAreaId", "")} options={this.getOptions(options.stateAreas.map(o => {return {"value": o.id, "label": o.stateArea, "parentId": o.stateAreaId}}))} floatingLabelText="State Area" readOnly={readOnly} required={true} errors={this.getAttr(errors, "stateAreaId")} />}
          </div>
          <div>
            {hide.includes("ocurrenceClassId")?<HiddenField ref={(input) => this._inputs["ocurrenceClassId"] = input} name="ocurrenceClassId" defaultValue={this.getAttr(obj, "ocurrenceClassId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["ocurrenceClassId"] = input}  name="ocurrenceClassId" fullWidth defaultValue={this.getAttr(obj, "ocurrenceClassId", "")} options={this.getOptions(options.ocurrenceClasss.map(o => {return {"value": o.id, "label": o.ocurrenceClass, "parentId": o.ocurrenceClassId}}))} floatingLabelText="Ocurrence Class" readOnly={readOnly} required={true} errors={this.getAttr(errors, "ocurrenceClassId")} />}
          </div>
            <GNestedForms ref={(i) => this._inputs["eccairsOcurrenceCategorys"] = i} description="List of Eccairs Ocurrence Categorys" prefix="eccairsOcurrenceCategorys" readOnly={readOnly} objs={obj.eccairsOcurrenceCategorys} renderNested={(nobj, k, refFunc) => <EccairsOcurrenceCategoryFormInline i={k} obj={Object.assign({eccairsId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["eccairsId"]} errors={errors} prefix="eccairsOcurrenceCategorys"/>}/>
          <div>
            {hide.includes("injuryLevelId")?<HiddenField ref={(input) => this._inputs["injuryLevelId"] = input} name="injuryLevelId" defaultValue={this.getAttr(obj, "injuryLevelId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["injuryLevelId"] = input}  name="injuryLevelId" fullWidth defaultValue={this.getAttr(obj, "injuryLevelId", "")} options={this.getOptions(options.injuryLevels.map(o => {return {"value": o.id, "label": o.injuryLevel, "parentId": o.injuryLevelId}}))} floatingLabelText="Injury Level" readOnly={readOnly} required={false} errors={this.getAttr(errors, "injuryLevelId")} />}
          </div>
          <div>
            {hide.includes("massGroupId")?<HiddenField ref={(input) => this._inputs["massGroupId"] = input} name="massGroupId" defaultValue={this.getAttr(obj, "massGroupId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["massGroupId"] = input}  name="massGroupId" fullWidth defaultValue={this.getAttr(obj, "massGroupId", "")} options={this.getOptions(options.aircraftMassGroups.map(o => {return {"value": o.id, "label": o.massGroup, "parentId": o.aircraftMassGroupId}}))} floatingLabelText="Mass Group" readOnly={readOnly} required={false} errors={this.getAttr(errors, "massGroupId")} />}
          </div>
          <div>
            {hide.includes("categoryId")?<HiddenField ref={(input) => this._inputs["categoryId"] = input} name="categoryId" defaultValue={this.getAttr(obj, "categoryId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["categoryId"] = input}  name="categoryId" fullWidth defaultValue={this.getAttr(obj, "categoryId", "")} options={this.getOptions(options.aircraftCategorys.map(o => {return {"value": o.id, "label": o.category, "parentId": o.aircraftCategoryId}}))} floatingLabelText="Category" readOnly={readOnly} required={false} errors={this.getAttr(errors, "categoryId")} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["registry"] = input}  name="registry" fullWidth defaultValue={this.getAttr(obj, "registry", "")} floatingLabelText="Registry" readOnly={readOnly} required={false} errors={this.getAttr(errors, "registry")}/>
          </div>
          <div>
            {hide.includes("operationTypeId")?<HiddenField ref={(input) => this._inputs["operationTypeId"] = input} name="operationTypeId" defaultValue={this.getAttr(obj, "operationTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["operationTypeId"] = input}  name="operationTypeId" fullWidth defaultValue={this.getAttr(obj, "operationTypeId", "")} options={this.getOptions(options.operationTypes.map(o => {return {"value": o.id, "label": o.operationType, "parentId": o.operationTypeId}}))} floatingLabelText="Operation Type" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operationTypeId")} />}
          </div>
          <div>
            {hide.includes("operatorTypeId")?<HiddenField ref={(input) => this._inputs["operatorTypeId"] = input} name="operatorTypeId" defaultValue={this.getAttr(obj, "operatorTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["operatorTypeId"] = input}  name="operatorTypeId" fullWidth defaultValue={this.getAttr(obj, "operatorTypeId", "")} options={this.getOptions(options.operatorTypes.map(o => {return {"value": o.id, "label": o.operatorType, "parentId": o.operatorTypeId}}))} floatingLabelText="Operator Type" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operatorTypeId")} />}
          </div>
          <div>
            {hide.includes("operatorId")?<HiddenField ref={(input) => this._inputs["operatorId"] = input} name="operatorId" defaultValue={this.getAttr(obj, "operatorId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["operatorId"] = input}  name="operatorId" fullWidth defaultValue={this.getAttr(obj, "operatorId", "")} options={this.getOptions(options.operators.map(o => {return {"value": o.id, "label": o.operator, "parentId": o.operatorId}}))} floatingLabelText="Operator" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operatorId")} />}
          </div>
          <div>
            {hide.includes("weatherConditionId")?<HiddenField ref={(input) => this._inputs["weatherConditionId"] = input} name="weatherConditionId" defaultValue={this.getAttr(obj, "weatherConditionId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["weatherConditionId"] = input}  name="weatherConditionId" fullWidth defaultValue={this.getAttr(obj, "weatherConditionId", "")} options={this.getOptions(options.weatherConditions.map(o => {return {"value": o.id, "label": o.weatherCondition, "parentId": o.weatherConditionId}}))} floatingLabelText="Weather Condition" readOnly={readOnly} required={false} errors={this.getAttr(errors, "weatherConditionId")} />}
          </div>
        </div>
    }

}


      