import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/obj/" component={ObjList} />
<Route path="/obj/new" component={ObjForm} />
<Route path="/obj/:id" component={ObjForm} />
<Route path="/obj/:page/:pageLength" component={ObjList} />
*/
export default class ObjForm extends GList{
    showUrl =  '/obj/'
    apiGetUrl =  '/obj/'
    apiCreateUrl = '/obj/save'
    apiDeleteUrl = '/obj/delete/'
    

    objsStr = 'Objs'

    renderPrimaryText = (obj) => {
        return obj.id
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.tpe} -- 
            {obj.q} -- 
            {obj.r} -- 
            {obj.s}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      