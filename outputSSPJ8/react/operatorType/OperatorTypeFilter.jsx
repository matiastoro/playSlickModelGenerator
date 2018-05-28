import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GFilter from '../gforms/GFilter';


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
export default class OperatorTypeFilter extends GFilter{

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
                    <TextField ref={(input) => this._inputs["operatorType"] = input}  name="operatorType" fullWidth defaultValue={this.getAttr(obj, "operatorType", "")} floatingLabelText="Operator Type" readOnly={readOnly}  errors={this.getAttr(errors, "operatorType")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["operatorTypeId"] = input}  name="operatorTypeId" fullWidth defaultValue={this.getAttr(obj, "operatorTypeId", "")} options={this.getOptions(options.operatorTypes.map(o => {return {"value": o.id, "label": o.operatorType, "parentId": o.operatorTypeId}}))} floatingLabelText="Operator Type Id" readOnly={readOnly}  errors={this.getAttr(errors, "operatorTypeId")} />
                </Col>
            </Row>
        </div>
    }

}


      