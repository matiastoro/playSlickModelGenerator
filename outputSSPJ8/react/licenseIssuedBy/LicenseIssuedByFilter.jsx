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




export default class LicenseIssuedByFilter extends GFilter{

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
                    <TextField ref={(input) => this._inputs["licenseIssuedBy"] = input}  name="licenseIssuedBy" fullWidth defaultValue={this.getAttr(obj, "licenseIssuedBy", "")} floatingLabelText="License Issued By" readOnly={readOnly}  errors={this.getAttr(errors, "licenseIssuedBy")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["licenseIssuedById"] = input}  name="licenseIssuedById" fullWidth defaultValue={this.getAttr(obj, "licenseIssuedById", "")} options={this.getOptions(options.licenseIssuedBys.map(o => {return {"value": o.id, "label": o.licenseIssuedBy, "parentId": o.licenseIssuedById}}))} floatingLabelText="License Issued By" readOnly={readOnly}  errors={this.getAttr(errors, "licenseIssuedById")} />
                </Col>
            </Row>
        </div>
    }

}


      