<template>
  <div class="home">
    <Button label="Primary" class="p-button-outlined" />  </div>
</template>

<script>
export default {
  name: 'HomeView',
  components: {
    Button: ()=> import ('primevue/button')
  },
  data(){
    return{
      isLoading: true
    }
  }
}
</script>