import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';
import {GFormInline} from '../gforms/GForm';

//inputs de nested


/*
<Route path="/character/" component={CharacterList} />
<Route path="/character/new" component={CharacterForm} />
<Route path="/character/:id" component={CharacterForm} />
<Route path="/character/:page/:pageLength" component={CharacterList} />
*/
export default class CharacterForm extends GForm{
    showUrl =  '/character/'
    listUrl =  '/character/'
    apiGetUrl =  '/character/show/'
    apiCreateUrl = '/character/save'
    apiUpdateUrl = '/character/update/'
    apiDeleteUrl = '/character/delete/'
    apiOptionsUrl = "/character/options"

    objStr = 'Character'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["character"] = input}  name="character" defaultValue={obj.character || ""} floatingLabelText="Character" readOnly={readOnly} required={true} errors={errors && errors.character}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["background"] = input}  name="background" defaultValue={obj.background || ""} floatingLabelText="Background" readOnly={readOnly} required={true} errors={errors && errors.background}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["observations"] = input}  name="observations" defaultValue={obj.observations || ""} floatingLabelText="Observations" readOnly={readOnly} required={true} errors={errors && errors.observations}/>
          </div>
          <div>
            <Checkbox ref={(input) => this._inputs["leader"] = input}  name="leader" defaultValue={obj.leader || ""} floatingLabelText="Leader" readOnly={readOnly} required={true} errors={errors && errors.leader} singlecontrol/>
          </div>
          <div>
            {hide.includes("userId")?<HiddenField ref={(input) => this._inputs["userId"] = input} name="userId" defaultValue={obj.userId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["userId"] = input}  name="userId" defaultValue={obj.userId || ""} options={this.state.options.users.map(o => {return {"value": o.id, "label": o.user}})} floatingLabelText="UserId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].userId"]} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["number"] = input}  name="number" defaultValue={obj.number || ""} floatingLabelText="Number" readOnly={readOnly} required={true} errors={errors && errors.number}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["q"] = input}  name="q" defaultValue={obj.q || ""} floatingLabelText="Q" readOnly={readOnly} required={true} errors={errors && errors.q}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["r"] = input}  name="r" defaultValue={obj.r || ""} floatingLabelText="R" readOnly={readOnly} required={true} errors={errors && errors.r}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["s"] = input}  name="s" defaultValue={obj.s || ""} floatingLabelText="S" readOnly={readOnly} required={true} errors={errors && errors.s}/>
          </div>
        </div>
    }

}


export class CharacterFormInline extends GFormInline{
    apiOptionsUrl = "/character/options"
    renderForm(){
        const obj = this.props.obj;
        const readOnly = this.props.readOnly
        const errors = this.props.errors
        const hide = this.props.hide || []
        const i = this.props.i
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["character"] = input}  name="character" defaultValue={obj.character || ""} floatingLabelText="Character" readOnly={readOnly} required={true} errors={errors && errors.character}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["background"] = input}  name="background" defaultValue={obj.background || ""} floatingLabelText="Background" readOnly={readOnly} required={true} errors={errors && errors.background}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["observations"] = input}  name="observations" defaultValue={obj.observations || ""} floatingLabelText="Observations" readOnly={readOnly} required={true} errors={errors && errors.observations}/>
          </div>
          <div>
            <Checkbox ref={(input) => this._inputs["leader"] = input}  name="leader" defaultValue={obj.leader || ""} floatingLabelText="Leader" readOnly={readOnly} required={true} errors={errors && errors.leader} singlecontrol/>
          </div>
          <div>
            {hide.includes("userId")?<HiddenField ref={(input) => this._inputs["userId"] = input} name="userId" defaultValue={obj.userId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["userId"] = input}  name="userId" defaultValue={obj.userId || ""} options={this.state.options.users.map(o => {return {"value": o.id, "label": o.user}})} floatingLabelText="UserId" readOnly={readOnly} required={true} errors={errors && errors[this.props.prefix+"["+i+"].userId"]} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["number"] = input}  name="number" defaultValue={obj.number || ""} floatingLabelText="Number" readOnly={readOnly} required={true} errors={errors && errors.number}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["q"] = input}  name="q" defaultValue={obj.q || ""} floatingLabelText="Q" readOnly={readOnly} required={true} errors={errors && errors.q}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["r"] = input}  name="r" defaultValue={obj.r || ""} floatingLabelText="R" readOnly={readOnly} required={true} errors={errors && errors.r}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["s"] = input}  name="s" defaultValue={obj.s || ""} floatingLabelText="S" readOnly={readOnly} required={true} errors={errors && errors.s}/>
          </div>
        </div>
    }

}
    
      