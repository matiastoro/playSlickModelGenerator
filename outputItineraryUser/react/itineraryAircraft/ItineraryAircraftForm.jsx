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
<Route path="/itineraryAircraft/" component={ItineraryAircraftList} />
<Route path="/itineraryAircraft/new" component={ItineraryAircraftForm} />
<Route path="/itineraryAircraft/:id" component={ItineraryAircraftForm} />
<Route path="/itineraryAircraft/:page/:pageLength" component={ItineraryAircraftList} />
*/
export default class ItineraryAircraftForm extends GForm{
    showUrl =  '/itineraryAircraft/'
    listUrl =  '/itineraryAircraft/'
    apiGetUrl =  '/itineraryAircraft/show/'
    apiCreateUrl = '/itineraryAircraft/save'
    apiUpdateUrl = '/itineraryAircraft/update/'
    apiDeleteUrl = '/itineraryAircraft/delete/'
    apiOptionsUrl = "/itineraryAircraft/options"

    objStr = 'ItineraryAircraft'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("manufacturerId")?<HiddenField ref={(input) => this._inputs["manufacturerId"] = input} name="manufacturerId" defaultValue={obj.manufacturerId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["manufacturerId"] = input}  name="manufacturerId" defaultValue={obj.manufacturerId || ""} options={this.state.options.itinerary_aircraft_manufacturers.map(o => {return {"value": o.id, "label": o.itineraryAircraftManufacturer}})} floatingLabelText="ManufacturerId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].manufacturerId"]} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["model"] = input}  name="model" defaultValue={obj.model || ""} floatingLabelText="Model" readOnly={readOnly} required={true} errors={errors && errors.model}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["tpe"] = input}  name="tpe" defaultValue={obj.tpe || ""} floatingLabelText="Tpe" readOnly={readOnly} required={true} errors={errors && errors.tpe}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["iataCode"] = input}  name="iataCode" defaultValue={obj.iataCode || ""} floatingLabelText="IataCode" readOnly={readOnly} required={false} errors={errors && errors.iataCode}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["nPassengers"] = input}  name="nPassengers" defaultValue={obj.nPassengers || ""} floatingLabelText="NPassengers" readOnly={readOnly} required={false} errors={errors && errors.nPassengers}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["length"] = input}  name="length" defaultValue={obj.length || ""} floatingLabelText="Length" readOnly={readOnly} required={true} errors={errors && errors.length}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["width"] = input}  name="width" defaultValue={obj.width || ""} floatingLabelText="Width" readOnly={readOnly} required={true} errors={errors && errors.width}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["wingspan"] = input}  name="wingspan" defaultValue={obj.wingspan || ""} floatingLabelText="Wingspan" readOnly={readOnly} required={true} errors={errors && errors.wingspan}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["OMGWS"] = input}  name="OMGWS" defaultValue={obj.OMGWS || ""} floatingLabelText="OMGWS" readOnly={readOnly} required={true} errors={errors && errors.OMGWS}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["series"] = input}  name="series" defaultValue={obj.series || ""} floatingLabelText="Series" readOnly={readOnly} required={false} errors={errors && errors.series}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["ACN"] = input}  name="ACN" defaultValue={obj.ACN || ""} floatingLabelText="ACN" readOnly={readOnly} required={false} errors={errors && errors.ACN}/>
          </div>
        </div>
    }

}


      