<template>
    <b-row>
        <b-col cols="12">
            <Dialog header="Detalle de cita" :visible.sync="visible" :containerStyle="{ width: '40vw' }"
                @hide="() => closeModal()" :modal="true" :closeOnEscape="false" :closable="false">
                <div class="p-fluid grid">
                    <b-row>
                        <b-col class="mt-4 mb-2" lg="6">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-shield" />
                                    <InputText id="field-name" type="text" v-model="appointInfo.space.name"/>
                                    <label for="field-name" class="form-label-required">Nombre</label>
                                </span>
                                <code>{{ appointInfo.space.name }}</code>
                            </div>
                        </b-col>
                        <b-col class="mt-3" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-pen" />
                                    <Textarea id="field-description" type="text" rows="2"/>
                                    <label for="field-description" class="form-label-required">Descripción</label>
                                </span>
                            </div>
                        </b-col>
                    </b-row>
                </div>
                <template #footer> 
                    <b-row>
                        <b-col cols="12">
                            <Button icon="pi pi-times" @click="closeModal()" label="Cancelar"
                                class="p-button-rounded p-button-secondary" />
                            <Button label="Registrar" icon="pi pi-plus" @click="saveAppoint()"
                                class="p-button-rounded button-style" />
                        </b-col>
                    </b-row>
                </template>
            </Dialog>
            <Toast />
        </b-col>
    </b-row>
</template>


<script>
import Toast from 'primevue/toast';
import Dialog from 'primevue/dialog';
import Textarea from "primevue/textarea"
export default {
    name: 'ModalDetailAppoint',
    components: {
        Dialog,
        Textarea,
        Toast
    },
    props: {
        visible: {
            type: Boolean,
            required: true
        },
        appoint: {
            required: true
        }
    },
    data(){
        return {
            appointInfo: {
                created_at: 0,
                endHour: 0,
                id: 0,
                startHour: 0,
                space: {
                    description: '',
                    id: 0,
                    name: '',
                },
                startHour: 0,
                status: ''
            },
            
        }
    },
    methods: {
        closeModal() {
            this.$emit('update:visible', false);
        },
        saveAppoint(){
            console.log('Save appoint');
        }
    },
    watch:{
        appoint:{
            handler(){
                const oldAppoint = JSON.parse(this.appoint)
                console.log(oldAppoint)
                this.appointInfo.created_at = oldAppoint.created_at
                this.appointInfo.endHour = oldAppoint.endHour
                this.appointInfo.id = oldAppoint.id
                this.appointInfo.startHour = oldAppoint.startHour
                this.appointInfo.space.description = oldAppoint.space.description
            },
            deep: true
        }
    }
}
</script>

<style scoped>
.button-style{
    background: #2a715a;
    border: none;
}
.button-style:hover{
    background-color: #368368 !important;
}

.my-custom-dialog .p-dialog {
  max-width: 90%;
}

@media (max-width: 768px) {
  .my-custom-dialog .p-dialog {
    max-width: 95%;
  }
}

.invalid-field-custom{
    border-color:  #ff0000 !important;
    box-shadow: 0 0 3px rgba(255, 0, 0, 0.4) !important;
}

.error-messages{
    margin-bottom: 0; font-weight: 350; font-size: 15px;
}

.error-messages::before{
    content: "* ";
    color: #ff0000;
}

.form-label-required::after{
    content: " *";
    color: #ff0000;
}
</style>