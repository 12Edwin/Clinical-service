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
import api from '@/config/http-client.gateway'
export default {
  components: {
    Navbar
  },
}
</script>

<style scoped>
.content{
  height: 92vh;
  width: 100vw;
  
}
</style>