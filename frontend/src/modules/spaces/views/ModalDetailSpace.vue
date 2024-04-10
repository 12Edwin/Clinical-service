<template>
    <b-row>
        <b-col cols="12">
            <Dialog header="Detalle de patologias" :visible.sync="visible" :containerStyle="{ width: '40vw' }"
                @hide="() => closeModal()" :modal="true" :closeOnEscape="false" :closable="false">
                <div class="p-fluid grid">
                    <b-row>
                        <b-col class="mt-4 mb-2" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-shield" />
                                    <InputText id="field-name" type="text" v-model="spaceInfo.name" />
                                    <label for="field-name" class="form-label-required">Nombre</label>
                                </span>
                            </div>
                        </b-col>
                        <b-col class="mt-3" lg="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-pen" />
                                    <Textarea id="field-description" type="text" rows="3"
                                        v-model="spaceInfo.description" />
                                    <label for="field-description" class="form-label-required">Descripción</label>
                                    <div class="text-danger text-start pt-2">
                                    </div>
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
                        </b-col>
                    </b-row>
                </template>
            </Dialog>
            <Toast />
        </b-col>
    </b-row>
</template>
<script>

import Dialog from 'primevue/dialog';
import Textarea from "primevue/textarea"
import Toast from 'primevue/toast';

export default {
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
        space: {
            required: true
        }
    },
    data() {
        return {
            spaceInfo: {
                name: '',
                description: ''
            }
        }
    },
    methods: {
        closeModal() {
            const oldspace = JSON.parse(this.space)
            this.spaceInfo.name = oldspace.name
            this.spaceInfo.description = oldspace.description
            this.$emit('update:visible', false);
        }
    },
    watch: {
        space: {
            handler() {
                const oldspace = JSON.parse(this.space)
                this.spaceInfo.name = oldspace.name
                this.spaceInfo.description = oldspace.description
            },
            deep: true
        }

    }
}
</script>

<style></style>