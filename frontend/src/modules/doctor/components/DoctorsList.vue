<template>
    <div>
        <Panel class="w-100 shadow-lg">
        <template #header>
            <div class="d-flex justify-content-between w-100 align-items-center">
                <p class="h5 text-secondary"><b>Gestion de doctores</b></p>
                <Button class="p-button-rounded p-button-outlined px-2 style" @click="$router.push(
                    { name: 'register' }
                )">
                    <BIcon icon="plus-circle" scale="2" />
                </Button>
            </div>
        </template>
        <b-row>
            <b-col>
                <DataTable class="p-datatable-lg w-100" :value="doctors" :paginator="true" :rows="2"
                    responsiveLayout="scroll" dataKey="id" :loading="loading" :selection.sync="selectedDoctors" :globalFilterFields="['fullname', 'speciality', 'phone', 'sex']"
                    :filters.sync="filters1" filterDisplay="menu">
                    <template #header>
                        <b-row>
                            <b-col cols="12" class="d-flex justify-content-end align-items-center">
                                <span class="p-input-icon-right">
                                    <i class="pi pi-search" />
                                    <InputText v-model="filters1['global'].value"  placeholder="Buscar por nombre..." />
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
                    <Column selectionMode="multiple" :styles="{ 'min-width': '3rem' }"></Column>
                    <Column field="name" header="Doctor (a)" sortable :styles="{ 'min-width': '14rem' }">
                        <template #body="{ data }">
                            {{ data.fullname }}
                        </template>
                    </Column>
                    <Column field="lastName" header="Género" sortable :styles="{ 'min-width': '14rem' }">
                        <template #body="{ data }">
                            {{ data.sex }}
                        </template>
                    </Column>
                    <Column field="curp" header="Número de teléfono" sortable :styles="{ 'min-width': '14rem' }">
                        <template #body="{ data }">
                            {{ data.phone }}
                        </template>
                    </Column>
                    <Column field="specialty" header="Especialidad" sortable :styles="{ 'min-width': '14rem' }">
                        <template #body="{ data }">
                            {{ data.speciality }}
                        </template>
                    </Column>
                    <Column header="Acciones" :headerStyle="{ 'min-width': '4rem', 'text-align': 'center' }"
                        :bodyStyle="{ 'text-align': 'center', overflow: 'visible' }">
                        <template #body="{ data }">
                            <Button icon="pi pi-pencil" class="p-button-rounded button-style"
                                        @click="openModalUpdate(data)"
                                         v-tooltip.top="'Editar'" />
                                    <Button icon="pi pi-trash" v-tooltip.top="'Eliminar'" class="p-button-rounded p-button-secondary" style="margin-left: .5em" @click="deleteDoctor(data.id)" />
                        </template>
                    </Column>
                </DataTable>
            </b-col>
        </b-row>
    </Panel>
    <Toast/>
    <ConfirmDialog></ConfirmDialog>
    <ModalUpdateVue :visible.sync="displayUpdateModal" :doctor="doctor" />
    </div>
</template>

<script>
import DataTable from 'primevue/datatable/DataTable'
import Column from 'primevue/column/Column'
import AccordionTab from 'primevue/accordiontab';
import ConfirmDialog from 'primevue/confirmdialog';
import Paginator from 'primevue/paginator';
import Toast from 'primevue/toast';
import service from '../services/doctor-service'
import { decrypt, encrypt } from '@/config/security';
import ModalUpdateVue from './ModalUpdate.vue'

export default {
    components: {
        Column,
        DataTable,
        AccordionTab,
        ConfirmDialog,
        Paginator,
        Toast,
        ModalUpdateVue
    },
    data() {
        return {
            loading: false,
            doctors: [],
            displayUpdateModal: false,
            doctor: {
                name: '',
                lastname: '',
                surname: '',
                phone: '',
            },
            pageable: {
                page: 0,
                size: 10
            },
            totalRecords: 0,
            selectedDoctors: null,
            filters1: {
                global: { value: '' } // Inicializar el filtro global
            },
        }
    },
    mounted(){
        this.getDoctors();
    },
    methods: {
        async getDoctors(event) {
            this.loading = true;
            if(event != undefined){
            const {page, rows} = event;
                this.pageable.page = page;
                this.pageable.size = rows;
           }
           try {
            const {status, data : { result } } = await service.get_doctors(this.pageable)          
            if(status === 200 || status === 201){
                const decripted = await decrypt(result)
                const {content, totalElements} = JSON.parse(decripted)
                this.totalRecords = totalElements
                this.doctors = content;
            }
           } catch (error) {
           }
           this.loading = false
        },
        deleteDoctor(doctorId) {
            this.$confirm.require({
                message: '¿Está seguro de eliminar este Doctor?',
                header: 'Confirmación',
                icon: 'pi pi-info-circle',
                acceptLabel: 'Sí',
                acceptClass: 'p-button-danger',
                accept: async () => {
                    try {
                        const encodedId  = await encrypt(doctorId)
                        const {status} = await service.deleteDoctor(encodedId)
                        if(status === 200 || status === 201){
                            this.getDoctors();
                            this.$toast.add({severity:'success', summary: 'Éxito', detail: 'Doctor eliminado correctamente', life: 3000});
                        }
                    } catch (error) {}
                },
                reject: () => {}
            });
        },
        openModalUpdate(doctor){
            this.displayUpdateModal = true;
            this.doctor = JSON.stringify(doctor)
        },
    }
} 
</script>

<style scoped lang="scss">
@import "@/styles/colors.scss";

.style {
    background: $green-primary;
    border: none;
}

.style:hover {
    background-color: #368368 !important;
}
</style>