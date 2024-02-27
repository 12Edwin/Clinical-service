<template>
  <div>
    <div class="card">
      <br><br><br>
      <div class="mt-3">
        <h1>Doctores</h1>
      </div>
      <div class="flex flex-wrap align-items-rigth gap-4 mb-4">
        <span class="text-lg font-medium">Busca por: </span>
        <InputText v-model="filter" placeholder="Nombre, especialización" class="w-full md:w-4" />
      </div>
      <DataTable :value="filteredDoctors" :paginator="true" :rows="3" :sort-mode="'multiple'" class="datatable-custom">
        <template #header>
          <div class="flex flex-wrap align-items-center justify-content-between gap-2">
            <span class="text-xl text-900 font-bold">Doctores</span>
            <Button icon="pi pi-refresh" rounded raised />
          </div>
        </template>
        <Column field="name" header="Nombre" :sortable="true"></Column>
        <Column header="Foto">
          <template #body="slotProps">
            <div class="flex items-center">
              <img width="90px" style="border-radius: 10px;" :src="slotProps.data.image" :alt="slotProps.data.name"
                class="w-12 h-12 border-round ml-2" />
            </div>
          </template>
        </Column>
        <Column header="Status" :sortable="true">
          <template #body="slotProps">
            <Tag :value="slotProps.data.status" :severity="getActivity(slotProps.data)" />
          </template>
        </Column>
        <Column field="specialty" header="Especialización" :sortable="true"></Column>
        <Column field="location" header="Zona" :sortable="true"></Column>
        <Column field="" header="">
          <Button type="button" label="Borrar" icon="pi trash" />
          <Button type="button" label="Editar" icon="pi pencil" />
        </Column>
        <template #footer>
          In total there are {{ doctors ? doctors.length : 0 }} doctors.
        </template>
      </DataTable>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Button from 'primevue/button';
import Tag from 'primevue/tag';
import InputText from 'primevue/inputtext';

export default {
  components: {
    DataTable,
    Column,
    Button,
    Tag,
    InputText
  },
  setup() {
    const doctors = ref([
      { id: 1, name: 'Dr. Juan Pérez', specialty: 'Pediatra', location: 'Ciudad de México', status: 'active', image: 'https://www.shutterstock.com/image-photo/healthcare-medical-staff-concept-portrait-600nw-2281024823.jpg' },
      { id: 2, name: 'Dra. María García', specialty: 'Dermatóloga', location: 'Madrid', status: 'inactive', image: 'https://www.shutterstock.com/image-photo/profile-photo-attractive-family-doc-600nw-1724693776.jpg' },
      { id: 3, name: 'Dr. Roberto López', specialty: 'Cardiólogo', location: 'Buenos Aires', status: 'active', image: 'https://www.shutterstock.com/image-photo/man-portrait-doctor-wearing-white-600nw-2278090533.jpg' },
      { id: 4, name: 'Dr. Juan Pérez', specialty: 'Pediatra', location: 'Ciudad de México', status: 'active', image: 'https://www.shutterstock.com/image-photo/healthcare-medical-staff-concept-portrait-600nw-2281024823.jpg' },
      { id: 5, name: 'Dra. María García', specialty: 'Dermatóloga', location: 'Madrid', status: 'inactive', image: 'https://www.shutterstock.com/image-photo/profile-photo-attractive-family-doc-600nw-1724693776.jpg' },
      { id: 6, name: 'Dr. Roberto López', specialty: 'Cardiólogo', location: 'Buenos Aires', status: 'active', image: 'https://www.shutterstock.com/image-photo/man-portrait-doctor-wearing-white-600nw-2278090533.jpg' }
    ]);

    const filter = ref('');

    const filteredDoctors = computed(() => {
      const query = filter.value.toLowerCase();
      return doctors.value.filter(doctor =>
        doctor.name.toLowerCase().includes(query) ||
        doctor.specialty.toLowerCase().includes(query)
      );
    });

    const getActivity = (doctor) => {
      switch (doctor.status) {
        case 'active':
          return 'success';
        case 'inactive':
          return 'danger';
        default:
          return null;
      }
    };

    return {
      doctors,
      filter,
      filteredDoctors,
      getActivity
    };
  }
};
</script>

<style scoped>
.datatable-custom {
  width: 95%;
}
</style>
