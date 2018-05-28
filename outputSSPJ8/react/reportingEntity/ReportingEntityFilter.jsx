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
export default class ReportingEntityFilter extends GFilter{

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
                    <TextField ref={(input) => this._inputs["reportingEntity"] = input}  name="reportingEntity" fullWidth defaultValue={this.getAttr(obj, "reportingEntity", "")} floatingLabelText="Reporting Entity" readOnly={readOnly}  errors={this.getAttr(errors, "reportingEntity")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["reportingEntityId"] = input}  name="reportingEntityId" fullWidth defaultValue={this.getAttr(obj, "reportingEntityId", "")} options={this.getOptions(options.reportingEntitys.map(o => {return {"value": o.id, "label": o.reportingEntity, "parentId": o.reportingEntityId}}))} floatingLabelText="Reporting Entity Id" readOnly={readOnly}  errors={this.getAttr(errors, "reportingEntityId")} />
                </Col>
            </Row>
        </div>
    }

}


      