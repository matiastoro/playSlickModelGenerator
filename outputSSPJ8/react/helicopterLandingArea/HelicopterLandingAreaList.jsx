import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import HelicopterLandingAreaFilter from './HelicopterLandingAreaFilter'

//inputs de nested


/*
<Route path="/helicopterLandingArea/" component={HelicopterLandingAreaList} />
<Route path="/helicopterLandingArea/new" component={HelicopterLandingAreaForm} />
<Route path="/helicopterLandingArea/:id" component={HelicopterLandingAreaForm} />
<Route path="/helicopterLandingArea/:page/:pageLength" component={HelicopterLandingAreaList} />
*/
export default class HelicopterLandingAreaList extends GList{
    showUrl =  '/helicopterLandingArea/'
    apiGetUrl =  '/helicopterLandingArea/'
    apiCreateUrl = '/helicopterLandingArea/save'
    apiDeleteUrl = '/helicopterLandingArea/delete/'
    apiOptionsUrl = '/helicopterLandingArea/options'
    listUrl = '/helicopterLandingArea/'

    objsStr = 'Helicopter Landing Areas'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.helicopterLandingArea
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.aerodromeId && this.state.indexedOptions?this.state.indexedOptions.aerodromes[obj.aerodromeId].aerodrome:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <HelicopterLandingAreaFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      