import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import WeatherConditionFilter from './WeatherConditionFilter'

//inputs de nested


/*
import WeatherConditionForm from './components/weatherCondition/WeatherConditionForm'
import WeatherConditionList from './components/weatherCondition/WeatherConditionList'
import WeatherConditionTable from './components/weatherCondition/WeatherConditionTable'

<Route path="/weatherCondition/" component={WeatherConditionList} />
<Route path="/weatherCondition/" component={WeatherConditionTable} />
<Route path="/weatherCondition/new" component={WeatherConditionForm} />
<Route path="/weatherCondition/:id" component={WeatherConditionForm} />
<Route path="/weatherCondition/:page/:pageLength" component={WeatherConditionList} />
<Route path="/weatherCondition/:page/:pageLength/:query" component={WeatherConditionList} />
<Route path="/weatherCondition/:page/:pageLength" component={WeatherConditionTable} />
<Route path="/weatherCondition/:page/:pageLength/:query" component={WeatherConditionTable} />
<Route path="/weatherCondition/:page/:pageLength/:sortColumn/:sortOrder" component={WeatherConditionTable} />
<Route path="/weatherCondition/:page/:pageLength/:sortColumn/:sortOrder/:query" component={WeatherConditionTable} />
*/
export default class WeatherConditionTable extends GTable{
    showUrl =  '/weatherCondition/'
    apiGetUrl =  '/weatherCondition/'
    apiCreateUrl = '/weatherCondition/save'
    apiDeleteUrl = '/weatherCondition/delete/'
    
    listUrl = '/weatherCondition/'

    objsStr = 'Weather Conditions'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "weatherCondition", rowName: "Weather Condition", sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <WeatherConditionFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      