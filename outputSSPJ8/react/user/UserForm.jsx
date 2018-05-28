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
      
//inputs de nested


/*
import UserForm from './components/user/UserForm'
import UserList from './components/user/UserList'

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
    

    objStr = 'User'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["username"] = input}  name="username" defaultValue={this.getAttr(obj, "username", "")} floatingLabelText="Username" readOnly={readOnly} required={true} errors={this.getAttr(errors, "username")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["password"] = input}  name="password" defaultValue={this.getAttr(obj, "password", "")} floatingLabelText="Password" readOnly={readOnly} required={true} errors={this.getAttr(errors, "password")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["name"] = input}  name="name" defaultValue={this.getAttr(obj, "name", "")} floatingLabelText="Name" readOnly={readOnly} required={true} errors={this.getAttr(errors, "name")}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["email"] = input}  name="email" defaultValue={this.getAttr(obj, "email", "")} floatingLabelText="Email" readOnly={readOnly} required={true} errors={this.getAttr(errors, "email")}/>
          </div>
            <GNestedForms ref={(i) => this._inputs["profileUsers"] = i} description="ProfileUsers" prefix="profileUsers" readOnly={readOnly} objs={obj.profileUsers} renderNested={(nobj, k, refFunc) => <ProfileUserFormInline i={k} obj={Object.assign({userId: obj.id},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["userId"]} errors={errors} prefix="profileUsers"/>}/>
        </div>
    }

}


      