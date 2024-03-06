<template>
  <Panel class="w-100 shadow-lg">
    <template #header>
      <div class="d-flex justify-content-between w-100 align-items-center">
        <p class="h5 text-secondary"><b>Gestion de doctores</b></p>
        <Button class="p-button-rounded p-button-outlined px-2 style" @click="$router.push(
            {name: 'register'}
        )"><BIcon icon="plus-circle" scale="2" /></Button>
      </div>
    </template>
    <b-row>
        <b-col>
            <DataTable
                class="p-datatable-lg w-100" 
                :value="doctors" 
                :paginator="true" 
                :rows="2"
                responsiveLayout="scroll"
                dataKey="id" 
                :loading="loading"
                :selection.sync="selectedDoctors"
            >
                <template #header>
                <b-row>
                        <b-col cols="12" class="d-flex justify-content-end align-items-center">
                            <span class="p-input-icon-right">
                                <i class="pi pi-search" />
                                <InputText placeholder="Buscar por nombre..." />
                            </span>
                        </b-col>
                </b-row>
                </template>
      <template #empty>
        <div class="text-center">
            No hay doctores registrados.
        </div>
      </template>
      <template #loading>
        <div class="text-left">
            Cargando doctores...
        </div>
      </template>
      <Column selectionMode="multiple" :styles="{'min-width': '3rem'}"></Column>
        <Column field="name" header="Nombre" sortable :styles="{'min-width': '14rem'}">
            <template #body="{data}">
                {{data.name}}
            </template>
        </Column>
        <Column field="lastName" header="Apellidos" sortable :styles="{'min-width': '14rem'}">
            <template #body="{data}">
                {{ data.lastName }} {{ data.secondLastName }}
            </template>
        </Column>
        <Column field="curp" header="Clave Unica de Registro" sortable :styles="{'min-width': '14rem'}">
            <template #body="{data}">
                {{ data.curp }}
            </template>
        </Column>
        <Column field="specialty" header="Especialidad" sortable :styles="{'min-width': '14rem'}">
            <template #body="{data}">
                {{ data.specialty }}
            </template>
        </Column>
        <Column header="Acciones" :headerStyle="{'min-width': '4rem', 'text-align': 'center'}" :bodyStyle="{'text-align': 'center', overflow: 'visible'}">
        <template #body>
            <Button type="button" icon="pi pi-pencil" class="p-button-rounded " style="background-color: #368368; border: none;"></Button>
        </template>
        </Column>
    </DataTable>
        </b-col>
    </b-row>
  </Panel>
</template>

<script>
import DataTable from 'primevue/datatable/DataTable'
import Column from 'primevue/column/Column'
export default {
    components:{
        Column,
        DataTable
    },
    data(){
        return {
            loading: false,
            doctors: [
            {
                    id:1,
                    name: 'Juan',
                    lastName: 'Pérez',
                    secondLastName: 'García',
                    curp: 'PEJG980101HDFRNN00',
                    birthDate: '1998-01-01',
                    specialty: 'Cardiología'
                },
                {
                    id:2,
                    name: 'María',
                    lastName: 'González',
                    secondLastName: 'García',
                    curp: 'GOGM980101HDFRNN00',
                    birthDate: '1998-01-01',
                    specialty: 'Pediatría'
                },
                {
                    id:3,
                    name: 'José',
                    lastName: 'García',
                    secondLastName: 'García',
                    curp: 'GOGG980101HDFRNN10',
                    birthDate: '1998-01-01',
                    specialty: 'Radiología'
                }
            ],
            selectedDoctors: null
        }
    }
} 
</script>

<style scoped>
.style{
    background: #2a715a;
    border: none;
}
.style:hover{
    background-color: #368368 !important;
}
</style>