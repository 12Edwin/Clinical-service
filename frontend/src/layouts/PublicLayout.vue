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
  methods:{
    async test(){
      const value = {
        name: "Carlos",
        surname: "gonzalez",
        lastname: "gonzalez",
        birthday: "2006-03-20",
        phone: "7773531410",
        sex: "Femenino",
        speciality_id: "184",
        code: "0000000002",
        password: "1234"
      }
      let data= ''
      await encrypt(JSON.stringify(value)).then(r => {
        console.log(r);
        data = r
      })
      await api.doPost('/doctor/', data, {headers:{'Content-Type': 'text/plain'}}).then(r => console.log(r))
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