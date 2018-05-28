import React from 'react';
import GList from '../gforms/GList'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import EccairsFilter from './EccairsFilter'

//inputs de nested


/*
<Route path="/eccairs/" component={EccairsList} />
<Route path="/eccairs/new" component={EccairsForm} />
<Route path="/eccairs/:id" component={EccairsForm} />
<Route path="/eccairs/:page/:pageLength" component={EccairsList} />
*/
export default class EccairsList extends GList{
    showUrl =  '/eccairs/'
    apiGetUrl =  '/eccairs/'
    apiCreateUrl = '/eccairs/save'
    apiDeleteUrl = '/eccairs/delete/'
    apiOptionsUrl = '/eccairs/options'
    listUrl = '/eccairs/'

    objsStr = 'Eccairss'

    /*constructor(props){
        super(props);
        this.secondaryLines = 1
    }*/

    renderPrimaryText = (obj) => {
        return obj.number
    }
    
    renderSecondaryText = (obj) => {
        return <p>
            {obj.date} -- 
            {obj.stateAreaId && this.state.indexedOptions?this.state.indexedOptions.stateAreas[obj.stateAreaId].stateArea:null} -- 
            {obj.ocurrenceClassId && this.state.indexedOptions?this.state.indexedOptions.ocurrenceClasss[obj.ocurrenceClassId].ocurrenceClass:null} -- 
            {obj.injuryLevelId && this.state.indexedOptions?this.state.indexedOptions.injuryLevels[obj.injuryLevelId].injuryLevel:null} -- 
            {obj.massGroupId && this.state.indexedOptions?this.state.indexedOptions.aircraftMassGroups[obj.massGroupId].mass_group:null} -- 
            {obj.categoryId && this.state.indexedOptions?this.state.indexedOptions.aircraftCategorys[obj.categoryId].category:null} -- 
            {obj.registry} -- 
            {obj.operationTypeId && this.state.indexedOptions?this.state.indexedOptions.operationTypes[obj.operationTypeId].operationType:null} -- 
            {obj.operatorTypeId && this.state.indexedOptions?this.state.indexedOptions.operatorTypes[obj.operatorTypeId].operatorType:null} -- 
            {obj.operatorId && this.state.indexedOptions?this.state.indexedOptions.operators[obj.operatorId].operator:null} -- 
            {obj.weatherConditionId && this.state.indexedOptions?this.state.indexedOptions.weatherConditions[obj.weatherConditionId].weatherCondition:null}
        </p>
    }
       

    renderFilter = () => {
        return <div>
            <EccairsFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      