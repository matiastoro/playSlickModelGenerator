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
export default class HelicopterLandingAreaAreaTypeFilter extends GFilter{

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
                    <TextField ref={(input) => this._inputs["helicopterLandingArea"] = input}  name="helicopterLandingArea" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingArea", "")} floatingLabelText="Helicopter Landing Area" readOnly={readOnly}  errors={this.getAttr(errors, "helicopterLandingArea")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["helicopterLandingAreaTypeId"] = input}  name="helicopterLandingAreaTypeId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaTypeId", "")} options={this.getOptions(options.helicopterLandingAreaTypes.map(o => {return {"value": o.id, "label": o.helicopterLandingAreaType, "parentId": o.helicopterLandingAreaTypeId}}))} floatingLabelText="Helicopter Landing Area Type Id" readOnly={readOnly}  errors={this.getAttr(errors, "helicopterLandingAreaTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["helicopterLandingAreaAreaConfigurationId"] = input}  name="helicopterLandingAreaAreaConfigurationId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaAreaConfigurationId", "")} options={this.getOptions(options.helicopterLandingAreaAreaConfigurations.map(o => {return {"value": o.id, "label": o.helicopterLandingAreaAreaConfiguration, "parentId": o.helicopterLandingAreaAreaConfigurationId}}))} floatingLabelText="Helicopter Landing Area Area Configuration Id" readOnly={readOnly}  errors={this.getAttr(errors, "helicopterLandingAreaAreaConfigurationId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["helicopterLandingAreaSurfaceTypeId"] = input}  name="helicopterLandingAreaSurfaceTypeId" fullWidth defaultValue={this.getAttr(obj, "helicopterLandingAreaSurfaceTypeId", "")} options={this.getOptions(options.helicopterLandingAreaSurfaceTypes.map(o => {return {"value": o.id, "label": o.helicopterLandingAreaSurfaceType, "parentId": o.helicopterLandingAreaSurfaceTypeId}}))} floatingLabelText="Helicopter Landing Area Surface Type Id" readOnly={readOnly}  errors={this.getAttr(errors, "helicopterLandingAreaSurfaceTypeId")} />
                </Col>
            </Row>
        </div>
    }

}


      