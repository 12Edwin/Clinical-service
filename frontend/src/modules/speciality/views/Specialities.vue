<template>
    <div class="w-100">
        <b-row>
            <b-col cols="12">
                <panel>
                    <template #header>
                        <div class="d-flex justify-content-between w-100 align-items-center">
                            <p class="h5"><b>Gestión de especialidades</b></p>
                            <Button class="p-button-rounded p-button-outlined px-2"  @click="openModalSaveSpeciality()">
                                <BIcon icon="plus-circle" scale="2" />
                            </Button>
                        </div>
                    </template>
                    <b-row>
                        <b-col cols="12" md="8" lg="10" class="mb-4 d-flex justify-content-end align-items-center w-100">
                            <span class="p-input-icon-right">
                                <i class="pi pi-search" />
                                <InputText placeholder="Buscar..." />
                            </span>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="12" md="6" lg="3" v-for="(speciality, index) in specialities" :key="index" class="d-flex justify-content-center align-items-center">
                            <Card
                                class="mb-1 mt-2 card-custom"
                            >
                                <template #header>
                                    <img
                                        style="border-radius: 10px 10px; width: 100%; height: 120px;" 
                                        src="https://picsum.photos/600/300/?image=25" 
                                        :alt="`medical-service-${speciality.name}`"
                                    >
                                </template>
                                <template #title>
                                    {{ speciality.name }}
                                </template>
                                <template #footer>
                                    <Button icon="pi pi-pencil" class="p-button-rounded button-style" @click="openModal(speciality)"/>
                                    <Button icon="pi pi-eye" class="p-button-rounded p-button-success" style="margin-left: .5em"/>
                                    <Button icon="pi pi-trash" class="p-button-rounded p-button-secondary" style="margin-left: .5em" @click="deleteSpeciality()" />
                                </template>
                            </Card>
                        </b-col>
                    </b-row>
                </panel>
            </b-col>
            <ConfirmDialog></ConfirmDialog>
        </b-row>
        <ModalUpdateSpecialityVue
            :visible.sync="displayModal"
            :speciality="speciality"
        />
        <ModalSaveSpeciality
            :visible.sync="displaySaveModal"
        y></ModalSaveSpeciality>
    </div>
</template>
<script>
import AccordionTab from 'primevue/accordiontab';
import ConfirmDialog from 'primevue/confirmdialog';
import ModalUpdateSpecialityVue from './ModalUpdateSpeciality.vue';
import ModalSaveSpeciality from './ModalSaveSpeciality.vue';
export default {
    components: {
        AccordionTab,
        ConfirmDialog,
        ModalUpdateSpecialityVue,
        ModalSaveSpeciality
    },	
    data(){
        return {
            specialities: [
            {
                name: 'Cardiología',
                description: 'Especialidad que se encarga del estudio, diagnóstico y tratamiento de las enfermedades del corazón y del aparato circulatorio.',
                price: 2500,
                status: 'ACTIVE'
            },
            {
                name: 'Neurología',
                description: 'Especialidad que se encarga del estudio, diagnóstico y tratamiento de las enfermedades de la piel.',
                price: 2500,
                status: 'ACTIVE'
            },
            {
                name: 'Dermatología',
                description: 'Especialidad que se encarga del estudio, diagnóstico y tratamiento de las enfermedades de la piel.',
                price: 2500,
                status: 'ACTIVE'
            },
            {
                name: 'Oftalmología',
                description: 'Especialidad de la medicina que estudia las enfermedades del ojo y sus tratamientos.',
                price: 2500,
                status: 'ACTIVE'
            }

            ],
            displayModal: false,
            displaySaveModal: false,
            speciality: {
                name: '',
                description: ''
            }
        }
    },
    methods:{
        deleteSpeciality() {
            this.$confirm.require({
                message: '¿Está seguro de deshabilitar esta especialidad?',
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
        }
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
</style>