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
import LanguageForm from './components/language/LanguageForm'
import LanguageList from './components/language/LanguageList'

<Route path="/language/" component={LanguageList} />
<Route path="/language/new" component={LanguageForm} />
<Route path="/language/:id" component={LanguageForm} />
<Route path="/language/:page/:pageLength" component={LanguageList} />
*/
export default class LanguageForm extends GForm{
    showUrl =  '/language/'
    listUrl =  '/language/'
    apiGetUrl =  '/language/show/'
    apiCreateUrl = '/language/save'
    apiUpdateUrl = '/language/update/'
    apiDeleteUrl = '/language/delete/'
    

    objStr = 'Language'
    objGender = 'F'

    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        const options = this.state.options
        return <div>
          <div>
            <HiddenField ref={(input) => this._inputs["id"] = input} name="id" defaultValue={this.getAttr(obj, "id", "")} readOnly={readOnly} />
          </div>
          <div>
            <TextField ref={(input) => this._inputs["language"] = input}  name="language" fullWidth defaultValue={this.getAttr(obj, "language", "")} floatingLabelText="Language" readOnly={readOnly} required={true} errors={this.getAttr(errors, "language")}/>
          </div>
        </div>
    }

}


      