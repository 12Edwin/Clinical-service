<template>
    <div style="position: relative;" class="w-100 h-100">
        <b-row>
            <b-col cols="12">
                <Header style="margin-bottom: 20px;" :title="'Catálogos'" />
            </b-col>
            <b-col cols="12">
                <transition-group name="fade">
                    <Loader v-if="isLoading" key="load" />
                    <panel v-else class="w-100 h-100" key="content">
                        <template #header>
                            <div class="d-flex justify-content-between w-100 align-items-center">
                                <p class="h5"><b>Gestión de servicios</b></p>
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
                                    <InputText placeholder="Buscar..." v-model="search" @input="filter(search)" />
                                </span>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col cols="12" md="6" lg="3" v-for="(service, index) in services" :key="index"
                                class="d-flex justify-content-center align-items-center">
                                <Card class="mb-1 mt-2 custom-card">
                                    <template #title>
                                        <div class="d-flex justify-content-center align-items-center">
                                            <h5>{{ service.name }}</h5>
                                        </div>
                                    </template>
                                    <template #content>
                                        <div class="description">
                                            <p>
                                                {{ service.description !== "" ? limitDescription(service.description) :
                    'Sin descripción' }}
                                            </p>
                                            <p style="font-weight: normal; color: black; ">{{ service.price > 0 ? `$${service.price}` : 'Gratuito'}}</p>
                                        </div>
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
                            <b-col cols="1" :style="{ marginTop: '35px' }">
                                <p class="h6"><b>Registros: </b> {{ totalRecords }}</p>
                            </b-col>
                            <b-col>
                                <Paginator :rows="pageable.size" :totalRecords="totalRecords"
                                    :rowsPerPageOptions="[5, 10, 15]" :first="0" :pageLinkSize="1"
                                    :style="{ marginTop: '20px' }" @page="pagination($event)" />
                            </b-col>
                        </b-row>
                    </panel>
                </transition-group>
            </b-col>
            <ConfirmDialog></ConfirmDialog>
        </b-row>
        <ModalUpdateService :visible.sync="displayModal" :service="service"  @pagination="pagination"/>
        <ModalSaveServiceVue :visible.sync="displaySaveModal" />
        <ModalDetailService :visible.sync="displayDetailModal" :service="service"  @pagination="pagination"/>
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
import { decrypt, encrypt } from "@/config/security";
import Header from '@/components/Header.vue';
import Loader from "@/components/loader.vue";
import utils from '@/kernel/utils';
import { onError, onSuccess } from '@/kernel/alerts';
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
        Toast,
        Header,
        Loader
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
                price: '',
                speciality: {
                    id: 0,
                    name: '',
                    description: ''
                }
            },
            pageable: {
                page: 0,
                size: 10
            },
            totalRecords: 0,
            isLoading: false,
            search: '',
            rowsPerPage: 10

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
            }
            try {
                this.isLoading = true
                const { status, data: { result } } = await servicios.get_services(this.pageable)
                if (status === 200 || status === 201) {
                    const decripted = await decrypt(result)
                    const { content, totalElements } = JSON.parse(decripted)
                    this.totalRecords = totalElements
                    this.services = content
                } else {
                    onError('Error', 'Ha ocurrido un error inesperado').then(() => { })
                }
            } catch (error) {}
            this.isLoading = false
        },
        async getServices() {
            const { status, data: { result } } = await servicios.get_services(this.pageable)
            if (status === 200 || status === 201) {
                const decripted = await decrypt(result)
                const { content, totalElements } = JSON.parse(decripted)
                this.totalRecords = totalElements
                this.services = content
            } else {
                onError('Error', 'Ha ocurrido un error inesperado').then(() => { })
            }
            this.isLoading = false
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
                        const { status, data: {text} } = await servicios.delete_service(encodedId)
                        if(status === 400){
                            const message = utils.getErrorMessages(text)
                            onError("Error", message).then(() => { 
                                this.$confirm.close();
                            })
                        }
                        if (status === 200 || status === 201) {
                            this.pagination()
                            onSuccess("¡Éxito!", "¡Servicio eliminado con éxito!");
                        }
                    } catch (error) { }
                    this.isLoading = false
                },
                reject: () => { }
            });
        },
        limitDescription(description) {
            const words = description.split(' ');
            if (words.length === 10 && words.length < 10) {
                return description;
            } else {
                const limitedWords = words.slice(0, 10);
                return limitedWords.join(' ') + '...';
            }
        },
        limitTittle(name) {
            const words = name.split(' ');
            if (words.length === 2 && words.length < 2) {
                return description;
            } else {
                const limitedWords = words.slice(0, 2);
                return limitedWords.join(' ') + '...';
            }
        },
        filter(name) {
            if (name === '') {
                this.pagination()
            } else {
                this.services = utils.filterByName(this.services, name)
            }
        }
    },

    mounted() {
        this.pagination({ page: 0, rows: 10 })
    },
}
</script>

<style scoped>
.p-inputtext {
    width: 550px;
    border-radius: 5px;
}

@media (max-width: 768px) {
    .p-inputtext {
        width: 100%;
    }

}

.p-card {
    width: 100%;
    height: 300px !important;
    border-radius: 10px;
    overflow: hidden;
    padding: 10px;
    box-shadow: 0 0 15px 0 rgba(0, 0, 0, 0.2);
    transition: all 0.3s;
}

.card-custom:hover {
    transform: scale(1.05);
}

.label {
    text-align: justify;
    font-size: 14px;
    font-family: 'Roboto', sans-serif;
    font-weight: 10;
    color: #000;
}

.button-style {
    background: #2a715a;
    border: none;
}

.button-style:hover {
    background-color: #368368 !important;
}

.p-button.p-button-icon-only {
    border-radius: 0;
}

.description {
    font-family: 'Arial', sans-serif;
    font-size: 18px;
    font-weight: normal;
    color: #666;
    margin-top: 0;
    text-align: center;
    line-height: 1.5;
}
</style>