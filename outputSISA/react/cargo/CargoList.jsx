import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/cargo/" component={CargoList} />
<Route path="/cargo/new" component={CargoForm} />
<Route path="/cargo/:id" component={CargoForm} />
<Route path="/cargo/:page/:pageLength" component={CargoList} />
*/
export default class CargoForm extends GList{
    showUrl =  '/cargo/'
    apiGetUrl =  '/cargo/'
    apiCreateUrl = '/cargo/save'
    apiDeleteUrl = '/cargo/delete/'
    apiOptionsUrl = '/cargo/options'

    objsStr = 'Cargos'

    renderPrimaryText = (obj) => {
        return obj.cargo
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.codigo} -- 
            {obj.organizacionId && this.state.indexedOptions?this.state.indexedOptions.organizacions[obj.organizacionId].organizacion:null}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        const hide = this.props.hide || []
        return <div>

        </div>
    }

}
      