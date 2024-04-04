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
      
  
  <style scoped>
  .datatable-custom {
    width: 95%;
  }
  </style>
  