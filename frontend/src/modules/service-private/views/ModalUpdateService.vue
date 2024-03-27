<template>
    <b-row>
        <b-col cols="12">
            <Dialog header="Modificar Servicio" :visible.sync="visible" :containerStyle="{ width: '40vw' }"
                @hide="() => closeModal()" :modal="true" :closeOnEscape="false" :closable="false">
                <div class="p-fluid grid">
                    <b-row>
                        <b-col class="mt-4 mb-2" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-shield" />
                                    <InputText id="field-name" type="text" v-model="service.name"/>
                                    <label for="field-name">Nombre</label>
                                </span>
                            </div>
                        </b-col>
                        <b-col class="mt-3" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-pen" />
                                    <Textarea id="field-description" type="text" rows="3" v-model="service.description"/>
                                    <label for="field-description">Descripción</label>
                                </span>
                            </div>
                        </b-col>
                        <b-col class="mt-3" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-pen" />
                                    <Textarea id="field-description" type="text" rows="3" v-model="service.price"/>
                                    <label for="field-description">Precio</label>
                                </span>
                            </div>
                        </b-col>
                    </b-row>
                </div>
                <template #footer>
                    <b-row>
                        <b-col cols="12">
                            <Button label="Cancelar" icon="pi pi-times" @click="closeModal"
                                class="p-button-rounded p-button-secondary" />
                            <Button label="Actualizar" icon="pi pi-pencil" @click="closeModal"
                                class="p-button-rounded button-style" />
                        </b-col>
                    </b-row>
                </template>
            </Dialog>
        </b-col>
    </b-row>
</template>

<script>
import Dialog from 'primevue/dialog';
import Textarea from "primevue/textarea"
export default {
    props: {
        visible: {
            type: Boolean,
            required: true
        },
        service: {
            type: Object,
            required: true
        }
    },
    components:{
        Dialog,
        Textarea
    },
    name: 'ModalUpdateService',
    methods:{
        closeModal() {
            this.$emit('update:visible', false);
        },
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
</style>