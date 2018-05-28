import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GFilter from '../gforms/GFilter';
import {GFormInline} from '../gforms/GForm';

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
export default class NeoAerodromeOcurrenceCategoryFilter extends GFilter{

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
                    <SelectField ref={(input) => this._inputs["neoAerodromeId"] = input}  name="neoAerodromeId" fullWidth defaultValue={this.getAttr(obj, "neoAerodromeId", "")} options={this.getOptions(options.neoAerodromes.map(o => {return {"value": o.id, "label": o.neoAerodrome, "parentId": o.neoAerodromeId}}))} floatingLabelText="Neo Aerodrome Id" readOnly={readOnly}  errors={this.getAttr(errors, "neoAerodromeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["ocurrenceCategoryId"] = input}  name="ocurrenceCategoryId" fullWidth defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} options={this.getOptions(options.ocurrenceCategorys.map(o => {return {"value": o.id, "label": o.ocurrenceCategory, "parentId": o.ocurrenceCategoryId}}))} floatingLabelText="Ocurrence Category Id" readOnly={readOnly}  errors={this.getAttr(errors, "ocurrenceCategoryId")} />
                </Col>
            </Row>
        </div>
    }

}


export class NeoAerodromeOcurrenceCategoryFormInline extends GFormInline{
    apiOptionsUrl = "/neoAerodromeOcurrenceCategory/options"
    renderForm(){
        const obj = this.props.obj;
        const readOnly = this.props.readOnly
        const errors = this.props.errors
        const hide = this.props.hide || []
        const i = this.props.i
        return <div>
<TextField ref={(input) => this._inputs["id"] = input}  name="id" fullWidth defaultValue={this.getAttr(obj, "id", "")} floatingLabelText="Id" readOnly={readOnly}  errors={this.getAttr(errors, "id")}/>
<SelectField ref={(input) => this._inputs["neoAerodromeId"] = input}  name="neoAerodromeId" fullWidth defaultValue={this.getAttr(obj, "neoAerodromeId", "")} options={this.getOptions(options.neoAerodromes.map(o => {return {"value": o.id, "label": o.neoAerodrome, "parentId": o.neoAerodromeId}}))} floatingLabelText="Neo Aerodrome Id" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].neoAerodromeId", "")} />
<SelectField ref={(input) => this._inputs["ocurrenceCategoryId"] = input}  name="ocurrenceCategoryId" fullWidth defaultValue={this.getAttr(obj, "ocurrenceCategoryId", "")} options={this.getOptions(options.ocurrenceCategorys.map(o => {return {"value": o.id, "label": o.ocurrenceCategory, "parentId": o.ocurrenceCategoryId}}))} floatingLabelText="Ocurrence Category Id" readOnly={readOnly}  errors={this.getAttr(errors, this.props.prefix+"["+i+"].ocurrenceCategoryId", "")} />
        </div>
    }

}
    
      