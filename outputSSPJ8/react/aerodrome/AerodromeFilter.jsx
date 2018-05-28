import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GFilter from '../gforms/GFilter';

import {GNestedForms} from '../gforms/GForm';
import {RunwayFormInline} from '../runway/RunwayForm'
import {HelicopterLandingAreaFormInline} from '../helicopterLandingArea/HelicopterLandingAreaForm'
      
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
export default class AerodromeFilter extends GFilter{

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
                    <TextField ref={(input) => this._inputs["icaoCode"] = input}  name="icaoCode" fullWidth defaultValue={this.getAttr(obj, "icaoCode", "")} floatingLabelText="IcaoCode" readOnly={readOnly}  errors={this.getAttr(errors, "icaoCode")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeStatusId"] = input}  name="aerodromeStatusId" fullWidth defaultValue={this.getAttr(obj, "aerodromeStatusId", "")} options={this.getOptions(options.aerodromeStatuss.map(o => {return {"value": o.id, "label": o.aerodromeStatus, "parentId": o.aerodromeStatusId}}))} floatingLabelText="Aerodrome Status Id" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeStatusId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aerodromeTypeId"] = input}  name="aerodromeTypeId" fullWidth defaultValue={this.getAttr(obj, "aerodromeTypeId", "")} options={this.getOptions(options.aerodromeTypes.map(o => {return {"value": o.id, "label": o.aerodromeType, "parentId": o.aerodromeTypeId}}))} floatingLabelText="Aerodrome Type Id" readOnly={readOnly}  errors={this.getAttr(errors, "aerodromeTypeId")} />
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["latitude"] = input}  name="latitude" fullWidth defaultValue={this.getAttr(obj, "latitude", "")} floatingLabelText="Latitude" readOnly={readOnly}  errors={this.getAttr(errors, "latitude")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["longitude"] = input}  name="longitude" fullWidth defaultValue={this.getAttr(obj, "longitude", "")} floatingLabelText="Longitude" readOnly={readOnly}  errors={this.getAttr(errors, "longitude")}/>
                </Col>
            </Row>
            <Row>
                <Col xs={6} sm={2}>
                    <TextField ref={(input) => this._inputs["elevationAboveMsl"] = input}  name="elevationAboveMsl" fullWidth defaultValue={this.getAttr(obj, "elevationAboveMsl", "")} floatingLabelText="Elevation Above Msl" readOnly={readOnly}  errors={this.getAttr(errors, "elevationAboveMsl")}/>
                </Col>
                <Col xs={6} sm={2}>
                                <GNestedForms ref={(i) => this._inputs["runways"] = i} description="List of Runways" prefix="runways" readOnly={readOnly} objs={obj.runways} renderNested={(nobj, k, refFunc) => <RunwayFormInline i={k} obj={Object.assign({aerodromeId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["aerodromeId"]} errors={errors} prefix="runways"/>}/>
                </Col>
                <Col xs={6} sm={2}>
                                <GNestedForms ref={(i) => this._inputs["helicopterLandingAreas"] = i} description="List of Helicopter Landing Areas" prefix="helicopterLandingAreas" readOnly={readOnly} objs={obj.helicopterLandingAreas} renderNested={(nobj, k, refFunc) => <HelicopterLandingAreaFormInline i={k} obj={Object.assign({aerodromeId: obj.id?obj.id:-1},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["aerodromeId"]} errors={errors} prefix="helicopterLandingAreas"/>}/>
                </Col>
            </Row>
        </div>
    }

}


      