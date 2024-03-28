<template>
    <div class="w-100">
        <b-row>
            <b-col cols="12">
                <panel>
                    <template #header>
                        <div class="d-flex justify-content-between w-100 align-items-center">
                            <p class="h5"><b>Gestión de Servicios</b></p>
                            <Button class="p-button-rounded p-button-outlined px-2" @click="openModalSaveService()">
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
                        <b-col sm="6" md="4" lg="4" v-for="(service, index) in services" :key="index" class="mt-4">
                            <Card class="custom-card">
                                <template #header>
                                    <img :src="require(`@/assets/${service.image}`)" class="image" alt="">
                                </template>
                                <template #title>
                                    <h2>{{ service.title }}</h2>
                                </template>
                                <template #content>
                                    <p>{{ service.description }}</p>
                                    <p>{{ service.price }}</p>
                                </template>
                                <template #footer>
                                    <Button icon="pi pi-pencil" class="p-button-rounded button-style"
                                        @click="openModal(service)" />
                                    <Button icon="pi pi-trash" class="p-button-rounded p-button-secondary"
                                        style="margin-left: .5em" @click="deleteSpeciality()" />
                                </template>
                            </Card>
                        </b-col>
                    </b-row>
                </panel>
            </b-col>
            <ConfirmDialog></ConfirmDialog>
        </b-row>
        <ModalUpdateServiceVue :visible.sync="displayModal" :service="service" />
        <ModalSaveServiceVue :visible.sync="displaySaveModal" y></ModalSaveServiceVue>
    </div>
</template>

<script>
import Card from 'primevue/card';
import Button from 'primevue/button';
import AccordionTab from 'primevue/accordiontab';
import ConfirmDialog from 'primevue/confirmdialog';
import ModalSaveServiceVue from '@/modules/service-private/views/ModalSaveService.vue'
import ModalUpdateServiceVue from '@/modules/service-private/views/ModalUpdateService.vue'
import servicios from './service-services/Services';
export default {
    data() {
        return {
            services: [
                {
                    title: "Urgencias",
                    description: "Los servicios de urgencias tienen como misión la prestación de la atención sanitaria urgente a las personas que la demanden, en el tiempo adecuado.",
                    image: "img/urgencias.jpg",
                    price: "$4000"
                },
                {
                    title: "Intervenciones Quirúrgicas",
                    description: "Es la operación instrumental, total o parcial, de lesiones causadas por enfermedades o accidentes, con fines diagnósticos, de tratamiento o de rehabilitación de secuelas.",
                    image: "img/intervenciones.jpg",
                    price: "$4000"
                },
                {
                    title: "Atención del embarazo parto y puerperio.",
                    description: "El embarazo, parto y puerperio constituye un proceso por el que atraviesan las mujeres que deciden tener hijos a lo largo de su vida reproductiva.",
                    image: "img/parto.jpg",
                    price: "$4000"
                },
                {
                    title: "Unidad de Cuidados Especiales Neonatales.",
                    description: "Unidad Médica destinada a atender a todo recién nacido (0 a 28 días de vida) con cualquier proceso mórbido o enfermedad que ponga en peligro su vida.",
                    image: "img/cuidado.jpg",
                    price: "$4000"
                },
                {
                    title: "Vacunas",
                    description: " La vacunación es una forma sencilla, inocua y eficaz de protegernos contra enfermedades dañinas antes de entrar en contacto con ellas.",
                    image: "img/vacuna.jpg",
                    price: "$4000"
                },
                {
                    title: "Exámenes de audición y visión.",
                    description: "Las pruebas de audición miden qué tan bien escucha. Pueden ayudar a diagnosticar pérdida de la audición, qué tan severa es y qué parte de su audición no está funcionando bien.",
                    image: "img/vision.jpg",
                    price: "$4000"
                },
            ],
            displayModal: false,
            displaySaveModal: false,
            service: {
                name: '',
                description: '',
                price: ''
            }
        };
    },
    components: {
        Card,
        Button,
        AccordionTab,
        ConfirmDialog,
        ModalSaveServiceVue,
        ModalUpdateServiceVue
    },
    mounted() {
        this.getServices();
    },
    methods: {
        openModalSaveService() {
            this.displaySaveModal = true;
        },
        openModal(service) {
            this.displayModal = true;
            this.service = service;
        },

        async getServices() {
            try {
                const { data, status } = await servicios.get_services();
                if (status === 200 || status === 201) {
                    // console.log("response de servicios", data.result);
                }

            } catch (error) {
                console.log("error en la peticion", error);
            }
        }

    }
};
</script>

<style scoped>
.custom-card {
    border: 1px solid #ccc;
    border-radius: 10px 10px !important;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    height: 100%;
    background-color: #fff;
}

.image {
    height: 200px !important;
    width: 95% !important;
    margin-top: 15px;
    border-radius: 10px 10px;
}

h1 {
    font-weight: bolder !important;
    font-family: Arial, Helvetica, sans-serif;
    color: black !important;
}

h2 {
    font-weight: bolder !important;
    font-family: Arial, Helvetica, sans-serif;
    margin-top: 30px !important;
    color: black !important;
}

p {
    font-family: Arial, Helvetica, sans-serif;
    font-weight: 300 !important;
    padding: 30px;
    font-size: larger !important;
    color: black !important;
}

.back {
    padding: 20px;
}

Button {
    margin: 30px;
    background-color: #2a715a;
    size: 50px !important;
}
</style>