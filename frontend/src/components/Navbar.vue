<template>
  <div class="navbar">
    <div class="title">
    </div>
    <div class="options">

      <div style="margin-right:20px; display: flex;" class="align-items-center">
        <Avatar icon="pi pi-user" size="medium" style="margin-right: 10px;" shape="circle" />
        <label class="text-white" v-if="userRole != ''">{{ userRole }}</label>
      </div>
    </div>
  </div>
</template>

<script>
import utils from '@/kernel/utils';
import Avatar from 'primevue/avatar';
export default {

  components:{
    Avatar
  },

  data(){
    return{
      options: [
        {
          label: 'Perfil',
          icon: 'pi pi-user',
          command: ()=> {
            this.$router.push('/perfil')
          }
        },
        {
          label: 'Cambiar contraseña',
          icon: 'pi pi-key',
          command: ()=> {
            this.$router.push('/recovery-password')
          }
        },
        {
          label: 'Cerrar sesión',
          icon: 'pi pi-sign-out',
          command: ()=> {
            this.logout()
          }
        }
      ],
      isLogged: false,
      userRole: ''
    }
  },
  methods: {
    async usernameRole(){
      this.userRole = await utils.getRoleNameBytoken()
    }
  },
  mounted() {
    this.usernameRole()
  }

}
</script>

<style scoped>
  .navbar{
    height: 8vh;
    width: 100vw;
    box-shadow: 5px 2px 20px 1px rgba(0, 0, 0, 0.2);
    z-index:2;
    background-color:#2a715a;
    display: flex;
    justify-content: space-between;
  }
  .options{
    position: relative;
  }
  .p-speeddial{
    position: absolute !important;
    z-index: 99;
  }
</style>

<style>
  .p-splitbutton{
    width: 150px !important;
  }
  .p-splitbutton-menubutton:hover{
    background-color: #ffff !important;

  }
  .p-splitbutton-defaultbutton, .p-splitbutton-menubutton{
    background-color: transparent !important;
    width: 80% !important;
    border: gray
  }
  .p-splitbutton-menubutton{
    width: 20% !important;
  }
  .p-highlight{
    background-color: #bae0cd !important;
  }
</style>