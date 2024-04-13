<template>
  <div>
    <div class="nav-shape" v-if="!isLogin">
      <Menubar :model="navbarItems" class="nav-content"
               style="background-color: white; justify-content: center; border: none; position: fixed; top: 0; width: 100%; z-index: 1000; font-weight: bold;"/>
    </div>
    <div class="content" :style="isLogin && [{'padding-top': 0}, {height: '100%'}]">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Menubar from "primevue/menubar";

export default {
  components: {Menubar},
  data() {
    return {
      navbarItems: [
        {
          label: 'Hospital San J',
          to: "/home",
        },
        {
          label: 'Blog',
          to: '/blog'
        },
        {
          label: 'Servicios',
          to: '/services'
        },
        {
          label: 'Nuestros Doctores',
          to: "/our_doctors"
        },
        {
          label: '',
          icon: 'pi pi-fw pi-user',
          to: "/login",
          end: true // this will align the item to the right
        },
      ],
    }
  },
  computed:{
    isLogin(){
      return this.$route.path.includes('login')
    }
  }
}
</script>

<style scoped>
.content {
  padding-top: 30px;
  width: 100%;
  overflow: auto;
  height: 92vh;
  background-color: white;
}

.nav-content {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  height: 8vh;
}

.nav-shape {
  height: 8vh;
  width:100vw;
  background-color: white;
}

</style>