import React from 'react';
import GTable from '../gforms/GTable'
import {grey400, darkBlack, lightBlack} from 'material-ui/styles/colors';
import EccairsFilter from './EccairsFilter'

//inputs de nested


/*
import EccairsForm from './components/eccairs/EccairsForm'
import EccairsList from './components/eccairs/EccairsList'
import EccairsTable from './components/eccairs/EccairsTable'

<Route path="/eccairs/" component={EccairsList} />
<Route path="/eccairs/" component={EccairsTable} />
<Route path="/eccairs/new" component={EccairsForm} />
<Route path="/eccairs/:id" component={EccairsForm} />
<Route path="/eccairs/:page/:pageLength" component={EccairsList} />
<Route path="/eccairs/:page/:pageLength/:query" component={EccairsList} />
<Route path="/eccairs/:page/:pageLength" component={EccairsTable} />
<Route path="/eccairs/:page/:pageLength/:query" component={EccairsTable} />
<Route path="/eccairs/:page/:pageLength/:sortColumn/:sortOrder" component={EccairsTable} />
<Route path="/eccairs/:page/:pageLength/:sortColumn/:sortOrder/:query" component={EccairsTable} />
*/
export default class EccairsTable extends GTable{
    showUrl =  '/eccairs/'
    apiGetUrl =  '/eccairs/'
    apiCreateUrl = '/eccairs/save'
    apiDeleteUrl = '/eccairs/delete/'
    apiOptionsUrl = '/eccairs/options'
    listUrl = '/eccairs/'

    objsStr = 'Eccairss'


    
    fieldMapping = [
            {key: "id", rowName: "Id", sortable: true},
            {key: "number", rowName: "Number", sortable: true},
            {key: "date", rowName: "Date", label: (o, v) => this.displayDateTime(v), sortable: true},
            {key: "stateAreaId", rowName: "State Area", label: (o) => (this.findRelatedObject("stateAreas", "stateAreaId", o, "stateArea")), sortable: true},
            {key: "ocurrenceClassId", rowName: "Ocurrence Class", label: (o) => (this.findRelatedObject("ocurrenceClasss", "ocurrenceClassId", o, "ocurrenceClass")), sortable: true},
            {key: "injuryLevelId", rowName: "Injury Level", label: (o) => (this.findRelatedObject("injuryLevels", "injuryLevelId", o, "injuryLevel")), sortable: true},
            {key: "massGroupId", rowName: "Mass Group", label: (o) => (this.findRelatedObject("aircraftMassGroups", "massGroupId", o, "massGroup")), sortable: true},
            {key: "categoryId", rowName: "Category", label: (o) => (this.findRelatedObject("aircraftCategorys", "categoryId", o, "category")), sortable: true},
            {key: "registry", rowName: "Registry", sortable: true},
            {key: "operationTypeId", rowName: "Operation Type", label: (o) => (this.findRelatedObject("operationTypes", "operationTypeId", o, "operationType")), sortable: true},
            {key: "operatorTypeId", rowName: "Operator Type", label: (o) => (this.findRelatedObject("operatorTypes", "operatorTypeId", o, "operatorType")), sortable: true},
            {key: "operatorId", rowName: "Operator", label: (o) => (this.findRelatedObject("operators", "operatorId", o, "operator")), sortable: true},
            {key: "weatherConditionId", rowName: "Weather Condition", label: (o) => (this.findRelatedObject("weatherConditions", "weatherConditionId", o, "weatherCondition")), sortable: true}
    ]
       

    renderFilter = () => {
        return <div>
            <EccairsFilter listUrl={this.listUrl} options={this.state.options} query={this.state.query} doFilter={this.doFilter} pageLength={this.state.pageLength}/>
        </div>
    }
}
      