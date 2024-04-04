<template>
  <div class="w-100">
    <b-row>
      <b-col cols="12">
        <panel>
          <template #header>
            <div class="d-flex justify-content-between w-100 align-items-center">
              <p class="h5"><b>Gestión de citas</b></p>
            </div>
          </template>
          <b-row>
            <b-col cols="12">
              <Calendar>
                <!-- template de header -->
                
              </Calendar>
            </b-col>
          </b-row>
        </panel>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import Calendar from "@/modules/appointment/components/Calendar.vue";

export default {
  components:{
    Calendar
  }

}
</script>

<style lang="scss" scoped>

</style>