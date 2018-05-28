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
export default class AircraftManufacturerModelFilter extends GFilter{

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
                    <TextField ref={(input) => this._inputs["manufacturerModel"] = input}  name="manufacturerModel" fullWidth defaultValue={this.getAttr(obj, "manufacturerModel", "")} floatingLabelText="Manufacturer Model" readOnly={readOnly}  errors={this.getAttr(errors, "manufacturerModel")}/>
                </Col>
                <Col xs={6} sm={2}>
                    <SelectField ref={(input) => this._inputs["aircraftManufacturerModelId"] = input}  name="aircraftManufacturerModelId" fullWidth defaultValue={this.getAttr(obj, "aircraftManufacturerModelId", "")} options={this.getOptions(options.aircraftManufacturerModels.map(o => {return {"value": o.id, "label": o.manufacturerModel, "parentId": o.aircraftManufacturerModelId}}))} floatingLabelText="Aircraft Manufacturer Model Id" readOnly={readOnly}  errors={this.getAttr(errors, "aircraftManufacturerModelId")} />
                </Col>
            </Row>
        </div>
    }

}


      