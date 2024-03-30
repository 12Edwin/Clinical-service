<template>
    <div class="w-100">
        <b-row>
            <b-col cols="12">
                <panel>
                    <template #header>
                        <div class="d-flex justify-content-between w-100 align-items-center">
                            <h5 style="font-weight: bolder!important; color: black;">Gestion de Servicios</h5>
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
                            <Card class="mb-1 mt-2 custom-card">
                                <template #header>
                                    <img style="border-radius: 10px 10px; width: 100%; height: 120px!important;"
                                        src="https://picsum.photos/600/300/?image=25"
                                        :alt="`medical-service-${service.name}`">
                                </template>
                                <template #title>
                                    <h3>{{ service.name }}</h3>
                                </template>
                                <template #footer>
                                    <Button icon="pi pi-pencil" class="p-button-rounded button-style"
                                        @click="openModal(service)" v-tooltip.top="'Editar'" />
                                    <Button icon="pi pi-eye" class="p-button-rounded p-button-success"
                                        style="margin-left: .5em" v-tooltip.top="'Detalle'"
                                        @click="openModalDetail(service)" />
                                    <Button icon="pi pi-trash" v-tooltip.top="'Eliminar'"
                                        class="p-button-rounded p-button-secondary" style="margin-left: .5em"
                                        @click="deleteService(service.id)" />
                                </template>
                            </Card>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="1" :style="{ marginTop: '20px' }">
                            <small style="">Registros: </small> {{ totalRecords }}
                        </b-col>
                        <b-col>
                            <Paginator :rows="pageable.size" :totalRecords="totalRecords" :rowsPerPageOptions="[5, 10, 15]"
                                :first="0" :pageLinkSize="1" :style="{ marginTop: '20px' }"
                                @page="pagination($event)" />
                        </b-col>
                    </b-row>
                </panel>
            </b-col>
            <ConfirmDialog></ConfirmDialog>
        </b-row>
        <ModalUpdateService :visible.sync="displayModal" :service="service" />
        <ModalSaveServiceVue :visible.sync="displaySaveModal" />
        <ModalDetailService :visible.sync="displayDetailModal" :service="service" />
    </div>
</template>

<script>
import Card from 'primevue/card';
import Button from 'primevue/button';
import AccordionTab from 'primevue/accordiontab';
import ConfirmDialog from 'primevue/confirmdialog';
import ModalSaveServiceVue from './ModalSaveService.vue'
import ModalUpdateService from './ModalUpdateService.vue';
import ModalDetailService from './ModalDetailService.vue';
import Paginator from 'primevue/paginator';
import Toast from 'primevue/toast';
import servicios from '../service-services/Services';
import { decrypt, encrypt } from "@/config/security"
export default {
    components: {
        Card,
        Button,
        AccordionTab,
        ConfirmDialog,
        ModalSaveServiceVue,
        ModalUpdateService,
        ModalDetailService,
        Paginator,
        Toast
    },
    data() {
        return {
            services: [],
            displayModal: false,
            displaySaveModal: false,
            displayDetailModal: false,
            service: {
                name: '',
                description: '',
                price: ''
            },
            pageable: {
                page: 0,
                size: 10
            },
            totalRecords: 0
        };
    },
    methods: {
        openModalSaveService() {
            this.displaySaveModal = true;
        },
        openModal(service) {
            this.displayModal = true;
            this.service = JSON.stringify(service)
        },
        openModalDetail(service) {
            this.displayDetailModal = true;
            this.service = JSON.stringify(service)
        },

        async pagination(event) {
            if (event != undefined) {
                const { page, rows } = event;
                this.pageable.page = page;
                this.pageable.size = rows;
                this.rowsPerPage = rows;
            }
            try {
                const { status, data: { result } } = await servicios.get_services(this.pageable)
                if (status === 200 || status === 201) {
                    const decripted = await decrypt(result)
                    const { content, totalElements } = JSON.parse(decripted)
                    this.totalRecords = totalElements
                    this.services = content
                }
            } catch (error) { }

        },
        deleteService(serviceId) {
            this.$confirm.require({
                message: '¿Está seguro de eliminar este servicio?',
                header: 'Confirmación',
                icon: 'pi pi-info-circle',
                acceptLabel: 'Sí',
                acceptClass: 'p-button-danger',
                accept: async () => {
                    try {
                        const encodedId = await encrypt(serviceId)
                        const { status } = await servicios.delete_service(encodedId)
                        if (status === 200 || status === 201) {
                            this.pagination()
                            this.$toast.add({ severity: 'success', summary: 'Éxito', detail: 'Especialidad eliminada correctamente', life: 3000 });
                        }
                    } catch (error) { }
                },
                reject: () => { }
            });
        },
    },
    mounted() {
        this.pagination();
    },

};
</script>

<style scoped>
.p-card {
    width: 100%;
    height: 400px !important;
    border-radius: 10px;
    overflow: hidden;
    padding: 10px;
    box-shadow: 0 0 15px 0 rgba(0, 0, 0, 0.2);
    transition: all 0.3s;
}

.custom-card:hover {
    transform: scale(1.05);
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

h3 {
    font-weight: bolder !important;
    font-family: Arial, Helvetica, sans-serif;
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