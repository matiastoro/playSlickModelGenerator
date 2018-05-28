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
<Route path="/action/" component={ActionList} />
<Route path="/action/new" component={ActionForm} />
<Route path="/action/:id" component={ActionForm} />
<Route path="/action/:page/:pageLength" component={ActionList} />
*/
export default class ActionForm extends GForm{
    showUrl =  '/action/'
    listUrl =  '/action/'
    apiGetUrl =  '/action/show/'
    apiCreateUrl = '/action/save'
    apiUpdateUrl = '/action/update/'
    apiDeleteUrl = '/action/delete/'
    apiOptionsUrl = "/action/options"

    objStr = 'Action'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            {hide.includes("userId")?<HiddenField ref={(input) => this._inputs["userId"] = input} name="userId" defaultValue={obj.userId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["userId"] = input}  name="userId" defaultValue={obj.userId || ""} options={this.state.options.users.map(o => {return {"value": o.id, "label": o.user}})} floatingLabelText="UserId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].userId"]} />}
          </div>
          <div>
            {hide.includes("turnId")?<HiddenField ref={(input) => this._inputs["turnId"] = input} name="turnId" defaultValue={obj.turnId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["turnId"] = input}  name="turnId" defaultValue={obj.turnId || ""} options={this.state.options.turns.map(o => {return {"value": o.id, "label": o.turn}})} floatingLabelText="TurnId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].turnId"]} />}
          </div>
          <div>
            {hide.includes("characterId")?<HiddenField ref={(input) => this._inputs["characterId"] = input} name="characterId" defaultValue={obj.characterId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["characterId"] = input}  name="characterId" defaultValue={obj.characterId || ""} options={this.state.options.characters.map(o => {return {"value": o.id, "label": o.character}})} floatingLabelText="CharacterId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].characterId"]} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["fromQ"] = input}  name="fromQ" defaultValue={obj.fromQ || ""} floatingLabelText="FromQ" readOnly={readOnly} required={true} errors={errors && errors.fromQ}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["fromR"] = input}  name="fromR" defaultValue={obj.fromR || ""} floatingLabelText="FromR" readOnly={readOnly} required={true} errors={errors && errors.fromR}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["fromS"] = input}  name="fromS" defaultValue={obj.fromS || ""} floatingLabelText="FromS" readOnly={readOnly} required={true} errors={errors && errors.fromS}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["toQ"] = input}  name="toQ" defaultValue={obj.toQ || ""} floatingLabelText="ToQ" readOnly={readOnly} required={true} errors={errors && errors.toQ}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["toR"] = input}  name="toR" defaultValue={obj.toR || ""} floatingLabelText="ToR" readOnly={readOnly} required={true} errors={errors && errors.toR}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["toS"] = input}  name="toS" defaultValue={obj.toS || ""} floatingLabelText="ToS" readOnly={readOnly} required={true} errors={errors && errors.toS}/>
          </div>


        </div>
    }

}


      