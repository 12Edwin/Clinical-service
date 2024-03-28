<template>
    <div class="w-100">
        <b-row>
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
                                <template #header>
                                    <img style="border-radius: 10px 10px; width: 100%; height: 120px!important;"
                                        src="https://picsum.photos/600/300/?image=25"
                                        :alt="`medical-service-${speciality.name}`">
                                </template>
                                <template #title>
                                    <div class="d-flex justify-content-center align-items-center">
                                        {{ speciality.name }}
                                    </div>
                                </template>
                                <template #footer>
                                    <Button icon="pi pi-pencil" class="p-button-rounded button-style"
                                        @click="openModal(speciality)" v-tooltip.top="'Editar'" />
                                    <Button icon="pi pi-eye" class="p-button-rounded p-button-success"
                                        style="margin-left: .5em" v-tooltip.top="'Detalle'" />
                                    <!-- <Button :icon="speciality.status ? 'pi pi-lock-open' : 'pi pi-lock'" v-tooltip.top="speciality.status ? 'Deshabilitar' : 'Habilitar'" class="p-button-rounded p-button-secondary" style="margin-left: .5em" @click="deleteSpeciality()" /> -->
                                </template>
                            </Card>
                        </b-col>
                    </b-row>
                    <b-row>
                        <!--                         <b-col cols="1" :style="{marginTop: '20px'}">
                            <small style="">Registros: </small> 0
                        </b-col> -->
                        <b-col>
                             <Paginator :rows="10" :totalRecords="2" :rowsPerPageOptions="[5,10,20]" :first="0" :pageLinkSize="1" :style="{marginTop: '20px'}"  @page="pagination($event)"/> 
 <!--                            <Paginator :first="0" :rows="10" :totalRecords="5" :rowsPerPageOptions="[10,20,30]">
                                <template #start="slotProps">
                                    {{ slotProps.state }}
                                </template>
                                <template #end>
                                    <Button type="button" icon="pi pi-search" />
                                </template>
                            </Paginator> -->
                        </b-col>
                    </b-row>
                </panel>
            </b-col>
            <ConfirmDialog></ConfirmDialog>

        </b-row>
        <ModalUpdateSpecialityVue :visible.sync="displayModal" :speciality="speciality" />
        <ModalSaveSpeciality :visible.sync="displaySaveModal" y></ModalSaveSpeciality>
    </div>
</template>
<script>
import AccordionTab from 'primevue/accordiontab';
import ConfirmDialog from 'primevue/confirmdialog';
import ModalUpdateSpecialityVue from './ModalUpdateSpeciality.vue';
import ModalSaveSpeciality from './ModalSaveSpeciality.vue';
import Paginator from 'primevue/paginator';
import specialitiesServices from "@/modules/speciality/services/speciality-services"
import {decrypt} from "@/config/security"
export default {
    components: {
        AccordionTab,
        ConfirmDialog,
        ModalUpdateSpecialityVue,
        ModalSaveSpeciality,
        Paginator
    },	
    data(){
        return {
            specialities: [],
            displayModal: false,
            displaySaveModal: false,
            speciality: {
                name: '',
                description: ''
            },
            pageable: {
                page: 0,
                size: 2
            }
        }
    },
    methods:{
        deleteSpeciality() {
            this.$confirm.require({
                message: '¿Está seguro de cambiar el estado de esta especialidad?',
                header: 'Confirmación',
                icon: 'pi pi-info-circle',
                acceptLabel: 'Sí',
                acceptClass: 'p-button-danger',
                accept: () => {},
                reject: () => {}
            });
        },
        openModal(speciality) {
            this.displayModal = true;
            this.speciality = speciality;
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
                this.specialities = JSON.parse(decripted).content
            }
           } catch (error) {}
           
        },
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

.label{
    text-align: justify;
    font-size: 14px;
    font-family: 'Roboto', sans-serif;
    font-weight: 10;
    color: #000;
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