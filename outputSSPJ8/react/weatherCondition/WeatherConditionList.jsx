import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import WeatherConditionFilter from './WeatherConditionFilter'

//inputs de nested


/*
<Route path="/weatherCondition/" component={WeatherConditionList} />
<Route path="/weatherCondition/new" component={WeatherConditionForm} />
<Route path="/weatherCondition/:id" component={WeatherConditionForm} />
<Route path="/weatherCondition/:page/:pageLength" component={WeatherConditionList} />
*/
export default class WeatherConditionList extends GList{
    showUrl =  '/weatherCondition/'
    apiGetUrl =  '/weatherCondition/'
    apiCreateUrl = '/weatherCondition/save'
    apiDeleteUrl = '/weatherCondition/delete/'
    
    listUrl = '/weatherCondition/'

    objsStr = 'Weather Conditions'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.weatherCondition
    }
    

    renderFilter = () => {
        return <div>
            <WeatherConditionFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      