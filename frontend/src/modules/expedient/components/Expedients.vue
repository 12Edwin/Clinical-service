<template>
  <Panel class="w-100">
    <template #header>
      <div class="d-flex justify-content-between w-100 align-items-center">
        <p class="h5"><b>Expedientes</b></p>
        <Button class="p-button-rounded p-button-outlined px-2"><BIcon icon="plus-circle" scale="2"/></Button>
      </div>
    </template>
    <DataTable class="p-datatable-lg w-100" :value="expedients" :paginator="true" :rows="2" responsiveLayout="scroll"
               dataKey="id" :filters.sync="filters" filterDisplay="row" :loading="loading">
      <template #empty>
        No customers found.
      </template>
      <template #loading>
        Loading customers data. Please wait.
      </template>
      <Column field="name" header="Nombre" :filterMatchModeOptions="matchModeOptions">
        <template #body="{data}">
          <Panel toggleable :header="data.name" collapsed>
            <span>{{data.name}}</span>
          </Panel>
        </template>
        <template #filter="{filterModel,filterCallback}">
          <InputText type="text" v-model="filterModel.value" @input="filterCallback()" class="p-column-filter" :placeholder="`Buscar por nombre - ${filterModel.matchMode}`"/>
        </template>
      </Column>
      <Column header="CURP" filterField="curp" :filterMatchModeOptions="matchModeOptions">
        <template #body="{data}">
          <span class="image-text">{{data.curp}}</span>
        </template>
        <template #filter="{filterModel,filterCallback}">
          <InputText type="text" v-model="filterModel.value" @input="filterCallback()" class="p-column-filter" :placeholder="`Buscar por CURP - ${filterModel.matchMode}`" />
        </template>
      </Column>
    </DataTable>
  </Panel>
</template>

<script>
import FilterMatchMode from 'primevue/api/FilterMatchMode';
import DataTable from "primevue/datatable/DataTable";
import Column from "primevue/column/Column";
import ColumnGroup from "primevue/columngroup/ColumnGroup";

export default {
  components:{
    DataTable,
    Column,
    ColumnGroup
  },
  data(){
    return{
      expedients:[
        {name: 'Expediente 1', curp: 'curp 1'}, {name: 'Expediente 1', curp: 'curp 2'}, {name: 'Expediente 1', curp: 'curp 3'}
      ],
      loading: false,
      filters: {
        'name': {value: null, matchMode: FilterMatchMode.CONTAINS},
        'curp': {value: null, matchMode: FilterMatchMode.CONTAINS},
      },
      matchModeOptions: [
        {label: 'Comenzando por...', value: FilterMatchMode.STARTS_WITH},
        {label: 'Contiene...', value: FilterMatchMode.CONTAINS}
      ],
    }
  },
}
</script>

<style scoped>

</style>