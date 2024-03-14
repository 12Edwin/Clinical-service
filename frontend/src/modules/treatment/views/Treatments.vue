<template>
  <div style="position: relative">
    <transition-group name="fade">
        <loader v-show="isLoading" key="load"/>
        <div v-show="!isLoading" key="main">
          <div class="head-title"></div>
          <b-row>
            <b-col lg="5" md="12" class="mt-4">
              <div class="left"></div>
            </b-col>
            <b-col lg="7" md="12" class="mt-4">
              <CardTreatment/>
            </b-col>
          </b-row>
        </div>
    </transition-group>

  </div>
</template>

<script>
import CardTreatment from "@/modules/treatment/views/CardTreatment.vue";
import Loader from "@/components/loader.vue";

export default {
  components: {Loader, CardTreatment},
  data(){
    return{
      isLoading: true
    }
  },

  created() {
    //setTimeout(()=> this.isLoading = false, 5000)
  }
}
</script>

<style scoped>
  .left{
    padding: 20px;
    border-radius: 5px;
    box-shadow: rgba(0, 0, 0, 0.2) 0 8px 12px 0;
    background-color: white;
    height: 100%;
    text-align: left;
  }

  .head-title{
    padding: 20px;
    border-radius: 5px;
    box-shadow: rgba(0, 0, 0, 0.2) 0 8px 12px 0;
    background-color: white;
    height: 100%;
    text-align: left;
  }
</style>