import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';


//inputs de nested


/*
import OperatorTypeForm from './components/operatorType/OperatorTypeForm'
import OperatorTypeList from './components/operatorType/OperatorTypeList'

<Route path="/operatorType/" component={OperatorTypeList} />
<Route path="/operatorType/new" component={OperatorTypeForm} />
<Route path="/operatorType/:id" component={OperatorTypeForm} />
<Route path="/operatorType/:page/:pageLength" component={OperatorTypeList} />
*/
export default class OperatorTypeForm extends GForm{
    showUrl =  '/operatorType/'
    listUrl =  '/operatorType/'
    apiGetUrl =  '/operatorType/show/'
    apiCreateUrl = '/operatorType/save'
    apiUpdateUrl = '/operatorType/update/'
    apiDeleteUrl = '/operatorType/delete/'
    apiOptionsUrl = "/operatorType/options"

    objStr = 'Operator Type'
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
            <TextField ref={(input) => this._inputs["operatorType"] = input}  name="operatorType" fullWidth defaultValue={this.getAttr(obj, "operatorType", "")} floatingLabelText="Operator Type" readOnly={readOnly} required={true} errors={this.getAttr(errors, "operatorType")}/>
          </div>
          <div>
            {hide.includes("operatorTypeId")?<HiddenField ref={(input) => this._inputs["operatorTypeId"] = input} name="operatorTypeId" defaultValue={this.getAttr(obj, "operatorTypeId", "")} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["operatorTypeId"] = input}  name="operatorTypeId" fullWidth defaultValue={this.getAttr(obj, "operatorTypeId", "")} options={this.getOptions(options.operatorTypes.map(o => {return {"value": o.id, "label": o.operatorType, "parentId": o.operatorTypeId}}))} floatingLabelText="Operator Type Id" readOnly={readOnly} required={false} errors={this.getAttr(errors, "operatorTypeId")} />}
          </div>
        </div>
    }

}


      