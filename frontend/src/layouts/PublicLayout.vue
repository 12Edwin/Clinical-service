<template>
  <div>
    <Navbar />
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
import {encrypt} from "@/config/security";
export default {
  components: {
    Navbar
  },
  methods:{
    test(){
      const value = {name: 'hola', description: 'hola'}
      encrypt(JSON.stringify(value)).then(r => console.log(r))
    }
  },
  created() {
    this.test()
  }
}
</script>

<style scoped>
.content{
  height: 92vh;
  width: 100vw;
  
}
</style>