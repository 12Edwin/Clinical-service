<template>
    <div>
        <Menubar :model="navbarItems"
      style="background-color: white; justify-content: center; border: none; position: fixed; top: 0; width: 100%; z-index: 1000; font-weight: bold;" />
    </div>
</template>

<script >
import Menubar from 'primevue/menubar/Menubar';

export default {
    
    components:{
        Menubar
    },
    data(){
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
    }

}
</script>