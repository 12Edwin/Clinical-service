<template>
    <div class="w-100">
        <b-row>
            <b-col cols="12">
                <Header style="margin-bottom: 20px;" :title="'Catálogos'"/>
            </b-col>
            <b-col cols="12">
                <panel>
                    <template #header>
                        <div class="d-flex justify-content-between w-100 align-items-center">
                            <p class="h5"><b>Gestión de especialidades</b></p>
                            <Button class="p-button-rounded p-button-outlined px-2" @click="openModalSaveSpeciality()">
                                <BIcon icon="plus-circle" scale="2" />
                            </Button>
                        </div>
                    </template>
                    <b-row>
                        <b-col cols="12" md="8" lg="10"
                            class="mb-4 d-flex justify-content-end align-items-center w-100">
                            <span class="p-input-icon-right">
                                <i class="pi pi-search" />
                                <InputText placeholder="Buscar..." />
                            </span>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="12" md="6" lg="3" v-for="(speciality, index) in specialities" :key="index"
                            class="d-flex justify-content-center align-items-center">
                            <Card class="mb-1 mt-2 card-custom">
                                <template #title>
                                    <div class="d-flex justify-content-center align-items-center">
                                        <h5>{{ speciality.name }}</h5>
                                    </div>
                                </template>
                                <template #content>
                                    <div class="description">
                                        <p>{{  limitDescription(speciality.description) }}</p>
                                    </div>
                                </template>
                                <template #footer>
                                    <Button icon="pi pi-pencil" class="p-button-rounded button-style"
                                        @click="openModal(speciality)" v-tooltip.top="'Editar'" />
                                    <Button icon="pi pi-eye" class="p-button-rounded p-button-success"
                                        style="margin-left: .5em" v-tooltip.top="'Detalle'" @click="openModalDetail(speciality)"/>
                                    <Button icon="pi pi-trash" v-tooltip.top="'Eliminar'" class="p-button-rounded p-button-secondary" style="margin-left: .5em" @click="deleteSpeciality(speciality.id)" />
                                </template>
                            </Card>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="1" :style="{ marginTop: '35px' }">
                            <p class="h6"><b>Registros: </b> {{ totalRecords }}</p>
                        </b-col>
                        <b-col>
                             <Paginator :rows="10" :totalRecords="totalRecords" :rowsPerPageOptions="[5,10,15]" :first="0" :pageLinkSize="1" :style="{marginTop: '20px'}"  @page="pagination($event)"/> 
                        </b-col>
                    </b-row>
                </panel>
                <Toast/>
            </b-col>
            <ConfirmDialog></ConfirmDialog>
        </b-row>
        <ModalUpdateSpecialityVue :visible.sync="displayModal" :speciality="speciality" />
        <ModalSaveSpeciality :visible.sync="displaySaveModal" />
        <ModalDetailSpeciality :visible.sync="displayDetailModal" :speciality="speciality"/>
    </div>
</template>
<script>
import AccordionTab from 'primevue/accordiontab';
import ConfirmDialog from 'primevue/confirmdialog';
import ModalUpdateSpecialityVue from './ModalUpdateSpeciality.vue';
import ModalSaveSpeciality from './ModalSaveSpeciality.vue';
import Paginator from 'primevue/paginator';
import specialitiesServices from "@/modules/speciality/services/speciality-services"
import {decrypt, encrypt} from "@/config/security"
import utils from '@/kernel/utils';
import Toast from 'primevue/toast';
import ModalDetailSpeciality from './ModalDetailSpeciality.vue';
import Header from '@/components/Header.vue';
export default {
    components: {
        AccordionTab,
        ConfirmDialog,
        ModalUpdateSpecialityVue,
        ModalSaveSpeciality,
        Paginator,
        Toast,
        ModalDetailSpeciality,
        Header
    },	
    data(){
        return {
            specialities: [],
            displayModal: false,
            displaySaveModal: false,
            displayDetailModal: false,
            speciality: {
                name: '',
                description: ''
            },
            pageable: {
                page: 0,
                size: 10
            },
            totalRecords: 0
        }
    },
    methods:{
        deleteSpeciality(especialityId) {
            this.$confirm.require({
                message: '¿Está seguro de eliminar esta especialidad?',
                header: 'Confirmación',
                icon: 'pi pi-info-circle',
                acceptLabel: 'Sí',
                acceptClass: 'p-button-danger',
                accept: async () => {
                    try {
                        const encodedId  = await encrypt(especialityId)
                        const {status} = await specialitiesServices.deleteSpeciality(encodedId)
                        if(status === 200 || status === 201){
                            this.pagination()
                            this.$toast.add({severity:'success', summary: 'Éxito', detail: 'Especialidad eliminada correctamente', life: 3000});
                        }
                    } catch (error) {}
                },
                reject: () => {}
            });
        },
        openModal(speciality) {
            this.displayModal = true;
            this.speciality = JSON.stringify(speciality)
        },
        openModalDetail(speciality) {
            this.displayDetailModal = true;
            this.speciality = JSON.stringify(speciality)
        },
        openModalSaveSpeciality() {
            this.displaySaveModal = true;
        },
        async pagination(event){ 
           if(event != undefined){
            const {page, rows} = event;
                this.pageable.page = page;
                this.pageable.size = rows;
           }
           try {
            const {status, data : { result } } = await specialitiesServices.getSpecialities(this.pageable)  
            if(status === 200 || status === 201){
                const decripted = await decrypt(result)
                const {content, totalElements} = JSON.parse(decripted)
                this.totalRecords = totalElements
                this.specialities = content
            }
           } catch (error) {}
           
        },
        limitDescription(description){
            return utils.limitDescription(description)
        }
    },
    mounted(){
        this.pagination()
    }
}
</script>

<style scoped>

.p-inputtext{
    width: 550px;
    border-radius: 5px;
}

@media (max-width: 768px) {
    .p-inputtext{
        width: 100%;
    }
    
}

.p-card{
    width: 100%;
    height: 300px !important;
    border-radius: 10px;
    overflow: hidden;
    padding: 10px;
    box-shadow: 0 0 15px 0 rgba(0, 0, 0, 0.2);
    transition: all 0.3s;
}

.card-custom:hover{
    transform: scale(1.05);
}

.description{
    font-family: 'Arial', sans-serif;
  font-size: 18px;
  font-weight: normal; 
  color: #666;
  margin-top: 0;
  text-align: center;
  line-height: 1.5;
}

.button-style{
    background: #2a715a;
    border: none;
}
.button-style:hover{
    background-color: #368368 !important;
}

.p-button.p-button-icon-only {
    border-radius: 0;
}
</style>