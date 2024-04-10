<template>
  <transition-group name="fade">
    <Loader v-if="isLoading" key="load"/>
    <Panel class="w-100" v-else key="content">
      <template #header>
        <div class="d-flex justify-content-between w-100 align-items-center">
          <p class="h5"><b>Expedientes</b></p>
          <Button class="p-button-rounded p-button-outlined px-2">
            <BIcon icon="plus-circle" scale="2" @click="goToCreateExpedient()"/>
          </Button>
        </div>
      </template>
      <DataTable v-model="expedients" :filters.sync="filters"
                 class="p-datatable-lg w-100"
                 dataKey="id"
                 filterDisplay="row" responsiveLayout="scroll" @page="onPage">
        <template #empty>
          No customers found.
        </template>
        <template #loading>
          Loading customers data. Please wait.
        </template>
        <Column :filterMatchModeOptions="matchModeOptions" field="folio" header="Folio">
          <template #body="{data}">
            <Panel :header="data.folio" collapsed toggleable>
              <div class="mb-1"><span> <b>Nombre: </b> {{ `${data.name} ${data.surname} ${data.lastname}` }}</span>
              </div>
              <div class="mb-1"><span> <b>Teléfono: </b> {{ data.phone }} </span></div>
              <div class="mb-1"><span> <b>Ocupación: </b> {{ data.occupation }} </span></div>
            </Panel>
          </template>
          <template #filter>
            <InputText v-model="filterFolio" :placeholder="`Filtrado por folio`" class="p-column-filter" type="text"
                       @input="findByFolio"/>
          </template>
        </Column>
        <Column :filterMatchModeOptions="matchModeOptions" filterField="email" header="Correo">
          <template #body="{data}">
            <Panel collapsed>
              <template #header>
                <div class="d-flex justify-content-between w-100 align-items-center">
                  <div class="h6"><b>{{ data.email }}</b></div>
                  <Button class="px-3 p-button-outlined px-2" @click="goTo(data.id)"><b class="me-3"> Ver
                    expediente </b>
                    <BIcon icon="box-arrow-in-right" scale="2"/>
                  </Button>
                </div>
              </template>
            </Panel>
          </template>
          <template #filter>
            <InputText v-model="filterEmail" :placeholder="`Filtrado por correo`" class="p-column-filter" type="text"
                       @input="findByEmail"/>
          </template>
        </Column>
        <template #footer>
          <Paginator v-model:first="params.page" :rows="params.size" :rows-per-page-options="[3,5,10]" :total-records="params.total"
                     @page="onPage"></Paginator>
        </template>
      </DataTable>
    </Panel>
  </transition-group>
</template>

<script>
import FilterMatchMode from 'primevue/api/FilterMatchMode';
import DataTable from "primevue/datatable/DataTable";
import Column from "primevue/column/Column";
import ColumnGroup from "primevue/columngroup/ColumnGroup";
import {
  getExpedients,
  getExpedientsByEmail,
  getExpedientsByFolio
} from "@/modules/expedient/services/service-expedient";
import {onError} from "@/kernel/alerts";
import {decrypt, encrypt} from "@/config/security";
import Paginator from "primevue/paginator";
import Loader from "@/components/loader.vue";

export default {
  components: {
    Loader,
    DataTable,
    Column,
    ColumnGroup,
    Paginator
  },
  data() {
    return {
      expedients: [],
      isLoading: true,
      params: {
        page: 0,
        size: 3,
        total: 0,
        sort: 'folio',
        direction: 'asc'
      },
      filterFolio: '',
      filterEmail: '',
      filters: {
        'folio': {value: null, matchMode: FilterMatchMode.CONTAINS},
        'email': {value: null, matchMode: FilterMatchMode.CONTAINS},
      },
      matchModeOptions: [
        {label: 'Contiene...', value: FilterMatchMode.CONTAINS}
      ],
    }
  },

  methods: {

    async onPage(event) {
      this.params.size = event.rows
      this.params.page = event.page
      if (this.filterEmail === '' && this.filterFolio === '') {
        await this.findAll()
      }
      if (this.filterEmail !== '') {
        await this.findByEmail(this.filterEmail)
      }
      if (this.filterFolio !== '') {
        await this.findByFolio(this.filterFolio)
      }
    },

    async findAll() {
      this.isLoading = true
      const {status, data} = await getExpedients(this.params)
      if (status === 400) {
        let message = 'Error al obtener los expedientes'
        await onError('Ocurrió un error', message)
        this.isLoading = false
        return
      }
      const resp = JSON.parse(await decrypt(data.result))
      this.expedients = resp.content
      this.params.page = resp.number
      this.params.total = resp.totalElements
      this.isLoading = false
    },
    goToCreateExpedient(){
      this.$router.push({name: 'create-expedient'})
    },
    async findByEmail(value) {
      if (value === '' || value === null || value === undefined) {
        await this.findAll()
        return
      }
      this.filterFolio = ''
      this.isLoading = true
      const {status, data} = await getExpedientsByEmail(this.params, value)
      if (status === 400) {
        let message = 'Error al filtrar los expedientes'
        await onError('Ocurrió un error', message)
        this.isLoading = false
        return
      }
      const resp = JSON.parse(await decrypt(data.result))
      this.expedients = resp.content
      this.params.page = resp.number
      this.params.total = resp.totalElements
      this.isLoading = false
    },

    async findByFolio(value) {
      if (value === '' || value === null || value === undefined) {
        await this.findAll()
        return
      }
      this.filterEmail = ''
      this.isLoading = true
      const {status, data} = await getExpedientsByFolio(this.params, value)
      if (status === 400) {
        let message = 'Error al filtrar los expedientes'
        await onError('Ocurrió un error', message)
        this.isLoading = false
        return
      }
      const resp = JSON.parse(await decrypt(data.result))
      this.expedients = resp.content
      this.params.page = resp.number
      this.params.total = resp.totalElements
      this.isLoading = false
    },

    async goTo(id) {
      const encrypted = await encrypt(id.toString())
      await this.$router.push({name: 'treatments', params: {idExpedient: encrypted}})
    }
  },
  mounted() {
    this.findAll()
  }
}
</script>

<style>
.fade-enter-active {
  transition: all 1s;
}

.fade-leave-active {
  transition: all .1s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>