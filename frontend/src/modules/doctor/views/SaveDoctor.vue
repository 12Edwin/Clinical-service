<template>
    <panel class="shadow shadow-lg rounded">
        <template #header>
            <h5 class="d-flex w-100">Registro de doctores
            </h5>
        </template>
        <div class="p-fluid grid">
            <div class="row">
                <div class="col d-flex align-items-lelft">
                    <h5 class="border-none pb-2 mb-1">Datos personales</h5>
                </div>
            </div>
            <b-row>
                <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                    <div class="field">
                        <span class="p-float-label p-input-icon-right">
                            <i class="pi pi-user" />
                            <InputText id="field-name" type="text"/>
                            <label for="field-name">Nombre</label>
                        </span>
                    </div>
                </b-col>
                <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                    <div class="field">
                        <span class="p-float-label p-input-icon-right">
                            <i class="pi pi-user" />
                            <InputText id="inputtext-right" type="text"/>
                            <label for="inputtext-right">Apellido paterno</label>
                        </span>
                    </div>
                </b-col>
                <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                    <div class="field">
                        <span class="p-float-label p-input-icon-right">
                            <i class="pi pi-user" />
                            <InputText id="inputtext-right" type="text"/>
                            <label for="inputtext-right">Apellido materno</label>
                        </span>
                    </div>
                </b-col>
            </b-row>
            <b-row>
                <b-col class="mt-4" cols="12" md="6" lg="4" >
                    <div class="field">
                        <span class="p-float-label p-input-icon-right">
                            <i class="pi pi-id-card" />
                            <InputText id="inputtext-right" type="text"/>
                            <label for="inputtext-right">CURP</label>
                        </span>
                    </div>
                </b-col>
                <b-col class="mt-4" cols="12" md="6" lg="4">
                    <span class="p-float-label p-input-icon-right">
                        <i class="pi pi-calendar" />
                        <Calendar id="calendar" icon="pi pi-calendar" :showIcon="true"/>
                        <label for="calendar">Fecha de nacimiento</label>
                    </span>
                </b-col>
                <b-col class="mt-4">
                    <span class="p-float-label p-input-icon-right">
                        <i class="pi pi-id-card" />
                        <InputText id="inputtext-right" type="text"/>
                        <label for="inputtext-right">RFC</label>
                    </span>
                </b-col>
            </b-row>
            <b-row>
                <b-col class="d-flex align-items-lelft mt-4">
                    <h5 class="border-none pb-2 mb-2">Dirección</h5>
                </b-col>
            </b-row>
            <b-row>
                <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                    <div class="field">
                        <span class="p-float-label p-input-icon-right">
                            <i class="pi pi-map" />
                                <InputText id="inputtext-right" type="text"/>
                            <label for="inputtext-right">Calle</label>
                        </span>
                    </div>
                </b-col>
                <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                    <div class="field">
                        <span class="p-float-label p-input-icon-right">
                            <i class="pi pi-map-marker" />
                                <InputText id="inputtext-right" type="text"/>
                            <label for="inputtext-right">Colonia</label>
                        </span>
                    </div>
                </b-col>
                <b-col class="mt-3" cols="12" md="6" lg="4" sm="12">
                    <div class="field">
                        <span class="p-float-label p-input-icon-right">
                            <i class="pi pi-home" />
                                <InputNumber id="inputnumber" :useGrouping="false"/>
                            <label for="inputtext-right">Codigo postal</label>
                        </span>
                    </div>
                </b-col>
                <b-col class="mt-4" cols="12" md="6" lg="6" sm="12">
                    <div class="field">
                        <span class="p-float-label p-input-icon-right">
                            <i class="pi pi-home" />
                                <InputNumber id="inputnumber" :useGrouping="false"/>
                            <label for="inputtext-right">Número interior</label>
                        </span>
                    </div>
                </b-col>
                <b-col class="field col-6 md:col-4 mt-4">
                    <span class="p-float-label p-input-icon-right">
                        <i class="pi pi-hashtag" />
                        <InputNumber id="inputnumber" :useGrouping="false"/>
                        <label for="inputnumber">Número exterior</label>
                    </span>
                </b-col>
            </b-row>
            <b-row>
                <b-col class="d-flex align-items-lelft mt-4">
                    <h5 class="border-none pb-2">Información profesional</h5>
                </b-col>
            </b-row>
            <b-row>
                <b-col>

                </b-col>
            </b-row>
            <b-row>
                <b-col class="d-flex align-items-lelft mt-4">
                    <h5 class="border-none pb-2">Contacto</h5>
                </b-col>
            </b-row>
            <b-row>
                <b-col class="mt-2" cols="12" md="6" lg="4">
                    <div class="field">
                        <span class="p-float-label p-input-icon-right">
                            <i class="pi pi-phone" />
                            <InputNumber id="inputnumber" :useGrouping="false"/>
                            <label for="inputnumber">Número de teléfono</label>
                        </span>
                    </div>
                </b-col>
            </b-row>
        </div>
    </panel>
</template>

<script>
import Calendar from 'primevue/calendar';
import InputNumber from 'primevue/inputnumber';
import FileUpload from 'primevue/fileupload';
export default {
    components: {
        Calendar,
        InputNumber
    },
    data(){
        return{
            doctor: {
                name: '',
                lastName: '',
                secondLastName: '',
                curp: '',
                birthDate: ''
            }
        }
    }

}
</script>
<!-- rgb(0 175 245) -->
<style></style>