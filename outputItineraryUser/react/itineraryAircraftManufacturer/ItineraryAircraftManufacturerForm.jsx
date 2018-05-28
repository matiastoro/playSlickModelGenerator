import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';

import {GNestedForms} from '../gforms/GForm';
import {ItineraryAircraftFormInline} from '../itineraryAircraft/ItineraryAircraftForm'
      
//inputs de nested


/*
<Route path="/itineraryAircraftManufacturer/" component={ItineraryAircraftManufacturerList} />
<Route path="/itineraryAircraftManufacturer/new" component={ItineraryAircraftManufacturerForm} />
<Route path="/itineraryAircraftManufacturer/:id" component={ItineraryAircraftManufacturerForm} />
<Route path="/itineraryAircraftManufacturer/:page/:pageLength" component={ItineraryAircraftManufacturerList} />
*/
export default class ItineraryAircraftManufacturerForm extends GForm{
    showUrl =  '/itineraryAircraftManufacturer/'
    listUrl =  '/itineraryAircraftManufacturer/'
    apiGetUrl =  '/itineraryAircraftManufacturer/show/'
    apiCreateUrl = '/itineraryAircraftManufacturer/save'
    apiUpdateUrl = '/itineraryAircraftManufacturer/update/'
    apiDeleteUrl = '/itineraryAircraftManufacturer/delete/'
    

    objStr = 'ItineraryAircraftManufacturer'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["name"] = input}  name="name" defaultValue={obj.name || ""} floatingLabelText="Name" readOnly={readOnly} required={true} errors={errors && errors.name}/>
          </div>
            <GNestedForms ref={(i) => this._inputs["itineraryAircrafts"] = i} description="ItineraryAircrafts" prefix="itineraryAircrafts" readOnly={readOnly} objs={obj.itineraryAircrafts} renderNested={(nobj, k, refFunc) => <ItineraryAircraftFormInline i={k} obj={Object.assign({itineraryAircraftManufacturerId: obj.id},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["itineraryAircraftManufacturerId"]} errors={errors} prefix="itineraryAircrafts"/>}/>
        </div>
    }

}


      