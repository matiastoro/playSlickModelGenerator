import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
//inputs de nested


/*
<Route path="/attachment/" component={AttachmentList} />
<Route path="/attachment/new" component={AttachmentForm} />
<Route path="/attachment/:id" component={AttachmentForm} />
<Route path="/attachment/:page/:pageLength" component={AttachmentList} />
*/
export default class AttachmentForm extends GList{
    showUrl =  '/attachment/'
    apiGetUrl =  '/attachment/'
    apiCreateUrl = '/attachment/save'
    apiDeleteUrl = '/attachment/delete/'
    

    objsStr = 'Attachments'

    renderPrimaryText = (obj) => {
        return obj.attachment
    }
    secondaryLines = 1
    renderSecondaryText = (obj) => {
        return <p>
            {obj.estado} -- 
            {obj.targetTable} -- 
            {obj.d}
        </p>
    }
       
    renderForm(obj, errors){
        const readOnly = this.state.readOnly
        
        return <div>

        </div>
    }

}
      