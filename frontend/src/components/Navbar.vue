<template>
  <div class="navbar">
    <div class="title">
    </div>
    <div class="options">

      <div style="margin-right:20px; display: flex;">
        <SplitButton 
          :label="usernameRole()" 
          :model="options" 
          class="p-splitbutton p-button-rounded p-button-secondary mb-1"
        ></SplitButton>
      </div>
    </div>
  </div>
</template>

<script>
import SplitButton from 'primevue/splitbutton'
import utils from '@/kernel/utils';
export default {

  components:{
    SplitButton
  },

  data(){
    return{
      items: [
        {
          label: 'Add',
          icon: 'pi pi-pencil',
          command: () => {
            this.$toast.add({ severity: 'info', summary: 'Add', detail: 'Data Added' });
          }
        },
        {
          label: 'Update',
          icon: 'pi pi-refresh',
          command: () => {
            this.$toast.add({ severity: 'success', summary: 'Update', detail: 'Data Updated' });
          }
        },
        {
          label: 'Delete',
          icon: 'pi pi-trash',
          command: () => {
            this.$toast.add({ severity: 'error', summary: 'Delete', detail: 'Data Deleted' });
          }
        },
        {
          label: 'Upload',
          icon: 'pi pi-upload',
          command: () => {
            window.location.hash = "/fileupload"
          }
        },
        {
          label: 'Vue Website',
          icon: 'pi pi-external-link',
          command: () => {
            window.location.href = 'https://vuejs.org/'
          }
        }
      ],
      options: [
        {
          label: 'Perfil',
          icon: 'pi pi-user',
          command: ()=> {
            this.$router.push('/perfil')
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
    logout(){
      utils.removeToken()
      this.$router.push('/login')
    },
    usernameRole(){
      return utils.getRoleNameBytoken().toLowerCase()
    }
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