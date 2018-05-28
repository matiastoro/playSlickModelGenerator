import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';

import {GNestedForms} from '../gforms/GForm';
import {ProfileUserFormInline} from '../profileUser/ProfileUserForm'
import {CharacterFormInline} from '../character/CharacterForm'
      
//inputs de nested


/*
<Route path="/user/" component={UserList} />
<Route path="/user/new" component={UserForm} />
<Route path="/user/:id" component={UserForm} />
<Route path="/user/:page/:pageLength" component={UserList} />
*/
export default class UserForm extends GForm{
    showUrl =  '/user/'
    listUrl =  '/user/'
    apiGetUrl =  '/user/show/'
    apiCreateUrl = '/user/save'
    apiUpdateUrl = '/user/update/'
    apiDeleteUrl = '/user/delete/'
    apiOptionsUrl = "/user/options"

    objStr = 'User'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={obj.id || ""} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["username"] = input}  name="username" defaultValue={obj.username || ""} floatingLabelText="Username" readOnly={readOnly} required={true} errors={errors && errors.username}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["password"] = input}  name="password" defaultValue={obj.password || ""} floatingLabelText="Password" readOnly={readOnly} required={true} errors={errors && errors.password}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["name"] = input}  name="name" defaultValue={obj.name || ""} floatingLabelText="Name" readOnly={readOnly} required={true} errors={errors && errors.name}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["email"] = input}  name="email" defaultValue={obj.email || ""} floatingLabelText="Email" readOnly={readOnly} required={true} errors={errors && errors.email}/>
          </div>
          <div>
            {hide.includes("armyId")?<HiddenField ref={(input) => this._inputs["armyId"] = input} name="armyId" defaultValue={obj.armyId || ""} readOnly={readOnly} />:<SelectField ref={(input) => this._inputs["armyId"] = input}  name="armyId" defaultValue={obj.armyId || ""} options={this.state.options.armys.map(o => {return {"value": o.id, "label": o.army}})} floatingLabelText="ArmyId" readOnly={readOnly} required={false} errors={errors && errors[this.props.prefix+"["+i+"].armyId"]} />}
          </div>
          <div>
            <TextField ref={(input) => this._inputs["army"] = input}  name="army" defaultValue={obj.army || ""} floatingLabelText="Army" readOnly={readOnly} required={false} errors={errors && errors.army}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["background"] = input}  name="background" defaultValue={obj.background || ""} floatingLabelText="Background" readOnly={readOnly} required={false} errors={errors && errors.background}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["observations"] = input}  name="observations" defaultValue={obj.observations || ""} floatingLabelText="Observations" readOnly={readOnly} required={false} errors={errors && errors.observations}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["color"] = input}  name="color" defaultValue={obj.color || ""} floatingLabelText="Color" readOnly={readOnly} required={false} errors={errors && errors.color}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["icon"] = input}  name="icon" defaultValue={obj.icon || ""} floatingLabelText="Icon" readOnly={readOnly} required={false} errors={errors && errors.icon}/>
          </div>
            <GNestedForms ref={(i) => this._inputs["profileUsers"] = i} description="ProfileUsers" prefix="profileUsers" readOnly={readOnly} objs={obj.profileUsers} renderNested={(nobj, k, refFunc) => <ProfileUserFormInline i={k} obj={Object.assign({userId: obj.id},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["userId"]} errors={errors} prefix="profileUsers"/>}/>
            <GNestedForms ref={(i) => this._inputs["characters"] = i} description="Characters" prefix="characters" readOnly={readOnly} objs={obj.characters} renderNested={(nobj, k, refFunc) => <CharacterFormInline i={k} obj={Object.assign({userId: obj.id},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["userId"]} errors={errors} prefix="characters"/>}/>
        </div>
    }

}


      