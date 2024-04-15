<template>
    <b-row>
        <b-col cols="12">
            <Dialog header="Cambiar contraseña" :visible.sync="visible" :containerStyle="{ width: '40vw' }"
                @hide="() => closeModal()" :modal="true" :closeOnEscape="false" :closable="false">
                <div class="p-fluid grid">
                    <div v-if="!readyTochange">
                        <b-row>
                            <b-col class="mt-4 mb-2" lg="12">
                                <div class="field">
                                    <span class="p-float-label p-input-icon-right">
                                        <i class="pi pi-phone" />
                                        <InputText id="field-phone" type="phone" :readonly="isCatchingNumber"/>
                                        <label for="field-phone" class="form-label-required">Número de teléfono</label>
                                    </span>
                                </div>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col class="mt-4 mb-2" lg="12">
                                <div class="field">
                                    <span class="p-float-label p-input-icon-right">
                                        <i class="pi pi-mobile"/>
                                        <InputText id="field-code" :readonly="!isCatchingNumber"/>
                                        <label for="field-code" class="form-label-required">Código de verificación</label>
                                    </span>
                                </div>
                            </b-col>
                        </b-row>
                    </div>
                    <div v-else>
                        <b-row>
                            <b-col class="mt-4 mb-2" lg="12">
                                <div class="field">
                                    <span class="p-float-label p-input-icon-right">
                                        <i :class="showPassword ? 'pi pi-eye-slash' : 'pi pi-eye'" @click="() => showPassword = !showPassword"/>
                                        <InputText id="field-password" :type="showPassword ? 'text' : 'password'" :readonly="!isCatchingNumber"/>
                                        <label for="field-password" class="form-label-required">Contraseña</label>
                                    </span>
                                </div>
                            </b-col>
                        </b-row>
                    </div>
                </div>
                <template #footer>
                    <b-row>
                        <b-col cols="12">
                            <Button icon="pi pi-times" @click="closeModal()" label="Cancelar"
                                class="p-button-rounded p-button-secondary" />
                            <Button :label="!isCatchingNumber ? 'Enviar código' : 'Guardar'" icon="pi pi-check"
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
import InputText from 'primevue/inputtext'
export default {
    name: 'ChangePassword',
    props: {
        visible: {
            type: Boolean,
            required: true
        }
    },
    components: {
        Dialog,
        InputText
    },
    methods: {
        closeModal() {
            this.$emit('update:visible', false);
        },
    },
    data(){
        return {
            isCatchingNumber: false,
            readyTochange: false,
            showPassword: false
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
.form-label-required::after{
    content: " *";
    color: #ff0000;
}
</style>