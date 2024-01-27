<template>
  <div class="home">
    <Button label="Primary" class="p-button-outlined" />  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

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
