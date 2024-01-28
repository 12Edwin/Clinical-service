<template>
  <div class="navbar">
    <div class="title"></div>
    <div class="options">
    </div>
  </div>
</template>

<script>
export default {

  components:{
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
      ]


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
    background-color: aliceblue;
    display: flex;
    justify-content: space-between;
  }
  .options{
    position: relative;
  }
</style>

<style>
  .p-speeddial{
    position: absolute !important;
    z-index: 99;
  }
</style>