<template>
  <div class="w-100">
    <DoctorsList/>
  </div>
</template>

<script>
import DoctorsList from '../components/DoctorsList.vue'
export default {
  components:{
    DoctorsList
  },
  data(){
    return {
      
    }
  }
}
</script>
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
