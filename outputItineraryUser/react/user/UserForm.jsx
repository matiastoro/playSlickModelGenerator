import React from 'react';
import TextField from '../gforms/GTextField';
import SelectField from '../gforms/GSelectField';
import Checkbox from '../gforms/GCheckbox';
import DateTime from '../gforms/GDateTime';
import DatePicker from '../gforms/GDatePicker';
import HiddenField from '../gforms/GHiddenField';
import GForm from '../gforms/GForm';

import {GNestedForms} from '../gforms/GForm';
import {UserProfileFormInline} from '../userProfile/UserProfileForm'
import {ItineraryUserAerodromeFormInline} from '../itineraryUserAerodrome/ItineraryUserAerodromeForm'
import {ItineraryUserCompanyFormInline} from '../itineraryUserCompany/ItineraryUserCompanyForm'
      
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
    

    objStr = 'User'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
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
            <TextField ref={(input) => this._inputs["lastName"] = input}  name="lastName" defaultValue={obj.lastName || ""} floatingLabelText="LastName" readOnly={readOnly} required={true} errors={errors && errors.lastName}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["lastName2"] = input}  name="lastName2" defaultValue={obj.lastName2 || ""} floatingLabelText="LastName2" readOnly={readOnly} required={true} errors={errors && errors.lastName2}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["email"] = input}  name="email" defaultValue={obj.email || ""} floatingLabelText="Email" readOnly={readOnly} required={true} errors={errors && errors.email}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["phone"] = input}  name="phone" defaultValue={obj.phone || ""} floatingLabelText="Phone" readOnly={readOnly} required={true} errors={errors && errors.phone}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["secretQuestion"] = input}  name="secretQuestion" defaultValue={obj.secretQuestion || ""} floatingLabelText="SecretQuestion" readOnly={readOnly} required={true} errors={errors && errors.secretQuestion}/>
          </div>
          <div>
            <TextField ref={(input) => this._inputs["secretAnswer"] = input}  name="secretAnswer" defaultValue={obj.secretAnswer || ""} floatingLabelText="SecretAnswer" readOnly={readOnly} required={true} errors={errors && errors.secretAnswer}/>
          </div>
            <GNestedForms ref={(i) => this._inputs["userProfiles"] = i} description="UserProfiles" prefix="userProfiles" readOnly={readOnly} objs={obj.userProfiles} renderNested={(nobj, k, refFunc) => <UserProfileFormInline i={k} obj={Object.assign({userId: obj.id},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["userId"]} errors={errors} prefix="userProfiles"/>}/>
            <GNestedForms ref={(i) => this._inputs["itineraryUserAerodromes"] = i} description="ItineraryUserAerodromes" prefix="itineraryUserAerodromes" readOnly={readOnly} objs={obj.itineraryUserAerodromes} renderNested={(nobj, k, refFunc) => <ItineraryUserAerodromeFormInline i={k} obj={Object.assign({userId: obj.id},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["userId"]} errors={errors} prefix="itineraryUserAerodromes"/>}/>
            <GNestedForms ref={(i) => this._inputs["itineraryUserCompanys"] = i} description="ItineraryUserCompanys" prefix="itineraryUserCompanys" readOnly={readOnly} objs={obj.itineraryUserCompanys} renderNested={(nobj, k, refFunc) => <ItineraryUserCompanyFormInline i={k} obj={Object.assign({userId: obj.id},nobj)} ref={(input) => refFunc(input)} readOnly={readOnly} hide={["userId"]} errors={errors} prefix="itineraryUserCompanys"/>}/>
        </div>
    }

}


      