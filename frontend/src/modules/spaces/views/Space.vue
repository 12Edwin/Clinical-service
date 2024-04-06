<template>
    <div class="w-100">
        <b-row>
            <b-col cols="12">
                <panel>
                    <template #header>
                        <div class="d-flex justify-content-between w-100 align-items-center">
                            <h5>Gestion de Espacios</h5>
                            <Button class="p-button-rounded p-button-outlined px-2" @click="openModalSaveSpace()">
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
                        <b-col cols="12" md="6" lg="3" v-for="(space, index) in spaces" :key="index"
                            class="d-flex justify-content-center align-items-center">
                            <Card class="mb-1 mt-2 custom-card">
                                <template #title>
                                    <div class="d-flex justify-content-center align-items-center">
                                        {{ space.name }}
                                    </div>
                                    <p style="font-weight: normal; color: black; padding-top: 10px;">
                                        {{ space.description }}
                                    </p>
                                </template>
                                <template #content>
                                    <Button icon="pi pi-pencil" class="p-button-rounded button-style"
                                        @click="openModal(space)" v-tooltip.top="'Editar'" />
                                    <Button icon="pi pi-eye" class="p-button-rounded p-button-success"
                                        style="margin-left: .5em" v-tooltip.top="'Detalle'"
                                        @click="openModalDetail(space)" />
                                    <Button icon="pi pi-trash" v-tooltip.top="'Eliminar'"
                                        class="p-button-rounded p-button-secondary" style="margin-left: .5em"
                                        @click="deletePathology(space.id)" />
                                </template>
                            </Card>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="1" :style="{ marginTop: '20px' }">
                            <small style="">Registros: </small> {{ totalRecords }}
                        </b-col>
                        <b-col>
                            <Paginator :rows="pageable.size" :totalRecords="totalRecords"
                                :rowsPerPageOptions="[5, 10, 15]" :first="0" :pageLinkSize="1"
                                :style="{ marginTop: '20px' }" @page="pagination($event)" />
                        </b-col>
                    </b-row>
                </panel>
            </b-col>
            <ConfirmDialog></ConfirmDialog>
        </b-row>
        <ModalDetailSpace :visible.sync="displayDetailModal" :space="space" />
        <ModalSaveSpace :visible.sync="displaySaveModal" />
    </div>
</template>

<script>
import Card from 'primevue/card';
import Button from 'primevue/button';
import AccordionTab from 'primevue/accordiontab';
import ConfirmDialog from 'primevue/confirmdialog';
import Paginator from 'primevue/paginator';
import Toast from 'primevue/toast';
import spaceService from '../services/spaces-services'
import ModalDetailSpace from './ModalDetailSpace.vue'
import ModalSaveSpace from './ModalSaveSpace.vue'
import { decrypt } from "@/config/security"
export default {
    components: {
        Card,
        Button,
        AccordionTab,
        ConfirmDialog,
        Paginator,
        Toast,
        ModalDetailSpace,
        ModalSaveSpace
    },
    data() {
        return {
            spaces: [],
            displayModal: false,
            displaySaveModal: false,
            displayDetailModal: false,
            space: {
                name: '',
                description: '',
                busy_spaces: ''
            },
            pageable: {
                page: 0,
                size: 10
            },
            totalRecords: 0
        };
    },
    methods: {
        openModalSaveSpace() {
            this.displaySaveModal = true;
        },
        openModal(space) {
            this.displayModal = true;
            this.space = JSON.stringify(space)
        },
        openModalDetail(space) {
            this.displayDetailModal = true;
            this.space = JSON.stringify(space)
        },

        async pagination(event) {
            if (event != undefined) {
                const { page, rows } = event;
                this.pageable.page = page;
                this.pageable.size = rows;
                this.rowsPerPage = rows;
            }
            try {
                const { status, data: { result } } = await spaceService.getAllSpaces(this.pageable)
                if (status === 200 || status === 201) {
                    const decripted = await decrypt(result)
                    const { content, totalElements } = JSON.parse(decripted)
                    this.totalRecords = totalElements
                    this.spaces = content
                    console.log(this.spaces);
                }
            } catch (error) { }

        },
        // deletePathology(pathologyId) {
        //     this.$confirm.require({
        //         message: '¿Está seguro de eliminar esta patología?',
        //         header: 'Confirmación',
        //         icon: 'pi pi-info-circle',
        //         acceptLabel: 'Sí',
        //         acceptClass: 'p-button-danger',
        //         accept: async () => {
        //             try {
        //                 const encodedId = await encrypt(pathologyId)
        //                 const { status } = await pathologyService.delete_Pathology(encodedId)
        //                 if (status === 200 || status === 201) {
        //                     this.pagination()
        //                     this.$toast.add({ severity: 'success', summary: 'Éxito', detail: 'Especialidad eliminada correctamente', life: 3000 });
        //                 }
        //             } catch (error) { }
        //         },
        //         reject: () => { }
        //     });
        // },
        // limitDescription(description) {
        //     const words = description.split(' ');
        //     if (words.length === 8 && words.length < 8) {
        //         return description;
        //     } else {
        //         const limitedWords = words.slice(0, 8);
        //         return limitedWords.join(' ') + '...';
        //     }
        // }
    },
    mounted() {
        this.pagination()
    },
};
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

.custom-card:hover {
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
</style>